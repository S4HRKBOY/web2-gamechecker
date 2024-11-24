package de.fhdo.eborrow.domain;

public enum AgeRating {
    USK_O("USK 0"), 
    USK_6("USK 6"), 
    USK_12("USK 12"),
    USK_16("USK 16"),
    USK_18("USK 18"),
    DEFAULT("Unbekannt");
    
    private final String ageRating; 

    AgeRating(String ageRating) {
        this.ageRating = ageRating; 
    }

    public String getAgeRating() {
        return ageRating; 
    }

    public static AgeRating fromDisplayName(String displayName) {
        for (AgeRating ageRating : AgeRating.values()) {
            if (ageRating.getAgeRating().equalsIgnoreCase(displayName)) {
                return ageRating;
            }
        }
       return DEFAULT; 
    }
}
