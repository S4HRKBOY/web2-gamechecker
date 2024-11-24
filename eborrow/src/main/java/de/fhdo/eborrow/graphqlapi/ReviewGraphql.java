package de.fhdo.eborrow.graphqlapi;

import java.time.LocalDate;

public class ReviewGraphql {

	String    reviewHeadline;
	String    reviewText;
	String    rating;
	String    reviewDate;
	String    game;

	public ReviewGraphql() {
	}

	public ReviewGraphql(String reviewHeadline, String reviewText, String rating, String reviewDate, String game) {
		this.reviewHeadline = reviewHeadline;
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewDate = reviewDate;
		this.game = game;
	}

	public String getReviewHeadline() {
		return reviewHeadline;
	}

	public void setReviewHeadline(String reviewHeadline) {
		this.reviewHeadline = reviewHeadline;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}
}
