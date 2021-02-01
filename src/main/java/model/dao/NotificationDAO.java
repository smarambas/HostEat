package model.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import model.dao.queries.CRUDQueries;
import model.dao.queries.SimpleQueries;

public class NotificationDAO {

	private static ConnectionSingleton cs;
	private static String format = "yyyy-MM-dd HH:mm";
	
	private NotificationDAO() {}
	
	public static List<Notification> retrieveNotificationsByUser(User user) throws ClassNotFoundException, SQLException, IOException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		List<Notification> notifications = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectNotificationsByUsername(stm, user.getUsername());
		
		if(rs.first()) {
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
		
		stm.close();
		rs.close();
		
		return notifications;
	}
	
	public static void saveNotification(User user, Notification notification) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectNotificationByUsernameText(stm, user.getUsername(), notification.getText());
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertNotification(stm, user.getUsername(), 
											   notification.getText(), 
											   notification.getType().toString().toUpperCase(), 
											   sdf.format(notification.getDate().getTime()));
		}
		
		stm.close();
	}
	
	public static void deleteNotification(User user, Notification notification) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectNotificationByUsernameText(stm, user.getUsername(), notification.getText());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteNotification(stm, user.getUsername(), notification.getText());
		}
		else {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		
		stm.close();
	}
	
	public static void deleteAllNotifications(User user) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectNotificationsByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteAllNotifications(stm, user.getUsername());
		}
		else {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		
		stm.close();
	}
	
}
