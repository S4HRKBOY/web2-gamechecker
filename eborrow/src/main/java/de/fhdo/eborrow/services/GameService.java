package de.fhdo.eborrow.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.repositories.GameRepository;

@Service
public class GameService {

    private GameRepository gameRepository;
    private GameMapper gameMapper;

    @Autowired
    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public Long addGame(GameDto gameDto) {
        Game game = gameMapper.dtoToGame(gameDto);
        return gameRepository.save(game).getId();
    }

    public GameDto getGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        return gameMapper.gameToDto(game);
    }

    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }

    public List<GameDto> getAll() {
        List<GameDto> games = new ArrayList<>();
        for (Game game : gameRepository.findAll()) {
            games.add(gameMapper.gameToDto(game));
        }
        return games;
    }

    public Long updateGame(GameDto gameDto) {
        GameDto updatedGame;
        if (gameDto.getId() != null) {
            updatedGame = gameMapper.gameToDto(gameRepository.findById(gameDto.getId()).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden")));
        } else {
            updatedGame = new GameDto();
        }

        if (gameDto.getTitle() != null) {
            updatedGame.setTitle(gameDto.getTitle());
        }
        if (gameDto.getDescription() != null) {
            updatedGame.setDescription(gameDto.getDescription());
        }

        if (!gameDto.getPlatforms().isEmpty()) {
            updatedGame.setPlatforms(gameDto.getPlatforms());
        }

        if (gameDto.getGenres() != null) {
            updatedGame.setGenres(gameDto.getGenres());
        }
        if (gameDto.getPublicationDate() != null) {
            updatedGame.setPublicationDate(gameDto.getPublicationDate());
        }

        updatedGame.setAgeRating(gameDto.getAgeRating());

        if (gameDto.getDeveloper() != null) {
            updatedGame.setDeveloper(gameDto.getDeveloper());
        }
        if (gameDto.getPublisher() != null) {
            updatedGame.setPublisher(gameDto.getPublisher());
        }
        if (gameDto.getGameImage() != null) {
            updatedGame.setGameImage(gameDto.getGameImage());
        }

        
        return gameRepository.save(gameMapper.dtoToGame(updatedGame)).getId();
    }

    public List<ReviewDto> getReviewsByGameId(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Spiel nicht gefunden"));
        GameDto dto = gameMapper.gameToDto(game); 
        return dto.getReviews(); 
    }



}
