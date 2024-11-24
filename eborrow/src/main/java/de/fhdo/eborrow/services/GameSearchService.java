package de.fhdo.eborrow.services;

import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform;
import de.fhdo.eborrow.dto.RichGameDto;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class GameSearchService {

	private GameService gameService;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final int maxAllowedLevenshteinDistance = 5;

	@Autowired
	public GameSearchService(GameService gameService){
		this.gameService = gameService;
	}

	public List<RichGameDto> sortByTitle(boolean naturalOrder){
		List<RichGameDto> gameDtos = gameService.getAll();
		List<RichGameDto> result;

		if(naturalOrder){
			result = gameDtos.stream().sorted(Comparator.comparing(RichGameDto::getTitle)).toList();
		}else{
			result = gameDtos.stream().sorted((e1, e2) -> e2.getTitle().compareTo(e1.getTitle())).toList();
		}

		return result;
	}

	public List<RichGameDto> getGamesByDate(String dateString, boolean after){
		LocalDate date = LocalDate.parse(dateString, formatter);

		List<RichGameDto> gameDtos = gameService.getAll();
		List<RichGameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
				if(after){
					return gameDto.getPublicationDate().isAfter(date);
				}else{
					return gameDto.getPublicationDate().isBefore(date);
				}
			}
		).toList();

		return results;
	}

	public List<RichGameDto> gamesByGenre(String genre){
		List<RichGameDto> gameDtos = gameService.getAll();
		List<RichGameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
			List<String> genres = gameDto.getGenres();
			return genres.contains(genre);
		}).toList();

		return results;
	}

	public List<RichGameDto> gamesByPlatform(String platform){
		List<RichGameDto> gameDtos = gameService.getAll();
		List<RichGameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
			List<String> platforms = gameDto.getPlatforms();
			return platforms.contains(platform);
		}).toList();

		return results;
	}

	public List<RichGameDto> gamesByDeveloper(String developer){
		List<RichGameDto> gameDtos = gameService.getAll();
		List<RichGameDto> results;

		results = gameDtos.stream().filter(gameDto -> gameDto.getDeveloper().equals(developer)).toList();
		return results;
	}

	public List<RichGameDto> getGamesBySearchQuery(String query){
		List<RichGameDto> gameDtos = gameService.getAll();
		List<RichGameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
			LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
			int distance = levenshteinDistance.apply(gameDto.getTitle(), query);
			System.out.println(distance);
			return distance <= maxAllowedLevenshteinDistance;
		}).toList();

		return results;
	}


}
