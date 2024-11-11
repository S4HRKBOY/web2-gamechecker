package de.fhdo.eborrow.converters;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fhdo.eborrow.domain.AgeRating;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform;
import de.fhdo.eborrow.dto.GameDto;

@Component
public class GameMapper {

    private ReviewMapper reviewMapper; 

    @Autowired
	public GameMapper(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}

    public GameDto gameToDto(Game game) {
        if (game == null) {
            return null;
        }

        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setTitle(game.getTitle());
        gameDto.setDescription(game.getDescription());
        gameDto.setPlatforms(game.getPlatforms().stream().map(Platform::getPlatform).collect(Collectors.toList()));
        gameDto.setGenres(game.getGenres().stream().map(Genre::getGenre).collect(Collectors.toList()));
        gameDto.setPublicationDate(game.getPublicationDate());
        gameDto.setAgeRating(game.getAgeRating().getAgeRating());
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        gameDto.setGameImage(Base64.getEncoder().encodeToString(game.getGameImage()));
        gameDto.setReviews(reviewMapper.reviewSetToDtoList(game.getReviews())); 

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
        game.setPlatforms(dto.getPlatforms().stream().map(Platform::valueOf).collect(Collectors.toList()));
        game.setGenres(dto.getGenres().stream().map(Genre::valueOf).collect(Collectors.toList()));
        game.setPublicationDate(dto.getPublicationDate());
        game.setAgeRating(AgeRating.valueOf(dto.getAgeRating()));
        game.setDeveloper(dto.getDeveloper());
        game.setPublisher(dto.getPublisher());
        if (dto.getGameImage() != null) {
            game.setGameImage(Base64.getDecoder().decode(dto.getGameImage().split(",")[1]));
        }
        game.setReviews(reviewMapper.dtoListToReviewSet(dto.getReviews())); 

        return game;
    }
}
