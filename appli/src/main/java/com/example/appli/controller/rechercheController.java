package com.example.appli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.appli.model.Jeu;
import com.example.appli.service.JeuService;


@Controller
@RequestMapping("/recherche")
public class rechercheController {

    @Autowired
    JeuService jeuService;
    
    @GetMapping("/jeu")
    public String rechercheJeu(@RequestParam("search") String mot, Model model){
        /*
         * Dans l'annotation RequestParam, le 1er argument est le nom de la valeur récupéré dans la requête HTTP (par exemple, le name d'un input)
         * Si la variable liée (ici, word) a le memenom que al valeur récupérée (ici, search),
         * on a pas besoin de le préciser dans l'annotation
         */
        List<Jeu> jeux = jeuService.searchJeu(mot);
        model.addAttribute("jeux", jeux);
        model.addAttribute("mot", mot);
        return "recherche/jeux";

    }
}