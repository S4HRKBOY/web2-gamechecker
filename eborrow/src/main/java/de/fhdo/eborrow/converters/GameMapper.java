package de.fhdo.eborrow.converters;

import java.util.*;
import java.util.stream.Collectors;

import de.fhdo.eborrow.domain.AgeRating;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.RichGameDto;

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
        return game;
    }

    public static RichGameDto gameToRichDto(Game game) {
        if (game == null) {
            return null;
        }

        RichGameDto gameDto = new RichGameDto();

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

    public static Game richDtoToGame(RichGameDto dto) {
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

    protected static List<GameDto> gameSetToDtoList(Set<Game> set) {
        if (set == null) {
            return null;
        }

        List<GameDto> result = new ArrayList<>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (Game game : set) {
            result.add(gameToDto(game));
        }

        return result;
    }

    protected static Set<Game> dtoListToGameSet(List<GameDto> list) {
        if (list == null) {
            return null;
        }

        Set<Game> result = new HashSet<>();
        for (GameDto gameDto : list) {
            result.add(dtoToGame(gameDto));
        }

        return result;
    }
}
