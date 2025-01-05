package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

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

    @QueryMapping("richGame")
    public RichGameDto getRichGameById(@Argument Long id) {
        return gameService.getRichGameById(id);
    }

    @QueryMapping("game")
    public GameDto getGameById(@Argument Long id) {
        return gameService.getGameById(id);
    }

    @QueryMapping("allGenres")
    public List<String> getAllGenres() {
        return gameService.getAllGenres();
    }

    @QueryMapping("allPlatforms")
    public List<String> getAllPlatforms() {
        return gameService.getAllPlatforms();
    }

    @QueryMapping("allAgeRatings")
    public List<String> getAllAgeRatings() {
        return gameService.getAllAgeRatings();
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

    @SchemaMapping(typeName = "RichGame", field = "reviews")
    public List<ReviewDto> reviews(RichGameDto richGame) {
        return richGame.getReviewsDto();
    }
}
