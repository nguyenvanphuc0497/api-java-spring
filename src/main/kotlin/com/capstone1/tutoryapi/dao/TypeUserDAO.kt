package com.capstone1.tutoryapi.dao

import com.capstone1.tutoryapi.entities.TypeUser
import com.capstone1.tutoryapi.entities.TypeUserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Nguyen Van Phuc on 11/6/18
 */
@Repository
@Transactional
class TypeUserDAO {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun save(typeUser: TypeUser) {
        jdbcTemplate.update("INSERT INTO TYPE_USER(ID_TYPE, TYPE_NAME) VALUES (?, ?)",
                typeUser.idType,
                typeUser.nameType)
    }

    fun view(): List<TypeUser> = jdbcTemplate.query("SELECT * FROM TYPE_USER", TypeUserMapper())
}
