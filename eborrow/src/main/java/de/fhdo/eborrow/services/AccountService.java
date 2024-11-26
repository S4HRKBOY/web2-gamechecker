package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.AccountMapper;
import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AccountService {
	private final AccountRepository accountRepository;
	private final GameService gameService;

	@Autowired
	public AccountService(AccountRepository accountRepository, GameService gameService) {
		this.accountRepository = accountRepository;
		this.gameService = gameService;
	}

	public Long addAccount(AccountDto accountDto) {
		// TODO Zak: Pruefen ob Account bereits existiert (z.B. nur benutzername oder email)
		// (muesste ich da echt die ganze Liste durchgehen? Jeden einzelnen Account zu DTO konvertieren 
		// waere echt ineffizient, vor allem mit Profilbildern)
		Account account = AccountMapper.dtoToAccount(accountDto);

		return accountRepository.save(account).getId();
	}

	public Iterable<AccountDto> getAccounts() {
		Iterable<Account> all = accountRepository.findAll();
		Iterable<AccountDto> allDto = StreamSupport.stream(all.spliterator(), false)
				.map(AccountMapper::accountToDto)  // Cast all accounts to DTOs
				.toList();

		return allDto;
	}

	public List<RichAccountDto> getRichAccounts() {
		Iterable<Account> all = accountRepository.findAll();
		List<RichAccountDto> allRichDto = StreamSupport.stream(all.spliterator(), false)
				.map(AccountMapper::accountToRichDto)  // Cast all accounts to RichDTOs
				.toList();

		return allRichDto;
	}

	public AccountDto getAccountById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		AccountDto accountDto = AccountMapper.accountToDto(account);

		return accountDto;
	}

	public RichAccountDto getRichAccountById(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return null;
		}

		RichAccountDto richAccountDto = AccountMapper.accountToRichDto(account);

		return richAccountDto;
	}

	public boolean deleteAccount(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			System.err.println("Delete failed: No Account found with id " + id);
			return false;
		}

		accountRepository.deleteById(id);
		return true;
	}

	// TODO Zak: Alternativer Ansatz fuer saubere Trennung: Ein UpdateAccount Objekt aus dem Request erzeugen, in dem nur die zu aendernden Felder gesetzt sind
	// und dann die Werte auf das existierende Objekt im Repository ueberfuehren

	// use in case the id in accountChanges is guaranteed to match the id in the database 
	public boolean updateAccount(AccountDto accountChanges) {
		return updateAccount(accountChanges, accountChanges.getId());
	}

	// use in case the id in accountChanges is not guaranteed to match the id in the database
	public boolean updateAccount(AccountDto accountChanges, Long id) {
		Account existingAccount = accountRepository.findById(id).orElse(null);
		if (existingAccount == null) {
			System.err.println("Update failed: No Account found with id " + id);
			return false;
		}

		AccountDto existingAccountDto = AccountMapper.accountToDto(existingAccount);

		return updateAccount(accountChanges, existingAccountDto);
	}

	public boolean updateAccount(AccountDto accountChanges, AccountDto existingAccountDto) {
		transferAccountChanges(existingAccountDto, accountChanges);
		Account updatedAccount = AccountMapper.dtoToAccount(existingAccountDto);
		if (updatedAccount.getId() == null || !accountRepository.existsById(updatedAccount.getId())) {
			System.err.println("Update failed: No Account found with id " + updatedAccount.getId());
			return false;
		}

		rechargeTaggedGames(updatedAccount);
		accountRepository.save(updatedAccount);

		return true;
	}

	public boolean accountHasGame(Long accountId, Long gameId) {
		return accountRepository.accountHasGame(accountId, gameId);
	}
	
	public boolean addGameToAccount(Long accountId, Long gameId) {
		RichAccountDto richAccountDto = getRichAccountById(accountId);
		if (richAccountDto == null) {
			System.err.println("Unlisting failed: No Account found with id " + accountId);
			return false;
		}

		// TODO Zak: Lieber eine Service Methode fuer Laden aller Games als GameDto statt RichGameDto nutzen
		RichGameDto richGameDto = gameService.getGameById(gameId);
		if (richGameDto == null) {
			System.err.println("Unlisting failed: No Game found with id " + gameId);
			return false;
		}
		GameDto gameDto = GameMapper.gameToDto(GameMapper.richDtoToGame(richGameDto));

		return addGameToAccount(richAccountDto, gameDto);
	}

	public boolean addGameToAccount(RichAccountDto richAccountDto, GameDto gameDto) {
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		if (gamesDtos == null) {
			System.err.println("Appending failed: account with id " + richAccountDto.getId() + " has no tagged games list");
			return false;
		}

		if (richAccountDto.getTaggedGames().stream().anyMatch(g -> g.getId().equals(gameDto.getId()))) {
			System.err.println("Appending failed: Game with id " + gameDto.getId() + " is already tagged by Account with id " + richAccountDto.getId());
			return false;
		}

		gamesDtos.add(gameDto);
		Account updatedAccount = AccountMapper.richDtoToAccount(richAccountDto);
		if (updatedAccount.getId() == null || !accountRepository.existsById(updatedAccount.getId())) {
			System.err.println("Appending failed: No Account found with id " + updatedAccount.getId());
			return false;
		}

		accountRepository.save(updatedAccount);

		return true;
	}

	public boolean unlistGameFromAccount(Long accountId, Long gameId) {
		RichAccountDto richAccountDto = getRichAccountById(accountId);
		if (richAccountDto == null) {
			System.err.println("Unlisting failed: No Account found with id " + accountId);
			return false;
		}

		return unlistGameFromAccount(richAccountDto, gameId);
	}

	public boolean unlistGameFromAccount(RichAccountDto richAccountDto, Long gameId) {
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		if (gamesDtos == null) {
			System.err.println("Unlisting failed: No games found for Account with id " + richAccountDto.getId());
			return false;
		}

		if (gamesDtos.stream().noneMatch(gameDto -> gameDto.getId().equals(gameId))) {
			System.err.println("Unlisting failed: Game with id " + gameId + " is not tagged by Account with id " + richAccountDto.getId());
			return false;
		}

		List<GameDto> updatedGamesDtos = gamesDtos.stream().filter(gameDto -> !gameDto.getId().equals(gameId)).toList();
		richAccountDto.setTaggedGames(updatedGamesDtos);
		Account updatedAccount = AccountMapper.richDtoToAccount(richAccountDto);
		if (updatedAccount.getId() == null || !accountRepository.existsById(updatedAccount.getId())) {
			System.err.println("Unlisting failed: No Account found with id " + updatedAccount.getId());
			return false;
		}
		accountRepository.save(updatedAccount);

		return true;
	}

	public boolean updatePublisherStatus(Long id, boolean isPublisher) {
		AccountDto accountDto = getAccountById(id);
		if (accountDto == null) {
			System.err.println("Update failed: No Account found with id " + id);
			return false;
		}

		return updatePublisherStatus(accountDto, isPublisher);
	}

	public boolean updatePublisherStatus(AccountDto accountDto, boolean isPublisher) {
		accountDto.setPublisher(isPublisher);
		Account updatedAccount = AccountMapper.dtoToAccount(accountDto);
		if (updatedAccount.getId() == null || !accountRepository.existsById(updatedAccount.getId())) {
			System.err.println("Unlisting failed: No Account found with id " + updatedAccount.getId());
			return false;
		}

		rechargeTaggedGames(updatedAccount);
		accountRepository.save(updatedAccount);

		return true;
	}

	private void transferAccountChanges(AccountDto existingAccountDto, AccountDto accountChanges) {
		if (accountChanges.getPrename() != null) {
			existingAccountDto.setPrename(accountChanges.getPrename());
		}

		if (accountChanges.getSurname() != null) {
			existingAccountDto.setSurname(accountChanges.getSurname());
		}

		if (accountChanges.getUsername() != null) {
			existingAccountDto.setUsername(accountChanges.getUsername());
		}

		if (accountChanges.getBirthday() != null) {
			existingAccountDto.setBirthday(accountChanges.getBirthday());
		}

		if (accountChanges.getEmail() != null) {
			existingAccountDto.setEmail(accountChanges.getEmail());
		}

		if (accountChanges.getPassword() != null) {
			existingAccountDto.setPassword(accountChanges.getPassword());
		}

		if (accountChanges.getProfilePicture() != null) {
			existingAccountDto.setProfilePicture(accountChanges.getProfilePicture());
		}

		// Zak: Auch Update von taggedGames und publisher update hier mit anbieten?
	}

	private void rechargeTaggedGames(Account accountWithoutTaggedGames) {
		// TODO Zak: Bessere Loesung ueberlegen
		accountWithoutTaggedGames.setTaggedGames(accountRepository.findById(accountWithoutTaggedGames.getId()).get().getTaggedGames());
	}
}
