package de.fhdo.eborrow.dto;
import java.time.LocalDate;
import java.util.List;

//TODO: ReviewDto -> Ids übergeben
//      Dto direkt im Controller umwandeln
//      Folgerequest für Dependencies -> Dependencies als Referenz übergeben (IDs, HATEOAS)
public class RichGameDto {

    private Long id; 

    private String title; 
    private String description; 
    private List<String> platforms; 
    private List<String> genres; 
    private LocalDate publicationDate; 
    private String ageRating; 
    private String developer; 
    private String publisher; 
    private String gameImage;
    private List<ReviewDto> reviews; 

    public RichGameDto(Long id, String title, String description, List<String> platforms, int licences, int remainingLicences, List<String> genres, LocalDate publicationDate,
            String ageRating, String developer, String publisher, String gameImage) {
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

    public RichGameDto() {

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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
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

    public List<ReviewDto> getReviewsDto() {
        return reviews; 
    }

    public void setReviewsDto(List<ReviewDto> reviews) {
        this.reviews = reviews; 
    }

    
}
