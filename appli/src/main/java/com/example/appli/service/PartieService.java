package com.example.appli.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.appli.model.Partie;
import com.example.appli.model.Joueur;
import com.example.appli.repository.PartieRepository;

import lombok.Data;

@Data
@Service
public class PartieService {

    @Autowired
    PartieRepository partieRepository;

    public Iterable<Partie> getParties(){
        return partieRepository.getAllParties();
    }

    public Partie getPartie(long id) {
        return partieRepository.getPartieById(id);
    }
    
    public Partie savePartie(String start_date, Integer game_id) {
        return partieRepository.addPartie(start_date, game_id);
    }

    public Boolean deletePartie(long id) {
        return partieRepository.deletePartieById(id);
    }

    public Partie updatePartie (Partie j) {
        return partieRepository.updatePartie(j);
    }

    public List<Joueur> getParticipants(Partie p){
        return partieRepository.getParticipants(p);
    }

    public Partie setVainqueur(Long id, Long idJoueur) throws Exception {
        //Etape de vérification "la partie existe?"
        Partie p = partieRepository.getPartieById(id);
        if (p != null) {
            return partieRepository.setVainqueur(id, idJoueur);
        }
        throw new Exception("La partie " + id + " n'existe pas.");
    }
    
   public Partie addParticipant(Long id, Long idJoueur){
       return partieRepository.addParticipant(id, idJoueur);
   }

   public Partie supprimerParticipant(Long id, Long idJoueur){
       return partieRepository.supprimerParticipant(id, idJoueur);
   }

    
}