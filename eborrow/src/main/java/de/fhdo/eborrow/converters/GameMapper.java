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

public class GameMapper {

    public static GameDto gameToDto(Game game) {
        if (game == null) {
            return null;
        }

        GameDto gameDto = new GameDto();

        gameDto.setId(game.getId());
        gameDto.setTitle(game.getTitle());
        gameDto.setDescription(game.getDescription());
        gameDto.setPlatforms(platformToDtoPlatform(game.getPlatforms()));
        gameDto.setGenres(genreToDtoGenre(game.getGenres()));
        gameDto.setPublicationDate(game.getPublicationDate());
        if (game.getAgeRating() != null) {
            gameDto.setAgeRating(game.getAgeRating().getAgeRating());
        }
        gameDto.setDeveloper(game.getDeveloper());
        gameDto.setPublisher(game.getPublisher());
        if (game.getGameImage() != null) {
            gameDto.setGameImage(Base64.getEncoder().encodeToString(game.getGameImage()));
        }
        gameDto.setReviewsDto(ReviewMapper.reviewSetToDtoList(game.getReviews())); 
        return gameDto;
    }

    public static Game dtoToGame(GameDto dto) {
        if (dto == null) {
            return null;
        }

        Game game = new Game();

        game.setId(dto.getId());
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setPlatforms(dtoPlatformToPlatform(dto.getPlatforms()));
        game.setGenres(dtoGenreToGenre(dto.getGenres()));
        game.setPublicationDate(dto.getPublicationDate());
        if (dto.getAgeRating() != null) {
            game.setAgeRating(AgeRating.fromDisplayName(dto.getAgeRating()));
        }
        game.setDeveloper(dto.getDeveloper());
        game.setPublisher(dto.getPublisher());
        if (dto.getGameImage() != null) {
            game.setGameImage(Base64.getDecoder().decode(dto.getGameImage()));
        }
        game.setReviews(ReviewMapper.dtoListToReviewSet(dto.getReviewsDto()));
        return game;
    }

    protected static List<String> platformToDtoPlatform(List<Platform> list) {
        if (list == null) {
            return null;
        }
        List<String> platforms = list.stream().map(Platform::getPlatform).collect(Collectors.toList());
        return platforms;
    }

    protected static List<String> genreToDtoGenre(List<Genre> list) {
        if (list == null) {
            return null;
        }
        List<String> genres = list.stream().map(Genre::getGenre).collect(Collectors.toList());
        return genres;
    }

    protected static List<Platform> dtoPlatformToPlatform(List<String> list) {
        if (list == null) {
            return null;
        }
        List<Platform> platforms = list.stream().map(Platform::fromDisplayName).collect(Collectors.toList());
        return platforms;
    }

    protected static List<Genre> dtoGenreToGenre(List<String> list) {
        if (list == null) {
            return null;
        }
        List<Genre> genres = list.stream().map(Genre::fromDisplayName).collect(Collectors.toList());
        return genres;
    }

}
