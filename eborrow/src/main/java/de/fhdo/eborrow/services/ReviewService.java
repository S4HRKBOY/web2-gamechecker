package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.GameMapper;
import de.fhdo.eborrow.converters.ReviewMapper;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.GameDto;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

	private ReviewMapper reviewMapper;
	private ReviewRepository reviewRepository;
	private GameMapper gameMapper;
	private GameService gameService;

	@Autowired
	public ReviewService(ReviewMapper reviewMapper, ReviewRepository reviewRepository, GameService gameService,
			GameMapper gameMapper){
		this.reviewMapper = reviewMapper;
		this.reviewRepository = reviewRepository;
		this.gameMapper = gameMapper;
		this.gameService = gameService;
	}

	public Long addReview(ReviewDto reviewDto){
		Review review = reviewMapper.convertDtoToReview(reviewDto);
		return reviewRepository.save(review).getId();
	}

	public ReviewDto getReviewById(Long id){
		Review review = reviewRepository.findById(id).get();
		return reviewMapper.convertReviewToDto(review);
	}

	public void deleteReviewById(Long id){
		reviewRepository.deleteById(id);
	}

	public List<ReviewDto> getAll() {
		List<ReviewDto> reviewDtos = new ArrayList<>();
		reviewRepository.findAll().forEach(review -> reviewDtos.add(reviewMapper.convertReviewToDto(review)));
		return reviewDtos;
	}

	public Long updateReview(ReviewDto reviewDto) {
		Review reviewToUpdate;

		if (reviewDto.getId() != null) {
			reviewToUpdate = reviewRepository.findById(reviewDto.getId()).get();
		} else {
			reviewToUpdate = new Review();
			reviewToUpdate.setGame(gameMapper.dtoToGame(gameService.getGameById(reviewDto.getGameId())));
		}

		if (reviewDto.getReviewText() != null) {
			reviewToUpdate.setReviewText(reviewToUpdate.getReviewText());
		}

		reviewToUpdate.setRating(reviewDto.getRating());
		return reviewRepository.save(reviewToUpdate).getId();
	}

}
