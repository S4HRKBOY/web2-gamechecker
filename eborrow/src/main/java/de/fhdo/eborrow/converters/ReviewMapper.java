package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.services.GameService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {


	public ReviewDto convertReviewToDto(Review review){
		if(review == null){
			return null;
		}

		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		reviewDto.setReviewHeadline(review.getReviewHeadline());
		reviewDto.setReviewText(review.getReviewText());
		reviewDto.setReviewDate(review.getReviewDate());
		reviewDto.setRating(review.getRating());
		reviewDto.setGame(review.getGame());

		return reviewDto;
	}

	public Review convertDtoToReview(ReviewDto reviewDto){
		if(reviewDto == null){
			return null;
		}

		Review review = new Review();
		review.setId(reviewDto.getId());
		review.setReviewHeadline(reviewDto.getReviewHeadline());
		review.setReviewText(reviewDto.getReviewText());
		review.setRating(reviewDto.getRating());
		review.setReviewDate(reviewDto.getReviewDate());
		review.setGame(reviewDto.getGame());

		return review;
	}

	protected List<ReviewDto> reviewListToDtoList(List<Review> list) {
		if (list == null) {
			return null;
		}

		List<ReviewDto> result = new ArrayList<ReviewDto>(Math.max((int) (list.size() / .75f) + 1, 16)); 
		for (Review review : list) {
			result.add(convertReviewToDto(review));
		}

		return result;
	}

	protected List<Review> dtoListToReviewList(List<ReviewDto> list) {
		if (list == null) {
			return null;
		}

		List<Review> result = new ArrayList<Review>(Math.max((int) (list.size() / .75f) + 1, 16));
		for (ReviewDto reviewDto : list) {
			result.add(convertDtoToReview(reviewDto));
		}

		return result;
	}

}
