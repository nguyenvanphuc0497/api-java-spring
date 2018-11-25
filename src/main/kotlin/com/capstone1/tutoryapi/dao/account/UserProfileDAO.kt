package com.capstone1.tutoryapi.dao.account

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.account.UserProfile
import com.capstone1.tutoryapi.entities.account.UserProfileMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
@Repository
class UserProfileDAO : BaseDAO() {

    internal fun accountAuthentication(userName: String?, password: String?): UserProfile {
        var userProfile = UserProfile()
        val sql = "SELECT ID_USER FROM ${EntitiesTable.userAccount} WHERE USER_NAME LIKE '${userName?.trim()}' AND PASS_WORD LIKE '${password?.trim()}' LIMIT 1"
        print("$userName$password")
        jdbcTemplate.query(sql) {
            userProfile = findProfileByIdUser(it.getInt("ID_USER"))[0]
        }
        return userProfile
    }

    private fun findProfileByIdUser(idUser: Int) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.userProfile} WHERE ID_USER = $idUser LIMIT 1", UserProfileMapper())
}
