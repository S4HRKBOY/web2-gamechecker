package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

	private GameService gameService;
	private GameMapper gameMapper;

	@Autowired
	public ReviewMapper(GameService gameService, GameMapper gameMapper){
		this.gameService = gameService;
		this.gameMapper = gameMapper;
	}

	public ReviewDto convertReviewToDto(Review review){
		if(review == null){
			return null;
		}

		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		reviewDto.setReviewText(review.getReviewText());
		reviewDto.setReviewDate(review.getReviewDate());
		reviewDto.setRating(review.getRating());
		reviewDto.setGameId(review.getGame().getId());

		return reviewDto;
	}

	public Review convertDtoToReview(ReviewDto reviewDto){
		if(reviewDto == null){
			return null;
		}

		Review review = new Review();
		review.setId(reviewDto.getId());
		review.setReviewText(reviewDto.getReviewText());
		review.setRating(reviewDto.getRating());
		review.setReviewDate(reviewDto.getReviewDate());
		review.setGame(gameMapper.dtoToGame(gameService.getGameById(reviewDto.getGameId())));

		return review;
	}

}
