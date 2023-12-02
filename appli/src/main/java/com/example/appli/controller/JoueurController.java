package com.example.appli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.appli.model.Joueur;
import com.example.appli.service.JoueurService;

@Controller
public class JoueurController {

    @Autowired
    JoueurService joueurService;

    @GetMapping("/joueurs")
    public String joueurs(Model model) {
        Iterable<Joueur> joueurs = joueurService.getJoueurs();
        // "joueurs" => variable dans le home , joueur => variable dans java
        model.addAttribute("joueurs", joueurs);
        return "joueur/liste";
    }

    @GetMapping("/joueur/{id}/fiche")
    public String fiche(@PathVariable("id") long id, Model model) {
        Joueur j = joueurService.getJoueur(id);
        // c'est la variable joueur qu'on manipule pour l'affichage dans le html ex=> "${joueur.id}"
        model.addAttribute("joueur", j);
        return "joueur/fiche";
    }
    

    //----------------------------------------AJOUTER ------------------------------------------------
    @GetMapping("/joueur/ajouter")
    public String ajouter(Model model){
        Joueur joueur = new Joueur();
        model.addAttribute("joueur", joueur);
        return "joueur/form";
    }
 
    @PostMapping("/joueur/ajouter")
    public ModelAndView sauvegarder(@ModelAttribute Joueur joueur){
        joueurService.saveJoueur(joueur);
        //redirection
        return new ModelAndView("redirect:/joueurs");
    }


    // ------------------------------------- SUPPRIMER --------------------------------------------

    // Methode GET pour obtenir confirmation de la suppression
     @GetMapping("/joueur/{id}/supprimer")
     public String supprimer(@PathVariable("id") long id, Model model) {
       Joueur j = joueurService.getJoueur(id);
       model.addAttribute("joueur", j);
       return "joueur/supprimer";
     }

    // Méthode POST sur le bouton du formulaire pour supprimer
     @PostMapping("joueur/{id}/supprimer")
     public ModelAndView supprimer(@PathVariable("id") long id){
        Joueur j = joueurService.getJoueur(id);
        if (j != null) {
            joueurService.deleteJoueur(id);
          return new ModelAndView("redirect:/joueurs");
        } else {
            return new ModelAndView("redirect:/joueurs");
        }
     }


     // -------------------------------------MODIFIER ---------------------------------------------
     // Methode PUT
    @GetMapping("/joueur/{id}/modifier")
    public String modifier(Model model, @PathVariable("id") long id){
         Joueur j = joueurService.getJoueur(id);
        if (j != null) {
        model.addAttribute("joueur", j);
        }
        return "joueur/form";
    }


    @PostMapping("/joueur/{id}/modifier")
    public ModelAndView sauvegarder(Joueur j, @PathVariable("id") long id){
        
        joueurService.modifyJoueur(id, j);
        //redirection
        return new ModelAndView("redirect:/joueurs");

}


}