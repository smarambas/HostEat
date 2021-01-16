package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.queries.CRUDQueries;
import model.queries.NestedQueries;
import model.queries.SimpleQueries;

public class EventDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	private static String format = "yyyy-MM-dd HH:mm";
	
	private EventDAO() {}
	
	public static List<Event> retrieveEventsByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventsByUsername(stm, user.getUsername());
		
		if(!rs.first()) {
			throw new NoRecordFoundException(norecord);
		}
		else {
			rs.first();
			do {
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(rs.getTimestamp("date"));
				int maxGuestsNum = rs.getInt("max_num_guests");
				int bill = rs.getInt("payment_bill");
				
				Event newEvent = new Event(user, date, maxGuestsNum, bill);
				
				Statement tempStatement = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				
				ResultSet tempResultSet = SimpleQueries.countGuestsNumberForEvent(tempStatement, user.getUsername(), sdf.format(date));
				
				int guestsNum = tempResultSet.getInt(1);
				
				newEvent.setGuestsNumber(guestsNum);
				
				eventList.add(newEvent);
				
				tempStatement.close();
				tempResultSet.close();
			}
			while(rs.next());
		}
		
		rs.close();
		stm.close();
		
		return eventList;
	}
	
	public static void saveEvent(User user, Event event) throws SQLException, ClassNotFoundException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventByUsernameDateTime(stm, user.getUsername(), sdf.format(event.getDateTime()));
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertEvent(stm, user, sdf.format(event.getDateTime()), event.getMaxGuestsNumber(), event.getBill());
		}
		
		stm.close();
	}
	
	public static void removeEvent(User user, Event event) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventByUsernameDateTime(stm, user.getUsername(), sdf.format(event.getDateTime()));
				
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteEvent(stm, user, sdf.format(event.getDateTime()));
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static List<Event> retrieveEventsBySearch(String region, String province, String city, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		boolean field1 = false; 
		boolean field2 = false;
		boolean field3 = false; 
		ResultSet rs;
		
		if(region.length() > 0) {
			field1 = true;
		}
		if(province.length() > 0) {
			field2 = true;
		}
		if(city.length() > 0) {
			field3 = true;
		}
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		if(field1) {
			if(field2) {
				if(field3) {	//111
					rs = NestedQueries.selectEventsByRegionProvinceCity(stm, region, province, city, sdf.format(dateTime));
				}
				else {	//110
					rs = NestedQueries.selectEventsByRegionProvince(stm, region, province, sdf.format(dateTime));
				}
			}
			else {
				if(field3) {	//101
					rs = NestedQueries.selectEventsByRegionCity(stm, region, city, sdf.format(dateTime));
				}
				else {	//100
					rs = NestedQueries.selectEventsByRegion(stm, region, sdf.format(dateTime));
				}
			}
		}
		else {
			if(field2) {
				if(field3) {	//011
					rs = NestedQueries.selectEventsByProvinceCity(stm, province, city, sdf.format(dateTime));
				}
				else {	//010
					rs = NestedQueries.selectEventsByProvince(stm, province, sdf.format(dateTime));
				}
			}
			else {
				if(field3) {	//001
					rs = NestedQueries.selectEventsByCity(stm, city, sdf.format(dateTime));
				}
				else {	//000
					rs = NestedQueries.selectEventsByDate(stm, sdf.format(dateTime));
				}
			}
		}
				
		if(!rs.first()) {
			throw new NoRecordFoundException(norecord);
		}
		else {
			rs.first();
			do {
				String owner = rs.getString("owner");
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(rs.getTimestamp("date"));
				int maxGuestsNum = rs.getInt("max_num_guests");
				int bill = rs.getInt("payment_bill");
				
				User user = UserDAO.retrieveUserByUsername(owner);
				
				Event newEvent = new Event(user, date, maxGuestsNum, bill);
				
				eventList.add(newEvent);
			}
			while(rs.next());
		}
		
		rs.close();
		stm.close();
		
		return eventList;
	}
	
}
