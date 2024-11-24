package de.fhdo.eborrow.repositories;

import de.fhdo.eborrow.domain.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value = "SELECT COUNT(*) > 0 FROM account_game ag WHERE ag.account_id = :accountId AND ag.game_id = :gameId", nativeQuery = true)
    boolean accountHasGame(@Param("accountId") Long accountId, @Param("gameId") Long gameId);
}