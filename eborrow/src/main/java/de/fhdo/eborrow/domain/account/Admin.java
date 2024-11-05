package de.fhdo.eborrow.domain.account;

import de.fhdo.eborrow.domain.account.builder.AdminBuilder;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Account {
    protected Admin() {
    }

    protected Admin(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public Admin(AdminBuilder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return "Admin{} " + super.toString();
    }
}