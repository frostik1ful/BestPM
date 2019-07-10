/**
 * 
 */
package juke.entities;

import java.io.Serializable;

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

import utils.MotivatorType;

/**
 * @author Anton
 *
 */
@Entity
@Table(name = "user_motivators")
public class UserMotivators implements Serializable {

	private static final long serialVersionUID = -3006985034332882497L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_motivator_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "motivator_name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivator_type")
	private MotivatorType type;

	@Column(name = "motivator_power", nullable = false)
	private Float power;

	@Column(name = "time_action")
	private Integer timeAction;

	@Column(name = "isActive")
	private boolean isActive = true;

	public UserMotivators() {
	}

	public UserMotivators(Motivator motivator) {
		this.name = new String(motivator.getName());
		this.power = motivator.getPower();
		this.timeAction = motivator.getTimeOfAction();
		this.type = motivator.getType();
	}

	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
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

	public Integer getTimeAction() {
		return timeAction;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(MotivatorType type) {
		this.type = type;
	}

	public void setPower(Float motivatorPower) {
		this.power = motivatorPower;
	}

	public void setTimeAction(Integer timeAction) {
		this.timeAction = timeAction;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
