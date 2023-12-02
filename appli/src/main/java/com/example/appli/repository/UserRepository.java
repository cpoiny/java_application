package com.example.appli.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.appli.model.User;



public interface UserRepository extends JpaRepository<User, Long>{

    //On peut ajouter toutes les methodes existantes grace au JPA en choisissant les propriétés présentes dans la classe utilisée
    // ici on utilise la classe User qui a la propriété username donc on peut faire findByUsername
    //Cette methode findBy renvoie un objet user (si l'user existe) ou null( (si il ne le trouve pas)
    User findByUsername(String username);


    //Methode 3
    Boolean existsByUsername(String username);
    
}