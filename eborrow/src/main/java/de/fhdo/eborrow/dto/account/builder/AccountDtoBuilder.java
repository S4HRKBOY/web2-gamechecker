package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.AccountDto;

import java.time.LocalDate;

public class AccountDtoBuilder{
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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public AccountDtoBuilder setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }
    // endregion

    public AccountDto build() {
        validateInputs();

        return new AccountDto(this);
    }

    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
