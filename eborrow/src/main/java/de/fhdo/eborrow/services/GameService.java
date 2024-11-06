package de.fhdo.eborrow.services;

import java.util.ArrayList;
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

        updatedGame.setLicences(gameDto.getLicences());
        if (gameDto.getId() != null) {
            if (gameDto.getLicences() > updatedGame.getLicences()) {
                int increase = gameDto.getLicences() - updatedGame.getLicences();
                updatedGame.setRemainingLicences(updatedGame.getRemainingLicences() + increase);
            }
            if (gameDto.getLicences() < updatedGame.getLicences()) {
                int decrease = updatedGame.getLicences() - gameDto.getLicences();
                int newRemainingLicences = updatedGame.getRemainingLicences() - decrease;
                if (newRemainingLicences <= 0) {
                    updatedGame.setRemainingLicences(0);
                } else {
                    updatedGame.setRemainingLicences(newRemainingLicences);
                }
            }
        }

        if (gameDto.getGenre() != null) {
            updatedGame.setGenre(gameDto.getGenre());
        }
        if (gameDto.getPublication() != null) {
            updatedGame.setPublication(gameDto.getPublication());
        }

        updatedGame.setAge(gameDto.getAge());

        if (gameDto.getDeveloper() != null) {
            updatedGame.setDeveloper(gameDto.getDeveloper());
        }
        if (gameDto.getPublisher() != null) {
            updatedGame.setPublisher(gameDto.getPublisher());
        }
        if (gameDto.getImage() != null) {
            updatedGame.setImage(gameDto.getImage());
        }

        return gameRepository.save(updatedGame).getId();
    }

}
