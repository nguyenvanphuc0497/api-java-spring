package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.messager.Messager
import com.capstone1.tutoryapi.entities.messager.MessagerMapper
import com.capstone1.tutoryapi.entities.messager.ThreadMessage
import com.capstone1.tutoryapi.entities.messager.ThreadMessageMapper
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.dialogflow.v2.*
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import org.json.JSONObject
import org.springframework.core.io.ClassPathResource
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@Repository
class ThreadMessageDAO : BaseDAO() {

    internal fun getAllProfilesForThreadByIdSender(idProfile: String?): List<ThreadMessage> {
//        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER , up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
//                "FROM thread_messager AS tm INNER JOIN user_profile AS up " +
//                "WHERE tm.RECEIVER_IDPROFILE = up.ID_PROFILE AND( " +
//                "tm.SENDER_IDPROFILE = $idProfile OR tm.RECEIVER_IDPROFILE = $idProfile ) "
        //Fix error get info profile not correct
        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER, up.ID_PROFILE AS ID_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = '$idProfile' " +
                "UNION SELECT tm.*, up.NAME AS NAME_RECEIVER, up.ID_PROFILE AS ID_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = '$idProfile' "
        return jdbcTemplate.query(sql, ThreadMessageMapper())
    }

    internal fun viewMessageByIdThread(idThread: String?): List<Messager> {
        val sql = "SELECT m.* ,tm.SENDER_IDPROFILE FROM ${EntitiesTable.message} AS m INNER JOIN ${EntitiesTable.threadMessage} AS tm " +
                "ON m.ID_THREAD = tm.ID_THREAD WHERE m.ID_THREAD = '$idThread'"
        return jdbcTemplate.query(sql, MessagerMapper())
    }

    internal fun createMessageByIdThread(idProfileSender: String?, idThread: String?, message: String?): Int {
        val sqlSelect = "SELECT * FROM ${EntitiesTable.threadMessage} WHERE SENDER_IDPROFILE = '$idProfileSender' AND ID_THREAD = '$idThread' " +
                "UNION SELECT * FROM ${EntitiesTable.threadMessage} WHERE RECEIVER_IDPROFILE = '$idProfileSender' AND ID_THREAD = '$idThread' LIMIT 1"

        try {
            var count: Int? = 0
            jdbcTemplate.query(sqlSelect) {
                if (it.getString("SENDER_IDPROFILE") == idProfileSender.toString().trim()) {
                    count = 1
                }
            }
            val isSender = if (count == 1) {
                1
            } else {
                0
            }
            val sql = "INSERT INTO `${EntitiesTable.message}` (`ID_THREAD`, `MESSAGECOL`, `IS_SENDER`) VALUES ('$idThread', '$message', '$isSender')"
            return jdbcTemplate.update(sql).apply {
                if (this@apply == 1) {
                    pushNotificationToDeviceForProfile(idProfileSender, idThread, message)
                }
            }
        } catch (ex: Exception) {
            return 0
        }
    }

    @Throws(Exception::class)
    internal fun createMessageWithAIByIdThread(idProfileSender: String?, idThread: String?, message: String?): Int {
        if (idThread.toString().isNotBlank()) {
            val messageOfBoot = callDialogFlow(idProfileSender.toString(), idThread, sessionId = idProfileSender.toString(), message = message.toString())
            Thread(Runnable {
                var fcmTokenReceiver: String? = ""
                jdbcTemplate.query("SELECT FCM_TOKEN_DEVICE FROM ${EntitiesTable.userProfile} WHERE ID_PROFILE = $idProfileSender") {
                    fcmTokenReceiver = it.getString("FCM_TOKEN_DEVICE") ?: ""
                }
                pushNotificationByFCM(fcmTokenReceiver, "BOT", messageOfBoot)
            }).start()

            Thread(Runnable {
                insertMessageToDB(idThread.toString(), message, 1)
                insertMessageToDB(idThread.toString(), messageOfBoot, 0)
            }).start()

            return 1
        }
        return 0
    }

