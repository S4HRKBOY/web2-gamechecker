package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.PaymentOptionDTO;
import de.fhdo.eborrow.dto.account.UserDTO;

public class UserDTOBuilder extends AccountDTOBuilder<UserDTOBuilder, UserDTO> {
    private PaymentOptionDTO paymentOptionDTO;

    public UserDTOBuilder setPaymentOptionDTO(PaymentOptionDTO paymentOptionDTO) {
        this.paymentOptionDTO = paymentOptionDTO;
        return this;
    }

    // region getter
    public PaymentOptionDTO getPaymentOptionDTO() {
        return paymentOptionDTO;
    }
    // endregion

    @Override
    public UserDTO build() {
        validateInputs();

        return new UserDTO(this);
    }

    @Override
    protected UserDTOBuilder self() {
        return this;
    }
    
    @Override
    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
