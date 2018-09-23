package com.capstone1.tutoryapi.controller

import com.capstone1.tutoryapi.model.Member
import org.springframework.web.bind.annotation.*

/**
 * Created by Nguyen Van Phuc on 9/20/18
 */
@RestController
@RequestMapping("/api/v0/")
class UserController {

    @GetMapping("/members")
    internal fun getAllMember(): List<Member>? {
        val list = mutableListOf<Member>()
        list.add(Member(1, "Nguyễn Trung Tiến", "Team Leader"))
        list.add(Member(2, "Nguyễn Văn Phúc", "Scrum Master"))
        list.add(Member(3, "Võ Thế Doãn", "Database Developer"))
        list.add(Member(4, "Đinh Triết ", "Web Developer"))
        return if (list.isNotEmpty()) {
            list
        } else {
            null
        }
    }
}
