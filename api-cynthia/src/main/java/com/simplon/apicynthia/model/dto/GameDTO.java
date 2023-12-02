package com.simplon.apicynthia.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.simplon.apicynthia.model.Contest;
import com.simplon.apicynthia.model.Game;

import lombok.Data;

@Data
public class GameDTO {
    private Long id;
    private String title;
    private Integer min;
    private Integer max;
    private List<String> contests;

    public GameDTO(Game game) {
        this.id = game.getId();
        this.title = game.getTitle();
        this.min = game.getMin();
        this.max = game.getMax();
        List<String> strings = new ArrayList<String>();
        
        if( game.getContests() != null ) {
            for (Contest contest : game.getContests()) {
                strings.add("Partie n°" + contest.getId() + " du " 
                            + contest.getStartDate() );
            }
        }else {
            this.contests = new ArrayList<String>();
        }
        this.contests = strings;
    }
}
