package com.capstone1.tutoryapi.controller.user

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.web.bind.annotation.*

/**
 * Created by Nguyen Van Phuc on 11/7/18
 */
@RestController
class UserAccountController : BaseController() {

    @GetMapping("/user")
    internal fun viewAll() = UserAccountResponse(userAccountDAO.view())

    @GetMapping("/user/{idUser}")
    fun viewByIdUser(@PathVariable idUser: Int) = userAccountDAO.viewByIdUser(idUser)

    @PostMapping(consumes = ["application/json", "multipart/form-data"])
    fun viewByIdUserAndType(@RequestBody body: Map<String, String>) = userAccountDAO.viewByIdUserAndIdType(body["idUser"]?.toInt(), body["isType"])

    @PostMapping("/user/login", consumes = ["application/json", "multipart/form-data"])
    fun checkAcountLogin(@RequestBody body: Map<String, String>) = userAccountDAO.checkAccountLogin(body["userName"], body["passWord"])
}
