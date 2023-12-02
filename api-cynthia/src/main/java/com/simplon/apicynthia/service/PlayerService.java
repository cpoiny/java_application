package com.simplon.apicynthia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplon.apicynthia.model.Player;
import com.simplon.apicynthia.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    /**
     * GET all players
     */
    public Iterable<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    /**
     * GET one player
     */
    public Optional<Player> getOnePlayer(final long id){
        return playerRepository.findById(id);
    }

    /*
     * POST : Add or update a player
     */
    public Player savePlayer(Player p){
        return playerRepository.save(p);
    }

    /*
     * DELETE : Delete a player
     */
    public void deletePlayer(final long id){
        playerRepository.deleteById(id);
    }


}
