package com.capstone1.tutoryapi.dao.user

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.user.TypeUser
import com.capstone1.tutoryapi.entities.user.TypeUserMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/6/18
 */
@Repository
class TypeUserDAO : BaseDAO() {

    fun save(typeUser: TypeUser) {
        jdbcTemplate.update("INSERT INTO TYPE_USER(ID_TYPE, TYPE_NAME) VALUES (?, ?)",
                typeUser.idType,
                typeUser.nameType)
    }

    fun view(): List<TypeUser>? = jdbcTemplate.query("SELECT * FROM TYPE_USER", TypeUserMapper())
}
