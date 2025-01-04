package de.fhdo.eborrow.restapi;

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
	private static final String PARSING_JSON_ERROR = "Error while trying parse provided json inside the http body! Json is most likely malformed!";

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

	@GetMapping(value = "/reviews", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ReviewDto>> allReviews() throws NotFoundException {
		List<ReviewDto> reviewDtoList = reviewService.getAll();

		if(reviewDtoList == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/{reviewId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReviewDto> reviewById(@PathVariable("reviewId") Long reviewId) throws NotFoundException {
		ReviewDto reviewDto = reviewService.getReviewById(reviewId);

		if(reviewDto == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
	}

	@GetMapping(value =  "/accounts-games", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ResponseMessage> getAccountsAndGames(){
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(200);
		responseMessage.setMessage("Operation Successful!");

		Map<String, Object> results = new HashMap<>();
		List<GameDto> gameDtos = gameService.getAll();
		//gameService.getAll().forEach(richGameDto -> {
		//	Game game = GameMapper.richDtoToGame(gameService.getRichGameById(richGameDto.getId()));
		//	gameDtos.add(GameMapper.gameToDto(game));
		//});
		results.put("games", gameDtos);
		results.put("accounts", accountService.getRichAccounts());
		responseMessage.setResponse(results);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@PostMapping(value = "/create-review", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto, @RequestParam("gameId") Long gameId, @RequestParam("accountId") Long accountId) throws NotFoundException {
		if(reviewDto == null || gameId == null || accountId == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Long reviewId = reviewService.addReview(reviewDto, gameId, accountId);
		if(reviewId == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.CREATED);
	}

	//TODO: Json benötigt ReviewDTO im HTTP-Body
	@DeleteMapping(value = "/delete-review/{reviewId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) throws NotFoundException  {
		if(reviewId == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		reviewService.deleteReviewById(reviewId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//TODO: Json benötigt ReviewDTO
	@PutMapping(value = "/update-review/{reviewId}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReviewDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) throws NotFoundException {
		if(reviewDto == null || reviewDto.getId() == null || !reviewId.equals(reviewDto.getId())){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		reviewService.updateReview(reviewDto);
		return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
	}

	@GetMapping(value = "/exists-by-account-and-game/{accountId}/{gameId}", produces={"application/json", "application/xml"})
	@ResponseStatus(HttpStatus.OK)
    public boolean existsByAccountAndGame(@PathVariable Long accountId, @PathVariable Long gameId) {
        return reviewService.existsByAccountAndGame(accountId, gameId);
    }

}
