package com.simplon.apicynthia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.simplon.apicynthia.model.Contest;

@Repository
public interface ContestRepository extends CrudRepository<Contest, Long>{


}
    
    
