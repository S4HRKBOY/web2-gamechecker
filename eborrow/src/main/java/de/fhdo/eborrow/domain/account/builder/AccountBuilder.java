package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.Account;

import java.time.LocalDate;

public class AccountBuilder {
    protected Long id;
    protected String prename;
    protected String surname;
    protected LocalDate birthday;
    protected String username;
    protected String email;
    protected String password;
    protected byte[] profilePicture;

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
    // endregion

    public Account build() {
        validateInputs();

        return new Account(this);
    }

    void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
