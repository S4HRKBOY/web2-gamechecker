package de.fhdo.eborrow.domain;

public enum Platform {
    MAC_OS("macOS"),
    NINTENDO_SWITCH("Nintendo Switch"),
    PLAYSTATION_4("Playstation 4"),
    PLAYSTATION_5("Playstation 5"),
    WINDOWS("Windows"),
    XBOX_ONE("XBox One"),
    XBOX_SERIES("XBox Series"),
    DEFAULT("Unbekannt");

    private final String platform;

    Platform(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return platform;
    }

    public static Platform fromDisplayName(String displayName) {
        for (Platform platform : Platform.values()) {
            if (platform.getPlatform().equalsIgnoreCase(displayName)) {
                return platform;
            }
        }
        //return DEFAULT;
        return null; 
    }
}
