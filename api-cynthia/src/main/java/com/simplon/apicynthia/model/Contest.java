package com.simplon.apicynthia.model;



import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import lombok.Data;

@Entity
@Data
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // on importe java.sql.Date et pas util car on manipule une donnée pour BDD
    @Column(name="start_date")
    private Date startDate;

    @ManyToOne // Many contest to one game, ManyToOne sur la table qui contient la FK
    //@JoinColumn(name="game_id") pour pouvoir retrouver le champ dans la BDD
    private Game game;

    @ManyToOne //Many contest to one winner
    private Player winner;

    // MappedBy correspond à la réciproque, quel est le champ qui est lié dans l'autre table
    @ManyToMany(mappedBy = "contests", cascade = CascadeType.PERSIST)
    private List<Player> players = new ArrayList<Player>();
    
}