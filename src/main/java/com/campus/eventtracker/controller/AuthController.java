package com.campus.eventtracker.controller;

import com.campus.eventtracker.model.User;
import com.campus.eventtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String registered,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid email or password. Please try again.");
        }
        if (registered != null) {
            model.addAttribute("registered", true);
        }
        return "login";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam(required = false, defaultValue = "") String name,
                           @RequestParam(required = false, defaultValue = "") String email,
                           @RequestParam(required = false, defaultValue = "") String password,
                           @RequestParam(required = false, defaultValue = "") String confirmPassword,
                           Model model) {

        boolean hasError = false;

        if (name == null || name.trim().length() < 2) {
            model.addAttribute("nameError", "Name must be at least 2 characters");
            hasError = true;
        }
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("emailError", "Email is required");
            hasError = true;
        }
        if (password == null || password.length() < 6) {
            model.addAttribute("passwordError", "Password must be at least 6 characters");
            hasError = true;
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            hasError = true;
        }
        if (userService.emailExists(email)) {
            model.addAttribute("emailError", "Email is already registered");
            hasError = true;
        }

        if (hasError) {
            return "register";
        }

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim());
        user.setPassword(password);
        userService.registerUser(user);
        return "redirect:/login?registered=true";
    }
}