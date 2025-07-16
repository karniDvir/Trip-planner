package com.travelplanner.controller;

import com.travelplanner.model.User;
import com.travelplanner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling home, registration, and login related requests.
 */
@Controller
public class HomeController {

    /**
     * Handles requests to the root URL ("/").
     *
     * @return the name of the home view (index.html).
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @Autowired
    private UserService userService;

    /**
     * Handles requests to show the registration form.
     *
     * @param model the model to pass data to the view.
     * @return the name of the registration view (register.html).
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Handles the form submission for user registration.
     *
     * @param user the user object to be registered.
     * @param bindingResult the result of validation.
     * @param model the model to pass data to the view.
     * @return a redirect to the login page on success, or the registration view on failure.
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            bindingResult.addError(new FieldError("user", "username", e.getMessage()));
            return "register";
        }
    }

    /**
     * Handles requests to show the login form.
     *
     * @return the name of the login view (login.html).
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Handles requests to the error page.
     *
     * @return the name of the error view (error.html).
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
