package model;

import java.util.GregorianCalendar;

public class Notification {

	private User user;
	private String text;
	private GregorianCalendar date;
	private NotificationType type;
	
	public Notification(User user, String text, NotificationType type) {
		this.user = user;
		this.text = text;
		this.type = type;
		this.date = new GregorianCalendar();
	}
	
	public Notification() {
		this.date = new GregorianCalendar();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}
	
}
