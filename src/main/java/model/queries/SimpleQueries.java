package model.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public static ResultSet selectEventsByUsernameDate(Statement stmt, String username, String date) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE owner = %s AND date = %s;", username, date);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectEventByUsernameDateTime(Statement stmt, String username, String date, String time) throws SQLException {
		String statement = String.format("SELECT * FROM event WHERE owner = %s AND date = %s AND time = %s;", username, date, time);
		return stmt.executeQuery(statement);
	}
	
	public static ResultSet selectMenuByUsernameDateTime(Statement stmt, String username, String date, String time) throws SQLException {
		String statement = String.format("SELECT name, course FROM dish WHERE event_owner = %s AND event_date = %s AND event_time = %s;", username, date, time);
		return stmt.executeQuery(statement);
	}
}
