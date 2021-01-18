package bean;

import java.util.List;

public class SessionBean {

	private String username;
	private List<EventBean> eventBeanList;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public List<EventBean> getEventBeans() {
		return eventBeanList;
	}

	public void setEventBeans(List<EventBean> eventBeans) {
		this.eventBeanList = eventBeans;
	}
	
}
