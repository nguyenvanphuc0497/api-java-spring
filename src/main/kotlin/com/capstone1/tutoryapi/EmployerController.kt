package com.capstone1.tutoryapi

import com.capstone1.tutoryapi.datasource.Repository
import com.capstone1.tutoryapi.model.Employer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/demo")
class EmployerController {
    @Autowired
    lateinit var repository: Repository

    @GetMapping("/employee")
    fun findAll(): List<Employer> = repository.findAll().toList()

    @PostMapping("/employee/search")
    fun search(@RequestBody body: Map<String, String>): List<Employer> {
        val searchName: String? = body.get("text")
        return repository.findEmployeByName(searchName)
    }

    @PostMapping("/employee/new")
    @ResponseBody
    fun create(@RequestBody body: Map<String, String>): Employer {
        val employer = Employer()
        employer.id = body["id"]?.toInt()
        employer.name = body["name"]
        employer.title = body["title"]

        return repository.save(employer)
    }

    @GetMapping("/employee/add")
    fun addNewUse(@RequestParam name: String, @RequestParam title: String): Employer {
        val employer = Employer()
        employer.name = name
        employer.title = title

        return repository.save(employer)
    }

    @DeleteMapping("/employee/delete/{id}")
    fun delete(@PathVariable id: String): Boolean {
        try {
            repository.deleteById(id.toInt())
        } catch (e: Exception) {
            return false
        } finally {
            return true
        }
    }
}
