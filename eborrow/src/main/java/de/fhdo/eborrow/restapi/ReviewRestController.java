package de.fhdo.eborrow.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.AccountService;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

	private static final String SUCCESS_MESSAGE = "Operation was successful!";
	private static final String PARSING_JSON_ERROR = "Error while trying parse provided json inside the http body!";

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRestController.class);

	private GameService    gameService;
	private AccountService accountService;
	private ReviewService  reviewService;

	private ObjectMapper objectMapper = new ObjectMapper();


	@Autowired
	public ReviewRestController(GameService gameService, AccountService accountService, ReviewService reviewService){
		this.gameService = gameService;
		this.accountService = accountService;
		this.reviewService = reviewService;
	}

	@GetMapping("/reviews")
	public ResponseEntity<ResponseMessage> allReviews(){
		ResponseMessage responseMessage = new ResponseMessage();
		Map<String, Object> results = new HashMap<>();

		responseMessage.setStatus(200);
		responseMessage.setMessage("Operation Successful!");
		results.put("results", reviewService.getAll());
		responseMessage.setResponse(results);

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<ResponseMessage> reviewById(@PathVariable("reviewId") Long reviewId){
		ResponseMessage responseMessage = new ResponseMessage();
		ReviewDto reviewDto = reviewService.getReviewById(reviewId);
		Map<String, Object> results = new HashMap<>();
		HttpStatus httpStatus = null;
		if(reviewDto != null){
			responseMessage.setStatus(200);
			responseMessage.setMessage("Operation Successful!");
			results.put("results", reviewDto);
			responseMessage.setResponse(results);
			httpStatus = HttpStatus.OK;
		}else{
			responseMessage.setStatus(400);
			responseMessage.setMessage(String.format("Could not get the review for the id: %s!", reviewId));
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(responseMessage, httpStatus);
	}

	@GetMapping("/accounts-games")
	public ResponseEntity<ResponseMessage> getAccountsAndGames(){
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(200);
		responseMessage.setMessage("Operation Successful!");

		Map<String, Object> results = new HashMap<>();
		List<GameDto> gameDtos = new ArrayList<>();
		gameService.getAll().forEach(richGameDto -> {
			Game game = GameMapper.richDtoToGame(gameService.getGameById(richGameDto.getId()));
			gameDtos.add(GameMapper.gameToDto(game));
		});
		results.put("games", gameDtos);
		results.put("accounts", accountService.getRichAccounts());
		responseMessage.setResponse(results);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@PostMapping(value = "/create-review", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseMessage> createReview(HttpEntity<String> httpEntity, @RequestParam("gameId") Long gameId, @RequestParam("accountId") Long accountId) throws NotFoundException {
		String json = httpEntity.getBody();
		ResponseMessage responseMessage = new ResponseMessage();
		HttpStatus httpStatus;
		ReviewDto reviewDto = null;
		try {
			reviewDto = objectMapper.readValue(json, ReviewDto.class);
		} catch (JsonProcessingException e) {
			LOGGER.error(PARSING_JSON_ERROR);
		}

		Long id = reviewService.addReview(reviewDto, gameId, accountId);

		if(id == null){
			responseMessage.setStatus(400);
			responseMessage.setMessage("Error occurred while trying to create a new review!");
			httpStatus = HttpStatus.BAD_REQUEST;
		}else{
			responseMessage.setStatus(200);
			httpStatus = HttpStatus.OK;
			responseMessage.setMessage(SUCCESS_MESSAGE);
			Map<String, Object> result = new HashMap<>();
			result.put("review", reviewService.getReviewById(id));
			responseMessage.setResponse(result);
		}

		return new ResponseEntity<>(responseMessage,httpStatus);
	}

	//TODO: Json benötigt ReviewDTO im HTTP-Body
	@DeleteMapping("/delete-review/{reviewId}")
	public ResponseEntity<ResponseMessage> deleteReview(@PathVariable("reviewId") Long reviewId) {
		reviewService.deleteReviewById(reviewId);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage(SUCCESS_MESSAGE);
		responseMessage.setStatus(200);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	//TODO: Json benötigt ReviewDTO
	@PutMapping(value = "/update-review/{reviewId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseMessage> updateReview(HttpEntity<String> httpEntity, @PathVariable("reviewId") Long reviewId) {
		String json = httpEntity.getBody();
		ResponseMessage responseMessage = new ResponseMessage();
		HttpStatus httpStatus = null;
		ReviewDto reviewDto = null;
		try {
			reviewDto = objectMapper.readValue(json, ReviewDto.class);
		} catch (JsonProcessingException e) {
			LOGGER.error(PARSING_JSON_ERROR);
		}
		if(reviewDto != null){
			Long id = reviewService.updateReview(reviewDto);

			if(id == null){
				responseMessage.setStatus(400);
				responseMessage.setMessage("Error occurred while trying to update review!");
				httpStatus = HttpStatus.BAD_REQUEST;
			}else{
				responseMessage.setStatus(200);
				httpStatus = HttpStatus.OK;
				responseMessage.setMessage(SUCCESS_MESSAGE);
				Map<String, Object> result = new HashMap<>();
				result.put("review", reviewService.getReviewById(id));
				responseMessage.setResponse(result);
			}
		}else{
			responseMessage.setStatus(400);
			responseMessage.setMessage("Error occurred while trying to update review!");
			httpStatus = HttpStatus.BAD_REQUEST;
		}


		return new ResponseEntity<>(responseMessage,httpStatus);
	}

	@GetMapping(value = "/exists-by-account-and-game/{accountId}/{gameId}", produces={"application/json", "application/xml"})
	@ResponseStatus(HttpStatus.OK)
    public boolean existsByAccountAndGame(@PathVariable Long accountId, @PathVariable Long gameId) {
        return reviewService.existsByAccountAndGame(accountId, gameId);
    }

}
