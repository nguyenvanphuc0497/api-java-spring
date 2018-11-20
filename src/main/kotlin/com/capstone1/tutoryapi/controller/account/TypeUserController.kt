package com.capstone1.tutoryapi.controller.account

import com.capstone1.tutoryapi.controller.BaseController
import com.capstone1.tutoryapi.entities.account.TypeUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.IOException


/**
 * Created by Nguyen Van Phuc on 11/6/18
 */
@RestController
class TypeUserController : BaseController() {

    @PostMapping("/type", consumes = ["application/json"])
    fun add(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        try {
            typeUserDAO.save(TypeUser(body["ID_TYPE"], body["TYPE_NAME"]))
        } catch (io: IOException) {
            println(io.message)
        } finally {
            return ResponseEntity("Successfully uploaded!", HttpStatus.OK)
        }
    }

    @GetMapping("/type")
    fun view() = TypeUserResponse(typeUserDAO.view())
}
