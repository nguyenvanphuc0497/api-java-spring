package com.capstone1.tutoryapi.controller.news

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.data.jpa.repository.Query
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
@RestController
class PostsController : BaseController() {

    @GetMapping("/post")
    internal fun viewPost() = PostsResponse(postsDAO.selectAddPostAvailbel())

    @GetMapping("/post/")
    internal fun viewPostByIdProfile(@RequestParam("id") idProfile: String?) = PostsResponse(postsDAO.selectPostAvailbelByIdProfile(idProfile))
}
