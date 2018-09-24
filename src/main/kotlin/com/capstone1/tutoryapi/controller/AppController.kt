package com.capstone1.tutoryapi.controller

import com.capstone1.tutoryapi.model.Member
import org.springframework.web.bind.annotation.*

/**
 * Created by Nguyen Van Phuc on 9/20/18
 */
@RestController
@RequestMapping("/api/v0")
class AppController {
    @GetMapping("/members/{memberId}")

    internal fun getMember(@PathVariable memberId: String): Member? {
        return when (memberId) {
            "1" -> Member(1, "Nguyễn Trung Tiến", "Team Leader")
            "2" -> Member(1, "Nguyễn Văn Phúc", "Scrum Master")
            "3" -> Member(3, "Võ Thế Doãn", "Database Developer")
            "4" -> Member(4, "Đinh Triết Kha", "Web Developer")
            else -> {
                null
            }
        }
    }
}
