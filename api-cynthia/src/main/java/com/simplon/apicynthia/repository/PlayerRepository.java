package com.simplon.apicynthia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simplon.apicynthia.model.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long>{
    
}