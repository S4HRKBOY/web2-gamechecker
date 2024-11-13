package de.fhdo.eborrow.domain;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AccountBuilder {
    private Long id;
    private String prename;
    private String surname;
    private LocalDate birthday;
    private String username;
    private String email;
    private String password;
    private byte[] profilePicture;
    private boolean isPublisher;

    private List<Game> taggedGames;
    // TODO Zak: Reviews hinzufuegen
    // private List<Review> writtenReviews;

    public AccountBuilder() {
    }
    
    // region getter
    public Long getId() {
        return id;
    }

    public AccountBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPrename() {
        return prename;
    }

    public AccountBuilder setPrename(String prename) {
        this.prename = prename;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public AccountBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public AccountBuilder setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AccountBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public AccountBuilder setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public AccountBuilder setPublisher(boolean publisher) {
        isPublisher = publisher;
        return this;
    }

    public List<Game> getTaggedGames() {
        return taggedGames;
    }

    public AccountBuilder setTaggedGames(List<Game> taggedGames) {
        this.taggedGames = taggedGames;
        return this;
    }
    // endregion

    public Account build() {
        validateInputs();

        return new Account(this);
    }

    void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
