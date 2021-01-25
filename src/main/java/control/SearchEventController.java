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
import model.Event;
import model.dao.EventDAO;
import standalone_view.GUIController;

public class SearchEventController {

	public SessionBean searchEvent(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		List<Event> eventList = EventDAO.retrieveEventsBySearch(eventBean.getRegionString(), 
																eventBean.getProvinceString(), 
																eventBean.getCityString(), 
																dateTime);
		
		for(Event e : eventList) {
			EventBean eBean = new EventBean();
			eBean.setEventOwner(e.getOwner().getUsername());
			eBean.setDateTime(sdf.format(e.getDateTime().getTime()));
			eBean.setGuestsNumber(e.getGuestsNumber());
			eBean.setMaxGuestsNumber(e.getMaxGuestsNumber());
			eBean.setActualGuests(e.getGuestsNumber() + "/" + e.getMaxGuestsNumber());
			eBean.setRegionString(e.getRegion());
			eBean.setProvinceString(e.getProvince());
			eBean.setCityString(e.getCity());
			eBean.setBill(e.getBill());
			
			sessionBean.getSearchedList().add(eBean);
		}
		
		return sessionBean;
	}
	
}
