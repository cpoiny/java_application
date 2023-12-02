package com.simplon.apicynthia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplon.apicynthia.model.Contest;
import com.simplon.apicynthia.repository.ContestRepository;

@Service
public class ContestService {

    @Autowired /* signifie connexion automatique / permet de faire l'injection de dépendences sans instancié */
    private ContestRepository ContestRepository;
    
    /* le @Autowired permet de ne pas créer de constructeur */
    /*  public ContestService(ContestRepository ContestRepository) {
        this.ContestRepository = ContestRepository;
    } */


    /* Méthodes en public pour être utilisable en dehors */
    
    
    /**
     * R du CRUD Methodes Récupérer tous les jeux 
    */
    /* Iterable est une classe générique : je vais renvoyer un objet d'une classe Contest qui peut etre itérable (ou on peut boucler dessus) */
    public Iterable<Contest> getAllContests() {
        return ContestRepository.findAll();
    }


    /**
     * R du CRUD Récupérer un jeu avec son id
    */
    /*  Optional est une classe générique: je vais renvoyer ou pas un objet Contest ou alors null, final pour constante signifie que l'id ne peut pas etre modifié */
    public Optional<Contest> getContest(final long id){
        return ContestRepository.findById(id);
    }

    /**
     * RU du CRUD Ajouter/Modifier un jeu
    */
     public Contest saveContest(Contest g) {
        return this.ContestRepository.save(g);
     }


    /**
      * D du CRUD Supprimer un jeu
    */
    public void deleteContest(final long id){
        this.ContestRepository.deleteById(id);
      }

}