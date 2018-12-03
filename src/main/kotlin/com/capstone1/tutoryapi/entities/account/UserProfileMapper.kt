package com.capstone1.tutoryapi.entities.account

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
class UserProfileMapper : RowMapper<UserProfile> {
    override fun mapRow(rs: ResultSet, rowNum: Int): UserProfile? = UserProfile(rs.getString("ID_PROFILE"),
            rs.getString("BIRTHDAY")?.trim(),
            rs.getString("EMAIL")?.trim(),
            rs.getString("ID_ADDRESS")?.trim(),
            rs.getString("ID_USER")?.trim(),
            rs.getString("NAME")?.trim(),
            rs.getString("PHONE")?.trim(),
            rs.getString("SEX")?.trim(),
            rs.getString("STATUS")?.trim(),
            rs.getString("URL_AVATAR")?.trim())
}
