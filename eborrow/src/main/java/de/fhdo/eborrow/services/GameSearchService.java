package de.fhdo.eborrow.services;

import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameSearchService {

	private GameService gameService;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Autowired
	public GameSearchService(GameService gameService){
		this.gameService = gameService;
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

	public List<GameDto> getGamesByDate(String dateString, boolean after){
		LocalDate date = LocalDate.parse(dateString, formatter);

		List<GameDto> gameDtos = gameService.getAll();
		List<GameDto> results;

		results = gameDtos.stream().filter(gameDto -> {
				if(after){
					return gameDto.getPublication().isAfter(date);
				}else{
					return gameDto.getPublication().isBefore(date);
				}
			}
		).toList();

		return results;
	}



}
