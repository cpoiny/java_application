package com.example.appli.model;

import java.util.List;

import lombok.Data;

@Data
public class Joueur {

    private Long id;
    
    private String nickname;
    
    private String email;

    private List<String> wins;

    private Integer contests;
    
}