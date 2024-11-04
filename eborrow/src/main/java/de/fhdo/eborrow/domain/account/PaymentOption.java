package de.fhdo.eborrow.domain.account;

import de.fhdo.eborrow.domain.account.builder.PaymentOptionBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaymentOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iban;
    private boolean isActive = true;

    private String accountOwnerPrename;
    private String accountOwnerSurname;
    private String accountOwnerStreet;

    protected PaymentOption() {
    }
    
    protected PaymentOption(Long id, String iban, boolean isActive, String accountOwnerPrename, String accountOwnerSurname, String accountOwnerStreet, byte accountOwnerHousenumber, byte accountOwnerZipCode, String accountOwnerResidence) {
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
    
    public PaymentOption(PaymentOptionBuilder builder) {
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

    @Override
    public String toString() {
        return "PaymentOption{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", isActive=" + isActive +
                ", accountOwnerPrename='" + accountOwnerPrename + '\'' +
                ", accountOwnerSurname='" + accountOwnerSurname + '\'' +
                ", accountOwnerStreet='" + accountOwnerStreet + '\'' +
                ", accountOwnerHousenumber=" + accountOwnerHousenumber +
                ", accountOwnerZipCode=" + accountOwnerZipCode +
                ", accountOwnerResidence='" + accountOwnerResidence + '\'' +
                '}';
    }
}
