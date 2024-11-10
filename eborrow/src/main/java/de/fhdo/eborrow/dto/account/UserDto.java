package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.UserDtoBuilder;

import java.time.LocalDate;

public class UserDto extends AccountDto {
    // TODO Zak: AusleiheDto Feld hinzufügen

    private UserDto(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public UserDto(UserDtoBuilder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "} " + super.toString();
    }
}
