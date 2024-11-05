package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.domain.account.Admin;
import de.fhdo.eborrow.domain.account.Publisher;
import de.fhdo.eborrow.domain.account.User;
import de.fhdo.eborrow.domain.account.builder.AdminBuilder;
import de.fhdo.eborrow.domain.account.builder.PublisherBuilder;
import de.fhdo.eborrow.domain.account.builder.UserBuilder;
import de.fhdo.eborrow.dto.account.AccountDto;
import de.fhdo.eborrow.dto.account.AdminDto;
import de.fhdo.eborrow.dto.account.PublisherDto;
import de.fhdo.eborrow.dto.account.UserDto;
import de.fhdo.eborrow.dto.account.builder.AdminDtoBuilder;
import de.fhdo.eborrow.dto.account.builder.PublisherDtoBuilder;
import de.fhdo.eborrow.dto.account.builder.UserDtoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final PaymentOptionMapper paymentOptionMapper;

    @Autowired
    public AccountMapper(PaymentOptionMapper paymentOptionMapper) {
        this.paymentOptionMapper = paymentOptionMapper;
    }

    public AccountDto accountToDto(Account account) {
        switch (account) {
            case User user -> {
                UserDtoBuilder userDtoBuilder = new UserDtoBuilder()
                        .setId(user.getId())
                        .setPrename(user.getPrename())
                        .setSurname(user.getSurname())
                        .setBirthday(user.getBirthday())
                        .setUsername(user.getUsername())
                        .setEmail(user.getEmail())
                        .setPassword(user.getPassword())
                        .setProfilePicture(user.getProfilePicture());

                userDtoBuilder.setPaymentOptionDto(paymentOptionMapper.paymentOptionToDto(user.getPaymentOption()));

                return userDtoBuilder.build();
            }
            case Admin admin -> {
                AdminDtoBuilder adminDtoBuilder = new AdminDtoBuilder()
                        .setId(admin.getId())
                        .setPrename(admin.getPrename())
                        .setSurname(admin.getSurname())
                        .setBirthday(admin.getBirthday())
                        .setUsername(admin.getUsername())
                        .setEmail(admin.getEmail())
                        .setPassword(admin.getPassword())
                        .setProfilePicture(admin.getProfilePicture());

                return adminDtoBuilder.build();
            }
            case Publisher publisher -> {
                PublisherDtoBuilder publisherDtoBuilder = new PublisherDtoBuilder()
                        .setId(publisher.getId())
                        .setPrename(publisher.getPrename())
                        .setSurname(publisher.getSurname())
                        .setBirthday(publisher.getBirthday())
                        .setUsername(publisher.getUsername())
                        .setEmail(publisher.getEmail())
                        .setPassword(publisher.getPassword())
                        .setProfilePicture(publisher.getProfilePicture());

                return publisherDtoBuilder.build();
            }
            default -> {
                return null;
            }
        }
    }

    public Account dtoToAccount(AccountDto accountDto) {
        switch (accountDto) {
            case UserDto userDto -> {
                UserBuilder userBuilder = new UserBuilder()
                        .setId(userDto.getId())
                        .setPrename(userDto.getPrename())
                        .setSurname(userDto.getSurname())
                        .setBirthday(userDto.getBirthday())
                        .setUsername(userDto.getUsername())
                        .setEmail(userDto.getEmail())
                        .setPassword(userDto.getPassword())
                        .setProfilePicture(userDto.getProfilePicture());
                userBuilder.setPaymentOption(paymentOptionMapper.dtoToPaymentOption(userDto.getPaymentOptionDto()));

                return userBuilder.build();
            }
            case AdminDto adminDto -> {
                AdminBuilder adminBuilder = new AdminBuilder()
                        .setId(adminDto.getId())
                        .setPrename(adminDto.getPrename())
                        .setSurname(adminDto.getSurname())
                        .setBirthday(adminDto.getBirthday())
                        .setUsername(adminDto.getUsername())
                        .setEmail(adminDto.getEmail())
                        .setPassword(adminDto.getPassword())
                        .setProfilePicture(adminDto.getProfilePicture());

                return adminBuilder.build();
            }
            case PublisherDto publisherDto -> {
                PublisherBuilder publisherBuilder = new PublisherBuilder()
                        .setId(publisherDto.getId())
                        .setPrename(publisherDto.getPrename())
                        .setSurname(publisherDto.getSurname())
                        .setBirthday(publisherDto.getBirthday())
                        .setUsername(publisherDto.getUsername())
                        .setEmail(publisherDto.getEmail())
                        .setPassword(publisherDto.getPassword())
                        .setProfilePicture(publisherDto.getProfilePicture());

                return publisherBuilder.build();
            }
            default -> {
                return null;
            }
        }
    }
}
