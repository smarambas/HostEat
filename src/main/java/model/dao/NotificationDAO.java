package model.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Notification;
import model.NotificationType;
import model.User;

public class NotificationDAO {

	private static ConnectionSingleton cs;
	private static String format = "yyyy-MM-dd HH:mm";
	
	private NotificationDAO() {}
	
	public static List<Notification> retrieveNotificationsByUser(User user) throws ClassNotFoundException, SQLException, IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		List<Notification> notifications = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM notification WHERE user = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				do {
					Notification newNotification = new Notification();
					
					GregorianCalendar date = new GregorianCalendar();
					date.setTime(sdf.parse(rs.getString("date")));
					
					newNotification.setUser(user);
					newNotification.setText(rs.getString("text"));
					newNotification.setType(NotificationType.valueOf(rs.getString("type").toUpperCase()));
					newNotification.setDate(date);
					
					notifications.add(newNotification);
				}
				while(rs.next());
			}
		
		}
		
		return notifications;
	}
	
	public static void saveNotification(User user, Notification notification) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM notification WHERE user = ? AND text = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, notification.getText());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(!rs.next()) {
				query = "INSERT INTO notification (user, text, date, type) VALUES (?, ?, ?, ?);";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, notification.getText());
					ps.setString(3, sdf.format(notification.getDate().getTime()));
					ps.setString(4, notification.getType().toString().toUpperCase());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new DuplicateRecordException("ERROR: the record already exists");
			}
		
		}
	}
	
	public static void deleteNotification(User user, Notification notification) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM notification WHERE user = ? AND text = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, notification.getText());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "DELETE FROM notification WHERE user = ? AND text = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, notification.getText());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException("ERROR: no record found");
			}
		}
	}
	
	public static void deleteAllNotifications(User user) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM notification WHERE user = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "DELETE FROM notification WHERE user = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException("ERROR: no record found");
			}
		}
	}
	
}
