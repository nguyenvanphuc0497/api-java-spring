package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
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
class ThreadMessagerDAO : BaseDAO() {

    internal fun getAllProfilesForThreadByIdSender(idProfile: Int?): List<ThreadMessage> {
//        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER , up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
//                "FROM thread_messager AS tm INNER JOIN user_profile AS up " +
//                "WHERE tm.RECEIVER_IDPROFILE = up.ID_PROFILE AND( " +
//                "tm.SENDER_IDPROFILE = $idProfile OR tm.RECEIVER_IDPROFILE = $idProfile ) "
        //Fix error get info profile not correct
        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM thread_messager AS tm INNER JOIN user_profile AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = $idProfile " +
                "UNION SELECT tm.*, up.NAME AS NAME_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM thread_messager AS tm INNER JOIN user_profile AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = $idProfile "
        return jdbcTemplate.query(sql, ThreadMessageMapper())
    }


    internal fun viewMessageByIdThread(idThread: Int?) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.message} WHERE ID_THREAD = $idThread", MessagerMapper())

    internal fun createMessageByIdThread(idProfile: Int?, idThread: Int?, message: String?): Int {
        val sqlSelect = "SELECT * FROM thread_messager WHERE SENDER_IDPROFILE = '$idProfile' AND ID_THREAD = '$idThread' UNION SELECT * FROM thread_messager WHERE RECEIVER_IDPROFILE = '$idProfile' AND ID_THREAD = '$idThread'"

        try {
            var count: Int? = 0
            jdbcTemplate.query(sqlSelect) {
                it.row.let { row ->
                    count = row
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
                    pushNotificationToDeviceForProfile(idProfile, idThread, message)
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
                "FROM thread_messager AS tm INNER JOIN user_profile AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = $idProfileSender AND tm.ID_THREAD = $idThread " +
                "UNION SELECT tm.ID_THREAD, up.* " +
                "FROM thread_messager AS tm INNER JOIN user_profile AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = $idProfileSender AND tm.ID_THREAD = $idThread LIMIT 1"
        jdbcTemplate.query(sql) {
            nameSender = it.getString("NAME")
            tokenDevice = it.getString("FCM_TOKEN_DEVICE")
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
