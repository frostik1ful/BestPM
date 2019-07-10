package juke.utils.classes;

import java.util.ArrayList;
import java.util.List;

public class ProgressMessage {
	private int id;
	private Long userBalance;
	private Long userDonate;
	private int daysLeft;
	private float difficultLeft;
	private float progressPercent;
	private long SecondsLeft;
	private int money;
	private List<String> notice = new ArrayList<>();
	public ProgressMessage() {

	}

	public ProgressMessage(int id, int daysLeft, float difficultLeft, float progressPercent, Long userBalance,
			int money) {
		this.id = id;
		this.daysLeft = daysLeft;
		this.difficultLeft = difficultLeft;
		this.progressPercent = progressPercent;
		this.userBalance = userBalance;
		this.money = money;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Long userBalance) {
		this.userBalance = userBalance;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(int daysLeft) {
		this.daysLeft = daysLeft;
	}

	public float getdifficultLeft() {
		return difficultLeft;
	}

	public void setdifficultLeft(float progressLeft) {
		this.difficultLeft = progressLeft;
	}
	/*public String getStringDifficultLeft() {
		String num = String.valueOf(difficultLeft);
		if(num.contains(".")) {
			num = num.substring(0,num.indexOf(".")+3 );
		}
		return num;
	}*/

	public float getProgressPercent() {
		return progressPercent;
	}

	public void setProgressPercent(float progressPercent) {
		this.progressPercent = progressPercent;
	}

	public long getSecondsLeft() {
		return SecondsLeft;
	}

	public void setSecondsLeft(long secondsLeft) {
		SecondsLeft = secondsLeft;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Long getUserDonate() {
		return userDonate;
	}

	public void setUserDonate(Long userDonate) {
		this.userDonate = userDonate;
	}
	public List<String> getNotice() {
		return notice;
	}
	public void setNotice(List<String> notice) {
		this.notice = notice;
	}
}
