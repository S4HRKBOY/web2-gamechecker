package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.AdminDto;

// region Builder
public class AdminDtoBuilder extends AccountDtoBuilder<AdminDtoBuilder, AdminDto> {
    @Override
    public AdminDto build() {
        validateInputs();

        return new AdminDto(this);
    }

    @Override
    protected AdminDtoBuilder self() {
        return this;
    }
    
    @Override
    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
