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
import model.GuestStatus;
import model.PaymentStatus;
import model.User;

public class JoinedEventDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	private static String format = "yyyy-MM-dd HH:mm";
	private static String guestStatusString = "guest_status";
	private static String paymentStatusString = "payment_status";
	private static String selectString = "SELECT * FROM joined_event WHERE guest = ? AND event_owner = ? AND event_date = ?;";
	
	private JoinedEventDAO() {}
	
	public static List<Event> retrieveJoinedEventsByUsername(User user) throws SQLException, ClassNotFoundException, IOException {
		List<Event> eventList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM joined_event WHERE guest = ?;";
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
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
		}
		
		return eventList;
	}
	
	public static void saveJoinedEvent(User user, Event event) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(!rs.next()) {
				query = "INSERT INTO joined_event (guest, event_owner, event_date, guest_status, payment_status, host_rated, guest_rated) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, event.getOwner().getUsername());
					ps.setString(3, sdf.format(event.getDateTime().getTime()));
					ps.setString(4, event.getGuestStatus().toString().toUpperCase());
					ps.setString(5, event.getPayStatus().toString().toUpperCase());
					ps.setInt(6, event.getHostRated());
					ps.setInt(7, event.getGuestRated());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new DuplicateRecordException("ERROR: the record already exists");
			}
		}
	}
	
	public static void removeJoinedGuest(User user, Event event) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
				query = "DELETE FROM joined_event WHERE guest = ? AND event_owner = ? AND event_date = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, event.getOwner().getUsername());
					ps.setString(3, sdf.format(event.getDateTime().getTime()));
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateJoinedEventPaymentStatus(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
				query = "UPDATE joined_event SET payment_status = ? WHERE guest = ? AND event_owner = ? AND event_date = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, event.getPayStatus().toString().toUpperCase());
					ps.setString(2, user.getUsername());
					ps.setString(3, event.getOwner().getUsername());
					ps.setString(4, sdf.format(event.getDateTime().getTime()));
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateJoinedEventGuestStatus(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
				query = "UPDATE joined_event SET guest_status = ? WHERE guest = ? AND event_owner = ? AND event_date = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, event.getGuestStatus().toString().toUpperCase());
					ps.setString(2, user.getUsername());
					ps.setString(3, event.getOwner().getUsername());
					ps.setString(4, sdf.format(event.getDateTime().getTime()));
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateJoinedEventGuestRated(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
				query = "UPDATE joined_event SET guest_rated = ? WHERE guest = ? AND event_owner = ? AND event_date = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setInt(1, event.getGuestRated());
					ps.setString(2, user.getUsername());
					ps.setString(3, event.getOwner().getUsername());
					ps.setString(4, sdf.format(event.getDateTime().getTime()));
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateJoinedEventHostRated(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
				query = "UPDATE joined_event SET host_rated = ? WHERE guest = ? AND event_owner = ? AND event_date = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setInt(1, event.getHostRated());
					ps.setString(2, user.getUsername());
					ps.setString(3, event.getOwner().getUsername());
					ps.setString(4, sdf.format(event.getDateTime().getTime()));
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static List<User> retrieveJoinedGuests(User user, Event event) throws SQLException, ClassNotFoundException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		List<User> guestsList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT guest, guest_status, payment_status FROM joined_event WHERE event_owner = ? AND event_date = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				do {
					String username = rs.getString("guest");
					
					User guest = UserDAO.retrieveUserByUsername(username);
					guest.setGuestStatus(GuestStatus.valueOf(rs.getString(guestStatusString).toUpperCase()));
					guest.setPayStatus(PaymentStatus.valueOf(rs.getString(paymentStatusString).toUpperCase()));
					
					guestsList.add(guest);
				}
				while(rs.next());
			}
		}
	
		return guestsList;
	}
	
	public static Event retrieveJoinedEvent(User user, Event event) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		Event newEvent = event;
				
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, event.getOwner().getUsername());
			preparedStatement.setString(3, sdf.format(event.getDateTime().getTime()));
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()) {
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
		}
		
		return newEvent;
	}
	 
}
