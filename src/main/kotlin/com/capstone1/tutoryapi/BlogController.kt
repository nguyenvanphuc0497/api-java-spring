package com.capstone1.tutoryapi

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BlogController {
    @RequestMapping("/api/v1/")
    internal fun index(): String = "- Nguyễn Văn Phúc " +
            "- Võ Thế Doãn " +
            "- Nguyễn Trung Tiến" +
            "- Đinh Triết Kha"
}
