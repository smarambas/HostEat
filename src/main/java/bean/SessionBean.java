package bean;

import java.util.ArrayList;
import java.util.List;

public class SessionBean {

	private UserBean userBean;
	private List<EventBean> eventBeanList;
	private List<EventBean> searchedList;
	private List<UserBean> savedHosts;
	
	public SessionBean() {
		this.userBean = new UserBean();
		this.eventBeanList = new ArrayList<>();
		this.searchedList = new ArrayList<>();
		this.savedHosts = new ArrayList<>();
	}
	
	public List<EventBean> getEventBeanList() {
		return eventBeanList;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public void setEventBeanList(List<EventBean> eventBeanList) {
		this.eventBeanList = eventBeanList;
	}

	public List<EventBean> getSearchedList() {
		return searchedList;
	}

	public void setSearchedList(List<EventBean> searchedList) {
		this.searchedList = searchedList;
	}

	public List<UserBean> getSavedHosts() {
		return savedHosts;
	}

	public void setSavedHosts(List<UserBean> savedHosts) {
		this.savedHosts = savedHosts;
	}
	
	public boolean containsSavedUser(String username) {
		boolean result = false;
				
		for(UserBean user : this.savedHosts) {
			if(user.getUsername().equals(username)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public void removeFromSavedUser(String username) {
		int i = 0;
		
		for(UserBean user : this.savedHosts) {
			if(user.getUsername().equals(username)) {
				this.savedHosts.remove(i);
				break;
			}
			else {
				i++;
			}
		}
	}
	
	public boolean containsEventBean(EventBean eventBean) {
		boolean result = false;
		
		for(EventBean event : this.eventBeanList) {
			if(event.getEventOwner().equals(eventBean.getEventOwner()) && 
				event.getDateTime().equals(eventBean.getDateTime())) {
				result = true;
				break;
			}
		}
		
		return result;
	}

}
