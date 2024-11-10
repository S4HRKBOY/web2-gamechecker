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
        gameDto.setGenre(game.getGenre());
        gameDto.setPublication(game.getPublication());
        gameDto.setAge(game.getAge());
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        gameDto.setImage(Base64.getEncoder().encodeToString(game.getImage()));

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
        game.setGenre(dto.getGenre());
        game.setPublication(dto.getPublication());
        game.setAge(dto.getAge());
        game.setDeveloper(dto.getDeveloper());
        game.setPublisher(dto.getPublisher());
        game.setImage(Base64.getDecoder().decode(dto.getImage().split(",")[1]));

        return game; 
    }
    
}
