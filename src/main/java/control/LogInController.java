package control;

import java.util.List;
import bean.SessionBean;
import bean.UserBean;
import exceptions.WrongPasswordException;
import model.Event;
import model.User;
import model.UserType;
import model.dao.EventDAO;
import model.dao.UserDAO;

public class LogInController {
	
	public SessionBean logIn(UserBean userBean) throws WrongPasswordException {
		User user;
		SessionBean result = new SessionBean();
		List<Event> eventList;
		
		try {
			user = UserDAO.retrieveUserByUsername(userBean.getUsername());
			
			if(!(userBean.getPassword().equals(user.getPassword()))) {
				throw new WrongPasswordException("Wrong password");
			}
			
			UserType userType = user.getType();
			if(userType.equals(UserType.HOST)) {
				eventList = EventDAO.retrieveEventsByUsername(user);
				result.setUsername(user.getUsername());
				result.setEventList(eventList);
			}
			else {
				eventList = null;	//joined event list
				result.setUsername(user.getUsername());
				result.setEventList(eventList);
			}			
		} catch (Exception e) {
			result = null;
		}
		
		return result;
	}
}
