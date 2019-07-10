package juke.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import utils.CreditType;

@Entity
@Table(name = "credits")
public class Credit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7715770797079118606L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Credit_Id")
	private int id;
	
	@Column(name = "bank_Name")
	private String bankName;

	@Column(name = "amount")
	private int amount;

	@Column(name = "credit_rating")
	private float creditRating;

	@Column(name = "credit_rating_change")
	private float creditRatingChange;

	@Column(name = "time_to_return")
	private int timeToReturn;
	
	@Column(name = "base_time_to_return")
	private int baseTimeToReturn;
	
	@Column(name = "percent")
	private float percent;
	
	@Column(name = "credit_Type")
	private CreditType creditType;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Credit() {

	}

	public Credit(int amount, float creditRating, float creditRatingChange, int timeToReturn, float percent, String bankName) {
		this.amount = amount;
		this.creditRating = creditRating;
		this.creditRatingChange = creditRatingChange;
		this.timeToReturn = timeToReturn;
		this.percent = percent;
		this.bankName = bankName;
		baseTimeToReturn = timeToReturn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public CreditType getCreditType() {
		return creditType;
	}

	public void setCreditType(CreditType creditType) {
		this.creditType = creditType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(float creditRating) {
		this.creditRating = creditRating;
	}

	public float getCreditRatingChange() {
		return creditRatingChange;
	}

	public void setCreditRatingChange(float creditRatingChange) {
		this.creditRatingChange = creditRatingChange;
	}

	public int getTimeToReturn() {
		return timeToReturn;
	}

	public void setTimeToReturn(int timeToReturn) {
		this.timeToReturn = timeToReturn;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public int getBaseTimeToReturn() {
		return baseTimeToReturn;
	}

	public void setBaseTimeToReturn(int baseTimeToReturn) {
		this.baseTimeToReturn = baseTimeToReturn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
