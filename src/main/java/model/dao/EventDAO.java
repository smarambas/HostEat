package model.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;

public class EventDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	private static String format = "yyyy-MM-dd HH:mm";
	private static String maxGuestsString = "max_num_guests";
	private static String billString = "payment_bill";
	private static String selectString = "SELECT * FROM event WHERE owner = ? AND date = ?;";
	private static String countString = "SELECT COUNT(*) FROM joined_event WHERE event_owner = ? AND event_date = ?;";
	
	private EventDAO() {}
	
	public static List<Event> retrieveEventsByUsername(User user) throws SQLException, ClassNotFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM event WHERE owner = ?;";
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				do {
					int guestsNum = 0;
					GregorianCalendar date = new GregorianCalendar();	
					date.setTime(rs.getTimestamp("date"));
									
					int maxGuestsNum = rs.getInt(maxGuestsString);
					int bill = rs.getInt(billString);
					
					Event newEvent = new Event(user, date, maxGuestsNum, bill);
					
					query = countString;

					try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
						ps.setString(1, user.getUsername());
						ps.setString(2, sdf.format(date.getTime()));
						ResultSet tempResultSet = ps.executeQuery();
						
						if(tempResultSet.next()) {
							guestsNum = tempResultSet.getInt(1);
						}
						else {
							guestsNum = 0;
						}
					}
					
					newEvent.setGuestsNumber(guestsNum);
					newEvent.setRegion(user.getUserRegion());
					newEvent.setProvince(user.getUserProvince());
					newEvent.setCity(user.getUserCity());
					newEvent.setAddress(user.getAddress());
					
					eventList.add(newEvent);
				}
				while(rs.next());
			}
		}
		
		return eventList;
	}
	
	public static Event retrieveEventByUsernameDateTime(User user, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		Event event = null;
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, sdf.format(dateTime.getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				int guestsNum = 0;
				GregorianCalendar date = new GregorianCalendar();	
				date.setTime(rs.getTimestamp("date"));
				
				int maxGuestsNum = rs.getInt(maxGuestsString);
				int bill = rs.getInt(billString);
				
				event = new Event(user, date, maxGuestsNum, bill);
				
				query = countString;

				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, sdf.format(dateTime.getTime()));
					ResultSet tempResultSet = ps.executeQuery();
					
					if(tempResultSet.next()) {
						guestsNum = tempResultSet.getInt(1);
					}
					else {
						guestsNum = 0;
					}
				}
				
				event.setGuestsNumber(guestsNum);
				event.setRegion(user.getUserRegion());
				event.setProvince(user.getUserProvince());
				event.setCity(user.getUserCity());
				event.setAddress(user.getAddress());
			}
		}
		
		return event;
	}
	
	public static void saveEvent(User user, Event event) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			if(!rs.next()) {
				query = "INSERT INTO event (owner, date, max_num_guests, payment_bill) VALUES (?, ?, ?, ?);";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, sdf.format(event.getDateTime().getTime()));
					ps.setInt(3, event.getMaxGuestsNumber());
					ps.setDouble(4, event.getBill());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new DuplicateRecordException("ERROR: the record already exists");
			}
		}
	}
	
	public static void removeEvent(User user, Event event) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "DELETE FROM event WHERE owner = ? AND date = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, sdf.format(event.getDateTime().getTime()));
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static List<Event> retrieveEventsBySearch(String region, String province, String city, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		List<Event> eventList = new ArrayList<>();

		boolean[] fields = new boolean[3];
		int searchType;
		
		setFields(region, province, city, fields);
		searchType = calcFields(fields);
		
		cs = ConnectionSingleton.createConnection();
		
		ResultSet rs;
		String query;
				
		switch (searchType) {
		case 0: 
			query = "SELECT * FROM event WHERE date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}
			
			break;
		case 1:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE city = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, city);
				ps.setString(2, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}
			
			break;
		case 10:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE province = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, province);
				ps.setString(2, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}

			break;
		case 11:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE province = ? AND city = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, province);
				ps.setString(2, city);
				ps.setString(3, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}

			break;
		case 100:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE region = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, region);
				ps.setString(2, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}

			break;
		case 101:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE region = ? AND city = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, region);
				ps.setString(2, city);
				ps.setString(3, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}

			break;
		case 110:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE region = ? AND province = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, region);
				ps.setString(2, province);
				ps.setString(3, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}

			break;
		case 111:
			query = "SELECT * FROM event WHERE owner = any (SELECT username FROM user WHERE region = ? AND province = ? AND city = ?) AND date > ?;";
			
			try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
				ps.setString(1, region);
				ps.setString(2, province);
				ps.setString(3, city);
				ps.setString(4, sdf.format(dateTime.getTime()));
				
				rs = ps.executeQuery();
				
				workOnRS(rs, eventList);
			}

			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + searchType);
		}
		
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
	
	private static void workOnRS(ResultSet rs, List<Event> events) throws NoRecordFoundException, ClassNotFoundException, SQLException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		if(rs.next()) {
			do {
				String owner = rs.getString("owner");
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(rs.getTimestamp("date"));
				int maxGuestsNum = rs.getInt(maxGuestsString);
				int bill = rs.getInt(billString);
				
				User user = UserDAO.retrieveUserByUsername(owner);
				
				int guestsNum;
				
				String query = countString;

				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, sdf.format(date.getTime()));
					ResultSet tempResultSet = ps.executeQuery();
					
					if(tempResultSet.next()) {
						guestsNum = tempResultSet.getInt(1);
					}
					else {
						guestsNum = 0;
					}
				}
						
				Event newEvent = new Event(user, date, maxGuestsNum, bill);
				newEvent.setRegion(user.getUserRegion());
				newEvent.setProvince(user.getUserProvince());
				newEvent.setCity(user.getUserCity());
				newEvent.setGuestsNumber(guestsNum);
				
				events.add(newEvent);
			}
			while(rs.next());
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
	}
	
}
