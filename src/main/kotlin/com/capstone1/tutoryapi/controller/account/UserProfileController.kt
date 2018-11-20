package com.capstone1.tutoryapi.controller.account

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
@RestController
class UserProfileController : BaseController() {

    @GetMapping("/user/profile")
    private fun getAll() = UserProfileResponse(userProfileDAO.findAll())
}
