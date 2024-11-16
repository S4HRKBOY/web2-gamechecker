package de.fhdo.eborrow.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.fhdo.eborrow.domain.Game;

public interface GameRepository extends CrudRepository<Game, Long>{
    @Modifying
    @Query(value = "DELETE FROM account_game WHERE game_id = :gameId", nativeQuery = true)
    void removeGameAssociations(@Param("gameId") Long gameId);
}
