package de.fhdo.eborrow.services;

import de.fhdo.eborrow.converters.ReviewMapper;
import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.dto.ReviewDto;
import de.fhdo.eborrow.repositories.GameRepository;
import de.fhdo.eborrow.repositories.ReviewRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
	private ReviewRepository reviewRepository;
	private GameService gameService;
	private AccountService accountService; 
	private GameRepository gameRepository; 

	@Autowired
	public ReviewService(ReviewRepository reviewRepository, GameService gameService, AccountService accountService, GameRepository gameRepository){
		this.reviewRepository = reviewRepository;
		this.gameService = gameService;
		this.accountService = accountService; 
		this.gameRepository = gameRepository; 
	}

	public Long addReview(ReviewDto reviewDto, Long gameId, Long accountId) throws NotFoundException {
		reviewDto.setAccountDto(accountService.getAccountById(accountId));
		gameService.addReview(reviewDto, gameId);
		return reviewRepository.findByGameIdAndAccountId(gameId, accountId).getId();
	}

	public ReviewDto getReviewById(Long id){
		Review review = reviewRepository.findById(id).get();
		return ReviewMapper.convertReviewToDto(review);
	}

	public void deleteReviewById(Long id){
		reviewRepository.deleteById(id);
	}

	public List<ReviewDto> getAll() {
		List<ReviewDto> reviewDtos = new ArrayList<>();
		reviewRepository.findAll().forEach(review -> reviewDtos.add(ReviewMapper.convertReviewToDto(review)));
		return reviewDtos;
	}

//	 public List<ReviewDto> getAllReviewsToGame(Long id){
//	 	List<ReviewDto> reviewDtos = new ArrayList<>();
//	 	reviewRepository.findAll().forEach(review -> {
//	 		if(Objects.equals(review.getGame().getId(), id)){
//	 			reviewDtos.add(ReviewMapper.convertReviewToDto(review));
//	 		}
//	 	});
//	 	return reviewDtos;
//	 }

	public Long updateReview(ReviewDto reviewDto) {
		Review reviewToUpdate;

		if (reviewDto.getId() != null) {
			reviewToUpdate = reviewRepository.findById(reviewDto.getId()).get();
		} else {
			reviewToUpdate = new Review();
			// reviewToUpdate.setGame(GameMapper.dtoToGame(gameService.getGameById(reviewDto.getGameDto().getId())));
		}

		if(reviewDto.getReviewHeadline() != null){
			reviewToUpdate.setReviewHeadline(reviewDto.getReviewHeadline());
		}

		if (reviewDto.getReviewText() != null) {
			reviewToUpdate.setReviewText(reviewDto.getReviewText());
		}

		reviewToUpdate.setRating(reviewDto.getRating());
		return reviewRepository.save(reviewToUpdate).getId();
	}


	public boolean existsByAccountAndGame(Long accountId, Long gameId){
		return reviewRepository.existsByAccountAndGame(accountId, gameId); 
	}

}
