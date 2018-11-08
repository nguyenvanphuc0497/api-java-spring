package com.capstone1.tutoryapi.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Nguyen Van Phuc on 11/8/18
 */
@Transactional
class BaseDAO {
    @Autowired
    protected lateinit var jdbcTemplate: JdbcTemplate
}
