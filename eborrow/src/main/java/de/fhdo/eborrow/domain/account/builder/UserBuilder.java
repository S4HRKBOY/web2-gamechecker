package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.PaymentOption;
import de.fhdo.eborrow.domain.account.User;

public class UserBuilder extends AccountBuilder<UserBuilder, User> {
    private PaymentOption paymentOption;

    public UserBuilder setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
        return this;
    }

    // region getter
    public PaymentOption getPaymentOption() {
        return paymentOption;
    }
    // endregion

    @Override
    public User build() {
        validateInputs();

        return new User(this);
    }

    @Override
    protected UserBuilder self() {
        return this;
    }
    
    @Override
    void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
