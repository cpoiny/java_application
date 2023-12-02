package com.example.appli.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=30, unique=true)
    // unique = true signifie que l'identifiant doit être unique dans ma BDD
    private String username;

    private String password;

    // un user peut avoir plusieurs roles (admin, contributeur, moderateur)
    @ManyToMany
    @JoinTable(
        name="user_role",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id")
    )
    //on importe Set => java.util
    private Set<Role> roles = new HashSet<Role>();

}