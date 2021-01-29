package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.Event;
import model.GuestStatus;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;

public class AcceptGuestController {

	public void acceptGuest(UserBean guestBean, EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
				
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
		User guest = UserDAO.retrieveUserByUsername(guestBean.getUsername());
		
		Event event = EventDAO.retrieveEventByUsernameDateTime(host, dateTime);
		
		event.setGuestStatus(GuestStatus.ACCEPTED);
		
		JoinedEventDAO.updateJoinedEventGuestStatus(guest, event);		
	}
	
}
