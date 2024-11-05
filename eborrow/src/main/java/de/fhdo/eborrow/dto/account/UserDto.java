package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.UserDtoBuilder;

import java.time.LocalDate;

public class UserDto extends AccountDto {
    private PaymentOptionDto paymentOptionDto;
    // TODO Zak: AusleiheDto Feld hinzufügen

    private UserDto(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public UserDto(UserDtoBuilder builder) {
        super(builder);
        this.paymentOptionDto = builder.getPaymentOptionDto();
    }

    // region getter and setter
    public PaymentOptionDto getPaymentOptionDto() {
        return paymentOptionDto;
    }

    public void setPaymentOptionDto(PaymentOptionDto paymentOptionDto) {
        this.paymentOptionDto = paymentOptionDto;
    }
    // endregion

    @Override
    public String toString() {
        return "UserDto{" +
                "paymentOptionDto=" + paymentOptionDto +
                "} " + super.toString();
    }
}
