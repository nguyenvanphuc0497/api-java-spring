package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.messager.ThreadMessageMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@Repository
class ThreadMessagerDAO : BaseDAO() {
    internal fun viewThreadMessageByIdProfile(idProfile: Int?) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.threadMessage} WHERE SENDER_IDPROFILE = $idProfile OR RECEIVER_IDPROFILE =$idProfile", ThreadMessageMapper())

}
