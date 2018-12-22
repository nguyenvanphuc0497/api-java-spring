package com.capstone1.tutoryapi.dao.posts

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.news.Post
import com.capstone1.tutoryapi.entities.news.PostMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
@Repository
class PostsDAO : BaseDAO() {

    internal fun viewPost() = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.postTable}", PostMapper())

    internal fun selectAddPostAvailbel(): List<Post> {
        val sql = "SELECT uf.ID_PROFILE, n.NEWS_ID,uf.URL_AVATAR, n.NEWS_TITLE, n.DETAILS, en.FROM_DATE " +
                "FROM news n " +
                "INNER JOIN editor_of_news en ON en.THING_ROLE_TYPE_ID_TO=n.NEWS_ID " +
                "INNER JOIN editor ed ON ed.PARTY_ID=en.PARTY_ROLE_TYPE_ID_FROM INNER JOIN user_profile uf ON uf.ID_USER= ed.ID_USER " +
                "WHERE n.STATUS= 1 ORDER BY en.FROM_DATE DESC "
        return jdbcTemplate.query(sql, PostMapper())
    }
}
