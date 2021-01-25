package bean;

import java.util.ArrayList;
import java.util.List;

public class SessionBean {

	private UserBean userBean;
	private List<EventBean> eventBeanList;
	private List<EventBean> searchedList;
	
	public SessionBean() {
		this.eventBeanList = new ArrayList<>();
		this.searchedList = new ArrayList<>();
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

}
