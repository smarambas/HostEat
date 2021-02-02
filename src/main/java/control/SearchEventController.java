package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import bean.EventBean;
import bean.SessionBean;
import exceptions.NoRecordFoundException;
import exceptions.WrongDateException;
import model.Event;
import model.dao.EventDAO;
import standalone_view.GUIController;

public class SearchEventController {

	public SessionBean searchEvent(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException, WrongDateException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		GregorianCalendar now = new GregorianCalendar();
		
		if(dateTime.getTime().compareTo(now.getTime()) < 0) {
			throw new WrongDateException("Wrong date selected");
		}
		
		List<Event> eventList = EventDAO.retrieveEventsBySearch(eventBean.getRegionString(), 
																eventBean.getProvinceString(), 
																eventBean.getCityString(), 
																dateTime);
		
		for(Event e : eventList) {
			EventBean event = new EventBean();
			event.setEventOwner(e.getOwner().getUsername());
			event.setDateTime(sdf.format(e.getDateTime().getTime()));
			event.setGuestsNumber(e.getGuestsNumber());
			event.setMaxGuestsNumber(e.getMaxGuestsNumber());
			event.setActualGuests(e.getGuestsNumber() + "/" + e.getMaxGuestsNumber());
			event.setRegionString(e.getRegion());
			event.setProvinceString(e.getProvince());
			event.setCityString(e.getCity());
			event.setBill(e.getBill());
			
			sessionBean.getSearchedList().add(event);
		}
		
		return sessionBean;
	}
	
}
