package de.fhdo.eborrow.converters;

import java.util.Base64;

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
        gameDto.setPlatforms(game.getPlatforms());
        gameDto.setGenres(game.getGenres());
        gameDto.setPublicationDate(game.getPublicationDate());
        gameDto.setAge(game.getAgeRating());
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        gameDto.setGameImage(Base64.getEncoder().encodeToString(game.getGameImage()));

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
        game.setPlatforms(dto.getPlatforms());
        game.setGenres(dto.getGenres());
        game.setPublicationDate(dto.getPublicationDate());
        game.setAgeRating(dto.getAgeRating());
        game.setDeveloper(dto.getDeveloper());
        game.setPublisher(dto.getPublisher());
        game.setGameImage(Base64.getDecoder().decode(dto.getGameImage().split(",")[1]));

        return game; 
    }
    
}
