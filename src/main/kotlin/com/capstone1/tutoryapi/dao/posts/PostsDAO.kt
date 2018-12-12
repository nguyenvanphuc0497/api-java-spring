package com.capstone1.tutoryapi.dao.posts

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.news.PostMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
@Repository
class PostsDAO : BaseDAO() {

    internal fun viewPost() = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.postTable}", PostMapper())
}
