package de.fhdo.eborrow.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.AgeRating;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.repositories.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Long createGame(GameDto gameDto) {
        Game game = GameMapper.dtoToGame(gameDto);
        return gameRepository.save(game).getId();
    }

    public RichGameDto getRichGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        return GameMapper.gameToRichDto(game);
    }

    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        return GameMapper.gameToDto(game);
    }

    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.removeGameAssociations(id);
        gameRepository.deleteById(id);
    }

    public List<GameDto> getAll() {
        List<GameDto> games = new ArrayList<>();
        for (Game game : gameRepository.findAll()) {
            games.add(GameMapper.gameToDto(game));
        }
        return games;
    }

    public Long updateGame(GameDto gameDto) {
        Game game = GameMapper.dtoToGame(gameDto);
        Game updatedGame = gameRepository.findById(game.getId())
                .orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));

        if (game.getTitle() != null) {
            updatedGame.setTitle(game.getTitle());
        }
        if (game.getDescription() != null) {
            updatedGame.setDescription(game.getDescription());
        }

        if (game.getPlatforms() != null && !game.getPlatforms().isEmpty()) {
            updatedGame.setPlatforms(game.getPlatforms());
        }

        if (game.getGenres() != null && !game.getGenres().isEmpty()) {
            updatedGame.setGenres(game.getGenres());
        }
        if (game.getPublicationDate() != null) {
            updatedGame.setPublicationDate(game.getPublicationDate());
        }
        if (game.getAgeRating() != null) {
            updatedGame.setAgeRating(game.getAgeRating());
        }

        if (game.getDeveloper() != null) {
            updatedGame.setDeveloper(game.getDeveloper());
        }
        if (game.getPublisher() != null) {
            updatedGame.setPublisher(game.getPublisher());
        }
        if (game.getGameImage() != null) {
            updatedGame.setGameImage(game.getGameImage());
        }
        return gameRepository.save(updatedGame).getId();
    }

    public void addReview(ReviewDto reviewDto, Long gameId) {
        RichGameDto gameDto = getRichGameById(gameId);
        gameDto.getReviewsDto().add(reviewDto);
        gameRepository.save(GameMapper.richDtoToGame(gameDto));
    }

    public List<String> getAllPlatforms() {
        return List.of(Platform.values()).stream().map(Platform::getPlatform).collect(Collectors.toList());
    }

    public List<String> getAllGenres() {
        return List.of(Genre.values()).stream().map(Genre::getGenre).collect(Collectors.toList());
    }

    public List<String> getAllAgeRatings() {
        return List.of(AgeRating.values()).stream().map(AgeRating::getAgeRating).collect(Collectors.toList());
    }

}
