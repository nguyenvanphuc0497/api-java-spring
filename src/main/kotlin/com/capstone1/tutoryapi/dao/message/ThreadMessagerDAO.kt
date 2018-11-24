package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.messager.ThreadMessage
import com.capstone1.tutoryapi.entities.messager.ThreadMessageMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@Repository
class ThreadMessagerDAO : BaseDAO() {

    internal fun getProfileForThread(idProfile: Int?): List<ThreadMessage> {
//        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER , up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
//                "FROM thread_messager AS tm INNER JOIN user_profile AS up " +
//                "WHERE tm.RECEIVER_IDPROFILE = up.ID_PROFILE AND( " +
//                "tm.SENDER_IDPROFILE = $idProfile OR tm.RECEIVER_IDPROFILE = $idProfile ) "
        //Fix error get info profile not correct
        val sql = "SELECT tm.*, up.NAME AS NAME_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM thread_messager AS tm INNER JOIN user_profile AS up ON tm.RECEIVER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.SENDER_IDPROFILE = $idProfile " +
                "UNION SELECT tm.*, up.NAME AS NAME_RECEIVER, up.URL_AVATAR AS URL_AVATAR_RECEIVER " +
                "FROM thread_messager AS tm INNER JOIN user_profile AS up ON tm.SENDER_IDPROFILE = up.ID_PROFILE " +
                "WHERE tm.RECEIVER_IDPROFILE = $idProfile "
        return jdbcTemplate.query(sql, ThreadMessageMapper())
    }
}
