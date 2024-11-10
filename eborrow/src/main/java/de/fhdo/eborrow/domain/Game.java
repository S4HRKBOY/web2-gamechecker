package de.fhdo.eborrow.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Lob
    private String description;
    private List<String> platforms; 
    private String genre;

    @DateTimeFormat
    private LocalDate publication;
    private int age;
    private String developer;
    private String publisher;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private List<Review> reviews;

    // TODO Überflüssig? Sollte ein Spiel wissen, welcher Spieler es auf die Liste gepackt hat? 
    /*@ManyToMany(mappedBy = "games")
    private Set<User> users = new HashSet<>();*/

    public Game() {

    }

    public Game(Long id, String title, String description, List<String> platforms, String genre, LocalDate publication,
            int age, String developer, String publisher, byte[] image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.platforms = platforms; 
        this.genre = genre;
        this.publication = publication;
        this.age = age;
        this.developer = developer;
        this.publisher = publisher;
        this.image = image;
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

    public List<String> getPlatforms() {
        return platforms; 
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms; 
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getPublication() {
        return publication;
    }

    public void setPublication(LocalDate publication) {
        this.publication = publication;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void addReview(Review review){
        if(reviews == null){
            reviews = new ArrayList<>();
        }
        review.setGame(this);
        reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /*
    //TODO Überflüssig? 
    public Set<User> getUsers() {
        return users; 
    }

    public void getUsers(Set<User> users) {
        this.users = users; 
    }
    */

}
