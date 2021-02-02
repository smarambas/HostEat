package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.SessionBean;
import exceptions.DuplicateRecordException;
import model.Event;
import model.GuestStatus;
import model.Notification;
import model.NotificationType;
import model.PaymentStatus;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.NotificationDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class JoinEventController {

	public SessionBean joinEvent(EventBean eventBean) throws ClassNotFoundException, SQLException, IOException, ParseException, DuplicateRecordException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		String username = sessionBean.getUserBean().getUsername();
		User guest = UserDAO.retrieveUserByUsername(username);
		
		User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
				
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		Event event = EventDAO.retrieveEventByUsernameDateTime(host, dateTime);
		
		event.setGuestStatus(GuestStatus.JOINED);
		if(event.getBill() > 0) {
			eventBean.setPayStatus("UNPAID");
			event.setPayStatus(PaymentStatus.UNPAID);
		}
		else {
			eventBean.setPayStatus("NOSET");
			event.setPayStatus(PaymentStatus.NOSET);
		}
		
		event.setHostRated(0);
		event.setGuestRated(0);
		
		JoinedEventDAO.saveJoinedEvent(guest, event);
		
		eventBean.setEventOwner(host.getUsername());
		eventBean.setGuestsNumber(event.getGuestsNumber() + 1);
		eventBean.setActualGuests(eventBean.getGuestsNumber() + "/" + event.getMaxGuestsNumber());
		eventBean.setRegionString(host.getUserRegion());
		eventBean.setProvinceString(host.getUserProvince());
		eventBean.setCityString(host.getUserCity());
		eventBean.setBill(event.getBill());
		eventBean.setGuestStatus("JOINED");
		eventBean.setHostRated(0);
		eventBean.setGuestRated(0);
		
		sessionBean.getEventBeanList().add(eventBean);		
		
		String text = "Guest " + guest.getUsername() + " joined your event on " + eventBean.getDateTime();
		Notification notification = new Notification(host, text, NotificationType.JOIN);
		
		NotificationDAO.saveNotification(host, notification);
		
		return sessionBean;
	}
	
}
