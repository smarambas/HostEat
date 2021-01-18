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
import model.GuestStatus;
import model.PaymentStatus;
import model.User;
import model.dao.queries.CRUDQueries;
import model.dao.queries.SimpleQueries;

public class JoinedEventDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	private static String format = "yyyy-MM-dd HH:mm";
	
	private JoinedEventDAO() {}
	
	public static List<Event> retrieveJoinedEventsByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventsByUsername(stm, user.getUsername());
		
		if(!rs.first()) {
			throw new NoRecordFoundException(norecord);
		}
		else {
			rs.first();
			do {
				String owner = rs.getString("event_owner");
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(rs.getTimestamp("event_date"));
				String guestStatus = rs.getString("guest_status");
				String paymentStatus = rs.getString("payment_status");
				
				User ownerUser = UserDAO.retrieveUserByUsername(owner);
				
				Statement tempStatement = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				
				ResultSet tempResultSet = SimpleQueries.selectEventByUsernameDateTime(tempStatement, owner, sdf.format(date));
				
				Event newEvent = new Event(ownerUser, date, tempResultSet.getInt("max_num_guests"), tempResultSet.getDouble("payment_bill"));
				newEvent.setGuestStatus(GuestStatus.valueOf(guestStatus.toUpperCase()));
				newEvent.setPayStatus(PaymentStatus.valueOf(paymentStatus.toUpperCase()));
				
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
	
	public static void saveJoinedEvent(User user, Event event) throws SQLException, ClassNotFoundException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventsByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertJoinedEvent(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime()), event.getGuestStatus().toString(), event.getPayStatus().toString());
		}
		
		stm.close();
	}
	
	public static void removeJoinedEvent(User user, Event event) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventByDateTime(stm, user.getUsername(), sdf.format(event.getDateTime()));
				
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteJoinedEvent(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime()));
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
}
