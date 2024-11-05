package de.fhdo.eborrow.domain.account.builder;

import de.fhdo.eborrow.domain.account.PaymentOption;

public class PaymentOptionBuilder {
    private Long id;
    private String iban;
    private String accountOwnerPrename;
    private String accountOwnerSurname;
    private String accountOwnerStreet;
    private byte accountOwnerHousenumber;
    private byte accountOwnerZipCode;
    private String accountOwnerResidence;
    private boolean isActive = true;

    public PaymentOptionBuilder setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public PaymentOption build() {
        validateInputs();

        return new PaymentOption(this);
    }

    private void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }

    // region Getter
    public Long getId() {
        return id;
    }

    public PaymentOptionBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public String getIban() {
        return iban;
    }

    public PaymentOptionBuilder setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public String getAccountOwnerPrename() {
        return accountOwnerPrename;
    }

    public PaymentOptionBuilder setAccountOwnerPrename(String accountOwnerPrename) {
        this.accountOwnerPrename = accountOwnerPrename;
        return this;
    }

    public String getAccountOwnerSurname() {
        return accountOwnerSurname;
    }

    public PaymentOptionBuilder setAccountOwnerSurname(String accountOwnerSurname) {
        this.accountOwnerSurname = accountOwnerSurname;
        return this;
    }

    public String getAccountOwnerStreet() {
        return accountOwnerStreet;
    }

    public PaymentOptionBuilder setAccountOwnerStreet(String accountOwnerStreet) {
        this.accountOwnerStreet = accountOwnerStreet;
        return this;
    }

    public byte getAccountOwnerHousenumber() {
        return accountOwnerHousenumber;
    }

    public PaymentOptionBuilder setAccountOwnerHousenumber(byte accountOwnerHousenumber) {
        this.accountOwnerHousenumber = accountOwnerHousenumber;
        return this;
    }

    public byte getAccountOwnerZipCode() {
        return accountOwnerZipCode;
    }

    public PaymentOptionBuilder setAccountOwnerZipCode(byte accountOwnerZipCode) {
        this.accountOwnerZipCode = accountOwnerZipCode;
        return this;
    }

    public String getAccountOwnerResidence() {
        return accountOwnerResidence;
    }

    public PaymentOptionBuilder setAccountOwnerResidence(String accountOwnerResidence) {
        this.accountOwnerResidence = accountOwnerResidence;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }
    // endregion
}
