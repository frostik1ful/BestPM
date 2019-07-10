package juke.utils.classes;

import java.util.ArrayList;
import java.util.List;

public class NoticeMessage {
	private List<String> notice = new ArrayList<>();
	private boolean showNotify = true;
	
	public NoticeMessage() {
		
	}
	public List<String> getNotice() {
		return notice;
	}
	public void setNotice(List<String> notice) {
		this.notice = notice;
	}
	public boolean isShowNotify() {
		return showNotify;
	}
	public void setShowNotify(boolean showNotify) {
		this.showNotify = showNotify;
	}
	
}
