package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.messager.MessagerMapper
import com.capstone1.tutoryapi.entities.messager.ThreadMessageMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@Repository
class MessagerDAO : BaseDAO() {
    internal fun viewMessageByIdThread(idThread: Int?) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.message} WHERE ID_THREAD = $idThread", MessagerMapper())

    internal fun createMessageByIdThread(idProfile: Int?, idThread: Int?, message: String?): Int {
        val count = jdbcTemplate.query("SELECT * FROM ${EntitiesTable.threadMessage} WHERE ID_THREAD = $idThread AND SENDER_IDPROFILE =$idProfile", ThreadMessageMapper()).size
        val isSender = if (count == 1) {
            1
        } else {
            0
        }

        return jdbcTemplate.update("INSERT INTO `${EntitiesTable.message}` (`ID_THREAD`, `MESSAGECOL`, `IS_SENDER`) VALUES ('$idThread', '$message', '$isSender')")
    }
}
