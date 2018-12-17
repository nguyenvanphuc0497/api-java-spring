package com.capstone1.tutoryapi.controller.other

import com.capstone1.tutoryapi.controller.BaseController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Create by Nguyen Van Phuc on 2018-12-16
 **/
@RestController
class OtherController : BaseController() {

    @GetMapping("/data/city")
    internal fun viewAllCityOnDB() = CityResponse(otherDAO.selectAllCityOnDB())
}
