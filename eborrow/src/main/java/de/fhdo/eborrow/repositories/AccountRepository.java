package de.fhdo.eborrow.repositories;

import de.fhdo.eborrow.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}