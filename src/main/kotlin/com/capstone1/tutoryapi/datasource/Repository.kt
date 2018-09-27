package com.capstone1.tutoryapi.datasource

import com.capstone1.tutoryapi.model.Employer
import org.springframework.data.repository.CrudRepository

/**
 * Created by Nguyen Van Phuc on 9/26/18
 */
interface Repository : CrudRepository<Employer, Int> {
    fun findEmployeByName(name: String?): List<Employer>
}
