package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.UserDTOBuilder;

import java.time.LocalDate;

public class UserDTO extends AccountDTO {
    private PaymentOptionDTO paymentOptionDTO;
    // TODO Zak: AusleiheDTO Feld hinzufügen

    private UserDTO(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }
    
    public UserDTO(UserDTOBuilder builder) {
        super(builder);
        this.paymentOptionDTO = builder.getPaymentOptionDTO();
    }
    
    // region getter and setter
    public PaymentOptionDTO getPaymentOptionDTO() {
        return paymentOptionDTO;
    }

    public void setPaymentOptionDTO(PaymentOptionDTO paymentOptionDTO) {
        this.paymentOptionDTO = paymentOptionDTO;
    }
    // endregion
}
