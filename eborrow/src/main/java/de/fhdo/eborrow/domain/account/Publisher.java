package de.fhdo.eborrow.domain.account;

import de.fhdo.eborrow.domain.account.builder.PublisherBuilder;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("Publisher")
public class Publisher extends Account {
    protected Publisher() {
    }
    
    protected Publisher(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public Publisher(PublisherBuilder builder) {
        super(builder);
    }
}
