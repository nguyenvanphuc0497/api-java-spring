package com.capstone1.tutoryapi.entities.account

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Create by Nguyen Van Phuc on 2018-12-04
 **/
class AddressUserMapper : RowMapper<AddressUser> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AddressUser? = AddressUser(rs.getString("ID_PROFILE"),
            rs.getString("NAME"),
            rs.getString("URL_AVATAR"),
            rs.getString("ADDRESS"))
}
