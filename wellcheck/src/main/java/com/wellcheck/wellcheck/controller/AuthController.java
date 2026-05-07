package com.wellcheck.wellcheck.controller;

import com.wellcheck.wellcheck.model.User;
import com.wellcheck.wellcheck.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") User user,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) return "auth/register";
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("emailError", "This email is already registered");
            return "auth/register";
        }
        userService.registerUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }
}