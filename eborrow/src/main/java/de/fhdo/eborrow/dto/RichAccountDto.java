package de.fhdo.eborrow.dto;

import de.fhdo.eborrow.dto.builder.RichAccountDtoBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public class RichAccountDto {
    private Long id;
    @NotNull
    private String prename;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @Past
    private LocalDate birthday;
    @Email
    @NotNull
    private String email;
    private String password;
    private String profilePicture;
    private boolean publisher;
    
    private List<GameDto> taggedGames;

    private RichAccountDto(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, String profilePicture) {
        this.id = id;
        this.prename = prename;
        this.surname = surname;
        this.birthday = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public RichAccountDto(RichAccountDtoBuilder builder) {
        this(builder.getId(),
                builder.getPrename(),
                builder.getSurname(),
                builder.getBirthday(),
                builder.getUsername(),
                builder.getEmail(),
                builder.getPassword(),
                builder.getProfilePicture());

        this.publisher = builder.isPublisher();
        this.taggedGames = builder.getTaggedGames();
    }

    // region getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isPublisher() {
        return publisher;
    }

    public void setPublisher(boolean publisher) {
        this.publisher = publisher;
    }

    public List<GameDto> getTaggedGames() {
        return taggedGames;
    }

    public void setTaggedGames(List<GameDto> taggedGames) {
        this.taggedGames = taggedGames;
    }
    // endregion

    @Override
    public String toString() {
        return "RichAccountDto{" +
                "id=" + id +
                ", prename='" + prename + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture=" + (profilePicture != null ? "yes" : "no") +
                ", publisher=" + publisher +
                ", taggedGames=" + taggedGames +
                '}';
    }
}
