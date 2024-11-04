package de.fhdo.eborrow.domain.account;

import de.fhdo.eborrow.domain.account.builder.UserBuilder;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("User")
public class User extends Account {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_option_id", referencedColumnName = "id")
    private PaymentOption paymentOption;
    // TODO Zak: Ausleihe Feld hinzufügen

    protected User() {
    }
    
    protected User(Long id, String prename, String surname, LocalDate birthday, String username, String email, String password, byte[] profilePicture) {
        super(id, prename, surname, birthday, username, email, password, profilePicture);
    }

    public User(UserBuilder builder) {
        super(builder);
        this.paymentOption = builder.getPaymentOption();
    }
    
    // region getter and setter
    public PaymentOption getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOption paymentOption) {
        this.paymentOption = paymentOption;
    }
    // endregion

    @Override
    public String toString() {
        return "User{" +
                "paymentOption=" + paymentOption +
                "} " + super.toString();
    }
}
