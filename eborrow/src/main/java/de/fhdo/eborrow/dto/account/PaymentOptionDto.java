package de.fhdo.eborrow.dto.account;

import de.fhdo.eborrow.dto.account.builder.PaymentOptionDtoBuilder;

public class PaymentOptionDto {
    private Long id;

    private String iban;
    private boolean isActive = true;

    private String accountOwnerPrename;
    private String accountOwnerSurname;
    private String accountOwnerStreet;

    private PaymentOptionDto(Long id, String iban, boolean isActive, String accountOwnerPrename, String accountOwnerSurname, String accountOwnerStreet, byte accountOwnerHousenumber, byte accountOwnerZipCode, String accountOwnerResidence) {
        this.id = id;
        this.iban = iban;
        this.isActive = isActive;
        this.accountOwnerPrename = accountOwnerPrename;
        this.accountOwnerSurname = accountOwnerSurname;
        this.accountOwnerStreet = accountOwnerStreet;
        this.accountOwnerHousenumber = accountOwnerHousenumber;
        this.accountOwnerZipCode = accountOwnerZipCode;
        this.accountOwnerResidence = accountOwnerResidence;
    }
    
    public PaymentOptionDto(PaymentOptionDtoBuilder builder) {
        this(builder.getId(), 
                builder.getIban(), 
                builder.isActive(), 
                builder.getAccountOwnerPrename(), 
                builder.getAccountOwnerSurname(),
                builder.getAccountOwnerStreet(), 
                builder.getAccountOwnerHousenumber(), 
                builder.getAccountOwnerZipCode(), 
                builder.getAccountOwnerResidence());
    }

    private byte accountOwnerHousenumber;
    private byte accountOwnerZipCode;
    private String accountOwnerResidence;

    // region getter and setter
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAccountOwnerPrename() {
        return accountOwnerPrename;
    }

    public void setAccountOwnerPrename(String accountOwnerPrename) {
        this.accountOwnerPrename = accountOwnerPrename;
    }

    public String getAccountOwnerSurname() {
        return accountOwnerSurname;
    }

    public void setAccountOwnerSurname(String accountOwnerSurname) {
        this.accountOwnerSurname = accountOwnerSurname;
    }

    public String getAccountOwnerStreet() {
        return accountOwnerStreet;
    }

    public void setAccountOwnerStreet(String accountOwnerStreet) {
        this.accountOwnerStreet = accountOwnerStreet;
    }

    public byte getAccountOwnerHousenumber() {
        return accountOwnerHousenumber;
    }

    public void setAccountOwnerHousenumber(byte accountOwnerHousenumber) {
        this.accountOwnerHousenumber = accountOwnerHousenumber;
    }

    public byte getAccountOwnerZipCode() {
        return accountOwnerZipCode;
    }

    public void setAccountOwnerZipCode(byte accountOwnerZipCode) {
        this.accountOwnerZipCode = accountOwnerZipCode;
    }

    public String getAccountOwnerResidence() {
        return accountOwnerResidence;
    }

    public void setAccountOwnerResidence(String accountOwnerResidence) {
        this.accountOwnerResidence = accountOwnerResidence;
    }
    // endregion
}
