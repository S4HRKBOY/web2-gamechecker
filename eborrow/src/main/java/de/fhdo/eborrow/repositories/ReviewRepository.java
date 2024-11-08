package de.fhdo.eborrow.repositories;

import de.fhdo.eborrow.domain.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
