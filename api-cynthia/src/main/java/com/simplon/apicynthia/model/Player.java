package com.simplon.apicynthia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Player {

    @Id // Clé primaire
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Auto increment
    private Long id;

    private String email;

    @Column(length=30)
    private String nickname;

    @OneToMany(mappedBy = "winner") 
    // one winner to many contest 
    // le champ dans l'entité contest avec qui je fais la relation s'appelle winner
    //Ne pas mettre iterable dans l'entité car on ne peut pas modifier (pas de update) possible c'est immuable
    // mettre List pour pouvoir faire des update
    private List<Contest> wins;

    @ManyToMany(cascade = CascadeType.PERSIST)
    // Cascade met à jour la BDD par rapport à la liste constests
    // je choisis comment va s'appeler la table intermédiaire
    @JoinTable(name="player_contest",
        joinColumns = { @JoinColumn(name = "player_id") },
        inverseJoinColumns = { @JoinColumn(name="contest_id") }
    )
    private List<Contest> contests= new ArrayList<Contest>();
//new ArrayList permet de donner une valeur par défaut

}