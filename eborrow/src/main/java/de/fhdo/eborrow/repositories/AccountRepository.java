package de.fhdo.eborrow.repositories;

import de.fhdo.eborrow.domain.Account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends CrudRepository<Account, Long> {
    //native SQL Query 
    @Query(value = "SELECT COUNT(*) > 0 FROM account_game ag WHERE ag.account_id = :accountId AND ag.game_id = :gameId", nativeQuery = true)
    boolean accountHasGame(@Param("accountId") Long accountId, @Param("gameId") Long gameId);
    
    @Query(value = "SELECT * FROM account a WHERE a.username = :username", nativeQuery = true)
    Account findByUsername(String username);

    @Query(value = "SELECT COUNT(*) > 0 FROM account a WHERE a.email = :email", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);

    @Query(value = "SELECT COUNT(*) > 0 FROM account a WHERE a.id != :id AND a.email = :email", nativeQuery = true)
    boolean existsByEmailInOtherAccount(@Param("id") Long id, @Param("email") String email);

    @Query(value = "SELECT COUNT(*) > 0 FROM account a WHERE a.username = :username", nativeQuery = true)
    boolean existsByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(*) > 0 FROM account a WHERE a.id != :id AND a.username = :username", nativeQuery = true)
    boolean existsByUsernameInOtherAccount(@Param("id") Long id, @Param("username") String username);
}