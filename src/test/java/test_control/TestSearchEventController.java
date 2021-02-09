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
import control.SearchEventController;
import exceptions.NoRecordFoundException;
import exceptions.WrongDateException;

public class TestSearchEventController {

	@Test
	public void testSearchEvent() throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException, WrongDateException {
		/*
		 * assuming that there are events (in the future) in the database, 
		 * check if the events returned are coherent with the search date
		 */

		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = new SessionBean();
		
		EventBean eventBean = new EventBean();
		
		GregorianCalendar now = new GregorianCalendar();
		now.add(GregorianCalendar.HOUR, 24);
		
		eventBean.setDateTime(sdf.format(now.getTime()));
		eventBean.setRegionString("");
		eventBean.setProvinceString("");
		eventBean.setCityString("");
		
		SearchEventController searchEventController = new SearchEventController();
		
		sessionBean = searchEventController.searchEvent(sessionBean, eventBean);
		
		long eventDateMillis = now.getTimeInMillis();
		
		boolean check = true;
		
		if(!(sessionBean.getSearchedList().isEmpty())) {
			for(EventBean eb : sessionBean.getSearchedList()) {
				
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(sdf.parse(eb.getDateTime()));
				long dateInMillis = date.getTimeInMillis();
				
				if(dateInMillis < eventDateMillis) {
					check = false;
					break;
				}
			}
		}
		else {
			check = false;	
		}
		
		assertTrue(check);
	}
}
