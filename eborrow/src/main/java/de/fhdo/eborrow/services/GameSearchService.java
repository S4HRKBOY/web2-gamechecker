package de.fhdo.eborrow.services;

import de.fhdo.eborrow.dto.GameDto;
import org.apache.commons.text.similarity.FuzzyScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class GameSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameSearchService.class);

	private GameService gameService;
	private ReviewService reviewService;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final int minScoreNeeded = 5;

	@Autowired
	public GameSearchService(GameService gameService, ReviewService reviewService){
		this.gameService = gameService;
		this.reviewService = reviewService;
	}

	public List<GameDto> sortByTitle(boolean naturalOrder){
		List<GameDto> gameDtos = gameService.getAll();
		List<GameDto> result;

		if(naturalOrder){
			result = gameDtos.stream().sorted(Comparator.comparing(GameDto::getTitle)).toList();
		}else{
			result = gameDtos.stream().sorted((e1, e2) -> e2.getTitle().compareTo(e1.getTitle())).toList();
		}

		return result;
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

		results = gameDtos.stream().filter(gameDto -> {
			FuzzyScore fuzzyScore = new FuzzyScore(Locale.ENGLISH);
			int score = fuzzyScore.fuzzyScore(gameDto.getTitle(), query);
			return score >= minScoreNeeded;
		}).toList();

		return results;
	}


}
