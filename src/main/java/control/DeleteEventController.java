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
import model.dao.UserDAO;
import standalone_view.GUIController;

public class DeleteEventController {

	public SessionBean deleteEvent(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		String username = sessionBean.getUserBean().getUsername();
		User user = UserDAO.retrieveUserByUsername(username);
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		Event event = EventDAO.retrieveEventByUsernameDateTime(user, dateTime);
		
		EventDAO.removeEvent(user, event);
		
		for(EventBean eb : sessionBean.getEventBeanList()) {
			if(eb.getDateTime().equals(eventBean.getDateTime())) {
				sessionBean.getEventBeanList().remove(eb);
				break;
			}
		}
		
		return sessionBean;
	}
	
}
