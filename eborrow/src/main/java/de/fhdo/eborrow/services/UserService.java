package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.AccountMapper;
import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.domain.account.User;
import de.fhdo.eborrow.dto.account.AccountDto;
import de.fhdo.eborrow.dto.account.UserDto;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService extends AccountService {
	// TODO Zak: Methoden fuer Zahlungsoptionen anbieten? (wenn: hier oder eigener Service?)

	@Autowired
	public UserService(AccountMapper accountMapper, AccountRepository accountRepository) {
		super(accountMapper, accountRepository);
	}

	public Iterable<UserDto> getUsers() {
		Iterable<Account> allAccounts = accountRepository.findAll();
		List<UserDto> allUsers = StreamSupport.stream(allAccounts.spliterator(), false).filter(User.class::isInstance) // Filter out all non-User accounts
				.map(accountMapper::accountToDto)   // Map to Dtos
				.filter(UserDto.class::isInstance) // Filter all User accounts
				.map(UserDto.class::cast)  // Cast all remaining accountDtos to UserDtos
				.toList();  // Collect all Users into a List

		return allUsers;
	}

	public UserDto getUserById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		if (!(account instanceof User)) {
			System.err.println("Read failed: Account with id " + id + " is not of type User");
			return null;
		}

		UserDto userDto = (UserDto) accountMapper.accountToDto(account);

		return userDto;
	}

	public boolean deleteUser(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			System.err.println("Delete failed: No Account found with id " + id);
			return false;
		}

		if (!(account instanceof User)) {
			System.err.println("Delete failed: Account with id " + id + " is not of type User");
			return false;
		}

		accountRepository.deleteById(id);

		return true;
	}

	public boolean updateUser(UserDto newUserDto, Long id) {
		Account existingAccount = accountRepository.findById(id).orElse(null);
		if (existingAccount == null) {
			System.err.println("Update failed: No Account found with id " + id);
			return false;
		}

		if (!(existingAccount instanceof User)) {
			System.err.println("Update failed: Account with id " + id + " is not of type User");
			return false;
		}

		newUserDto.setId(id);
		UserDto existingUserDto = (UserDto) accountMapper.accountToDto(existingAccount);
		copyExistingFields(existingUserDto, newUserDto);
		User newUser = (User) accountMapper.dtoToAccount(newUserDto);
		accountRepository.save(newUser);

		return true;
	}

	@Override
	protected void copyExistingFields(AccountDto existingAccountDto, AccountDto newAccountDto) {
		super.copyExistingFields(existingAccountDto, newAccountDto);

		UserDto existingUser = (UserDto) existingAccountDto;
		UserDto newUser = (UserDto) newAccountDto;

		if (newUser.getPaymentOptionDto() == null) {
			// Zak: Reicht es hier, die Referenz zu kopieren, oder muss eine neue PaymentOption Kopie erstellt werden?
			newUser.setPaymentOptionDto(existingUser.getPaymentOptionDto());
		}
	}

}
