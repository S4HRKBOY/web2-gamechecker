package de.fhdo.eborrow.dto;
import java.time.LocalDate;
import java.util.List;

import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform; 

public class GameDto {

    private Long id; 

    private String title; 
    private String description; 
    private List<Platform> platforms; 
    private List<Genre> genres; 
    private LocalDate publicationDate; 
    private int ageRating; 
    private String developer; 
    private String publisher; 
    private String gameImage;

    public GameDto(Long id, String title, String description, List<Platform> platforms, int licences, int remainingLicences, List<Genre> genres, LocalDate publicationDate,
            int ageRating, String developer, String publisher, String gameImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.platforms = platforms; 
        this.genres = genres;
        this.publicationDate = publicationDate;
        this.ageRating = ageRating;
        this.developer = developer;
        this.publisher = publisher;
        this.gameImage = gameImage;
    }

    public GameDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Platform> getPlatforms() {
        return platforms; 
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms; 
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public void setAge(int ageRating) {
        this.ageRating = ageRating;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }

    
}
