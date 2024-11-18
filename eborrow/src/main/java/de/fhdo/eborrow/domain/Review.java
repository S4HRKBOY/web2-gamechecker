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

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", 
        nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_GAME_ID",
            foreignKeyDefinition = "FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE"))
	private Game game;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", 
		nullable = false,
        foreignKey = @ForeignKey(
            name = "FK_ACCOUNT_ID",
            foreignKeyDefinition = "FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE"))
	private Account account;


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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getReviewHeadline() {
		return reviewHeadline;
	}

	public void setReviewHeadline(String reviewHeadline) {
		this.reviewHeadline = reviewHeadline;
	}
}
