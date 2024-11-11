package de.fhdo.eborrow.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Lob
    private String description;
    private List<Platform> platforms; 
    private List<Genre> genres;

    @DateTimeFormat
    private LocalDate publicationDate;
    private AgeRating ageRating;
    private String developer;
    private String publisher;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] gameImage;

    @OneToMany(mappedBy = "game", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Review> reviews = new HashSet<>();


    // TODO Überflüssig? Sollte ein Spiel wissen, welcher Spieler es auf die Liste gepackt hat? 
    /*@ManyToMany(mappedBy = "games")
    private Set<User> users = new HashSet<>();*/

    public Game() {

    }

    public Game(Long id, String title, String description, List<Platform> platforms, List<Genre> genres, LocalDate publicationDate,
            AgeRating ageRating, String developer, String publisher, byte[] gameImage) {
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

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(AgeRating ageRating) {
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

    public byte[] getGameImage() {
        return gameImage;
    }

    public void setGameImage(byte[] gameImage) {
        this.gameImage = gameImage;
    }

    public Set<Review> getReviews() {
        return reviews; 
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews; 
    }

    //TODO Überflüssig? 
    /*public Set<User> getUsers() {
        return users; 
    }

    public void getUsers(Set<User> users) {
        this.users = users; 
    }
    */

}
