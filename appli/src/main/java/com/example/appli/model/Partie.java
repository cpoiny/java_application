package com.example.appli.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Partie {

    private Long id;
    private Date startDate;
    private String game;
    private String winner;
    private List<String> players = new ArrayList<String>();

}