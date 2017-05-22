package com.ryanpmartz.kupload.web.form

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank

import javax.validation.constraints.Size

class RegistrationForm {

    @NotBlank(message = "Please enter your first name")
    var firstName: String? = null

    @NotBlank(message = "Please enter your last name")
    var lastName: String? = null

    @Email(message = "Please enter a valid email address")
    var email: String? = null

    @NotBlank(message = "Please enter a password")
    @Size(min = 8, message = "Password must be at least 8 characters")
    var password: String? = null

    @NotBlank(message = "Please re-enter password for confirmation")
    @Size(min = 8, message = "Password must be at least 8 characters")
    var confirmPassword: String? = null
}
