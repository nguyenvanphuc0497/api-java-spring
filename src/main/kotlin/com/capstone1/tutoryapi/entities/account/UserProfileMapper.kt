package com.capstone1.tutoryapi.entities.account

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
class UserProfileMapper : RowMapper<UserProfile> {
    override fun mapRow(rs: ResultSet, rowNum: Int): UserProfile? = UserProfile(rs.getString("ID_PROFILE"),
            rs.getString("BIRH_DAY"),
            rs.getString("EMAIL"),
            rs.getString("EMAIL"),
            rs.getString("ID_USER"),
            rs.getString("NAME"),
            rs.getString("PHONE"),
            rs.getString("SEX"),
            rs.getString("STATUS"))
}
