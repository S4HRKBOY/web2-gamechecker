package de.fhdo.eborrow.dto.account.builder;

import de.fhdo.eborrow.dto.account.PaymentOptionDTO;

public class PaymentOptionDTOBuilder {
    private Long id;
    private String iban;
    private String accountOwnerPrename;
    private String accountOwnerSurname;
    private String accountOwnerStreet;
    private byte accountOwnerHousenumber;
    private byte accountOwnerZipCode;
    private String accountOwnerResidence;
    private boolean isActive = true;

    public PaymentOptionDTOBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PaymentOptionDTOBuilder setIban(String iban) {
        this.iban = iban;
        return this;
    }

    public PaymentOptionDTOBuilder setAccountOwnerPrename(String accountOwnerPrename) {
        this.accountOwnerPrename = accountOwnerPrename;
        return this;
    }

    public PaymentOptionDTOBuilder setAccountOwnerSurname(String accountOwnerSurname) {
        this.accountOwnerSurname = accountOwnerSurname;
        return this;
    }

    public PaymentOptionDTOBuilder setAccountOwnerStreet(String accountOwnerStreet) {
        this.accountOwnerStreet = accountOwnerStreet;
        return this;
    }

    public PaymentOptionDTOBuilder setAccountOwnerHousenumber(byte accountOwnerHousenumber) {
        this.accountOwnerHousenumber = accountOwnerHousenumber;
        return this;
    }

    public PaymentOptionDTOBuilder setAccountOwnerZipCode(byte accountOwnerZipCode) {
        this.accountOwnerZipCode = accountOwnerZipCode;
        return this;
    }

    public PaymentOptionDTOBuilder setAccountOwnerResidence(String accountOwnerResidence) {
        this.accountOwnerResidence = accountOwnerResidence;
        return this;
    }

    public PaymentOptionDTOBuilder setIsActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public PaymentOptionDTO build() {
        validateInputs();

        return new PaymentOptionDTO(this);
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
