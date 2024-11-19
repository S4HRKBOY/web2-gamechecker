package de.fhdo.eborrow.graphqlapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;

@Controller
public class GameGraphQlController {
    private GameService gameService;

    @Autowired
    public GameGraphQlController(GameService gameService) {
        this.gameService = gameService;
    }

    @QueryMapping("games")
    public List<GameDto> getGames() {
        return gameService.getAll();
    }

    @QueryMapping("game")
    public GameDto getGameById(@Argument Long id) {
        return gameService.getGameById(id);
    }

    @MutationMapping("deleteGame")
    public Boolean deleteGameById(@Argument Long id) {
        try {
            gameService.deleteGameById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @MutationMapping("createGame")
    public GameDto createGame(@Argument GameDto game) {
        Long gameId = gameService.createGame(game);
        return gameService.getGameById(gameId);
    }

    @MutationMapping("updateGame")
    public GameDto updateGame(@Argument GameDto game) {
        Long id = gameService.updateGame(game); 
        return gameService.getGameById(id);
    }

    /*@QueryMapping("reviews")
    public List<ReviewDto> getReviewsByGameId(@Argument Long id) {
        return gameService.getReviewsByGameId(id); 
    }*/
}
