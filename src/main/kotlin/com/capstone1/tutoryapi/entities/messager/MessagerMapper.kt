package com.capstone1.tutoryapi.entities.messager

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
class MessagerMapper : RowMapper<Messager> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Messager? = Messager(rs.getInt("ID_MESSAGE"),
            rs.getInt("ID_THREAD"),
            rs.getString("IS_SENDER"),
            rs.getString("MESSAGECOL"),
            rs.getString("SEND_TIME"),
            rs.getString("READER"),
            rs.getString("TIME_READ"),
            rs.getString("SENDER_IDPROFILE"))
}