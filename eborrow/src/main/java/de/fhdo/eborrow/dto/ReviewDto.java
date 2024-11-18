package de.fhdo.eborrow.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import de.fhdo.eborrow.domain.Game;

//TODO: GameID oder game?!
public class ReviewDto {

	private Long id;

	private String reviewHeadline;

	private String reviewText;

	private int rating;

	private LocalDate reviewDate;

	private GameDto gameDto;

	private AccountDto accountDto;

	public ReviewDto(){}

	public ReviewDto(Long id, String reviewHeadline, String reviewText, int rating, LocalDate reviewDate) {
		this.id = id;
		this.reviewHeadline = reviewHeadline;
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

	public GameDto getGameDto() {
		return gameDto;
	}

	public void setGameDto(GameDto gameDto) {
		this.gameDto = gameDto;
	}

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public String getReviewHeadline() {
		return reviewHeadline;
	}

	public void setReviewHeadline(String reviewHeadline) {
		this.reviewHeadline = reviewHeadline;
	}
}
