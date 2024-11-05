package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.PaymentOptionDto;
import de.fhdo.eborrow.dto.account.UserDto;

public class UserDtoBuilder extends AccountDtoBuilder<UserDtoBuilder, UserDto> {
    private PaymentOptionDto paymentOptionDto;

    // region getter
    public PaymentOptionDto getPaymentOptionDto() {
        return paymentOptionDto;
    }

    public UserDtoBuilder setPaymentOptionDto(PaymentOptionDto paymentOptionDto) {
        this.paymentOptionDto = paymentOptionDto;
        return this;
    }
    // endregion

    @Override
    public UserDto build() {
        validateInputs();

        return new UserDto(this);
    }

    @Override
    protected UserDtoBuilder self() {
        return this;
    }

    @Override
    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
