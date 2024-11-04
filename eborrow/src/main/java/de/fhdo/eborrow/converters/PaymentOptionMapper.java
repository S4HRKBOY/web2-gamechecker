package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.account.PaymentOption;
import de.fhdo.eborrow.domain.account.builder.PaymentOptionBuilder;
import de.fhdo.eborrow.dto.account.PaymentOptionDto;
import de.fhdo.eborrow.dto.account.builder.PaymentOptionDtoBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentOptionMapper {
    public PaymentOptionDto paymentOptionToDto(PaymentOption paymentOption) {
        return new PaymentOptionDtoBuilder()
                .setId(paymentOption.getId())
                .setIban(paymentOption.getIban())
                .setIsActive(paymentOption.isActive())
                .setAccountOwnerPrename(paymentOption.getAccountOwnerPrename())
                .setAccountOwnerSurname(paymentOption.getAccountOwnerSurname())
                .setAccountOwnerStreet(paymentOption.getAccountOwnerStreet())
                .setAccountOwnerHousenumber(paymentOption.getAccountOwnerHousenumber())
                .setAccountOwnerZipCode(paymentOption.getAccountOwnerZipCode())
                .setAccountOwnerResidence(paymentOption.getAccountOwnerResidence())
                .build();
    }

    public PaymentOption dtoToPaymentOption(PaymentOptionDto paymentOptionDto) {
        return new PaymentOptionBuilder()
                .setId(paymentOptionDto.getId())
                .setIban(paymentOptionDto.getIban())
                .setIsActive(paymentOptionDto.isActive())
                .setAccountOwnerPrename(paymentOptionDto.getAccountOwnerPrename())
                .setAccountOwnerSurname(paymentOptionDto.getAccountOwnerSurname())
                .setAccountOwnerStreet(paymentOptionDto.getAccountOwnerStreet())
                .setAccountOwnerHousenumber(paymentOptionDto.getAccountOwnerHousenumber())
                .setAccountOwnerZipCode(paymentOptionDto.getAccountOwnerZipCode())
                .setAccountOwnerResidence(paymentOptionDto.getAccountOwnerResidence())
                .build();
    }
}