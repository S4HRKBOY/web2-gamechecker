package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.RichGameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReviewGraphQlController {

	private GameService gameService;
	private ReviewService reviewService;

	@Autowired
	public ReviewGraphQlController(GameService gameService, ReviewService reviewService){
		this.gameService = gameService;
		this.reviewService = reviewService;
	}

	@QueryMapping("review")
	public ReviewDto getReviewByid(@Argument Long id){
		return reviewService.getReviewById(id);
	}

	@QueryMapping("reviews")
	public List<ReviewDto> getAllReviews(){
		return reviewService.getAll();
	}

	@MutationMapping("deleteReview")
	public Boolean deleteReviewById(@Argument Long id){
		try{
			reviewService.deleteReviewById(id);
			return true;
		}catch (Exception e){
			return false;
		}
	}

	@MutationMapping("createReview")
	public ReviewDto addReview(@Argument ReviewDto review, @Argument Long gameId, @Argument Long accountId){
		Long id = reviewService.addReview(review, gameId, accountId);
		return reviewService.getReviewById(id);
	}

	@MutationMapping("updateReview")
	public ReviewDto updateReview(@Argument ReviewDto review){
		Long id = reviewService.updateReview(review);
		return reviewService.getReviewById(id);
	}

}
