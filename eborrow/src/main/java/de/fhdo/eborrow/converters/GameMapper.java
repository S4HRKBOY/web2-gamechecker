package de.fhdo.eborrow.converters;

import org.springframework.stereotype.Component;

import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.GameDto;

@Component
public class GameMapper {

    public GameDto gameToDto(Game game) {
        if (game == null) {
            return null; 
        }

        GameDto gameDto = new GameDto(); 

        gameDto.setId(game.getId()); 
        gameDto.setTitle(game.getTitle());
        gameDto.setDescription(game.getDescription());
        gameDto.setLicences(game.getLicences());
        gameDto.setRemainingLicences(game.getRemainingLicences());
        gameDto.setGenre(game.getGenre());
        gameDto.setPublication(game.getPublication());
        gameDto.setAge(game.getAge());
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        gameDto.setImage(game.getImage());

        return gameDto; 
    }

    public Game dtoToGame(GameDto dto) {
        if (dto == null) {
            return null; 
        }

        Game game = new Game(); 
        
        game.setId(dto.getId()); 
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setLicences(dto.getLicences());
        game.setRemainingLicences(dto.getRemainingLicences());
        game.setGenre(dto.getGenre());
        game.setPublication(dto.getPublication());
        game.setAge(dto.getAge());
        game.setDeveloper(dto.getDeveloper());
        game.setPublication(dto.getPublication());
        game.setImage(dto.getImage());

        return game; 
    }
    
}
