package com.capstone1.tutoryapi.entities.account

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet


/**
 * Created by Nguyen Van Phuc on 11/6/18
 */
class TypeUserMapper : RowMapper<TypeUser> {
    override fun mapRow(rs: ResultSet, rowNum: Int): TypeUser? = TypeUser(rs.getString("ID_TYPE"),
            rs.getString("TYPE_NAME"))
}