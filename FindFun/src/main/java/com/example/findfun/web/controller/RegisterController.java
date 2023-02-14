package com.example.findfun.web.controller;


import com.example.findfun.model.Role;
import com.example.findfun.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class RegisterController {

    private final UserService service;

    public RegisterController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String signupPage(){
        return "signUp";
    }

    @PostMapping
    public String signUp(@RequestParam String username,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String repeatPassword){

        service.register(username, password, repeatPassword, email, Role.ROLE_USER);
        return "redirect:/login";
    }
}
