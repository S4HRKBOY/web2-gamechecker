package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.domain.account.User;
import de.fhdo.eborrow.domain.account.builder.UserBuilder;
import de.fhdo.eborrow.dto.account.AccountDto;
import de.fhdo.eborrow.dto.account.UserDto;
import de.fhdo.eborrow.dto.account.builder.UserDtoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountMapper {
    public AccountDto accountToDto(Account account) {
		if (Objects.requireNonNull(account) instanceof User user) {
			UserDtoBuilder userDtoBuilder = new UserDtoBuilder()
					.setId(user.getId())
					.setPrename(user.getPrename())
					.setSurname(user.getSurname())
					.setBirthday(user.getBirthday())
					.setUsername(user.getUsername())
					.setEmail(user.getEmail())
					.setPassword(user.getPassword())
					.setProfilePicture(user.getProfilePicture());

			return userDtoBuilder.build();
		}
		return null;
	}

    public Account dtoToAccount(AccountDto accountDto) {
		if (Objects.requireNonNull(accountDto) instanceof UserDto userDto) {
			UserBuilder userBuilder = new UserBuilder()
					.setId(userDto.getId())
					.setPrename(userDto.getPrename())
					.setSurname(userDto.getSurname())
					.setBirthday(userDto.getBirthday())
					.setUsername(userDto.getUsername())
					.setEmail(userDto.getEmail())
					.setPassword(userDto.getPassword())
					.setProfilePicture(userDto.getProfilePicture());

			return userBuilder.build();
		}
		return null;
	}
}
