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
	
	public static List<Event> retrieveJoinedEventsByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventsByUsername(stm, user.getUsername());
		
//		if(!rs.first()) {
//			throw new NoRecordFoundException(norecord);
//		}
//		else {
		if(rs.first()) {
			rs.first();
			do {
				Event newEvent;
				String owner = rs.getString("event_owner");
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(rs.getTimestamp("event_date"));
								
				String guestStatus = rs.getString("guest_status");
				String paymentStatus = rs.getString("payment_status");
				
				User ownerUser = UserDAO.retrieveUserByUsername(owner);
				
//				Statement tempStatement = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//						ResultSet.CONCUR_READ_ONLY);
//				
//				ResultSet tempResultSet = SimpleQueries.selectEventByUsernameDateTime(tempStatement, owner, sdf.format(date.getTime()));
//				
//				if(tempResultSet.first()) {
//					tempResultSet.first();
//					newEvent = new Event(ownerUser, date, tempResultSet.getInt("max_num_guests"), tempResultSet.getDouble("payment_bill"));
//				}
//				else {
//					newEvent = new Event(ownerUser, date, 0, 0);
//				}
				
				newEvent = EventDAO.retrieveEventByUsernameDateTime(ownerUser, date);
				
				newEvent.setGuestStatus(GuestStatus.valueOf(guestStatus.toUpperCase()));
				newEvent.setPayStatus(PaymentStatus.valueOf(paymentStatus.toUpperCase()));
				newEvent.setRegion(ownerUser.getRegion());
				newEvent.setProvince(ownerUser.getProvince());
				newEvent.setCity(ownerUser.getCity());
				newEvent.setAddress(ownerUser.getAddress());
				
				eventList.add(newEvent);
				
//				tempStatement.close();
//				tempResultSet.close();
			}
			while(rs.next());
		}
		
		rs.close();
		stm.close();
		
		return eventList;
	}
	
	public static void saveJoinedEvent(User user, Event event) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventByDateTime(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()));
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertJoinedEvent(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()), event.getGuestStatus().toString(), event.getPayStatus().toString());
		}
		
		stm.close();
	}
	
	public static void removeJoinedGuest(User user, Event event) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventByDateTime(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()));
				
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteJoinedGuest(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()));
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateJoinedEventPaymentStatus(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventByDateTime(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()));
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updatePaymentStatus(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()), event.getPayStatus().toString().toUpperCase());
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static List<User> retrieveJoinedGuests(User user, Event event) throws SQLException, ClassNotFoundException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		List<User> guestsList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedGuestsByDateTime(stm, user.getUsername(), sdf.format(event.getDateTime().getTime()));
		
		if(rs.first()) {
			do {
				String username = rs.getString("guest");
				
				User guest = UserDAO.retrieveUserByUsername(username);
				guest.setGuestStatus(GuestStatus.valueOf(rs.getString("guest_status").toUpperCase()));
				guest.setPayStatus(PaymentStatus.valueOf(rs.getString("payment_status").toUpperCase()));
				
				guestsList.add(guest);
			}
			while(rs.next());
		}
		
		stm.close();
		rs.close();
	
		return guestsList;
	}
	
}
