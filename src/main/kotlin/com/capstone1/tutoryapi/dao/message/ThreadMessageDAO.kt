package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.messager.Messager
import com.capstone1.tutoryapi.entities.messager.MessagerMapper
import com.capstone1.tutoryapi.entities.messager.ThreadMessage
import com.capstone1.tutoryapi.entities.messager.ThreadMessageMapper
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@Repository
class ThreadMessageDAO : BaseDAO() {

    internal fun getAllProfilesForThreadByIdSender(idProfile: Int?): List<ThreadMessage> {
//        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER , up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
//                "FROM thread_messager AS tm INNER JOIN user_profile AS up " +
//                "WHERE tm.RECEIVER_IDPROFILE = up.ID_PROFILE AND( " +
//                "tm.SENDER_IDPROFILE = $idProfile OR tm.RECEIVER_IDPROFILE = $idProfile ) "
        //Fix error get info profile not correct
        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = $idProfile " +
                "UNION SELECT tm.*, up.NAME AS NAME_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM ${EntitiesTable.threadMessage} AS tm INNER JOIN ${EntitiesTable.userProfile} AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = $idProfile "
        return jdbcTemplate.query(sql, ThreadMessageMapper())
    }

    internal fun viewMessageByIdThread(idThread: Int?): List<Messager> {
        val sql = "SELECT m.* ,tm.SENDER_IDPROFILE FROM ${EntitiesTable.message} AS m INNER JOIN ${EntitiesTable.threadMessage} AS tm " +
                "ON m.ID_THREAD = tm.ID_THREAD WHERE m.ID_THREAD = '$idThread'"
        return jdbcTemplate.query(sql, MessagerMapper())
    }

    internal fun createMessageByIdThread(idProfileSender: Int?, idThread: Int?, message: String?): Int {
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

    private fun pushNotificationToDeviceForProfile(idProfileSender: Int?, idThread: Int?, message: String?): HttpEntity<String> {
        //get profile
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

        //push notification
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
}
