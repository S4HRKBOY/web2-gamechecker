package de.fhdo.eborrow.domain;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "review", uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "account_id"}))
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String reviewHeadline;

	@Lob
	private String reviewText;

	private int rating;

	@DateTimeFormat
	private LocalDate reviewDate;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id")
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
