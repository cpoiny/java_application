package com.simplon.apicynthia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.simplon.apicynthia.model.Contest;
import com.simplon.apicynthia.model.Player;
import com.simplon.apicynthia.model.dto.ContestDTO;
import com.simplon.apicynthia.model.dto.PlayerDTO;
import com.simplon.apicynthia.service.PlayerService;

@RestController
public class PlayerController {
 
    @Autowired
    private PlayerService playerService;

    /* GET all players */
    @GetMapping("/players")
    public Iterable<PlayerDTO> getAllPlayers(){
        Iterable<Player> players = playerService.getAllPlayers();
        List<PlayerDTO> playersDTO = new ArrayList<PlayerDTO>();

        for (Player player : players) {
            playersDTO.add(new PlayerDTO(player));
        }
        return playersDTO;
    }

    /*
     * GET : one player
     */
    @GetMapping("/player/{id}")
    public PlayerDTO getOnePlayer(@PathVariable("id") Long id){
        Optional <Player> p = playerService.getOnePlayer(id);
        if (p.isPresent()){
            return new PlayerDTO(p.get());
        }else {
            return null;
        }
    }

    /*
     * POST : Add a player
     */
     @PostMapping("/player")
     public PlayerDTO addPlayer(@RequestBody Player player){
        return new PlayerDTO(playerService.savePlayer(player));
     }


     /* 
      * DELETE : Delete a player
      */
    @DeleteMapping("/player/{id}")
    public boolean deletePlayer(@PathVariable("id") long id){
        Optional<Player> p = playerService.getOnePlayer(id);
        if (p.isPresent()){
            playerService.deletePlayer(id);
            return true;
        }else{
            return false;
        }
    }

    /*
     * PUT : Update a player
     */
    @PutMapping("/player/{id}")
    public PlayerDTO updatePayer(@PathVariable("id") long id, @RequestBody Player player){
        Optional<Player> p = playerService.getOnePlayer(id);

        // if (p.isPresent()) {
        //     Player playerToUpdate = p.get();

        //     if (player.getEmail() != null){
        //         playerToUpdate.setEmail(player.getEmail());
        //     }

        //     if (player.getNickname() != null) {
        //         playerToUpdate.setNickname(player.getNickname());
        //     }

        //     return new PlayerDTO(playerService.savePlayer(playerToUpdate));

        // }
        //     return null;
        // }

        if (p.isPresent()) {
            player.setId(id);
             return new PlayerDTO(playerService.savePlayer(player));
        }
        return null;
    }

// on veut récupérer la liste des contests par player
@GetMapping("player/{id}/contests")
public Iterable<ContestDTO> contests(@PathVariable("id") long id){
    Optional<Player> player = playerService.getOnePlayer(id);

    if(player.isPresent()){
        List<Contest> contests = player.get().getContests();
        List<ContestDTO> contestsDTO = new ArrayList<ContestDTO>();

        for (Contest contest : contests) {
            contestsDTO.add(new ContestDTO(contest));
        }
        return contestsDTO;
    }
    return null;
    
}

}

