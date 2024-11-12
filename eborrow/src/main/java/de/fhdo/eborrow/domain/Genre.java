package de.fhdo.eborrow.domain;

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
    STRATEGY("Strategie"),
    DEFAULT("Unbekannt");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public static Genre fromDisplayName(String displayName) {
        for (Genre genre : Genre.values()) {
            if (genre.getGenre().equalsIgnoreCase(displayName)) {
                return genre;
            }
        }
        return DEFAULT;
    }

}
