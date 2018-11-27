package com.capstone1.tutoryapi.controller.message

import com.capstone1.tutoryapi.controller.BaseController
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

/**
 * Created by Nguyen Van Phuc on 11/26/18
 */
@RestController
class PushNotificationAndroidController : BaseController() {

    private val TOPIC = "TutoryStudent"

    @GetMapping("/send", consumes = ["application/json", "multipart/form-data"])
    fun send(): ResponseEntity<String> {

        val body = JSONObject()
        body.put("to", "/topics/$TOPIC")
        body.put("priority", "high")
        body.put("sound", "enabled")

        val notification = JSONObject()
        notification.put("title", "JSA Notification")
        notification.put("body", "Happy Message!")

        val data = JSONObject()
        data.put("Key-1", "JSA Data 1")
        data.put("Key-2", "JSA Data 2")

        body.put("notification", notification)
        body.put("data", data)

        /**
         * {
         * "notification": {
         * "title": "JSA Notification",
         * "body": "Happy Message!"
         * },
         * "data": {
         * "Key-1": "JSA Data 1",
         * "Key-2": "JSA Data 2"
         * },
         * "to": "/topics/JavaSampleApproach",
         * "priority": "high"
         * }
         */

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