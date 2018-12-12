package com.capstone1.tutoryapi.entities.messager

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
class ThreadMessageMapper : RowMapper<ThreadMessage> {
    override fun mapRow(rs: ResultSet, rowNum: Int): ThreadMessage? = ThreadMessage(rs.getInt("ID_THREAD"),
            rs.getString("CREATE_TIME"),
            rs.getInt("RECEIVER_IDPROFILE"),
            rs.getInt("SENDER_IDPROFILE"),
            rs.getString("STATUS"),
            rs.getString("ID_RECEIVER"),
            rs.getString("NAME_RECEIVER"),
            rs.getString("URL_AVATAR_RECEIVER"))
}
