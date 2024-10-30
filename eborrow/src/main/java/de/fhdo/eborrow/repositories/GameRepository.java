package de.fhdo.eborrow.repositories;

import org.springframework.data.repository.CrudRepository;

import de.fhdo.eborrow.domain.Game;

public interface GameRepository extends CrudRepository<Game, Long>{

}
