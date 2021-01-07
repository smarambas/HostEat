package model.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleQueries {
	
	private SimpleQueries() {}
	
	public static ResultSet selectUserByUsername(Statement stmt, String username) throws SQLException {
		String statement = "SELECT * FROM user WHERE username = '" + username + "';";
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectAllergiesByUsername(Statement stmt, String username) throws SQLException {
		String statement = "SELECT name FROM allergy WHERE user = '" + username + "';";
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectCuisinesByUsername(Statement stmt, String username) throws SQLException {
		String statement = "SELECT name FROM cuisine WHERE user = '" + username + "';";
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByUsername(Statement stmt, String username) throws SQLException {
		String statement = "SELECT * FROM event WHERE owner = '" + username + "';";
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByDate(Statement stmt, String username, String date) throws SQLException {
		String statement = "SELECT * FROM event WHERE owner = '" + username + "' AND date = '" + date + "';";
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByDateTime(Statement stmt, String username, String date, String time) throws SQLException {
		String statement = "SELECT * FROM event WHERE owner = '" + username + "' AND date = '" + date + "' AND time = '" + time + "';";
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectMenuByUsernameDateTime(Statement stmt, String username, String date, String time) throws SQLException {
		String statement = "SELECT name, course FROM dish WHERE event_owner = '" + username + "' AND event_date = '" + date + "' AND event_time = '" + time + "';";
		return stmt.executeQuery(statement);
	}
}
