package de.fhdo.eborrow.converters;

import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.graphqlapi.ReviewGraphql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReviewMapper {

	public static ReviewDto convertReviewToDto(Review review) {
		if (review == null) {
			return null;
		}

		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		reviewDto.setReviewHeadline(review.getReviewHeadline());
		reviewDto.setReviewText(review.getReviewText());
		reviewDto.setReviewDate(review.getReviewDate());
		reviewDto.setRating(review.getRating());
		//reviewDto.setGameDto(GameMapper.gameToDto(review.getGame()));
		reviewDto.setAccountDto(AccountMapper.accountToDto(review.getAccount()));

		return reviewDto;
	}

	public static Review convertDtoToReview(ReviewDto reviewDto) {
		if (reviewDto == null) {
			return null;
		}

		Review review = new Review();
		review.setId(reviewDto.getId());
		review.setReviewHeadline(reviewDto.getReviewHeadline());
		review.setReviewText(reviewDto.getReviewText());
		review.setRating(reviewDto.getRating());
		review.setReviewDate(reviewDto.getReviewDate());
		//review.setGame(GameMapper.dtoToGame(reviewDto.getGameDto()));
		review.setAccount(AccountMapper.dtoToAccount(reviewDto.getAccountDto()));

		return review;
	}

	protected static List<ReviewDto> reviewSetToDtoList(Set<Review> set) {
		if (set == null) {
			return null;
		}

		List<ReviewDto> result = new ArrayList<ReviewDto>(Math.max((int) (set.size() / .75f) + 1, 16));
		for (Review review : set) {
			result.add(convertReviewToDto(review));
		}

		return result;
	}

	protected static Set<Review> dtoListToReviewSet(List<ReviewDto> list) {
		if (list == null) {
			return null;
		}

		Set<Review> result = new HashSet<Review>(Math.max((int) (list.size() / .75f) + 1, 16));
		for (ReviewDto reviewDto : list) {
			result.add(convertDtoToReview(reviewDto));
		}

		return result;
	}

}
