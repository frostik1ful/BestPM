package juke.dto;

import java.io.Serializable;

import juke.entities.User;

public class UserInformation implements Serializable {

	/**
	 * DTO for user
	 */
	private static final long serialVersionUID = -4901307407156254405L;

	private long userMoney;
	
	private long donate;

	public UserInformation() {
	}

	public UserInformation(User user) {
		this.donate = user.getDonate();
		this.userMoney = user.getBalance();
	}

	public long getUserMoney() {
		return userMoney;
	}

	public void setUserMoney(long userMoney) {
		this.userMoney = userMoney;
	}

	public long getDonate() {
		return donate;
	}

	public void setDonate(long donate) {
		this.donate = donate;
	}
	
	

}
