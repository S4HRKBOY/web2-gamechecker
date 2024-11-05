package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.Account;

import java.time.LocalDate;

public abstract class AccountBuilder<B extends AccountBuilder<B, A>, A extends Account> {
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

    public B setId(Long id) {
        this.id = id;
        return self();
    }

    public String getPrename() {
        return prename;
    }

    public B setPrename(String prename) {
        this.prename = prename;
        return self();
    }

    public String getSurname() {
        return surname;
    }

    public B setSurname(String surname) {
        this.surname = surname;
        return self();
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public B setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return self();
    }

    public String getUsername() {
        return username;
    }

    public B setUsername(String username) {
        this.username = username;
        return self();
    }

    public String getEmail() {
        return email;
    }

    public B setEmail(String email) {
        this.email = email;
        return self();
    }

    public String getPassword() {
        return password;
    }

    public B setPassword(String password) {
        this.password = password;
        return self();
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public B setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return self();
    }
    // endregion

    protected abstract B self();

    protected abstract A build();

    abstract void validateInputs();
}
