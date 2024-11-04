package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.PaymentOptionDto;

public class PaymentOptionDtoBuilder {
    private Long id;
    private String iban;
    private String accountOwnerPrename;
    private String accountOwnerSurname;
    private String accountOwnerStreet;
    private byte accountOwnerHousenumber;
    private byte accountOwnerZipCode;
    private String accountOwnerResidence;
    private boolean isActive = true;

    public PaymentOptionDtoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PaymentOptionDtoBuilder setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public PaymentOptionDtoBuilder setAccountOwnerPrename(String accountOwnerPrename) {
        this.accountOwnerPrename = accountOwnerPrename;
        return this;
    }

    public PaymentOptionDtoBuilder setAccountOwnerSurname(String accountOwnerSurname) {
        this.accountOwnerSurname = accountOwnerSurname;
        return this;
    }

    public PaymentOptionDtoBuilder setAccountOwnerStreet(String accountOwnerStreet) {
        this.accountOwnerStreet = accountOwnerStreet;
        return this;
    }

    public PaymentOptionDtoBuilder setAccountOwnerHousenumber(byte accountOwnerHousenumber) {
        this.accountOwnerHousenumber = accountOwnerHousenumber;
        return this;
    }

    public PaymentOptionDtoBuilder setAccountOwnerZipCode(byte accountOwnerZipCode) {
        this.accountOwnerZipCode = accountOwnerZipCode;
        return this;
    }

    public PaymentOptionDtoBuilder setAccountOwnerResidence(String accountOwnerResidence) {
        this.accountOwnerResidence = accountOwnerResidence;
        return this;
    }

    public PaymentOptionDtoBuilder setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public PaymentOptionDto build() {
        validateInputs();

        return new PaymentOptionDto(this);
    }
    
    private void validateInputs() {
        // TODO Zak: Check each field and throw Exception if invalid
    }
    
    // region Getter
    public Long getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public String getAccountOwnerPrename() {
        return accountOwnerPrename;
    }

    public String getAccountOwnerSurname() {
        return accountOwnerSurname;
    }

    public String getAccountOwnerStreet() {
        return accountOwnerStreet;
    }

    public byte getAccountOwnerHousenumber() {
        return accountOwnerHousenumber;
    }

    public byte getAccountOwnerZipCode() {
        return accountOwnerZipCode;
    }

    public String getAccountOwnerResidence() {
        return accountOwnerResidence;
    }

    public boolean isActive() {
        return isActive;
    }
    // endregion
}
