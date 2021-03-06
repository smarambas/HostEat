package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.SessionBean;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;

public class DeleteJoinedEventController {

	public SessionBean deleteEvent(SessionBean session, EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = session;
		String username = sessionBean.getUserBean().getUsername();
		User user = UserDAO.retrieveUserByUsername(username);
				
		User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		Event event = EventDAO.retrieveEventByUsernameDateTime(host, dateTime);
		
		JoinedEventDAO.removeJoinedGuest(user, event);
		
		for(EventBean eb : sessionBean.getEventBeanList()) {
			if(eb.getDateTime().equals(eventBean.getDateTime())) {
				sessionBean.getEventBeanList().remove(eb);
				break;
			}
		}
		
		return sessionBean;
	}
	
}
