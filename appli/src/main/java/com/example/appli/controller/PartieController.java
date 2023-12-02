package com.example.appli.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.appli.model.Jeu;
import com.example.appli.model.Joueur;
import com.example.appli.model.Partie;
import com.example.appli.service.JeuService;
import com.example.appli.service.JoueurService;
import com.example.appli.service.PartieService;

@Controller
@RequestMapping("/parties")
public class PartieController {
    
    @Autowired
    PartieService partieService;

    @Autowired
    JeuService jeuService;

    @Autowired
    JoueurService joueurService;

    // ------------------------- GET PARTIES -------------------
    @GetMapping("")
    public String parties(Model model){
        Iterable<Partie> parties = partieService.getParties();
        model.addAttribute("parties", parties);
        return "partie/liste";
    }


    // --------------------- GET une PARTIE ---------------------
    @GetMapping("/{id}/fiche")
    public String partie (@PathVariable Long id, Model model){
        Partie partie = partieService.getPartie(id);
        model.addAttribute("partie", partie);
        return "partie/fichePartie";
    }


    // ---------------------AJOUTER une PARTIE ------------------
    
    /*
    * Afficher le formulaire
    */

    @GetMapping("/ajouter")
    public String ajouterPartie(Model model){
        Iterable<Jeu> jeux = jeuService.getJeux();
        Partie partie = new Partie();
        model.addAttribute("partie", partie);
        model.addAttribute("jeux", jeux);
        return "partie/formPartie";
    }
    
    @PostMapping("/ajouter")
    public ModelAndView sauvegarderPartie(@RequestParam String start_date, 
                                          @RequestParam Integer game_id){
        partieService.savePartie(start_date, game_id);
        return new ModelAndView("redirect:/parties");
    }


    //------------------SUPPRIMER une PARTIE ------------------------
    @GetMapping("/{id}/supprimer")
    public String supprimerPartie(@PathVariable Long id, Model model){
        Partie p = partieService.getPartie(id);
        model.addAttribute("partie", p);
        return "partie/supprimerPartie";
    }

    @PostMapping("/{id}/supprimer")
    public ModelAndView supprimerPartie(@PathVariable Long id){
        Partie p = partieService.getPartie(id);
        if (p != null){
            partieService.deletePartie(id);
            return new ModelAndView("redirect:/parties");
        } else {
            return new ModelAndView("redirect:/parties");
        }
        }
        
    // ----------------MODIFIER une PARTIE -------------------------

        @GetMapping("/{id}/modifier")
        public String modifierPartie(Model model, @PathVariable("id") Long id){
              Partie p = partieService.getPartie(id);
        if (p != null) {
        model.addAttribute("partie", p);
        }
        return "partie/formPartie";
    }

    @PostMapping("/{id}/modifier")
    public ModelAndView sauvegarderPartie(Partie p, @PathVariable("id") Long id){
        partieService.updatePartie(p);
        return new ModelAndView("redirect:/parties");
}
    

    //--------------SELECT Winner --------------------------------------

    @GetMapping("/{id}/choisir-vainqueur")
    public String selectionnerWinner (Model model, @PathVariable("id") Long id){
        Partie p = partieService.getPartie(id);
        List<Joueur> participants = partieService.getParticipants(p);
         if (p != null) {
            model.addAttribute("partie", p);
            if(participants != null){
                model.addAttribute("participants", participants);
            }
        }
        return "partie/form_vainqueur";
    }

    @PostMapping("/{id}/choisir-vainqueur")
    public ModelAndView sauvegarderVainqueur(@PathVariable("id") Long id, @RequestParam Long joueur_id){
        try {
            partieService.setVainqueur(id, joueur_id);
        } catch (Exception e) {
           return new ModelAndView("redirect:/erreur/418");
           //throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Cette partie n'existe pas");
        }
        return new ModelAndView("redirect:/parties");
}

// -------------------------------AJOUTER un participant ---------------------------------

@GetMapping("/{id}/ajouter-participant")
public String ajouterParticipant (Model model, @PathVariable("id") Long id){
    Partie p = partieService.getPartie(id);
    Iterable<Joueur> joueurs = joueurService.getJoueurs();
    if (p != null) {
        model.addAttribute("partie", p);
     if (joueurs != null) {
        model.addAttribute("joueurs", joueurs);
    }
}
return "partie/form_participant";
}

@PostMapping("/{id}/ajouter-participant")
public ModelAndView addParticipant(@PathVariable("id") Long id, @RequestParam Long joueur_id){
    partieService.addParticipant(id, joueur_id);
    return new ModelAndView("redirect:/parties");
}


// -------------------------- SUPPRIMER PARTICIPANT -------------------



@GetMapping("/{id}/supprimer-participant")
public String supprimerParticipant (Model model, @PathVariable("id") Long id){
    Partie p = partieService.getPartie(id);
    Iterable<Joueur> participants = partieService.getParticipants(p);
    if (p != null) {
        model.addAttribute("partie", p);
     if (participants != null) {
        model.addAttribute("participants", participants);
    }
}
return "partie/form_supprimer_participant";
}

@PostMapping("/{id}/supprimer-participant")
public ModelAndView supprimerParticipant(@PathVariable("id") Long id, @RequestParam Long joueur_id){
    partieService.supprimerParticipant(id, joueur_id);
    return new ModelAndView("redirect:/parties");
}
}

