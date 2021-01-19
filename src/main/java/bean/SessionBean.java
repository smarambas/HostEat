package bean;

import java.util.List;

public class SessionBean {

	private String userType;
	private String username;
	private List<EventBean> eventBeanList;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<EventBean> getEventBeanList() {
		return eventBeanList;
	}

	public void setEventBeanList(List<EventBean> eventBeanList) {
		this.eventBeanList = eventBeanList;
	}

}
