package de.fhdo.eborrow.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;

//TODO: Rating Bounds
@Entity
@Table(name = "review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String reviewHeadline;

	private String reviewText;

	private int rating;

	@DateTimeFormat
	private LocalDate reviewDate;

	@ManyToOne
    @JoinColumn(name = "game_id")
	@JsonBackReference
	private Game game;

	public Review(){}

	public Review(Long id, String reviewHeadline ,String reviewText, int rating, LocalDate reviewDate) {
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getReviewHeadline() {
		return reviewHeadline;
	}

	public void setReviewHeadline(String reviewHeadline) {
		this.reviewHeadline = reviewHeadline;
	}
}
