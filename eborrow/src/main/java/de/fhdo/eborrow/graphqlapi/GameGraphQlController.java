package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.controller.wrapper.FilterInfo;
import de.fhdo.eborrow.controller.wrapper.Query;
import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.services.GameSearchService;
import de.fhdo.eborrow.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
public class GameGraphQlController {

    private GameService gameService;
    private GameSearchService gameSearchService;

    @Autowired
    public GameGraphQlController(GameService gameService, GameSearchService gameSearchService) {
        this.gameService = gameService;
        this.gameSearchService = gameSearchService;
    }

    @QueryMapping("games")
    public List<GameDto> getGames() {
        return gameService.getAll();
    }

    @QueryMapping("game")
    public RichGameDto getRichGameById(@Argument Long id) {
        return gameService.getRichGameById(id);
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

    @SchemaMapping(typeName = "Game", field = "reviews")
    public List<ReviewDto> reviews(RichGameDto richGame) {
        return richGame.getReviewsDto();
    }

    @QueryMapping("gamesByQuery")
    public List<GameDto> getGamesByQuery(@Argument Query query){
        if (Objects.equals(query.getQuery(), "")) {
            return gameService.getAll();
        }
        return gameSearchService.getGamesBySearchQuery(query.getQuery());
    }

    @QueryMapping("gamesByFilter")
    public List<GameDto> getGamesByFilter(@Argument FilterInfo filterInfo){
        return gameSearchService.getGamesByFilterInfo(filterInfo);
    }
}
