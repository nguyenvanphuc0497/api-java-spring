package com.capstone1.tutoryapi.controller.account

import com.capstone1.tutoryapi.controller.BaseController
import com.capstone1.tutoryapi.controller.account.response.UserProfileResponse
import org.springframework.web.bind.annotation.*

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
@RestController
class UserProfileController : BaseController() {

    @PostMapping("/account/authentication", consumes = ["application/json", "multipart/form-data"])
    internal fun accountAuthentication(@RequestBody body: Map<String, String>) = userProfileDAO.accountAuthentication(body["user_name"], body["password"], body["fcm_token_device"])

    @PostMapping("/account/logout", consumes = ["application/json", "multipart/form-data"])
    internal fun logoutAccount(@RequestBody body: Map<String, String>) = userProfileDAO.accountLogout(body["id_user"])

    @PostMapping("/user/profile", consumes = ["application/json", "multipart/form-data"])
    internal fun getAllUserProfileByIdAccount(@RequestBody body: Map<String, String>) =
            UserProfileResponse(userProfileDAO.findAllProfileWithoutIdProfile(body["idProfile"]))

    @PostMapping("/user/profile/tutor", consumes = ["application/json", "multipart/form-data"])
    internal fun getAllUserProfileForTutorByIdAccount(@RequestBody body: Map<String, String>) =
            UserProfileResponse(userProfileDAO.findAllProfileTutorWithoutIdProfile(body["idProfile"]))

    @PostMapping("/user/profile/student", consumes = ["application/json", "multipart/form-data"])
    internal fun getAllUserProfileForStudentByIdAccount(@RequestBody body: Map<String, String>) =
            UserProfileResponse(userProfileDAO.findAllProfileStudentWithoutIdProfile(body["idProfile"]))

    @GetMapping("/user/profile/about")
    internal fun getAboutUserByIdProfile(@RequestParam("id") idProfile: String?) =
            userProfileDAO.findAboutUserByIdProfile(idProfile)

    @GetMapping("/user/profile/")
    internal fun getUserProfileByIdProfile(@RequestParam("id") idProfile: String?) =
            userProfileDAO.findAboutUserByIdProfile(idProfile)
}