    @Throws(Exception::class)
    internal fun callDialogFlow(idProfileSender: String, idThread: String?, projectId: String = EntitiesTable.PROJECT_DIALOG_FLOW, sessionId: String,
                                message: String): String? {
        val credentials = GoogleCredentials.fromStream(ClassPathResource("tutor-api.json").inputStream)
        val sessionsSettings = SessionsSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build()
        // Instantiates a client
        SessionsClient.create(sessionsSettings).use { sessionsClient ->
            // Set the session name using the sessionId and projectID
            val session = SessionName.of(projectId, sessionId)

            // Set the text and language code (en-US) for the query
            val textInput = TextInput.newBuilder().setText(message).setLanguageCode("en")

            // Build the query with the TextInput
            val queryInput = QueryInput.newBuilder().setText(textInput).build()

            // Performs the detect intent request
            val response = sessionsClient.detectIntent(session, queryInput)

            // Display the query result
            val queryResult = response.queryResult

            println("====================")
            System.out.format("Query Text: '%s'\n", queryResult.queryText)
            System.out.format("Detected Intent: %s (confidence: %f)\n",
                    queryResult.intent.displayName, queryResult.intentDetectionConfidence)
            System.out.format("Fulfillment Text: '%s'\n", queryResult.fulfillmentText)
            return queryResult.fulfillmentText
        }
    }

    internal fun getIdThreadByProfileSenderAndProfileReceiver(idProfileSender: String?, idProfileReceiver: String?): String? {
        var result: String? = null
        val sql = "SELECT tm.ID_THREAD FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = '$idProfileSender' AND tm.RECEIVER_IDPROFILE =  '$idProfileReceiver'" +
                "UNION SELECT tm.ID_THREAD FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = '$idProfileSender' AND tm.SENDER_IDPROFILE = '$idProfileReceiver' LIMIT 1"
        jdbcTemplate.query(sql) {
            result = it.getString("ID_THREAD") ?: ""
        }
        return result
    }

    @Throws(DataAccessException::class, MySQLIntegrityConstraintViolationException::class)
    internal fun createNewThread(idProfileSender: String?, idProfileReceiver: String?): Int {
        var count = -1
        var result = -1
        val sqlInsert = "INSERT INTO `${EntitiesTable.threadMessage}`( `SENDER_IDPROFILE`, `RECEIVER_IDPROFILE`) VALUES ('$idProfileSender','$idProfileReceiver')"
        try {
            count = jdbcTemplate.update(sqlInsert)
            result = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID() AS ID_THREAD", Int::class.java) ?: 0
            println(result)
            return if (count == 1) {
                result
            } else {
                0
            }
        } catch (ex: Exception) {
            //TODO:SOMETHING
        } catch (eSql: SQLException) {
            println(eSql.message.toString())
        }
        return if (count == 1) {
            result
        } else {
            0
        }
    }

    private fun pushNotificationToDeviceForProfile(idProfileSender: String?, idThread: String?, message: String?): HttpEntity<String> {
        var nameSender = ""
        var tokenDevice = ""

        val sql = "SELECT tm.ID_THREAD, up.* " +
                "FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = $idProfileSender AND tm.ID_THREAD = $idThread " +
                "UNION SELECT tm.ID_THREAD, up.* " +
                "FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = $idProfileSender AND tm.ID_THREAD = $idThread LIMIT 1"
        jdbcTemplate.query(sql) {
            tokenDevice = it.getString("FCM_TOKEN_DEVICE")
        }

        jdbcTemplate.query("SELECT NAME FROM ${EntitiesTable.userProfile} WHERE ID_PROFILE = $idProfileSender") {
            nameSender = it.getString("NAME")
        }

        return pushNotificationByFCM(tokenDevice, nameSender, message)
    }

    private fun pushNotificationByFCM(tokenDevice: String?, nameSender: String?, message: String?): HttpEntity<String> {
        val body = JSONObject()
        body.put("to", tokenDevice)
        body.put("priority", "high")
        body.put("sound", "enabled")

        val notification = JSONObject()
        notification.put("title", "Bạn có 1 in nhắn mới ")
        notification.put("body", "$nameSender: $message")

        body.put("notification", notification)

        val request = HttpEntity(body.toString())

        val pushNotification = androidPushNotificationService.send(request)
        CompletableFuture.allOf(pushNotification).join()

        try {
            val fireBaseResponse = pushNotification.get()

            return ResponseEntity(fireBaseResponse, HttpStatus.OK)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return ResponseEntity("Push Notification ERROR!", HttpStatus.BAD_REQUEST)
    }

    private fun insertMessageToDB(idThread: String?, message: String?, isSender: Int = 1): Int {
        val sql = "INSERT INTO `${EntitiesTable.message}` (`ID_THREAD`, `MESSAGECOL`, `IS_SENDER`) VALUES ('$idThread', '$message', '$isSender')"
        return jdbcTemplate.update(sql)
    }
}
