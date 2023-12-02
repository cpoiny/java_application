package com.example.appli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appli.model.Joueur;
import com.example.appli.repository.JoueurRepository;

import lombok.Data;

@Data
@Service
public class JoueurService {

    @Autowired
    JoueurRepository joueurRepository;

    public Iterable<Joueur> getJoueurs(){
        return joueurRepository.getAllJoueurs();
    }

    public Joueur getJoueur(long id) {
        return joueurRepository.getJoueurById(id);
    }
    
    public Joueur saveJoueur(Joueur joueur) {
        return joueurRepository.addJoueur(joueur);
    }

    public Boolean deleteJoueur(long id) {
        return joueurRepository.deleteJoueurById(id);
    }

    // Cyn
    public Joueur modifyJoueur (Long id, Joueur j) {
        return joueurRepository.modifyJoueur(id, j);
    }
}