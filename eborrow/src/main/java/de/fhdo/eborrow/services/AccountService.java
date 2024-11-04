package de.fhdo.eborrow.services;

import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.domain.account.Admin;
import de.fhdo.eborrow.domain.account.builder.AdminBuilder;
import de.fhdo.eborrow.repositories.AccountRepository;
import jakarta.persistence.Id;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    protected AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Zak: Spaeter DTO Objekt statt Domain Objekt verwenden
    public Long addAccount(Account account) {
        return accountRepository.save(account).getId();
    }

    public Iterable<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public boolean updateAccount(Account newAccount, Long id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount == null) {
            return false;
        }

        newAccount.setId(id);
        copyExistingFields(existingAccount, newAccount);
        accountRepository.save(newAccount);

        return true;
    }

    protected void copyExistingFields(Account existingAccount, Account newAccount) {
        if (newAccount.getPrename() == null) {
            newAccount.setPrename(existingAccount.getPrename());
        }

        if (newAccount.getSurname() == null) {
            newAccount.setSurname(existingAccount.getSurname());
        }

        if (newAccount.getUsername() == null) {
            newAccount.setUsername(existingAccount.getUsername());
        }

        if (newAccount.getBirthday() == null) {
            newAccount.setBirthday(existingAccount.getBirthday());
        }

        if (newAccount.getEmail() == null) {
            newAccount.setEmail(existingAccount.getEmail());
        }

        if (newAccount.getPassword() == null) {
            newAccount.setPassword(existingAccount.getPassword());
        }

        if (newAccount.getProfilePicture() == null) {
            newAccount.setProfilePicture(existingAccount.getProfilePicture());
        }
    }
}
