package com.capstone1.tutoryapi.entities.other

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Create by Nguyen Van Phuc on 2018-12-16
 **/
class CityMapper : RowMapper<City> {
    override fun mapRow(rs: ResultSet, rowNum: Int): City? = City(rs.getString("matp"),
            rs.getString("name"),
            rs.getString("type"))
}
