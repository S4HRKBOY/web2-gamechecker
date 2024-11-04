package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.PublisherDTO;

public class PublisherDTOBuilder extends AccountDTOBuilder<PublisherDTOBuilder, PublisherDTO> {
    @Override
    public PublisherDTO build() {
        validateInputs();

        return new PublisherDTO(this);
    }

    @Override
    protected PublisherDTOBuilder self() {
        return this;
    }

    @Override
    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}