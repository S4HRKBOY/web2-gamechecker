package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.domain.builder.AccountBuilder;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.builder.AccountDtoBuilder;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.dto.builder.RichAccountDtoBuilder;

import java.util.Base64;

public class AccountMapper {
	public static AccountDto accountToDto(Account account) {
		AccountDtoBuilder accountDtoBuilder = new AccountDtoBuilder()
				.setId(account.getId())
				.setPrename(account.getPrename())
				.setSurname(account.getSurname())
				.setBirthday(account.getBirthday())
				.setUsername(account.getUsername())
				.setEmail(account.getEmail())
				.setPassword(account.getPassword())
				.setPublisher(account.isPublisher());

		byte[] profilePicture = account.getProfilePicture();
		if(profilePicture != null) {
			accountDtoBuilder.setProfilePicture(Base64.getEncoder().encodeToString(profilePicture));
		}

		return accountDtoBuilder.build();
	}

	// TODO Zak: "DRY Prinzip": Lasst uns ueberlegen, ob wir den doppelten Code nicht vermeiden koennen
	// (Beispiele: RichAccountDto extends AccountDto oder RichAccountDto.getAccountDto() (Composition over inheritance) )
	public static RichAccountDto accountToRichDto(Account account) {
		RichAccountDtoBuilder richAccountDtoBuilder = new RichAccountDtoBuilder()
				.setId(account.getId())
				.setPrename(account.getPrename())
				.setSurname(account.getSurname())
				.setBirthday(account.getBirthday())
				.setUsername(account.getUsername())
				.setEmail(account.getEmail())
				.setPassword(account.getPassword())
				.setPublisher(account.isPublisher());

		byte[] profilePicture = account.getProfilePicture();
		if(profilePicture != null) {
			richAccountDtoBuilder.setProfilePicture(Base64.getEncoder().encodeToString(profilePicture));
		}

		richAccountDtoBuilder.setTaggedGames(GameMapper.gameSetToDtoList(account.getTaggedGames()));

		return richAccountDtoBuilder.build();
	}

	public static Account dtoToAccount(AccountDto accountDto) {
		// TODO Zak: Das ist ziemlich fehleranfaellig, da die Liste an taggedGames nicht gesetzt und somit auf null gesetzt wird
		// im Moment muessen Aufrufer die taggedGames aus der DB "nachladen" -> alternative ueberlegen! 
		AccountBuilder accountBuilder = new AccountBuilder()
				.setId(accountDto.getId())
				.setPrename(accountDto.getPrename())
				.setSurname(accountDto.getSurname())
				.setBirthday(accountDto.getBirthday())
				.setUsername(accountDto.getUsername())
				.setEmail(accountDto.getEmail())
				.setPassword(accountDto.getPassword()) // TODO Zak: Passwort sollte nicht im Klartext uebertragen werden (oder auf null setzen)
				.setPublisher(accountDto.isPublisher());

		String profilePicture = accountDto.getProfilePicture();
		if(profilePicture != null) {
			accountBuilder.setProfilePicture(Base64.getDecoder().decode(profilePicture));
		}

		return accountBuilder.build();
	}

	public static Account richDtoToAccount(RichAccountDto accountDto) {
		AccountBuilder accountBuilder = new AccountBuilder()
				.setId(accountDto.getId())
				.setPrename(accountDto.getPrename())
				.setSurname(accountDto.getSurname())
				.setBirthday(accountDto.getBirthday())
				.setUsername(accountDto.getUsername())
				.setEmail(accountDto.getEmail())
				.setPassword(accountDto.getPassword())
				.setPublisher(accountDto.isPublisher());

		String profilePicture = accountDto.getProfilePicture();
		if(profilePicture != null) {
			accountBuilder.setProfilePicture(Base64.getDecoder().decode(profilePicture));
		}

		accountBuilder.setTaggedGames(GameMapper.dtoListToGameSet(accountDto.getTaggedGames()));

		return accountBuilder.build();
	}
}
