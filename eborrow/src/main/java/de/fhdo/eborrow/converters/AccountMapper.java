package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.domain.account.builder.AccountBuilder;
import de.fhdo.eborrow.dto.account.AccountDto;
import de.fhdo.eborrow.dto.account.builder.AccountDtoBuilder;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto accountToDto(Account account) {
			AccountDtoBuilder accountDtoBuilder = new AccountDtoBuilder()
					.setId(account.getId())
					.setPrename(account.getPrename())
					.setSurname(account.getSurname())
					.setBirthday(account.getBirthday())
					.setUsername(account.getUsername())
					.setEmail(account.getEmail())
					.setPassword(account.getPassword())
					.setProfilePicture(account.getProfilePicture())
					.setPublisher(account.isPublisher());

			return accountDtoBuilder.build();
	}

    public Account dtoToAccount(AccountDto accountDto) {
			AccountBuilder accountBuilder = new AccountBuilder()
					.setId(accountDto.getId())
					.setPrename(accountDto.getPrename())
					.setSurname(accountDto.getSurname())
					.setBirthday(accountDto.getBirthday())
					.setUsername(accountDto.getUsername())
					.setEmail(accountDto.getEmail())
					.setPassword(accountDto.getPassword())
					.setProfilePicture(accountDto.getProfilePicture())
					.setPublisher(accountDto.isPublisher());

			return accountBuilder.build();
	}
}
