package com.capstone1.tutoryapi.controller.news

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
@RestController
class PostsController : BaseController() {

    @GetMapping("/post")
    internal fun viewPost() = PostsResponse(postsDAO.selectAddPostAvailbel())

}
