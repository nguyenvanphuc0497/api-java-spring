package com.capstone1.tutoryapi.dao.account

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.account.TypeUser
import com.capstone1.tutoryapi.entities.account.TypeUserMapper
import org.springframework.stereotype.Repository

/**
 * Created by Nguyen Van Phuc on 11/6/18
 */
@Repository
class TypeUserDAO : BaseDAO() {

    fun save(typeUser: TypeUser) {
        jdbcTemplate.update("INSERT INTO ${EntitiesTable.typeUserTable}(ID_TYPE, TYPE_NAME) VALUES (?, ?)",
                typeUser.idType,
                typeUser.nameType)
    }

    fun view(): List<TypeUser>? = jdbcTemplate.query("SELECT * FROM ${EntitiesTable.typeUserTable}", TypeUserMapper())
}
