package model.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class SimpleQueries {
	
	private SimpleQueries() {}
	
	public static ResultSet selectUserByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM user WHERE username = %s;", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectAllergiesByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT name FROM allergy WHERE user = %s;", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectAllergy(Statement stmt, String username, String allergy) throws SQLException {
		String statement = String.format("SELECT name FROM allergy WHERE user = %s AND name = %s;", username, allergy);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectCuisinesByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT name FROM cuisine WHERE user = %s;", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectCuisine(Statement stmt, String username, String cuisine) throws SQLException {
		String statement = String.format("SELECT name FROM cuisine WHERE user = %s AND name = %s;", username, cuisine);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventsByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE owner = %s;", username);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByUsernameDateTime(Statement stmt, String username, String dateTime) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE owner = %s AND date = %s;", username, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectMenuByUsernameDateTime(Statement stmt, String username, String date, String time) throws SQLException {
		String statement = String.format("SELECT name, course FROM dish WHERE event_owner = %s AND event_date = %s AND event_time = %s;", username, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet countGuestsNumberForEvent(Statement stmt, String username, String dateTime) throws SQLException {
		String statement = String.format("SELECT COUNT(*) FROM joined_event WHERE event_owner = %s AND event_date = %s;", username, dateTime);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectJoinedEventsByUsername(Statement stmt, String username) throws SQLException {
		String statement = String.format("SELECT * FROM joined_event WHERE guest = %s;", username);
		return stmt.executeQuery(statement);
	}
}
