package de.fhdo.eborrow.services;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.GameDto;
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
        Game game = gameRepository.findById(id).get();
        return gameMapper.gameToDto(game);
    }

    public void deleteGame(Long id) {
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
        Game updatedGame;
        if (gameDto.getId() != null) {
            updatedGame = gameRepository.findById(gameDto.getId()).get();
        } else {
            updatedGame = new Game();
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
            updatedGame.setGameImage(Base64.getDecoder().decode(gameDto.getGameImage().split(",")[1]));
        }

        return gameRepository.save(updatedGame).getId();
    }

}
