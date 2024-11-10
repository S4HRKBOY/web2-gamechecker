package de.fhdo.eborrow.domain.account;

import de.fhdo.eborrow.domain.account.builder.UserBuilder;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("User")
public class User extends Account {
    // TODO Zak: Ausleihe Feld hinzufügen

    protected User() {
    }

    protected User(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public User(UserBuilder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return "User{" +
                "} " + super.toString();
    }
}
