package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.account.builder.PublisherDTOBuilder;

import java.time.LocalDate;

public class PublisherDTO extends AccountDTO{
    private PublisherDTO(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public PublisherDTO(PublisherDTOBuilder builder) {
        super(builder);
    }
    
    // Zak: Ueberlegen, ob das PublisherDTO Objekt diese Aufgabe uebernehmen soll und nicht eher eine Service-Klassen
    public GameDto createGame(Long id, String title, String description, int licence, String genre, LocalDate publication,
                              int age, String developer, String publisher, byte[] image) {
        return new GameDto(id, title, description, licence, genre, publication, age, developer, publisher, image);
    }
}
