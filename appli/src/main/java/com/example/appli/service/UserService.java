package com.example.appli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.appli.model.User;
import com.example.appli.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        
        // on récupère le password de l'user,on l'encode et on le set sur l'User qui va être save
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // sauvegarder l'user
        return userRepository.save(user);
    }
    
    //Méthode pour vérifier si l'username est déja présent dans la BDD
    public Boolean existsByUsername(String username){

        //1ere méthode :
        // User userInDataBase = userRepository.findByUsername(username);
        // if(userInDataBase == null){
        //     return false;  
        // }
        // return true;


        //2eme méthode : renvoit un boolean en évaluant la méthode findByusername(username) != null
        // return userRepository.findByUsername(username) != null;

        //3eme methode
        return userRepository.existsByUsername(username);
    }
}