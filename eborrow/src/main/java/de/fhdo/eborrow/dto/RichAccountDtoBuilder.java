package de.fhdo.eborrow.dto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RichAccountDtoBuilder{
    private Long id;
    private String prename;
    private String surname;
    private LocalDate birthday;
    private String username;
    private String email;
    private String password;
    private byte[] profilePicture;
    private boolean isPublisher;

    private List<GameDto> taggedGames;

    // region getter
    public Long getId() {
        return id;
    }

    public RichAccountDtoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPrename() {
        return prename;
    }

    public RichAccountDtoBuilder setPrename(String prename) {
        this.prename = prename;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RichAccountDtoBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public RichAccountDtoBuilder setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RichAccountDtoBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RichAccountDtoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RichAccountDtoBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public RichAccountDtoBuilder setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public RichAccountDtoBuilder setPublisher(boolean publisher) {
        isPublisher = publisher;
        return this;
    }

    public List<GameDto> getTaggedGames() {
        return taggedGames;
    }

    public RichAccountDtoBuilder setTaggedGames(List<GameDto> taggedGames) {
        this.taggedGames = taggedGames;
        return this;
    }
    // endregion

    public RichAccountDto build() {
        validateInputs();

        return new RichAccountDto(this);
    }

    private void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
