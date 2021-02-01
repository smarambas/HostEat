package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import bean.EventBean;
import bean.UserBean;
import exceptions.AlreadyRatedException;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class RateUserController {

	public void rateUser(UserBean userBean, EventBean eventBean, int rating) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException, AlreadyRatedException {
		boolean isRated = false;
		
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		User userToRate = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			// userToRate is a guest
			User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
			
			GregorianCalendar date = new GregorianCalendar();
			date.setTime(sdf.parse(eventBean.getDateTime()));
			
			Event event = EventDAO.retrieveEventByUsernameDateTime(host, date);
			
			event = JoinedEventDAO.retrieveJoinedEvent(userToRate, event);
			
			if(event.getHostRated() != 1) {
				event.setHostRated(1);
				JoinedEventDAO.updateJoinedEventHostRated(userToRate, event);
			}
			else {
				isRated = true;
			}
		}
		else {
			//userToRate is a host
			User guest = UserDAO.retrieveUserByUsername(GUIController.getSessionBean().getUserBean().getUsername());
			
			GregorianCalendar date = new GregorianCalendar();
			date.setTime(sdf.parse(eventBean.getDateTime()));
			
			Event event = EventDAO.retrieveEventByUsernameDateTime(userToRate, date);
			
			event = JoinedEventDAO.retrieveJoinedEvent(guest, event);
			
			if(event.getGuestRated() != 1) {
				event.setGuestRated(1);
				JoinedEventDAO.updateJoinedEventGuestRated(guest, event);
			}
			else {
				isRated = true;
			}
		}
		
		if(!isRated) {
			UserDAO.updateUserRatings(userToRate, userToRate.getRating() + rating, userToRate.getRatingsNum() + 1);
		}
		else {
			throw new AlreadyRatedException("Error: you already rated that user");
		}
	}
}
