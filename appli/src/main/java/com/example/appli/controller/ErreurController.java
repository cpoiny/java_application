package com.example.appli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/erreur")
@Controller
public class ErreurController {
    
@GetMapping("/418")
public String getErreur418(){
    return "erreur/418";
}

}