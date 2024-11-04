package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.AdminDTO;

// region Builder
public class AdminDTOBuilder extends AccountDTOBuilder<AdminDTOBuilder, AdminDTO> {
    @Override
    public AdminDTO build() {
        return new AdminDTO(this);
    }

    @Override
    protected AdminDTOBuilder self() {
        return this;
    }
}
