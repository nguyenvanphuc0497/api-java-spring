package com.capstone1.tutoryapi.entities.account

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
class AddressUserProfileMapper : RowMapper<AddressUserProfile> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AddressUserProfile? = AddressUserProfile(rs.getString("ID_PROFILE")?.trim(),
            rs.getString("NAME")?.trim(),
            rs.getString("STATUS")?.trim(),
            rs.getString("URL_AVATAR")?.trim(),
            rs.getString("SO_NHA")?.trim(),
            rs.getString("Phuong")?.trim(),
            rs.getString("Quan")?.trim(),
            rs.getString("City")?.trim())
}
