package com.capstone1.tutoryapi.entities.user

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/7/18
 */
class UserAccountMapper : RowMapper<UserAccount> {
    override fun mapRow(rs: ResultSet, rowNum: Int): UserAccount? = UserAccount(rs.getInt("ID_USER"),
            rs.getString("ID_TYPE"),
            rs.getString("USER_NAME"),
            rs.getString("PASS_WORD")
    )
}
