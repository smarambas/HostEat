package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.queries.SimpleQueries;

public class EventDAO {

	private static ConnectionSingleton cs;
	
	private EventDAO() {}
	
	public static List<Event> retrieveEventListByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectEventsByUsername(stm, user.getUsername());
		
		if(!rs.first()) {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		else {
			rs.first();
			do {
				String date = rs.getString("date");
				String time = rs.getString("time");
				int guestsNum = rs.getInt("num_guests");
				
				Event newEvent = new Event(date, time, guestsNum);
				
				eventList.add(newEvent);
			}
			while(rs.next());
		}
		
		rs.close();
		stm.close();
		
		return eventList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
