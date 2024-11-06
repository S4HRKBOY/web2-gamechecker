package de.fhdo.eborrow.dto;
import java.time.LocalDate; 

public class GameDto {

    private Long id; 

    private String title; 
    private String description; 
    private int licences; 
    private int remainingLicences;
    private String genre; 
    private LocalDate publication; 
    private int age; 
    private String developer; 
    private String publisher; 
    private byte[] image;

    public GameDto(Long id, String title, String description, int licences, int remainingLicences, String genre, LocalDate publication,
            int age, String developer, String publisher, byte[] image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.licences = licences;
        this.remainingLicences = remainingLicences; 
        this.genre = genre;
        this.publication = publication;
        this.age = age;
        this.developer = developer;
        this.publisher = publisher;
        this.image = image;
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

    public int getLicences() {
        return licences;
    }

    public void setLicences(int licences) {
        this.licences = licences;
    }

    public int getRemainingLicences() {
        return remainingLicences;
    }

    public void setRemainingLicences(int remainingLicences) {
        this.remainingLicences = remainingLicences;
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

    
}
