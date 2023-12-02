package com.simplon.apicynthia.model;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data /* permet de ne pas ajouter les g(s)etters dans la classe */
@Entity /* cette classe a le role d'une entité */
public class Game {
    // attribut ou propriété correspond à la table créée dans la BDD, toujours en private

    @Id /* Clé primaire */
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* auto-incrementation */
    private Long id;

    private String title;

    // @Column(name= "min_players") => si on veut associer a un champ dans la BDD qui a un nom différent, pour faire la correspondance
    private Integer min;

    private Integer max;

    // Ce champ permet de récupérer toutes les parties liées à ce game
    // Le champ "contests" ne va pas être créé dans la BDD dans la table game
    //on importe java.util pour List
    @OneToMany(mappedBy = "game")
    private List<Contest> contests = new ArrayList<Contest>();
    // on met contestS au pluriel car on récupère une liste une collection


    // On crée un nouveau constructeur donc on déclare celui par défaut pour qu'il continue d'exister
    public Game(Long id, String title, Integer min, Integer max) {
        this.id = id;
        this.title = title;
        this.min = min;
        this.max = max;
    }

    public Game(){}

}