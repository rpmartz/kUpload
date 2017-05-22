package com.ryanpmartz.kupload.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class AuthController {

    @GetMapping("/login")
    fun loginForm(): String {
        return "login"
    }

    @GetMapping("/login-failed")
    fun loginFailed(model: Model): String {

        model.addAttribute("loginFailed", true)

        return "login"
    }

    @GetMapping("/account/locked")
    fun accountLocked(): String {
        return "account-locked"
    }
}
