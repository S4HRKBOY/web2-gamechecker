package de.fhdo.eborrow.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.converters.ReviewMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Review;
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

    public Long addGame(GameDto gameDto) {
        Game game = GameMapper.dtoToGame(gameDto);
        return gameRepository.save(game).getId();
    }

    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        return GameMapper.gameToDto(game, true);
    }

    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.removeGameAssociations(id); 
        gameRepository.deleteById(id);
    }

    public List<GameDto> getAll() {
        List<GameDto> games = new ArrayList<>();
        for (Game game : gameRepository.findAll()) {
            games.add(GameMapper.gameToDto(game, true));
        }
        return games;
    }

    public Long updateGame(GameDto gameDto) {
        GameDto updatedGame;
        if (gameDto.getId() != null) {
            updatedGame = GameMapper.gameToDto(gameRepository.findById(gameDto.getId())
                    .orElseThrow(() -> new RuntimeException("Spiel nicht gefunden")), true);
        } else {
            updatedGame = new GameDto();
        }

        if (gameDto.getTitle() != null) {
            updatedGame.setTitle(gameDto.getTitle());
        }
        if (gameDto.getDescription() != null) {
            updatedGame.setDescription(gameDto.getDescription());
        }

        if (gameDto.getPlatforms() != null && !gameDto.getPlatforms().isEmpty()) {
            updatedGame.setPlatforms(gameDto.getPlatforms());
        }

        if (gameDto.getGenres() != null && gameDto.getGenres().isEmpty()) {
            updatedGame.setGenres(gameDto.getGenres());
        }
        if (gameDto.getPublicationDate() != null) {
            updatedGame.setPublicationDate(gameDto.getPublicationDate());
        }
        if (gameDto.getAgeRating() != null) {
            updatedGame.setAgeRating(gameDto.getAgeRating());
        }

        if (gameDto.getDeveloper() != null) {
            updatedGame.setDeveloper(gameDto.getDeveloper());
        }
        if (gameDto.getPublisher() != null) {
            updatedGame.setPublisher(gameDto.getPublisher());
        }
        if (gameDto.getGameImage() != null) {
            updatedGame.setGameImage(gameDto.getGameImage());
        }
        Game game = GameMapper.dtoToGame(updatedGame); 
        return gameRepository.save(game).getId();
    }

    public List<ReviewDto> getReviewsByGameId(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        Set<Review> reviews = game.getReviews();
        return reviews.stream().map(review -> ReviewMapper.convertReviewToDto(review, true)).toList();
    }

}
