package de.fhdo.eborrow.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum Platform {
    WINDOWS("Windows"),
    PLAYSTATION_4("Playstation 4"), 
    PLAYSTATION_5("Playstation 5"), 
    XBOX_ONE("XBox One"), 
    XBOX_SERIES("XBox Series");

    private final String platform; 

    Platform(String platform) {
        this.platform = platform; 
    }

    public String getPlatform() {
        return platform; 
    }
}
