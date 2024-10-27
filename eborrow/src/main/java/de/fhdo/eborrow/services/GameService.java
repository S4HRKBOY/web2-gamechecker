package de.fhdo.eborrow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.repositories.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepository; 

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository; 
    }

    public Long addGame(Game game) {
        return gameRepository.save(game).getId(); 
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).get();
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    public void updateGame(Game game) {
        Game updatedGame = new Game(); 
        if(game.getTitle() != null) {
            updatedGame.setTitle(game.getTitle());
        }
        if(game.getDescription() != null) {
            updatedGame.setDescription(game.getDescription());
        }
            
        updatedGame.setLicence(game.getLicence());
        
        if(game.getGenre() != null) {
            updatedGame.setGenre(game.getGenre());
        }
        if(game.getPublication() != null) {
            updatedGame.setPublication(game.getPublication());
        }

        updatedGame.setAge(game.getAge());

        if(game.getDeveloper() != null) {
            updatedGame.setDeveloper(game.getDeveloper());
        }
        if(game.getPublisher() != null) {
            updatedGame.setPublisher(game.getPublisher());
        }
        if(game.getImage() != null) {
            updatedGame.setImage(game.getImage()); 
        }

        gameRepository.save(updatedGame).getId(); 
    }

}
