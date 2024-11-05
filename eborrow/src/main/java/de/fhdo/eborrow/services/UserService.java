package de.fhdo.eborrow.services;

import de.fhdo.eborrow.domain.account.Account;
import de.fhdo.eborrow.domain.account.User;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService extends AccountService {
    @Autowired
    public UserService(AccountRepository accountRepository) {
        super(accountRepository);
    }

    public Iterable<User> getUsers() {
        Iterable<Account> allAccounts = accountRepository.findAll();
        List<User> allUsers = StreamSupport.stream(allAccounts.spliterator(), false)
                .filter(User.class::isInstance) // Filter out all non-User accounts
                .map(User.class::cast)  // Cast all remaining accounts to User
                .toList();  // Collect all Users into a List

        return allUsers;
    }

    public User getUserById(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (!(account instanceof User)) {
            System.err.println("Update failed: No Account of Type User found with id " + id);
            return null;
        }

        return (User) account;
    }

    public void deleteUser(Long id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (!(account instanceof User)) {
            System.err.println("Delete failed: No Account of Type User found with id " + id);
            return;
        }

        accountRepository.deleteById(id);
    }

    public boolean updateUser(User newUser, Long id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (!(existingAccount instanceof User existingUser)) {
            return false;
        }

        newUser.setId(id);
        copyExistingFields(existingUser, newUser);
        accountRepository.save(newUser);

        return true;
    }

    @Override
    protected void copyExistingFields(Account existingAccount, Account newAccount) {
        super.copyExistingFields(existingAccount, newAccount);
        User existingUser = (User) existingAccount;
        User newUser = (User) newAccount;

        if (newUser.getPaymentOption() == null) {
            // Zak: Reicht es hier, die Referenz zu kopieren, oder muss eine neue PaymentOption Kopie erstellt werden?
            newUser.setPaymentOption(existingUser.getPaymentOption());
        }
    }

}
