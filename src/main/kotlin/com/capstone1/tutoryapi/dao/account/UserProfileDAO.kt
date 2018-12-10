package com.capstone1.tutoryapi.dao.account

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.account.UserProfile
import com.capstone1.tutoryapi.entities.account.UserProfileFully
import com.capstone1.tutoryapi.entities.account.UserProfileFullyMapper
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

    internal fun findUserProfileByIdProfile(idProfile: String?): UserProfileFully? {
        val sql = "SELECT up.ID_PROFILE, up.NAME, up.BIRTHDAY, up.SEX, up.SO_NHA, up.PHONE, up.EMAIL, up.URL_AVATAR, up.STATUS, xp.name AS Phuong, qh.name AS Quan, tp.name AS City " +
                "FROM ${EntitiesTable.userProfile} AS up " +
                "LEFT JOIN ${EntitiesTable.xaPhuong} AS xp ON up.ID_ADDRESS = xp.xaid LEFT JOIN ${EntitiesTable.quanHuyen} AS qh ON xp.maqh = qh.maqh " +
                "LEFT JOIN ${EntitiesTable.city} AS tp ON qh.matp = tp.matp " +
                "WHERE up.ID_PROFILE = '$idProfile' "
        val result = jdbcTemplate.query(sql, UserProfileFullyMapper())
        if (result.isNotEmpty()) {
            return result[0]
        }
        return UserProfileFully()
    }

    /**
     * Function Private
     */
    private fun findProfileByIdUser(idUser: Int) = jdbcTemplate.query(
            "SELECT * FROM ${EntitiesTable.userProfile} WHERE ID_USER = $idUser LIMIT 1", UserProfileMapper())

    private fun updateStatusOnlineForIdProfile(isUser: Int?, status: Int? = 0, tokenDevice: String? = "") = jdbcTemplate.update(
            "UPDATE ${EntitiesTable.userProfile} SET STATUS = '$status' , FCM_TOKEN_DEVICE = '$tokenDevice' WHERE ID_USER = $isUser")
}
