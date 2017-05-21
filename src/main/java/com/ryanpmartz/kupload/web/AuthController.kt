package com.ryanpmartz.kupload.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class AuthController {

    @RequestMapping("/")
    fun home(): String {
        return "home"
    }
}


