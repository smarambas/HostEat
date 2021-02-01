package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import bean.EventBean;
import bean.SessionBean;
import bean.UserBean;
import exceptions.DuplicateRecordException;
import exceptions.WrongPasswordException;
import model.Event;
import model.Notification;
import model.NotificationType;
import model.User;
import model.UserType;
import model.dao.EventDAO;
import model.dao.FavoritesDAO;
import model.dao.JoinedEventDAO;
import model.dao.NotificationDAO;
import model.dao.UserDAO;

public class LogInController {
	
	private String format = "yyyy-MM-dd HH:mm";
		
	public SessionBean logIn(UserBean userBean) throws WrongPasswordException {
		User user;
		SessionBean sessionBean = new SessionBean();
		List<Event> eventList;
		List<EventBean> eventBeanList = new ArrayList<>();
		List<User> savedUsers = null;
		
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
				savedUsers = FavoritesDAO.retrieveFavoritesByUsername(user);
			}	
			
			for(int i = 0; i < eventList.size(); i++) {
				EventBean eventBean = new EventBean();
				
				eventBean.setEventOwner(eventList.get(i).getOwner().getUsername());
				eventBean.setDateTime(sdf.format(eventList.get(i).getDateTime().getTime()));
				eventBean.setGuestsNumber(eventList.get(i).getGuestsNumber());
				eventBean.setMaxGuestsNumber(eventList.get(i).getMaxGuestsNumber());
				
				sendReminder(user, eventList.get(i).getDateTime());
				
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
				eventBean.setActualGuests(eventBean.getGuestsNumber() + "/" + eventBean.getMaxGuestsNumber());
				eventBean.setRegionString(eventList.get(i).getRegion());
				eventBean.setProvinceString(eventList.get(i).getProvince());
				eventBean.setCityString(eventList.get(i).getCity());
				eventBean.setAddressString(eventList.get(i).getAddress());
				
				eventBeanList.add(eventBean);
			}
			
			if(savedUsers != null) {	//only for guests
				for(int i = 0; i < savedUsers.size(); i++) {
					User tempUser = savedUsers.get(i);
					UserBean tempUserBean = new UserBean();
					
					tempUserBean.setUsername(tempUser.getUsername());
					tempUserBean.setName(tempUser.getName());
					tempUserBean.setSurname(tempUser.getSurname());
					tempUserBean.setBirthday(sdf.format(tempUser.getBirthday().getTime()));
					tempUserBean.setSex(tempUser.getSex());
					tempUserBean.setEmailAddr(tempUser.getEmail());
					tempUserBean.setReg(tempUser.getRegion());
					tempUserBean.setProv(tempUser.getProvince());
					tempUserBean.setCity(tempUser.getCity());
					tempUserBean.setRatings(tempUser.getRating());
					
					sessionBean.getSavedHosts().add(tempUserBean);
				}
			}
			
			userBean.setName(user.getName());
			userBean.setSurname(user.getSurname());
			userBean.setBirthday(sdf.format(user.getBirthday().getTime()));
			userBean.setSex(user.getSex());
			userBean.setEmailAddr(user.getEmail());
			userBean.setUserType(userType.toString().toUpperCase());
			userBean.setReg(user.getRegion());
			userBean.setProv(user.getProvince());
			userBean.setCity(user.getCity());
			userBean.setAddr(user.getAddress());
			userBean.setRatings(user.getRating());
			userBean.setRatingsNum(user.getRatingsNum());
			
			sessionBean.setUserBean(userBean);
			sessionBean.setEventBeanList(eventBeanList);
		} catch (Exception e) {
			e.printStackTrace();
			sessionBean = null;
		}
		
		return sessionBean;
	}
	
	private int sendReminder(User user, GregorianCalendar date) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		GregorianCalendar nowCalendar = new GregorianCalendar();
		
		long day = 86400000;	//in millis
		long dateInMillis = date.getTimeInMillis();
		long nowInMillis = nowCalendar.getTimeInMillis();
		
		if((dateInMillis - nowInMillis < day) && (dateInMillis - nowInMillis > 0)) {
			String text = "Don''t forget the event on " + sdf.format(date.getTime()) + ", less than one day left!";
			Notification notification = new Notification(user, text, NotificationType.REMINDER);
			
			try {
				NotificationDAO.saveNotification(user, notification);
			} catch(DuplicateRecordException dre) {
				return 1;
			}
		}
		else if(dateInMillis - nowInMillis < 0) {
			String text = "Don''t forget to leave a review for the event on " + sdf.format(date.getTime());
			Notification notification = new Notification(user, text, NotificationType.RATING);
			
			try {
				NotificationDAO.saveNotification(user, notification);
			} catch(DuplicateRecordException dre) {
				return 1;
			}
		}
		
		return 0;
	}
}
