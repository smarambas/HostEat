package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.SessionBean;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.dao.EventDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class CreateEventController {

	public SessionBean createEvent(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException, DuplicateRecordException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		String username = sessionBean.getUserBean().getUsername();
		User user = UserDAO.retrieveUserByUsername(username);
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
				
		Event newEvent = new Event(user, dateTime, eventBean.getMaxGuestsNumber(), eventBean.getBill());
		
		EventDAO.saveEvent(user, newEvent);
		
		eventBean.setEventOwner(username);
		eventBean.setGuestsNumber(0);
		eventBean.setActualGuests("0/" + eventBean.getMaxGuestsNumber());
		eventBean.setRegionString(user.getRegion());
		eventBean.setProvinceString(user.getProvince());
		eventBean.setCityString(user.getCity());
		eventBean.setAddressString(user.getAddress());
		
		sessionBean.getEventBeanList().add(eventBean);		
		
		return sessionBean;
	}
}