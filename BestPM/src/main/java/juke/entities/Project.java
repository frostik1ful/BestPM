package juke.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import utils.TypeOfPayment;

@Entity
@Table(name = "projects")
public class Project implements Serializable ,Comparator<Project>, Comparable<Project>{
	private static final long serialVersionUID = -1614927876210863170L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name", nullable = false, length = 55)
	private String name;
	@Column(name = "time", nullable = false)
	private int time;
	@Column(name = "timeLeft", nullable = false)
	private int timeLeft;
	@Column(name = "timeOutLeft", nullable = false)
	private int timeOutLeft;
	@Column(name = "money", nullable = false)
	private int money;
	@Column(name = "moneyLeft", nullable = false)
	private int moneyLeft;
	@Column(name = "difficult", nullable = false)
	private float difficult;
	@Column(name = "difficultLeft", nullable = false)
	private float difficultLeft;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment", nullable = false)
	private TypeOfPayment typeOfPayment;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
	private List<Programmer> programmers = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "progressPercent", nullable = false)
	private float progressPercent=0;
	
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project() {
	}

	public Project(String name, int time, int money, float difficult, TypeOfPayment typeOfPayment) {
		this.name = name;
		this.time = time;
		this.timeLeft=time;
		this.timeOutLeft=(int) (time*0.2);
		this.money = money;
		this.moneyLeft = money;
		this.difficult = difficult;
		this.difficultLeft=difficult;
		this.typeOfPayment = typeOfPayment;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public int getTimeOutLeft() {
		return timeOutLeft;
	}

	public void setTimeOutLeft(int timeOutLeft) {
		this.timeOutLeft = timeOutLeft;
	}

	public float getDifficultLeft() {
		return difficultLeft;
	}

	public void setDifficultLeft(float difficultLeft) {
		this.difficultLeft = difficultLeft;
	}
	public String getStringDifficult() {
		String num = String.valueOf(difficultLeft);
		if(num.contains(".")) {
			try {
			num = num.substring(0,num.indexOf(".")+3 );
			}
			catch (Exception e) {
				num = num.substring(0,num.indexOf(".")+2 );
			}
			}
		return num;
	}

	public float getProgressPercent() {
		return progressPercent;
	}
	public String getStringPercent() {
		String num = String.valueOf(progressPercent);
		if(num.contains(".")) {
			try {
			num = num.substring(0,num.indexOf(".")+3 );
			}
			catch (Exception e) {
				num = num.substring(0,num.indexOf(".")+2 );
			}
		}
		return num;
	}

	public void setProgressPercent(float progressPercent) {
		this.progressPercent = progressPercent;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getMoneyLeft() {
		return moneyLeft;
	}

	public void setMoneyLeft(int moneyLeft) {
		this.moneyLeft = moneyLeft;
	}

	public float getDifficult() {
		return this.difficult;
	}

	public void setDifficult(float difficult) {
		this.difficult = difficult;
	}

	public TypeOfPayment getTypeOfPayment() {
		return typeOfPayment;
	}

	public void setTypeOfPayment(TypeOfPayment typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}

	public List<Programmer> getProgrammers() {
		return programmers;
	}

	public void setProgrammers(List<Programmer> programmers) {
		this.programmers = programmers;
	}
	
	@Override
	public int compareTo(Project arg0) {
		// TODO Auto-generated method stub
		return this.name.compareTo(arg0.name);
	}

	@Override
	public int compare(Project p0, Project p1) {
		// TODO Auto-generated method stub
		return (int) (p0.id-p1.id);
	}
}