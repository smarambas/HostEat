package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import bean.EventBean;
import bean.SessionBean;
import bean.UserBean;
import exceptions.WrongPasswordException;
import model.Event;
import model.User;
import model.UserType;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;

public class LogInController {
		
	public SessionBean logIn(UserBean userBean) throws WrongPasswordException {
		User user;
		SessionBean sessionBean = new SessionBean();
		List<Event> eventList;
		List<EventBean> eventBeanList = new ArrayList<>();
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		try {
			user = UserDAO.retrieveUserByUsername(userBean.getUsername());			
			
			if(!(userBean.getPassw().equals(user.getPassword()))) {
				throw new WrongPasswordException("Wrong password");
			}
			
			UserType userType = user.getType();
			
			if(userType.equals(UserType.HOST)) {
				eventList = EventDAO.retrieveEventsByUsername(user);
			}
			else {
				eventList = JoinedEventDAO.retrieveJoinedEventsByUsername(user);	
			}	
			
			for(int i = 0; i < eventList.size(); i++) {
				EventBean eventBean = new EventBean();
				
				eventBean.setEventOwner(eventList.get(i).getOwner().toString());
				eventBean.setDateTime(sdf.format(eventList.get(i).getDateTime().getTime()));
				eventBean.setGuestsNumber(eventList.get(i).getGuestsNumber());
				eventBean.setMaxGuestsNumber(eventList.get(i).getMaxGuestsNumber());
				
				if(eventList.get(i).getGuestStatus() == null) {
					eventBean.setGuestStatus("NOSET");
				}
				else {
					eventBean.setGuestStatus(eventList.get(i).getGuestStatus().toString());
				}
				
				if(eventList.get(i).getPayStatus() == null) {
					eventBean.setPayStatus("NOSET");
				}
				else {
					eventBean.setPayStatus(eventList.get(i).getPayStatus().toString());
				}
				
				eventBean.setBill(eventList.get(i).getBill());
				
				eventBeanList.add(eventBean);
			}
			
			sessionBean.setUserType(userType.toString().toUpperCase());
			sessionBean.setUsername(user.getUsername());
			sessionBean.setEventBeanList(eventBeanList);
		} catch (Exception e) {
			sessionBean = null;
			e.printStackTrace();
		}
		
		return sessionBean;
	}
}
