package com.capstone1.tutoryapi.dao.account

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.account.UserProfileMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
@Repository
class UserProfileDAO : BaseDAO() {

    internal fun findAll() = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.userProfile}",
            UserProfileMapper())

    internal fun accountAuthentication(userName: String?, password: String?): Boolean {
        var status = false
        val sql = "SELECT * FROM ${EntitiesTable.userAccount} WHERE USER_NAME LIKE '${userName?.trim()}' AND PASS_WORD LIKE '${password?.trim()}'"
        print("$userName$password")
        jdbcTemplate.query(sql) {
            if (it.row > 0) status = true
        }
        return status
    }
}
