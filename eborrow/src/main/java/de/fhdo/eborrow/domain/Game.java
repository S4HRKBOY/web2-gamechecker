package de.fhdo.eborrow.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String description;

    @Column(nullable = false)
    @ElementCollection 
    @Enumerated(EnumType.STRING)
    private List<Platform> platforms; 

    @Column(nullable = false)
    @ElementCollection 
    @Enumerated(EnumType.STRING)
    private List<Genre> genres;

    @Column(nullable = false)
    @DateTimeFormat
    private LocalDate publicationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;

    @Column(nullable = false)
    private String developer;

    @Column(nullable = false)
    private String publisher;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] gameImage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
	private Set<Review> reviews = new HashSet<>();

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
    

}
