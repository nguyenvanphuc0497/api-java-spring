package com.capstone1.tutoryapi.dao.account

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.account.UserAccount
import com.capstone1.tutoryapi.entities.account.UserAccountMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/7/18
 */
@Repository
class UserAccountDAO : BaseDAO() {

    internal fun view(): List<UserAccount> = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.userTable}",
            UserAccountMapper())

    internal fun viewByIdUser(id: Int): UserAccount? = jdbcTemplate.queryForObject(
            "SELECT * FROM ${EntitiesTable.userTable} WHERE ID_USER=?",
            UserAccountMapper(), id)

    internal fun viewByIdUserAndIdType(idUser: Int?, idType: String?): UserAccount? = jdbcTemplate.queryForObject(
            "SELECT * FROM ${EntitiesTable.userTable} WHERE ID_USER=? AND ID_TYPE=?",
            UserAccountMapper(), idUser, idType)

    internal fun checkAccountLogin(userName: String?, passWord: String?): Boolean {
        var status = false
        jdbcTemplate.query("SELECT * FROM ${EntitiesTable.userTable} WHERE USER_NAME LIKE ? AND PASS_WORD LIKE ?",
                UserAccountMapper(), userName, passWord).let {
            if (it.size == 1) {
                status = true
            }
        }
        return status
    }
}
