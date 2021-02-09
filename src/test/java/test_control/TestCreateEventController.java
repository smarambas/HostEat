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
import control.DeleteEventController;
import control.LogInController;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import exceptions.WrongDateException;
import exceptions.WrongPasswordException;

public class TestCreateEventController {

	@Test
	public void testCreateEvent() throws WrongPasswordException, ClassNotFoundException, SQLException, IOException, ParseException, DuplicateRecordException, WrongDateException, NoRecordFoundException {
		/*
		 * Assuming that the host exists in the DB,
		 * check that the event is correctly created
		 */
		
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		UserBean hostBean = new UserBean();
		hostBean.setUsername("testHost");
		hostBean.setPassw("test");
		
		LogInController logInController = new LogInController();
		SessionBean sessionBean = logInController.logIn(hostBean);
		
		EventBean eventBean = new EventBean();
		
		GregorianCalendar date = new GregorianCalendar();
		date.add(GregorianCalendar.MONTH, 1);
		
		eventBean.setDateTime(sdf.format(date.getTime()));
		eventBean.setMaxGuestsNumber(0);
		eventBean.setBill(0);
		
		CreateEventController createEventController = new CreateEventController();
		sessionBean = createEventController.createEvent(sessionBean, eventBean);
		
		boolean check = false;
		
		if(!(sessionBean.getEventBeanList().isEmpty())) {
			for(EventBean eb : sessionBean.getEventBeanList()) {
				
				if(eb.getDateTime().equals(eventBean.getDateTime())) {
					check = true;
					
					//clear DB
					DeleteEventController deleteEventController = new DeleteEventController();
					deleteEventController.deleteEvent(sessionBean, eventBean);
					
					break;
				}
			}
		}
		
		assertTrue(check);
	}
}
