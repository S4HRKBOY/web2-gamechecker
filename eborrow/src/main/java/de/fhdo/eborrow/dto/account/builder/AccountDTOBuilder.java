package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.AccountDTO;

import java.time.LocalDate;

public abstract class AccountDTOBuilder<B extends AccountDTOBuilder<B, A>, A extends AccountDTO> {
    protected Long id;
    protected String prename;
    protected String surname;
    protected LocalDate birthday;
    protected String username;
    protected String email;
    protected String password;
    protected byte[] profilePicture;

    public B setId(Long id) {
        this.id = id;
        return self();
    }

    public B setPrename(String prename) {
        this.prename = prename;
        return self();
    }

    public B setSurname(String surname) {
        this.surname = surname;
        return self();
    }

    public B setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        return self();
    }

    public B setUsername(String username) {
        this.username = username;
        return self();
    }

    public B setEmail(String email) {
        this.email = email;
        return self();
    }

    public B setPassword(String password) {
        this.password = password;
        return self();
    }

    public B setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return self();
    }
    
    // region getter
    public Long getId() {
        return id;
    }

    public String getPrename() {
        return prename;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }
    // endregion
    
    protected abstract B self();
    
    protected abstract A build();
}
