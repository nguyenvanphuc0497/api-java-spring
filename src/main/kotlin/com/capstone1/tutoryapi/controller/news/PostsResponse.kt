package com.capstone1.tutoryapi.controller.news

import com.capstone1.tutoryapi.entities.news.Post

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
data class PostsResponse(val posts: List<Post>? = null)
