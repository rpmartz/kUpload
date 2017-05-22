package com.ryanpmartz.kupload.web;

import com.ryanpmartz.kupload.domain.User;
import com.ryanpmartz.kupload.service.UserService;
import com.ryanpmartz.kupload.web.form.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(@ModelAttribute RegistrationForm registrationForm, Model model) {
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String doRegistration(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult, Model model) {

        // if annotation validation failed, return form with errors
        if (bindingResult.hasErrors()) {
            return registrationForm(registrationForm, model);
        }

        String email = registrationForm.getEmail();
        User existingUserWithEmail = userService.findByEmail(email);
        if (existingUserWithEmail != null) {
            bindingResult.rejectValue("email", null, "This email is in use");

            return registrationForm(registrationForm, model);
        }

        String password = registrationForm.getPassword();
        String confirmPassword = registrationForm.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            bindingResult.rejectValue("confirmPassword", null, "Passwords do not match");

            return registrationForm(registrationForm, model);
        }

        User user = buildUserFromForm(registrationForm);

        userService.createUser(user);

        return "redirect:/register/success";
    }

    @GetMapping("/register/success")
    public String success() {
        return "registration-complete";
    }

    private User buildUserFromForm(RegistrationForm form) {
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setEnabled(true);

        return user;
    }
}
