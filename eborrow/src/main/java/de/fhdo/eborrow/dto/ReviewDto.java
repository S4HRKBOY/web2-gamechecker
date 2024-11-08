package de.fhdo.eborrow.dto;

import java.time.LocalDate;

//TODO: GameID oder game?!
public class ReviewDto {

	private Long id;

	private String reviewText;

	private int rating;

	private LocalDate reviewDate;

	private Long gameId;

	public ReviewDto(){}

	public ReviewDto(Long id, String reviewText, int rating, LocalDate reviewDate) {
		this.id = id;
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewDate = reviewDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDate getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
}
