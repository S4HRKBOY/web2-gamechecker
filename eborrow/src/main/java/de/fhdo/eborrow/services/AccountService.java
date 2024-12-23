package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.AccountMapper;
import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Account;
import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichAccountDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.repositories.AccountRepository;
import javassist.NotFoundException;
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

	public Long addAccount(AccountDto newAccountDto) {
		if (newAccountDto.getId() != null) {
			throw new IllegalArgumentException("Account ID must be null for new accounts");
		}

		newAccountDto.setEmail(newAccountDto.getEmail().toLowerCase());
		Account newAccount = AccountMapper.dtoToAccount(newAccountDto);

		return accountRepository.save(newAccount).getId();
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

	public AccountDto getAccountById(Long id) throws NotFoundException {
		Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("No Account found with id " + id));
		AccountDto accountDto = AccountMapper.accountToDto(account);

		return accountDto;
	}

	public RichAccountDto getRichAccountById(Long id) throws NotFoundException {
		Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("No Account found with id " + id));
		RichAccountDto richAccountDto = AccountMapper.accountToRichDto(account);

		return richAccountDto;
	}

	public void deleteAccount(Long id) throws NotFoundException {
		if (!accountRepository.existsById(id)) {
			throw new NotFoundException("No Account found with id " + id);
		}

		accountRepository.deleteById(id);
	}

	// TODO Zak: Alternativer Ansatz fuer saubere Trennung: Ein UpdateAccount Objekt aus dem Request erzeugen, in dem nur die zu aendernden Felder gesetzt sind
	// und dann die Werte auf das existierende Objekt im Repository ueberfuehren

	// use in case the id in accountChanges is guaranteed to match the id in the database 
	public void updateAccount(AccountDto accountChanges) throws NotFoundException {
		updateAccount(accountChanges.getId(), accountChanges);
	}

	// use in case the id in accountChanges is NOT guaranteed to match the id in the database
	public void updateAccount(Long id, AccountDto accountChangesDto) throws NotFoundException {
		accountChangesDto.setEmail(accountChangesDto.getEmail().toLowerCase());
		Account existingAccount = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("No Account found with id " + id));

		Account changes = AccountMapper.dtoToAccount(accountChangesDto);
		Account updatedAccount = transferAccountChanges(existingAccount, changes);
		accountRepository.save(updatedAccount);
	}

	public void addGameToAccount(Long accountId, Long gameId) throws NotFoundException {
		RichAccountDto richAccountDto = getRichAccountById(accountId);
		if (richAccountDto == null) {
			throw new NotFoundException("No Account found with id " + accountId);
		}

		// TODO Zak: Lieber eine Service Methode fuer Laden aller Games als GameDto statt RichGameDto nutzen
		RichGameDto richGameDto = gameService.getGameById(gameId);
		if (richGameDto == null) {
			throw new NotFoundException("No Game found with id " + gameId);
		}

		GameDto gameDto = GameMapper.gameToDto(GameMapper.richDtoToGame(richGameDto));
		addGameToAccount(richAccountDto, gameDto);
	}

	public void addGameToAccount(RichAccountDto richAccountDto, GameDto gameDto) throws NotFoundException {
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		if (gamesDtos == null) {
			throw new RuntimeException("Appending failed: account with id " + richAccountDto.getId() + " has no tagged games list");
		}

		if (richAccountDto.getTaggedGames().stream().anyMatch(g -> g.getId().equals(gameDto.getId()))) {
			throw new RuntimeException("Appending failed: Game with id %d has already been tagged by that Account with id %d"
					.formatted(gameDto.getId(), richAccountDto.getId()));
		}

		gamesDtos.add(gameDto);
		Account updatedAccount = AccountMapper.richDtoToAccount(richAccountDto);
		if (updatedAccount.getId() == null || !accountRepository.existsById(updatedAccount.getId())) {
			// Defensive programming, in case the mapping goes wrong
			throw new RuntimeException("An error occurred during appending game with id %d to account with id %d"
					.formatted(gameDto.getId(), richAccountDto.getId()));
		}

		accountRepository.save(updatedAccount);
	}

	public void unlistGameFromAccount(Long accountId, Long gameId) throws NotFoundException {
		RichAccountDto richAccountDto = getRichAccountById(accountId);
		if (richAccountDto == null) {
			throw new NotFoundException("No Account found with id " + accountId);
		}

		unlistGameFromAccount(richAccountDto, gameId);
	}

	public void unlistGameFromAccount(RichAccountDto richAccountDto, Long gameId) throws NotFoundException {
		List<GameDto> gamesDtos = richAccountDto.getTaggedGames();
		if (gamesDtos == null) {
			throw new NotFoundException("Unlisting failed: No games found for Account with id " + richAccountDto.getId());
		}

		if (gamesDtos.stream().noneMatch(gameDto -> gameDto.getId().equals(gameId))) {
			throw new RuntimeException("Unlisting failed: Game with id " + gameId + " is not tagged by Account with id " + richAccountDto.getId());
		}

		List<GameDto> updatedGamesDtos = gamesDtos.stream().filter(gameDto -> !gameDto.getId().equals(gameId)).toList();
		richAccountDto.setTaggedGames(updatedGamesDtos);
		Account updatedAccount = AccountMapper.richDtoToAccount(richAccountDto);
		if (updatedAccount.getId() == null || !accountRepository.existsById(updatedAccount.getId())) {
			// Defensive programming, in case the mapping goes wrong
			throw new RuntimeException("An error occurred during unlisting game with id %d from account with id %d"
					.formatted(gameId, richAccountDto.getId()));
		}

		accountRepository.save(updatedAccount);
	}

	public void updatePublisherStatus(Long id, boolean publisher) throws NotFoundException {
		Account existingAccount = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("No Account found with id " + id));
		existingAccount.setPublisher(publisher);
		accountRepository.save(existingAccount);
	}

	public boolean accountHasGame(Long accountId, Long gameId) {
		return accountRepository.accountHasGame(accountId, gameId);
	}

	public boolean isEmailTaken(String email) {
		email = email.toLowerCase();
		return accountRepository.existsByEmail(email);
	}

	public boolean isUsernameTaken(String username) {
		return accountRepository.existsByUsername(username);
	}

	private Account transferAccountChanges(Account existingAccount, Account changes) {
		existingAccount.setPrename(changes.getPrename());
		existingAccount.setSurname(changes.getSurname());
		existingAccount.setUsername(changes.getUsername());
		existingAccount.setBirthday(changes.getBirthday());
		existingAccount.setEmail(changes.getEmail());
		existingAccount.setPassword(changes.getPassword());
		existingAccount.setProfilePicture(changes.getProfilePicture());
		// Zak: Auch Update von taggedGames und publisher hier mit anbieten?

		return existingAccount;
	}
}
