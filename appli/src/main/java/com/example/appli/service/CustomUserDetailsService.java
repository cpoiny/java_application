package com.example.appli.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.appli.model.User;
import com.example.appli.model.Role;
import com.example.appli.repository.UserRepository;


//Sert à utiliser les données utilisateur
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);
        if(user == null) 
            throw new UsernameNotFoundException("L'utilisateur " + username + " n'existe pas.");
        

        Set<Role> roles = new HashSet<Role>();
        roles = user.getRoles();

            // on appelle le new org.springframework car on va utiliser une classe User de UserDetails et on a deja une classe User alors
            // Spring ne saurait pas lequel utilisé
            // on definit dans le constructeur comment le user sera construit
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            null);
    };
}