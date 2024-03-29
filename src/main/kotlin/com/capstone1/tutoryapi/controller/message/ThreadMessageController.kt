package com.capstone1.tutoryapi.controller.message

import com.capstone1.tutoryapi.controller.BaseController
import com.capstone1.tutoryapi.controller.message.response.AboutThreadResponse
import com.capstone1.tutoryapi.controller.message.response.MessageResponse
import com.capstone1.tutoryapi.controller.message.response.ThreadMessageResponse
import org.springframework.web.bind.annotation.*

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@RestController
class ThreadMessageController : BaseController() {

    @PostMapping("/thread", consumes = ["application/json", "multipart/form-data"])
    internal fun getThreadByIdProfile(@RequestBody body: Map<String, String>) =
            ThreadMessageResponse(threadMessageDAO.getAllProfilesForThreadByIdSender(body["idProfile"].toString()).toList())

    @PostMapping("/thread/message", consumes = ["application/json", "multipart/form-data"])
    internal fun getMessageByIdThread(@RequestBody body: Map<String, String>) =
            MessageResponse(threadMessageDAO.viewMessageByIdThread(body["idThread"].toString()).toList())

    @PostMapping("/thread/message/sending", consumes = ["application/json", "multipart/form-data"])
    internal fun sendingMessageByIdThread(@RequestBody body: Map<String, String>) =
            threadMessageDAO.createMessageByIdThread(body["idProfile"].toString(), body["idThread"].toString(), body["message"])

    @PostMapping("/thread/message/bot/sending", consumes = ["application/json", "multipart/form-data"])
    internal fun sendingMessageToAIByIdThread(@RequestBody body: Map<String, String>) =
            threadMessageDAO.createMessageWithAIByIdThread(body["idProfile"].toString(), body["idThread"].toString(), body["message"].toString())

    @GetMapping("/thread/")
    internal fun getIdThread(@RequestParam("idSender") idProfileSender: String?, @RequestParam("idReceiver") idProfileReceiver: String?) =
            AboutThreadResponse(threadMessageDAO.getIdThreadByProfileSenderAndProfileReceiver(idProfileSender, idProfileReceiver))

    @PostMapping("thread/new")
    internal fun newThread(@RequestParam("idSender") idProfileSender: String?, @RequestParam("idReceiver") idProfileReceiver: String?) =
            AboutThreadResponse(threadMessageDAO.createNewThread(idProfileSender, idProfileReceiver).toString())
}
