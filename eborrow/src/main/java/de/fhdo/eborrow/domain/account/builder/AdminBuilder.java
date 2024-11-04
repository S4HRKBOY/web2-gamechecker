package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.Admin;

// region Builder
public class AdminBuilder extends AccountBuilder<AdminBuilder, Admin> {
    @Override
    public Admin build() {
        validateInputs();
        
        return new Admin(this);
    }

    @Override
    void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }

    @Override
    protected AdminBuilder self() {
        return this;
    }
}
