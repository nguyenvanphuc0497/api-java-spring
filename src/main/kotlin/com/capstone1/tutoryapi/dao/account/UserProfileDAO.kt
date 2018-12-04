package com.capstone1.tutoryapi.dao.account

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.account.AddressUserMapper
import com.capstone1.tutoryapi.entities.account.UserProfile
import com.capstone1.tutoryapi.entities.account.UserProfileMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/20/18
 */
@Repository
class UserProfileDAO : BaseDAO() {

    internal fun accountAuthentication(userName: String?, password: String?, tokenDevice: String?): UserProfile {
        var userProfile = UserProfile()
        val sql = "SELECT ID_USER FROM ${EntitiesTable.userAccount} WHERE USER_NAME LIKE '${userName?.trim()}' AND PASS_WORD LIKE '${password?.trim()}' LIMIT 1"
        print("$userName$password")
        jdbcTemplate.query(sql) {
            updateStatusOnlineForIdProfile(it.getInt("ID_USER"), 1, tokenDevice)
            userProfile = findProfileByIdUser(it.getInt("ID_USER"))[0]
        }
        return userProfile
    }

    internal fun accountLogout(idUser: Int?): Int = updateStatusOnlineForIdProfile(idUser, 0, "")

    internal fun findAllProfileWithoutIdProfile(idProfile: Int? = 0): List<UserProfile> {
        val sql = "SELECT * FROM ${EntitiesTable.userProfile} WHERE ID_PROFILE NOT LIKE $idProfile "
        return jdbcTemplate.query(sql, UserProfileMapper())
    }

    internal fun findAllProfileStudentWithoutIdProfile(idProfile: Int? = 0): List<UserProfile> {
        val sql = "SELECT * FROM ${EntitiesTable.userProfile} AS up INNER JOIN ${EntitiesTable.student} AS s WHERE up.ID_PROFILE NOT LIKE $idProfile AND up.ID_PROFILE = s.ID_PROFILE "
        return jdbcTemplate.query(sql, UserProfileMapper())
    }

    internal fun findAllProfileTutorWithoutIdProfile(idProfile: Int? = 0): List<UserProfile> {
        val sql = "SELECT * FROM ${EntitiesTable.userProfile} AS up INNER JOIN ${EntitiesTable.tutor} AS s WHERE up.ID_PROFILE NOT LIKE $idProfile AND up.ID_PROFILE = s.ID_PROFILE "
        return jdbcTemplate.query(sql, UserProfileMapper())
    }

    internal fun findAllAddressForUser() = jdbcTemplate.query("SELECT up.ID_PROFILE, up.NAME, up.URL_AVATAR, CONCAT(a.SO_NHA,', ',tp.name) AS ADDRESS " +
            "FROM ${EntitiesTable.userProfile} AS up INNER JOIN ${EntitiesTable.address} AS a ON up.ID_ADDRESS = a.ID_ADDRESS INNER JOIN ${EntitiesTable.city} AS tp ON a.ID_THANHPHO = tp.matp",
            AddressUserMapper())
    /**
     * Function Private
     */
    private fun findProfileByIdUser(idUser: Int) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.userProfile} WHERE ID_USER = $idUser LIMIT 1", UserProfileMapper())

    private fun updateStatusOnlineForIdProfile(isUser: Int?, status: Int? = 0, tokenDevice: String? = "") = jdbcTemplate.update(
            "UPDATE ${EntitiesTable.userProfile} SET STATUS = '$status' , FCM_TOKEN_DEVICE = '$tokenDevice' WHERE ID_USER = $isUser")
}
