package de.fhdo.eborrow.graphqlapi;

import de.fhdo.eborrow.dto.AccountDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;
import de.fhdo.eborrow.services.ReviewService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
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

	@QueryMapping("existsByAccountAndGame")
	public Boolean existsByAccountAndGame(@Argument Long accountId, @Argument Long gameId) {
		return reviewService.existsByAccountAndGame(accountId, gameId);
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
	public ReviewDto addReview(@Argument ReviewDto review, @Argument Long gameId, @Argument Long accountId) throws NotFoundException {
		Long id = reviewService.addReview(review, gameId, accountId);
		return reviewService.getReviewById(id);
	}

	@MutationMapping("updateReview")
	public ReviewDto updateReview(@Argument ReviewDto review){
		Long id = reviewService.updateReview(review);
		return reviewService.getReviewById(id);
	}

	@SchemaMapping(typeName = "Review", field = "account")
    public AccountDto account(ReviewDto review) {
        return review.getAccountDto();
    }

}
