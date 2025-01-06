package de.fhdo.eborrow.restapi;

import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.ReviewService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewRestController {

	private ReviewService  reviewService;

	@Autowired
	public ReviewRestController(ReviewService reviewService){
		this.reviewService = reviewService;
	}

	@GetMapping(value = "/reviews", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<ReviewDto>> allReviews() {
		List<ReviewDto> reviewDtoList = reviewService.getAll();

		if(reviewDtoList == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
	}

	@GetMapping(value = "/{reviewId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ReviewDto> reviewById(@PathVariable("reviewId") Long reviewId) {
		ReviewDto reviewDto = reviewService.getReviewById(reviewId);

		if(reviewDto == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
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

	@DeleteMapping(value = "/delete-review/{reviewId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {
		if(reviewId == null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		reviewService.deleteReviewById(reviewId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/update-review/{reviewId}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReviewDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
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
