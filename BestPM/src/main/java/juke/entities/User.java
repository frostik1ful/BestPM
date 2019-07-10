package juke.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import utils.Language;

/**
 * Simple JavaBean object that represents User
 * 
 * @author Sergey Christensen
 */

@Entity
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877701578616353580L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@NotNull
	@Column(name = "username", unique = true)
	private String username;

	@NotNull
	@Column(name = "email", unique = true)
	private String email;

	@NotNull
	@Column(name = "password")
	private String password;

	@Transient
	private String confirmPassword;

	@Column(name = "balance")
	private Long balance = 10000L;

	@Column(name = "donate")
	private Long donate = 10L;

	@Column(name = "creditRating")
	private Float creditRating = (float) 10;

	@Column(name = "banned")
	private boolean banned = false;

	@ManyToOne
	@JoinColumn(name = "office_id")
	private Office office;

	@Column(name = "userImage")
	private String userImage = "defaultUser.png";

	@OneToMany(mappedBy = "user")
	private List<HistoryEvent> historyEvents;

	@OneToMany(mappedBy = "user")
	private List<UserMotivators> motivators;

	@Enumerated(EnumType.STRING)
	@Column(name = "language", nullable = false)
	private Language language = Language.RU;

	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role;

	@OneToMany(mappedBy = "user")
	private List<Programmer> programmers;

	// @OneToMany аннотации через mappedBy указано свойство дочернего класса,
	// которое связывает его с родительским. CascadeType.ALL все операции изменения
	// в коллекции (добавление/изменение) отражает в базе данных. Атрибут аннотации
	// orphanRemoval=true указывает, что при удалении элемента из коллекции, нужно
	// удалить элемент в базе данных. FetchType.LAZY — ленивая выборка. Элементы
	// коллекции будут выбираться из базы данных только при обращении к какому-либо
	// свойству коллекции.
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
	private Set<Project> projects;

	@OneToMany(mappedBy = "user")
	private List<Credit> creditList;

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public User() {
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<Credit> getCreditList() {
		return creditList;
	}

	public void setCreditList(List<Credit> creditList) {
		this.creditList = creditList;
	}

	public Office getOffice() {
		return office;
	}

	public List<HistoryEvent> getHistoryEvents() {
		return historyEvents;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public void setHistoryEvents(List<HistoryEvent> historyEvents) {
		this.historyEvents = historyEvents;
	}

	public List<UserMotivators> getMotivators() {
		return motivators;
	}

	public List<Programmer> getProgrammers() {
		return programmers;
	}

	public void setMotivators(List<UserMotivators> motivators) {
		this.motivators = motivators;
	}

	public void setProgrammers(List<Programmer> programmers) {
		this.programmers = programmers;
	}

	public Long getDonate() {
		return donate;
	}

	public Float getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(Float creditRating) {
		this.creditRating = creditRating;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setDonate(Long donate) {
		this.donate = donate;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public Set<Role> getRoles() {
		return role;
	}

	public void setRoles(Set<Role> role) {
		this.role = role;
	}

	public List<Project> getSortedProjects() {
		List<Project> sortedList = new ArrayList<Project>();
		sortedList.addAll(projects);
		Collections.sort(sortedList);
		return sortedList;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}
}
