package com.simplon.apicynthia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplon.apicynthia.model.Game;
import com.simplon.apicynthia.repository.GameRepository;

@Service
public class GameService {

    @Autowired /* signifie connexion automatique / permet de faire l'injection de dépendences sans instancié */
    private GameRepository gameRepository;
    
    /* le @Autowired permet de ne pas créer de constructeur */
    /*  public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    } */

    @Autowired
    private EntityManager entityManager;

    /* Méthodes en public pour être utilisable en dehors */
    
    
    /**
     * R du CRUD Methodes Récupérer tous les jeux 
    */
    /* Iterable est une classe générique : je vais renvoyer un objet d'une classe Game qui peut etre itérable (ou on peut boucler dessus) */
    public Iterable<Game> getAllGames() {
        return gameRepository.findAll();
    }


    /**
     * R du CRUD Récupérer un jeu avec son id
    */
    /*  Optional est une classe générique: je vais renvoyer ou pas un objet Game ou alors null, final pour constante signifie que l'id ne peut pas etre modifié */
    public Optional<Game> getGame(final long id){
        return gameRepository.findById(id);
    }

    /**
     * RU du CRUD Ajouter/Modifier un jeu
    */
     public Game saveGame(Game g) {
        return this.gameRepository.save(g);
     }


    /**
      * D du CRUD Supprimer un jeu
    */
    public void deleteGame(final long id){
        this.gameRepository.deleteById(id);
      }


    /**
     * SELECT g.* FROM game g WHERE g.title LIKE '%a%'
     */

    public List<Game> searchTitle(String word) {
        String sql = " SELECT new " + Game.class.getName() + " (g.id,g.title, g.min, g.max) " 
                       + " FROM " + Game.class.getName() + " g "
                       + " WHERE g.title LIKE '%" + word +"%'" ;

    //    EntityManager est une classe qui va gérer les objets, et qui va faire les appels a la BDD
        Query query = entityManager.createQuery(sql,Game.class);
        List<Game> liste = new ArrayList<Game>();
        liste = query.getResultList();
        return liste; 
    }

}