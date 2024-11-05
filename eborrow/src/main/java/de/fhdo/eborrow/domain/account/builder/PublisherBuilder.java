package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.Publisher;

public class PublisherBuilder extends AccountBuilder<PublisherBuilder, Publisher> {
    @Override
    public Publisher build() {
        validateInputs();

        return new Publisher(this);
    }

    @Override
    void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }

    @Override
    protected PublisherBuilder self() {
        return this;
    }
}