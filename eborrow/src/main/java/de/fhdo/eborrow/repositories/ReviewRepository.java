package de.fhdo.eborrow.repositories;

import de.fhdo.eborrow.domain.Review;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Query(value = "SELECT * FROM review WHERE game_id = :gameId AND account_id = :accountId", nativeQuery = true)
    Review findByGameIdAndAccountId(@Param("gameId") Long gameId, @Param("accountId") Long accountId);

}
