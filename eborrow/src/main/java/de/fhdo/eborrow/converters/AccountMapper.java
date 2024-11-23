package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.domain.builder.AccountBuilder;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.builder.AccountDtoBuilder;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.dto.builder.RichAccountDtoBuilder;
import de.fhdo.eborrow.dto.GameDto;

import java.util.Base64;
import java.util.List;

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

		List<GameDto> gameDTOs = account.getTaggedGames().stream()
				.map(GameMapper::gameToDto)
				.toList();
		richAccountDtoBuilder.setTaggedGames(gameDTOs);

		return richAccountDtoBuilder.build();
	}

	public static Account dtoToAccount(AccountDto accountDto) {
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

		List<Game> games = accountDto.getTaggedGames().stream()
				.map(GameMapper::dtoToGame)
				.toList();
		accountBuilder.setTaggedGames(games);

		return accountBuilder.build();
	}
}
