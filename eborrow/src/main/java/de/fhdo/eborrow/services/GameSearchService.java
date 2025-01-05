package de.fhdo.eborrow.services;

import de.fhdo.eborrow.controller.wrapper.FilterInfo;
import de.fhdo.eborrow.dto.GameDto;
import org.apache.commons.text.similarity.FuzzyScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class GameSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameSearchService.class);

	private GameService gameService;
	private ReviewService reviewService;

	private final int minScoreNeeded = 10;

	@Autowired
	public GameSearchService(GameService gameService, ReviewService reviewService){
		this.gameService = gameService;
		this.reviewService = reviewService;
	}

	public List<GameDto> gamesByGenre(List<GameDto> gameDtos, String genre){
		List<GameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
			List<String> genres = gameDto.getGenres();
			return genres.contains(genre);
		}).toList();

		return results;
	}

	public List<GameDto> gamesByPlatform(List<GameDto> gameDtos, String platform){
		List<GameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
			List<String> platforms = gameDto.getPlatforms();
			return platforms.contains(platform);
		}).toList();

		return results;
	}

	public List<GameDto> gamesByDeveloper(List<GameDto> gameDtos, String developer){
		List<GameDto> results;

		results = gameDtos.stream().filter(gameDto -> gameDto.getDeveloper().equals(developer)).toList();
		return results;
	}

	public List<GameDto> getGamesBySearchQuery(String query){
		List<GameDto> gameDtos = gameService.getAll();
		List<GameDto> results;

		results = gameDtos.stream().filter(gameDto -> checkFuzzyScore(gameDto.getTitle(), query)).toList();

		return results;
	}

	public List<GameDto> getGamesByFilterInfo(FilterInfo filterInfo){
		List<GameDto> games = gameService.getAll();

		if(filterInfo == null){
			return games;
		}

		if (!Objects.equals(filterInfo.getGenre(), "All")) {
			games = games.stream().filter(gameDto -> gameDto.getGenres().contains(filterInfo.getGenre())).toList();
		}

		if (filterInfo.getDeveloper() != null && !filterInfo.getDeveloper().isEmpty()
				&& !filterInfo.getDeveloper().isBlank()) {
			games = games.stream().filter(gameDto -> checkFuzzyScore(gameDto.getDeveloper(), filterInfo.getDeveloper())).toList();
		}

		if (!Objects.equals(filterInfo.getPlatform(), "All")) {
			games = games.stream().filter(gameDto -> gameDto.getPlatforms().contains(filterInfo.getPlatform()))
					.toList();
		}

		return games;
	}

	private boolean checkFuzzyScore(String target, String value){
		FuzzyScore fuzzyScore = new FuzzyScore(Locale.ENGLISH);
		return fuzzyScore.fuzzyScore(target, value) >= minScoreNeeded;
	}

}
