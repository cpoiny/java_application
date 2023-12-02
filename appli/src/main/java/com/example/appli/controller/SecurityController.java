package com.example.appli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.appli.model.User;
import com.example.appli.service.UserService;

@Controller
public class SecurityController {

    @Autowired
    UserService userService;

@GetMapping("/login")
public String login(){
    return "loginForm";
}


// ----------------------------------------- INSCRIPTION ---------------------

@GetMapping("/inscription")
public String inscription(Model model){
    model.addAttribute("user", new User());
    return "registerForm";
}

@PostMapping("/inscription")
    public String inscription(@ModelAttribute User user, Model model){
        if (userService.existsByUsername(user.getUsername())){
            model.addAttribute("user", user);
                return "registerForm";
        }
        else {
            userService.saveUser(user);
        } 
           return "redirect:/login";  
    }
}