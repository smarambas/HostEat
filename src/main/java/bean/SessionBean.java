package bean;

import java.util.List;

public class SessionBean {

//	private String userType;
//	private String username;
	private UserBean userBean;
	private List<EventBean> eventBeanList;
	
//	public String getUserType() {
//		return userType;
//	}
//
//	public void setUserType(String userType) {
//		this.userType = userType;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//	
//	public void setUsername(String username) {
//		this.username = username;
//	}
	
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

}
