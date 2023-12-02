package com.simplon.apicynthia.model.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.simplon.apicynthia.model.Contest;
import com.simplon.apicynthia.model.Player;

import lombok.Data;

@Data
public class ContestDTO {

    private Long id;
    private Date startDate;
    private String game = new String();
    private String winner = new String();

    private List<String> players = new ArrayList<String>();

    public ContestDTO(Contest contest){
        this.id = contest.getId();
        this.startDate = contest.getStartDate();
        this.game = contest.getGame().getId() + " - " + contest.getGame().getTitle();
        if (contest.getWinner() !=null) {
            this.winner = contest.getWinner().getNickname();
        } else {
            this.winner = "Pas de vainqueur";
        }
        // version ternaire
      // this.winner = contest.getWinner()!=null ? contest.getWinner().getNickname() : "Pas de vainqueur";
      
      // Liste pour récuperer la liste de tous les players 
      List<String> listPlayers = new ArrayList<String>();
      // Liste vide qui va être remplie

      // Je recupère tous mes joueurs
      if (contest.getPlayers() != null) {
        for (Player player  : contest.getPlayers()){
            listPlayers.add(player.getId()+ " - " + player.getNickname()
                  + "(" + player.getEmail() + ")");
        }
      } else {
        this.players = new ArrayList<String>();
      }
      this.players = listPlayers;

      // if (contest.getPlayers() != null) {
      //   for (Player player: contest.getPlayers()){
      //     listPlayers.add(player);
      //   }
      //   this.players = listPlayers;
      // }
    }    
}