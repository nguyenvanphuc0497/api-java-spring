package com.capstone1.tutoryapi.entities.news

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
class PostMapper : RowMapper<Post> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Post? = Post(rs.getString("NEWS_ID"),
            rs.getString("COVER_NEWS"),
            rs.getString("DETAILS"),
            rs.getString("NEWS_IMAGE"),
            rs.getString("NEWS_TITLE")
    )
}
