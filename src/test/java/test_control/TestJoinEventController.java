package test_control;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.junit.Test;

import bean.EventBean;
import bean.SessionBean;
import bean.UserBean;
import control.CreateEventController;
import control.DeleteAllNotificationsController;
import control.DeleteEventController;
import control.JoinEventController;
import control.LogInController;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import exceptions.WrongDateException;
import exceptions.WrongPasswordException;

public class TestJoinEventController {

	@Test
	public void testJoinEvent() throws WrongPasswordException, ClassNotFoundException, SQLException, IOException, ParseException, DuplicateRecordException, WrongDateException, NoRecordFoundException {
		/*
		 * Assuming that the two users exists in the DB,
		 * check if the guest can successfully join the event
		 * created by the host
		 */
		
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		UserBean hostBean = new UserBean();
		hostBean.setUsername("testHost");
		hostBean.setPassw("test");
		
		UserBean guestBean = new UserBean();
		guestBean.setUsername("testGuest");
		guestBean.setPassw("test");
		
		LogInController logInController = new LogInController();
		
		SessionBean hostSessionBean = logInController.logIn(hostBean);
		SessionBean guestSessionBean = logInController.logIn(guestBean);
		
		EventBean eventBean = new EventBean();
		
		GregorianCalendar date = new GregorianCalendar();
		date.add(GregorianCalendar.MONTH, 1);
		
		eventBean.setEventOwner(hostBean.getUsername());
		eventBean.setDateTime(sdf.format(date.getTime()));
		eventBean.setMaxGuestsNumber(1);
		eventBean.setBill(0);
		
		CreateEventController createEventController = new CreateEventController();
		hostSessionBean = createEventController.createEvent(hostSessionBean, eventBean);
		
		JoinEventController joinEventController = new JoinEventController();
		guestSessionBean = joinEventController.joinEvent(guestSessionBean, eventBean);
		
		boolean check = false;
		
		if(!(guestSessionBean.getEventBeanList().isEmpty())) {
			for(EventBean eb : guestSessionBean.getEventBeanList()) {
				if(eb.getEventOwner().equals(eventBean.getEventOwner()) &&
				   eb.getDateTime().equals(eventBean.getDateTime()) &&
				   eb.getGuestStatus().equalsIgnoreCase("JOINED")) { 
					
					check = true;
					
					//clear DB
					DeleteEventController deleteEventController = new DeleteEventController();
					deleteEventController.deleteEvent(hostSessionBean, eventBean);
					
					DeleteAllNotificationsController deleteAllNotificationsController = new DeleteAllNotificationsController();
					deleteAllNotificationsController.deleteAllNotifications(hostBean);
					
					break;
				}
			}
		}
		
		assertTrue(check);
	}
}
