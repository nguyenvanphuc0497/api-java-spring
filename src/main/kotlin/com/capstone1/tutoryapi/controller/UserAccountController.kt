package com.capstone1.tutoryapi.controller

import com.capstone1.tutoryapi.dao.UserAccountDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by Nguyen Van Phuc on 11/7/18
 */
@RestController
@RequestMapping("/api/v0/user")
class UserAccountController {
    @Autowired
    private lateinit var userAccountDAO: UserAccountDAO

    @GetMapping
    internal fun viewAll() = userAccountDAO.view()

    @GetMapping("/{idUser}")
    fun viewByIdUser(@PathVariable idUser: Int) = userAccountDAO.viewByIdUser(idUser)

    @PostMapping(consumes = ["application/json", "multipart/form-data"])
    fun viewByIdUserAndType(@RequestBody body: Map<String, String>) = userAccountDAO.viewByIdUserAndIdType(body["idUser"]?.toInt(), body["isType"])

    @PostMapping("/login", consumes = ["application/json", "multipart/form-data"])
    fun checkAcountLogin(@RequestBody body: Map<String, String>) = userAccountDAO.checkAccountLogin(body["userName"], body["passWord"])
}
