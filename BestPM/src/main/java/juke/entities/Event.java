package juke.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import utils.EventType;

@Entity
@Table(name = "events")
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2223954636302215786L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Integer id;

	@Column(name = "event_type")
	private EventType eventType;

	@Column(name = "event_name")
	private String name;

	@Column(name = "event_power_float")
	private float powerInFloat;

	@Column(name = "event_power_int")
	private int powerInInteger;

	@Column(name = "event_time")
	private int eventTime;

	@Column(name = "Happy")
	private Boolean happy;

	@OneToMany(mappedBy = "event")
	private List<HistoryEvent> historyEvents;

	public Event() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPowerInFloat() {
		return powerInFloat;
	}

	public void setPowerInFloat(float powerInFloat) {
		this.powerInFloat = powerInFloat;
	}

	public int getPowerInInteger() {
		return powerInInteger;
	}

	public void setPowerInInteger(int powerInInteger) {
		this.powerInInteger = powerInInteger;
	}

	public int getEventTime() {
		return eventTime;
	}

	public void setEventTime(int eventTime) {
		this.eventTime = eventTime;
	}

	public Boolean getHappy() {
		return happy;
	}

	public void setHappy(Boolean happy) {
		this.happy = happy;
	}

	public List<HistoryEvent> getHistoryEvents() {
		return historyEvents;
	}

	public void setHistoryEvents(List<HistoryEvent> historyEvents) {
		this.historyEvents = historyEvents;
	}

}
