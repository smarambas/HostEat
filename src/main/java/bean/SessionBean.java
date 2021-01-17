package bean;

import java.util.List;
import model.Event;

public class SessionBean {

	private String username;
	private List<Event> eventList;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<Event> getEventList() {
		return eventList;
	}
	
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
}
