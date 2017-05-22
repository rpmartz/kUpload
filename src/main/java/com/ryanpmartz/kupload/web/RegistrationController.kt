package com.ryanpmartz.kupload.web

import com.ryanpmartz.kupload.domain.User
import com.ryanpmartz.kupload.service.UserService
import com.ryanpmartz.kupload.web.form.RegistrationForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

import javax.transaction.Transactional
import javax.validation.Valid

@Controller
class RegistrationController @Autowired
constructor(private val passwordEncoder: PasswordEncoder, private val userService: UserService) {

    @GetMapping("/register")
    fun registrationForm(@ModelAttribute registrationForm: RegistrationForm, model: Model): String {
        return "register"
    }

    @Transactional
    @PostMapping("/register")
    fun doRegistration(@Valid @ModelAttribute registrationForm: RegistrationForm, bindingResult: BindingResult, model: Model): String {

        // if annotation validation failed, return form with errors
        if (bindingResult.hasErrors()) {
            return registrationForm(registrationForm, model)
        }

        val email = registrationForm.email
        val existingUserWithEmail = userService.findByEmail(email)
        if (existingUserWithEmail != null) {
            bindingResult.rejectValue("email", null, "This email is in use")

            return registrationForm(registrationForm, model)
        }

        val password = registrationForm.password
        val confirmPassword = registrationForm.confirmPassword
        if (password != confirmPassword) {
            bindingResult.rejectValue("confirmPassword", null, "Passwords do not match")

            return registrationForm(registrationForm, model)
        }

        val user = buildUserFromForm(registrationForm)

        userService.createUser(user)

        return "redirect:/register/success"
    }

    @GetMapping("/register/success")
    fun success(): String {
        return "registration-complete"
    }

    private fun buildUserFromForm(form: RegistrationForm): User {
        val user = User()
        user.firstName = form.firstName
        user.lastName = form.lastName
        user.email = form.email
        user.password = passwordEncoder.encode(form.password)
        user.enabled = true

        return user
    }
}
