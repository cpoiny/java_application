package com.simplon.apicynthia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simplon.apicynthia.model.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
    
}