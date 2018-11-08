package com.capstone1.tutoryapi.controller

import com.capstone1.tutoryapi.dao.posts.PostsDAO
import com.capstone1.tutoryapi.dao.user.TypeUserDAO
import com.capstone1.tutoryapi.dao.user.UserAccountDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
@RestController
@RequestMapping("/api/v1")
class BaseController {

    @Autowired
    protected lateinit var typeUserDAO: TypeUserDAO

    @Autowired
    protected lateinit var userAccountDAO: UserAccountDAO

    @Autowired
    protected lateinit var postsDAO: PostsDAO
}