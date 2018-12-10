package com.capstone1.tutoryapi.entities.account

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
class AboutUserProfileMapper : RowMapper<AboutUserProfile> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AboutUserProfile? = AboutUserProfile(rs.getString("ID_PROFILE")?.trim(),
            rs.getString("NAME")?.trim(),
            rs.getString("BIRTHDAY")?.trim(),
            rs.getString("SEX")?.trim(),
            rs.getString("PHONE")?.trim(),
            rs.getString("STATUS")?.trim(),
            rs.getString("URL_AVATAR")?.trim(),
            rs.getString("EMAIL")?.trim(),
            rs.getString("SO_NHA")?.trim(),
            rs.getString("Phuong")?.trim(),
            rs.getString("Quan")?.trim(),
            rs.getString("City")?.trim())
}
