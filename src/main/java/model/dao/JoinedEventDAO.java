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
	private static String guestStatusString = "guest_status";
	private static String paymentStatusString = "payment_status";
	
	private JoinedEventDAO() {}
	
	public static List<Event> retrieveJoinedEventsByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventsByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.first();
			do {
				Event newEvent;
				String owner = rs.getString("event_owner");
				GregorianCalendar date = new GregorianCalendar();
				date.setTime(rs.getTimestamp("event_date"));
								
				String guestStatus = rs.getString(guestStatusString);
				String paymentStatus = rs.getString(paymentStatusString);
				
				int hrated = rs.getInt("host_rated");
				int grated = rs.getInt("guest_rated");
				
				User ownerUser = UserDAO.retrieveUserByUsername(owner);
				
				newEvent = EventDAO.retrieveEventByUsernameDateTime(ownerUser, date);
				
				newEvent.setGuestStatus(GuestStatus.valueOf(guestStatus.toUpperCase()));
				newEvent.setPayStatus(PaymentStatus.valueOf(paymentStatus.toUpperCase()));
				newEvent.setRegion(ownerUser.getUserRegion());
				newEvent.setProvince(ownerUser.getUserProvince());
				newEvent.setCity(ownerUser.getUserCity());
				newEvent.setAddress(ownerUser.getAddress());
				newEvent.setHostRated(hrated);
				newEvent.setGuestRated(grated);
				
				eventList.add(newEvent);
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
			
			CRUDQueries.insertJoinedEvent(stm, user.getUsername(), event, sdf.format(event.getDateTime().getTime()));
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
	
	public static void updateJoinedEventGuestStatus(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
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
			
			CRUDQueries.updateGuestStatus(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()), event.getGuestStatus().toString().toUpperCase());
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateJoinedEventGuestRated(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
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
			
			CRUDQueries.updateGuestRated(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()), event.getGuestRated());
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateJoinedEventHostRated(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
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
			
			CRUDQueries.updateHostRated(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()), event.getHostRated());
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
				guest.setGuestStatus(GuestStatus.valueOf(rs.getString(guestStatusString).toUpperCase()));
				guest.setPayStatus(PaymentStatus.valueOf(rs.getString(paymentStatusString).toUpperCase()));
				
				guestsList.add(guest);
			}
			while(rs.next());
		}
		
		stm.close();
		rs.close();
	
		return guestsList;
	}
	
	public static Event retrieveJoinedEvent(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		Event newEvent = event;
				
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectJoinedEventByDateTime(stm, user.getUsername(), event.getOwner().getUsername(), sdf.format(event.getDateTime().getTime()));
		
		if(rs.first()) {
			GregorianCalendar date = new GregorianCalendar();
			date.setTime(rs.getTimestamp("event_date"));
							
			String guestStatus = rs.getString(guestStatusString);
			String paymentStatus = rs.getString(paymentStatusString);
			
			int hrated = rs.getInt("host_rated");
			int grated = rs.getInt("guest_rated");
			
			newEvent.setGuestStatus(GuestStatus.valueOf(guestStatus.toUpperCase()));
			newEvent.setPayStatus(PaymentStatus.valueOf(paymentStatus.toUpperCase()));
			newEvent.setHostRated(hrated);
			newEvent.setGuestRated(grated);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		rs.close();
		stm.close();
		
		return newEvent;
	}
	 
}
