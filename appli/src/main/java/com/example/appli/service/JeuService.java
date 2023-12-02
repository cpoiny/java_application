package com.example.appli.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appli.model.Jeu;
import com.example.appli.repository.JeuRepository;

import lombok.Data;

@Data
@Service
public class JeuService {

    @Autowired
    JeuRepository jeuRepository;

    public Iterable<Jeu> getJeux(){
        return jeuRepository.getJeux();
    }

    public Jeu getJeu(long id){
        return jeuRepository.getJeuById(id);
    }

    public Jeu saveJeu(Jeu j){
        return jeuRepository.addJeu(j);
    }

    public Jeu modifierJeu(Jeu j, Long id) {
        return jeuRepository.modifyJeu(j, id);
    }
    
    public boolean deleteJeu(Long id) {
        return jeuRepository.deleteGamebyId(id);
    }

    //Query
    public List<Jeu> searchJeu(String mot){
        return jeuRepository.searchJeu(mot);
    }
}