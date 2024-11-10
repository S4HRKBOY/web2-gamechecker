package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.User;

public class UserBuilder extends AccountBuilder<UserBuilder, User> {

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
