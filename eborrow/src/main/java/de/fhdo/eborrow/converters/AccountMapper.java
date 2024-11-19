package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.domain.AccountBuilder;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.AccountDtoBuilder;
import de.fhdo.eborrow.dto.GameDto;

import java.util.List;

public class AccountMapper {

	// Zak: mir gefaellt die jetzige Implementierung nicht, da man bei Aenderungen von DTO oder Domain-Klasse schnell
	// vergisst, den Mapper mit anzupassen
	public static AccountDto accountToDto(Account account) {
		AccountDtoBuilder accountDtoBuilder = new AccountDtoBuilder()
				//.setId(account.getId())
				.setPrename(account.getPrename())
				.setSurname(account.getSurname())
				.setBirthday(account.getBirthday())
				.setUsername(account.getUsername())
				.setEmail(account.getEmail())
				.setPassword(account.getPassword())
				.setProfilePicture(account.getProfilePicture())
				.setPublisher(account.isPublisher());

		List<GameDto> gameDTOs = account.getTaggedGames().stream()
				.map(game -> GameMapper.gameToDto(game))
				.toList();
		accountDtoBuilder.setTaggedGames(gameDTOs);

		return accountDtoBuilder.build();
	}

	public static Account dtoToAccount(AccountDto accountDto) {
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

		List<Game> games = accountDto.getTaggedGames().stream()
				.map(GameMapper::dtoToGame)
				.toList();
		accountBuilder.setTaggedGames(games);

		return accountBuilder.build();
	}
}
