package model.dao.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleQueries {
		
	private SimpleQueries() {}
	
	public static ResultSet selectUserByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM user WHERE username = '%s';", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE owner = '%s';", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByUsernameDateTime(Statement stmt, String username, String dateTime) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE owner = '%s' AND date = '%s';", username, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectMenuByUsernameDateTime(Statement stmt, String username, String dateTime) throws SQLException {
		String statement = String.format("SELECT name, course FROM dish WHERE event_owner = '%s' AND event_date = '%s';", username, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet countGuestsNumberForEvent(Statement stmt, String username, String dateTime) throws SQLException {
		String statement = String.format("SELECT COUNT(*) FROM joined_event WHERE event_owner = '%s' AND event_date = '%s';", username, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectJoinedEventsByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM joined_event WHERE guest = '%s';", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectJoinedEventByDateTime(Statement stmt, String username, String eventOwner, String dateTime) throws SQLException {
		String statement = String.format("SELECT * FROM joined_event WHERE guest = '%s' AND event_owner = '%s' AND event_date = '%s';", username, eventOwner, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectJoinedGuestsByDateTime(Statement stmt, String username, String dateTime) throws SQLException {
		String statement = String.format("SELECT guest, guest_status, payment_status FROM joined_event WHERE event_owner = '%s' AND event_date = '%s';", username, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectFavoritesByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM saved_user WHERE user = '%s';", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectFavorite(Statement stmt, String username, String favorite) throws SQLException {
		String statement = String.format("SELECT * FROM saved_user WHERE user = '%s' AND favorite = '%s';", username, favorite);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectNotificationsByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM notification WHERE user = '%s';", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectNotificationByUsernameText(Statement stmt, String username, String text) throws SQLException {
		String statement = String.format("SELECT * FROM notification WHERE user = '%s' AND text = '%s';", username, text);
		return stmt.executeQuery(statement);
	}
	
}
