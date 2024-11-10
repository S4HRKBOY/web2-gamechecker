package de.fhdo.eborrow.domain.account;

import de.fhdo.eborrow.domain.account.builder.AccountBuilder;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String prename;
    private String surname;
    @DateTimeFormat
    private LocalDate birthday;

    private String username;
    private String email;
    private String password;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profilePicture;
    
    private boolean isPublisher;

    protected Account() {
    }

    private Account(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture, boolean isPublisher) {
        this.id = id;
        this.prename = prename;
        this.surname = surname;
        this.birthday = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.isPublisher = isPublisher;
    }

    public Account(AccountBuilder builder) {
        this(builder.getId(),
                builder.getPrename(),
                builder.getSurname(),
                builder.getBirthday(),
                builder.getUsername(),
                builder.getEmail(),
                builder.getPassword(),
                builder.getProfilePicture(),
                builder.isPublisher());
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

    public boolean isPublisher() {
        return isPublisher;
    }

    public void setPublisher(boolean publisher) {
        isPublisher = publisher;
    }
    // endregion

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", prename='" + prename + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture=" + (profilePicture != null ? "yes" : "no") +
                ", isPublisher=" + isPublisher +
                '}';
    }
}
