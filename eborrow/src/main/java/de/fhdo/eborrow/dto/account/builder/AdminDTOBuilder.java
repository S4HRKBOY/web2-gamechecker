package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.AdminDTO;

// region Builder
public class AdminDTOBuilder extends AccountDTOBuilder<AdminDTOBuilder, AdminDTO> {
    @Override
    public AdminDTO build() {
        validateInputs();

        return new AdminDTO(this);
    }

    @Override
    protected AdminDTOBuilder self() {
        return this;
    }
    
    @Override
    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}
