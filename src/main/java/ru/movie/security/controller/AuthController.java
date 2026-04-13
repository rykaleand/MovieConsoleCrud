package ru.movie.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.movie.entity.User;
import ru.movie.exception.UserAlreadyExistsException;
import ru.movie.service.UserApi;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserApi userApi;

    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/sign-up")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
            return "registration";
        }
        try {
            userApi.addUser(user);
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("message", e.getMessage());
            return "registration";
        }
    }
}