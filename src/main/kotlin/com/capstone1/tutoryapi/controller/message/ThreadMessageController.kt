package com.capstone1.tutoryapi.controller.message

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@RestController
class ThreadMessageController : BaseController() {

    @PostMapping("/thread", consumes = ["application/json", "multipart/form-data"])
    internal fun getThreadByIdProfile(@RequestBody body: Map<String, String>) =
            ThreadMessageResponse(threadMessageDAO.getProfileForThread(body["idProfile"]?.toInt()).toList())

    @PostMapping("/thread/message", consumes = ["application/json", "multipart/form-data"])
    internal fun getMessageByIdThread(@RequestBody body: Map<String, String>) =
            MessageResponse(messageDao.viewMessageByIdThread(body["idThread"]?.toInt()).toList())

    @PostMapping("/thread/message/sending", consumes = ["application/json", "multipart/form-data"])
    internal fun sendingMessageByIdThread(@RequestBody body: Map<String, String>) = messageDao.createMessageByIdThread(body["idProfile"]?.toInt(), body["idThread"]?.toInt(), body["message"])

}
