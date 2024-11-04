package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.AdminDTOBuilder;
import de.fhdo.eborrow.dto.account.builder.PublisherDTOBuilder;

import java.time.LocalDate;

public class AdminDTO extends AccountDTO {
    private AdminDTO(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public AdminDTO(AdminDTOBuilder builder) {
        super(builder);
    }

    // Zak: Ueberlegen, ob das AdminDTO Objekt diese Aufgabe uebernehmen soll und nicht eher eine Service-Klassen
    public PublisherDTO createPublisher(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        return new PublisherDTOBuilder()
                .setId(id)
                .setPrename(prename)
                .setSurname(surname)
                .setBirthday(birthday)
                .setUsername(username)
                .setEmail(email)
                .setPassword(password)
                .setProfilePicture(profilePicture)
                .build();
    }
}