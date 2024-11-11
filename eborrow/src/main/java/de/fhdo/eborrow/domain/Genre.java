package de.fhdo.eborrow.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum Genre {
    ACTION("Aktion"), 
    ADVENTURE("Adventure"), 
    FIGHTING("Kampfspiel"), 
    PLATFORMER("Plattformer"), 
    PUZZLE("Puzzle"), 
    RACING("Rennspiel"), 
    ROLE_PLAYING("Rollenspiel"), 
    SHOOTER("Shooter"), 
    SIMULATION("Simulation"), 
    SPORTS("Sport"), 
    STRATEGY("Strategie"); 

    private final String genre; 

    Genre(String genre) {
        this.genre = genre; 
    }

    public String getGenre() {
        return genre; 
    }
}
