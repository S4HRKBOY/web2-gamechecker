package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.account.builder.PublisherDtoBuilder;

import java.time.LocalDate;

public class PublisherDto extends AccountDto {
    private PublisherDto(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public PublisherDto(PublisherDtoBuilder builder) {
        super(builder);
    }
    
    // Zak: Ueberlegen, ob das PublisherDto Objekt diese Aufgabe uebernehmen soll und nicht eher eine Service-Klassen
    public GameDto createGame(Long id, String title, String description, int licence, String genre, LocalDate publication,
                              int age, String developer, String publisher, byte[] image) {
        return new GameDto(id, title, description, licence, genre, publication, age, developer, publisher, image);
    }
}
