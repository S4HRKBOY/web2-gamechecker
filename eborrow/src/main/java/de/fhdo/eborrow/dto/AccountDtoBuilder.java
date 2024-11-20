package de.fhdo.eborrow.dto;

import java.time.LocalDate;
import java.util.List;

public class AccountDtoBuilder{
    private Long id;
    private String prename;
    private String surname;
    private LocalDate birthday;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private boolean isPublisher;

    private List<GameDto> taggedGames;
    // TODO Zak: Reviews hinzufuegen
    // private List<Review> writtenReviews;

    // region getter
    public Long getId() {
        return id;
    }

    public AccountDtoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPrename() {
        return prename;
    }

    public AccountDtoBuilder setPrename(String prename) {
        this.prename = prename;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public AccountDtoBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public AccountDtoBuilder setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AccountDtoBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountDtoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountDtoBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public AccountDtoBuilder setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public AccountDtoBuilder setPublisher(boolean publisher) {
        isPublisher = publisher;
        return this;
    }

    public List<GameDto> getTaggedGames() {
        return taggedGames;
    }

    public AccountDtoBuilder setTaggedGames(List<GameDto> taggedGames) {
        this.taggedGames = taggedGames;
        return this;
    }
    // endregion

    public AccountDto build() {
        validateInputs();

        return new AccountDto(this);
    }

    private void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
