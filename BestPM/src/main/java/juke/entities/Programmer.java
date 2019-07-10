package juke.entities;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import utils.Rating;

@Entity
@Table(name = "programmers")
public class Programmer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7142514312474555909L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Programmer_Id")
	private int id;

	@Column(name = "ProgrammerName")
	private String name;

	@Column(name = "ProgrammerExp")
	private double experience;

	@Column(name = "ProgrammerRating")
	@Enumerated(EnumType.STRING)
	private Rating rating;

	@Column(name = "ProgrammerSalary")
	private int money;

	@Column(name = "ProgrammerMotivation")
	private float motivation;

	@Column(name = "ProgrammerProductivity")
	private float productivity;

	@Column(name = "IsHired")
	private boolean isHired;

	@Column(name = "change_motivation")
	private boolean canMotivatedChanged;

	@Column(name = "works")
	private int dayInWork;

	@Column(name = "event_time")
	private int dayInEvent;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Programmer() {
	}

	public Programmer(String name, double experience) {
		this.name = name;
		this.experience = experience;
		if (experience <= 500) {
			rating = Rating.TRAINEE;
			money = new Random().nextInt(100) + 400;
		} else if (experience <= 1000) {
			rating = Rating.JUNIOR;
			money = new Random().nextInt(300) + 600;
		} else if (experience <= 2000) {
			rating = Rating.MIDDLE;
			money = new Random().nextInt(1000) + 1000;
		} else if (experience <= 4000) {
			rating = Rating.SENIOR;
			money = new Random().nextInt(1500) + 2000;
		} else {
			rating = Rating.TEAMLEAD;
			money = new Random().nextInt(1000) + 3000;
		}
		canMotivatedChanged = true;
		dayInWork = 0;
		dayInEvent = -1;
		float leftLimit = 1F;
		float rightLimit = 10F;
		motivation = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);
		productivity = leftLimit + new Random().nextFloat() * (rightLimit - leftLimit);

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getExperience() {
		return experience;
	}

	public Rating getRating() {
		return rating;
	}

	public int getMoney() {
		return money;
	}

	public float getMotivation() {
		return motivation;
	}

	public float getProductivity() {
		return productivity;
	}

	public boolean isHired() {
		return isHired;
	}

	public Project getProject() {
		return project;
	}

	public User getUser() {
		return user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setMotivation(float motivation) {
		this.motivation = motivation;
	}

	public void setProductivity(float productivity) {
		this.productivity = productivity;
	}

	public void setHired(boolean isHired) {
		this.isHired = isHired;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCanMotivatedChanged() {
		return canMotivatedChanged;
	}

	public int getDayInWork() {
		return dayInWork;
	}

	public void setCanMotivatedChanged(boolean canMotivatedChanged) {
		this.canMotivatedChanged = canMotivatedChanged;
	}

	public void setDayInWork(int dayInWork) {
		this.dayInWork = dayInWork;
	}

	public int getDayInEvent() {
		return dayInEvent;
	}

	public void setDayInEvent(int dayInEvent) {
		this.dayInEvent = dayInEvent;
	}

}
