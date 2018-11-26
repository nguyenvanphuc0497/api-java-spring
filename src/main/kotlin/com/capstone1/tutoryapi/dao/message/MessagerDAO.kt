package com.capstone1.tutoryapi.dao.message

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.messager.MessagerMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/22/18
 */
@Repository
class MessagerDAO : BaseDAO() {
    internal fun viewMessageByIdThread(idThread: Int?) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.message} WHERE ID_THREAD = $idThread", MessagerMapper())

    internal fun createMessageByIdThread(idProfile: Int?, idThread: Int?, message: String?): Int {
        var count: Int? = 0
        jdbcTemplate.query("SELECT * FROM ${EntitiesTable.threadMessage} WHERE ID_THREAD = $idThread AND SENDER_IDPROFILE =$idProfile") {
            it.row.let { row ->
                count = row
            }
        }
        val isSender = if (count == 1) {
            1
        } else {
            0
        }

        val sql = "INSERT INTO `${EntitiesTable.message}` (`ID_THREAD`, `MESSAGECOL`, `IS_SENDER`) VALUES ('$idThread', '$message', '$isSender')"
//        Kiem tra su ton tai cua nguoi do hay khong trong phong chat
//        val sql = "INSERT INTO `${EntitiesTable.message}` (`ID_THREAD`, `MESSAGECOL`, `IS_SENDER`) " +
//                " VALUES ('$idThread', '$message', '$isSender') " +
//                "WHERE EXITST( SELECT * FROM ${EntitiesTable.threadMessage} WHERE SENDER_IDPROFILE = $idProfile OR RECEIVER_IDPROFILE = $idProfile )"
        return jdbcTemplate.update(sql)
    }
}
