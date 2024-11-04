package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.AccountDTOBuilder;

import java.time.LocalDate;

public abstract class AccountDTO {
    private Long id;

    private String prename;
    private String surname;
    private LocalDate birthday;
    
    private String username;
    private String email;
    private String password;
    private byte[] profilePicture;
    
    protected AccountDTO(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        this.id = id;
        this.prename = prename;
        this.surname = surname;
        this.birthday = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public AccountDTO(AccountDTOBuilder<?,?> builder) {
        this(builder.getId(), 
                builder.getPrename(), 
                builder.getSurname(), 
                builder.getBirthday(),
                builder.getUsername(),
                builder.getEmail(), 
                builder.getPassword(), 
                builder.getProfilePicture());
    }
    
    public final boolean isAdmin() {
        return this instanceof AdminDTO;
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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    // endregion
}
