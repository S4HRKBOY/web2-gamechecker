package de.fhdo.eborrow.services;

import de.fhdo.eborrow.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSearchService {

	private GameRepository gameRepository;

	@Autowired
	public GameSearchService(GameRepository gameRepository){
		this.gameRepository = gameRepository;
	}


}
