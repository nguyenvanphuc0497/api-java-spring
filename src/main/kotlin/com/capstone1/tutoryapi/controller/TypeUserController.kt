package com.capstone1.tutoryapi.controller

import com.capstone1.tutoryapi.dao.TypeUserDAO
import com.capstone1.tutoryapi.entities.user.TypeUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException


/**
 * Created by Nguyen Van Phuc on 11/6/18
 */
@RestController
@RequestMapping("/api/v0/type")
class TypeUserController {
    @Autowired
    private lateinit var typeUserDAO: TypeUserDAO

    @PostMapping("/", consumes = ["application/json"])
    fun add(@RequestBody body: Map<String, String>): ResponseEntity<String> {
        try {
            typeUserDAO.save(TypeUser(body["ID_TYPE"], body["TYPE_NAME"]))
        } catch (io: IOException) {
            println(io.message)
        } finally {
            return ResponseEntity("Successfully uploaded!", HttpStatus.OK)
        }
    }

    @GetMapping("/")
    fun view() = typeUserDAO.view()
}
