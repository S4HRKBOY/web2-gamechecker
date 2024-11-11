package de.fhdo.eborrow.converters;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
        gameDto.setPlatforms(this.platformToDtoPlatform(game.getPlatforms()));
        gameDto.setGenres(this.genreToDtoGenre(game.getGenres()));
        gameDto.setPublicationDate(game.getPublicationDate());
        if (game.getAgeRating() != null) {
            gameDto.setAgeRating(game.getAgeRating().getAgeRating());
        }
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        if (game.getGameImage() != null) {
            gameDto.setGameImage(Base64.getEncoder().encodeToString(game.getGameImage()));
        }
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
        game.setPlatforms(this.dtoPlatformToPlatform(dto.getPlatforms()));
        game.setGenres(this.dtoGenreToGenre(dto.getPlatforms()));
        game.setPublicationDate(dto.getPublicationDate());
        if (dto.getAgeRating() != null) {
            game.setAgeRating(AgeRating.valueOf(dto.getAgeRating()));
        }
        game.setDeveloper(dto.getDeveloper());
        game.setPublisher(dto.getPublisher());
        if (dto.getGameImage() != null) {
            game.setGameImage(Base64.getDecoder().decode(dto.getGameImage().split(",")[1]));
        }
        game.setReviews(reviewMapper.dtoListToReviewSet(dto.getReviews()));

        return game;
    }

    protected List<String> platformToDtoPlatform(List<Platform> list) {
        if (list == null) {
            return null;
        }
        List<String> platforms = list.stream().map(Platform::getPlatform).collect(Collectors.toList());
        return platforms;
    }

    protected List<String> genreToDtoGenre(List<Genre> list) {
        if (list == null) {
            return null;
        }
        List<String> genres = list.stream().map(Genre::getGenre).collect(Collectors.toList());
        return genres;
    }

    protected List<Platform> dtoPlatformToPlatform(List<String> list) {
        if (list == null) {
            return null;
        }
        List<Platform> platforms = list.stream().map(Platform::valueOf).collect(Collectors.toList());
        return platforms;
    }

    protected List<Genre> dtoGenreToGenre(List<String> list) {
        if (list == null) {
            return null;
        }
        List<Genre> genres = list.stream().map(Genre::valueOf).collect(Collectors.toList());
        return genres;
    }

}
