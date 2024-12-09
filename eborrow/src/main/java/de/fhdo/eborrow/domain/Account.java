package de.fhdo.eborrow.domain;

import de.fhdo.eborrow.domain.builder.AccountBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String prename;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false, unique = true)
    private String username;
    @DateTimeFormat
    private LocalDate birthday;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profilePicture;

    private boolean isPublisher;

	@ManyToMany
	@JoinTable(
			name = "account_game",
			joinColumns = @JoinColumn(name = "account_id"),
			inverseJoinColumns = @JoinColumn(name = "game_id")
	)
	private Set<Game> taggedGames;
	
    protected Account() {
    }

    private Account(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        this();
		this.id = id;
        this.prename = prename;
        this.surname = surname;
        this.birthday = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public Account(AccountBuilder builder) {
        this(builder.getId(),
                builder.getPrename(),
                builder.getSurname(),
                builder.getBirthday(),
                builder.getUsername(),
                builder.getEmail(),
                builder.getPassword(),
                builder.getProfilePicture());

		this.isPublisher = builder.isPublisher();
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

	public Set<Game> getTaggedGames() {
		return taggedGames;
	}

	public void setTaggedGames(Set<Game> taggedGames) {
		this.taggedGames = taggedGames;
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
                ", taggedGames=" + taggedGames +
                '}';
    }
}
