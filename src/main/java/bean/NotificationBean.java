package bean;

public class NotificationBean {

	private UserBean user;
	private String text;
	private String type;
	private String date;
	
	public NotificationBean() {}
	
	public NotificationBean(UserBean user, String text, String type, String date) {
		this.user = user;
		this.text = text;
		this.type = type;
		this.date = date;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
