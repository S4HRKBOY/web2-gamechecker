package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.AdminDtoBuilder;
import de.fhdo.eborrow.dto.account.builder.PublisherDtoBuilder;

import java.time.LocalDate;

public class AdminDto extends AccountDto {
    private AdminDto(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public AdminDto(AdminDtoBuilder builder) {
        super(builder);
    }

    // Zak: Ueberlegen, ob das AdminDto Objekt diese Aufgabe uebernehmen soll und nicht eher eine Service-Klassen
    public PublisherDto createPublisher(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        return new PublisherDtoBuilder()
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

    @Override
    public String toString() {
        return "AdminDto{} " + super.toString();
    }
}