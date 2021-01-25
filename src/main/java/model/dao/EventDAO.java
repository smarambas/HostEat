package model.dao;

import java.io.IOException;
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
import model.dao.queries.CRUDQueries;
import model.dao.queries.NestedQueries;
import model.dao.queries.SimpleQueries;

public class EventDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	private static String format = "yyyy-MM-dd HH:mm";
	
	private EventDAO() {}
	
	public static List<Event> retrieveEventsByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventsByUsername(stm, user.getUsername());
//		
//		if(!rs.first()) {
//			throw new NoRecordFoundException(norecord);
//		}
//		else {
		if(rs.first()) {
			rs.first();
			do {
				int guestsNum = 0;
				GregorianCalendar date = new GregorianCalendar();	
				date.setTime(rs.getTimestamp("date"));
								
				int maxGuestsNum = rs.getInt("max_num_guests");
				int bill = rs.getInt("payment_bill");
				
				Event newEvent = new Event(user, date, maxGuestsNum, bill);
				
				Statement tempStatement = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
								
				ResultSet tempResultSet = SimpleQueries.countGuestsNumberForEvent(tempStatement, user.getUsername(), sdf.format(date.getTime()));
								
				if(tempResultSet.first()) {
					tempResultSet.first();
					guestsNum = tempResultSet.getInt(1);
				}
				else {
					guestsNum = 0;
				}
				
				newEvent.setGuestsNumber(guestsNum);
				newEvent.setRegion(user.getRegion());
				newEvent.setProvince(user.getProvince());
				newEvent.setCity(user.getCity());
				newEvent.setAddress(user.getAddress());
				
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
	
	public static Event retrieveEventByUsernameDateTime(User user, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		Event event = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventByUsernameDateTime(stm, user.getUsername(), sdf.format(dateTime.getTime()));
		
		if(rs.first()) {
			rs.first();
			
			int guestsNum = 0;
			GregorianCalendar date = new GregorianCalendar();	
			date.setTime(rs.getTimestamp("date"));
			
			int maxGuestsNum = rs.getInt("max_num_guests");
			int bill = rs.getInt("payment_bill");
			
			event = new Event(user, date, maxGuestsNum, bill);
			
			Statement tempStatement = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
							
			ResultSet tempResultSet = SimpleQueries.countGuestsNumberForEvent(tempStatement, user.getUsername(), sdf.format(date.getTime()));
							
			if(tempResultSet.first()) {
				tempResultSet.first();
				guestsNum = tempResultSet.getInt(1);
			}
			else {
				guestsNum = 0;
			}
			
			event.setGuestsNumber(guestsNum);
			event.setRegion(user.getRegion());
			event.setProvince(user.getProvince());
			event.setCity(user.getCity());
			event.setAddress(user.getAddress());
			
			tempStatement.close();
			tempResultSet.close();
		}
		
		rs.close();
		stm.close();
		
		return event;
	}
	
	public static void saveEvent(User user, Event event) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventByUsernameDateTime(stm, user.getUsername(), sdf.format(event.getDateTime().getTime()));
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertEvent(stm, user.getUsername(), sdf.format(event.getDateTime().getTime()), event.getMaxGuestsNumber(), event.getBill());
		}
		
		stm.close();
	}
	
	public static void removeEvent(User user, Event event) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventByUsernameDateTime(stm, user.getUsername(), sdf.format(event.getDateTime().getTime()));
				
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteEvent(stm, user.getUsername(), sdf.format(event.getDateTime().getTime()));
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static List<Event> retrieveEventsBySearch(String region, String province, String city, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		ResultSet rs;
		boolean[] fields = new boolean[3];
		int searchType;
		
		setFields(region, province, city, fields);
		searchType = calcFields(fields);
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		switch (searchType) {
		case 0: 
			rs = NestedQueries.selectEventsByDate(stm, sdf.format(dateTime.getTime()));
			break;
		case 1:
			rs = NestedQueries.selectEventsByCity(stm, city, sdf.format(dateTime.getTime()));
			break;
		case 10:
			rs = NestedQueries.selectEventsByProvince(stm, province, sdf.format(dateTime.getTime()));
			break;
		case 11:
			rs = NestedQueries.selectEventsByProvinceCity(stm, province, city, sdf.format(dateTime.getTime()));
			break;
		case 100:
			rs = NestedQueries.selectEventsByRegion(stm, region, sdf.format(dateTime.getTime()));
			break;
		case 101:
			rs = NestedQueries.selectEventsByRegionCity(stm, region, city, sdf.format(dateTime.getTime()));
			break;
		case 110:
			rs = NestedQueries.selectEventsByRegionProvince(stm, region, province, sdf.format(dateTime.getTime()));
			break;
		case 111:
			rs = NestedQueries.selectEventsByRegionProvinceCity(stm, region, province, city, sdf.format(dateTime.getTime()));
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + searchType);
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
	
	private static void setFields(String r, String p, String c, boolean[] f) {
		/*
		 * Check input strings to select the right search method
		 */
		
		if(r.length() > 0) {
			f[0] = true;
		}
		else {
			f[0] = false;
		}
		
		if(p.length() > 0) {
			f[1] = true;
		}
		else {
			f[1] = false;
		}
		
		if(c.length() > 0) {
			f[2] = true;
		}
		else {
			f[2] = false;
		}
	}
	
	private static int calcFields(boolean[] f) {
		int result = 0;
		
		if(f[2]) {
			result += 1;
		}
		if(f[1]) {
			result += 10;
		}
		if(f[0]) {
			result += 100;
		}
		
		return result;
	}
	
}
