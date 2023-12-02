package com.example.appli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.appli.model.Jeu;
import com.example.appli.service.JeuService;

@Controller
public class JeuController {

    @Autowired
    JeuService jeuService;

    @GetMapping("/jeux")
    public String jeux(Model model) {
        Iterable<Jeu> jeux = jeuService.getJeux();
        model.addAttribute("jeux", jeux);
        return "jeu/jeux";
    }

    @GetMapping("/jeu/{id}/fiche")
    public String jeu (@PathVariable("id") long id, Model model){
        Jeu jeu = jeuService.getJeu(id);
        model.addAttribute("jeu", jeu);
        return "jeu/fiche";
    }
// -------------------------------------------- AJOUTER -------------------------------


    @GetMapping("/jeu/ajouter")
        public String ajouter(Model model){
            Jeu jeu = new Jeu();
            model.addAttribute("jeu", jeu);
            return "jeu/form";
        }

    @PostMapping("/jeu/ajouter")
    public ModelAndView sauvegarder(@ModelAttribute Jeu jeu) {
        jeuService.saveJeu(jeu);
        return new ModelAndView("redirect:/jeux");
    }

    // ------------------------------------ SUPPRIMER -------------------------------------------

        // Methode GET pour obtenir confirmation de la suppression
     @GetMapping("/jeu/{id}/supprimer")
     public String supprimer(@PathVariable("id") long id, Model model) {
       Jeu j = jeuService.getJeu(id);
       model.addAttribute("jeu", j);
       return "jeu/supprimer";
     }

    // Méthode POST sur le bouton du formulaire pour supprimer
     @PostMapping("jeu/{id}/supprimer")
     public ModelAndView supprimer(@PathVariable("id") long id){
        Jeu j = jeuService.getJeu(id);
        if (j != null) {
            jeuService.deleteJeu(id);
          return new ModelAndView("redirect:/jeux");
        } else {
            return new ModelAndView("redirect:/jeux");
        }
     }



// ------------------------------------ MODIFIER --------------------------------
    //Ajouter le put
  @GetMapping("/jeu/{id}/modifier")
        public String modifier(Model model, @PathVariable("id") Long id){
              Jeu j = jeuService.getJeu(id);
        if (j != null) {
        model.addAttribute("jeu", j);
        }
        return "jeu/form";
    }

    @PostMapping("/jeu/{id}/modifier")
    public ModelAndView sauvegarder(Jeu j, @PathVariable("id") Long id){
        
        jeuService.modifierJeu(j, id);
        //redirection
        return new ModelAndView("redirect:/jeux");

}


//-------------------------------------
@GetMapping("/")
public String accueil(Model model ){
    return "home";
}

}

    
    
