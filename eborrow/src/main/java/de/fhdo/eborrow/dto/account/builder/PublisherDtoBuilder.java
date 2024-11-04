package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.PublisherDto;

public class PublisherDtoBuilder extends AccountDtoBuilder<PublisherDtoBuilder, PublisherDto> {
    @Override
    public PublisherDto build() {
        validateInputs();

        return new PublisherDto(this);
    }

    @Override
    protected PublisherDtoBuilder self() {
        return this;
    }

    @Override
    protected void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
}