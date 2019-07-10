package juke.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import utils.MotivatorType;

@Entity
@Table(name = "motivators")
public class Motivator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2443910724223679283L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MotivatorId")
	private int id;

	@Column(name = "motivator_name", nullable = false, unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivator_type", nullable = false)
	private MotivatorType type;

	@Column(name = "motivator_power", nullable = false)
	private Float power;

	@Column(name = "motivator_time", nullable = false)
	private int timeOfAction;

	@Column(name = "motivator_price", nullable = false)
	private Integer price;

	@Column(name = "motivator_price_donate", nullable = true)
	private Integer priceDonate;

	@Column(name = "motivator_discription", nullable = false)
	private String discription;

	public Motivator() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public MotivatorType getType() {
		return type;
	}

	public Float getPower() {
		return power;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(MotivatorType type) {
		this.type = type;
	}

	public void setPower(Float power) {
		this.power = power;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public int getTimeOfAction() {
		return timeOfAction;
	}

	public void setTimeOfAction(int timeOfAction) {
		this.timeOfAction = timeOfAction;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Integer getPriceDonate() {
		return priceDonate;
	}

	public void setPriceDonate(Integer priceDonate) {
		this.priceDonate = priceDonate;
	}

}
