package de.fhdo.eborrow.graphqlapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import de.fhdo.eborrow.dto.RichGameDto;
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
    public List<RichGameDto> getGames() {
        return gameService.getAll();
    }

    @QueryMapping("game")
    public RichGameDto getGameById(@Argument Long id) {
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
    public RichGameDto createGame(@Argument RichGameDto game) {
        Long gameId = gameService.createGame(game);
        return gameService.getGameById(gameId);
    }

    @MutationMapping("updateGame")
    public RichGameDto updateGame(@Argument RichGameDto game) {
        Long id = gameService.updateGame(game); 
        return gameService.getGameById(id);
    }

    /*@QueryMapping("reviews")
    public List<ReviewDto> getReviewsByGameId(@Argument Long id) {
        return gameService.getReviewsByGameId(id); 
    }*/
}
