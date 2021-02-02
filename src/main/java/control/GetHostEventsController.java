package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import bean.EventBean;
import bean.SessionBean;
import bean.UserBean;
import model.Event;
import model.User;
import model.dao.EventDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class GetHostEventsController {

	public SessionBean getHostEvents(UserBean hostBean) throws ClassNotFoundException, SQLException, IOException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		
		User host = UserDAO.retrieveUserByUsername(hostBean.getUsername());
		
		List<Event> eventList = EventDAO.retrieveEventsByUsername(host);
		
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
