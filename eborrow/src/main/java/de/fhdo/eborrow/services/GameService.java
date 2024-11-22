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
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.repositories.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Long createGame(RichGameDto gameDto) {
        Game game = GameMapper.richDtoToGame(gameDto);
        return gameRepository.save(game).getId();
    }

    public RichGameDto getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        return GameMapper.gameToRichDto(game);
    }

    @Transactional
    public void deleteGameById(Long id) {
        gameRepository.removeGameAssociations(id); 
        gameRepository.deleteById(id);
    }

    public List<RichGameDto> getAll() {
        List<RichGameDto> games = new ArrayList<>();
        for (Game game : gameRepository.findAll()) {
            games.add(GameMapper.gameToRichDto(game));
        }
        return games;
    }

    public Long updateGame(RichGameDto gameDto) {
        Game game = GameMapper.richDtoToGame(gameDto); 
        Game updatedGame;
        if (game.getId() != null) {
            updatedGame = gameRepository.findById(game.getId())
                    .orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        } else {
            updatedGame = new Game();
        }

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
        RichGameDto gameDto = getGameById(gameId);
		gameDto.getReviewsDto().add(reviewDto);
        gameRepository.save(GameMapper.richDtoToGame(gameDto));
    }

    /*public List<ReviewDto> getReviewsByGameId(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        Set<Review> reviews = game.getReviews();
        return reviews.stream().map(review -> ReviewMapper.convertReviewToDto(review)).toList();
    }*/

}
