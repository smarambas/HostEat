package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.SessionBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class DenyGuestController {

	public SessionBean denyGuest(UserBean userBean, EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
		User guest = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		Event event = EventDAO.retrieveEventByUsernameDateTime(host, dateTime);
		
		JoinedEventDAO.removeJoinedGuest(guest, event);
		
		for(int i = 0; i < sessionBean.getEventBeanList().size(); i++) {
			if(sessionBean.getEventBeanList().get(i).getDateTime().equals(eventBean.getDateTime())) {
				int n = sessionBean.getEventBeanList().get(i).getGuestsNumber();
				int max = sessionBean.getEventBeanList().get(i).getMaxGuestsNumber();
				
				n = n - 1;
				
				sessionBean.getEventBeanList().get(i).setGuestsNumber(n);
				sessionBean.getEventBeanList().get(i).setActualGuests(n + "/" + max);
				
				break;
			}
		}
		
		return sessionBean;
	}
	
}
