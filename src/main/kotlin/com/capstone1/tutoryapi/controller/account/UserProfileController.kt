package com.capstone1.tutoryapi.controller.account

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
@RestController
class UserProfileController : BaseController() {

    @GetMapping("/user/profile")
    private fun getAll() = UserProfileResponse(userProfileDAO.findAll())

    @PostMapping("/account/authentication", consumes = ["application/json", "multipart/form-data"])
    internal fun accountAuthentication(@RequestBody body: Map<String, String>) = userProfileDAO.accountAuthentication(body["user_name"], body["password"])
}
