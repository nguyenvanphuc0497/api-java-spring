package com.capstone1.tutoryapi.dao.other

import com.capstone1.tutoryapi.dao.BaseDAO
import com.capstone1.tutoryapi.entities.EntitiesTable
import com.capstone1.tutoryapi.entities.other.CityMapper
import org.springframework.stereotype.Repository

/**
 * Create by Nguyen Van Phuc on 2018-12-16
 **/
@Repository
class OtherDAO : BaseDAO() {
    internal fun selectAllCityOnDB() = jdbcTemplate.query("SELECT * FROM `${EntitiesTable.city}`", CityMapper())
}
