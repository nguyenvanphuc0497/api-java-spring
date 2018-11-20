package com.capstone1.tutoryapi.controller.account

import com.capstone1.tutoryapi.entities.account.UserProfile

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
data class UserProfileResponse(val userProfiles: List<UserProfile>? = null)
