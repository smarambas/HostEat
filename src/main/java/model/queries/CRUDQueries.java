package model.queries;

import java .sql.*;
import model.User;

public class CRUDQueries {
	
	private CRUDQueries() {}
	
	/*
	 * User statements
	 */
	public static int insertUser(Statement stmt, User user) throws SQLException {
		String statement = String.format("INSERT INTO user (username, email, password, type, name, surname, sex, region, province, city, address, age) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %d);", 
		user.getUsername(), user.getEmail(), user.getPassword(), user.getType().toString(), user.getName(), user.getSurname(), user.getSex(), user.getRegion(), user.getProvince(), user.getCity(), user.getAddress(), user.getAge());
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteUser(Statement stmt, User user) throws SQLException {
		String statement = String.format("DELETE FROM user WHERE username = %s;", user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserEmail(Statement stmt, User user, String newEmail) throws SQLException {
		String statement = String.format("UPDATE user SET email = %s WHERE username = %s;", newEmail, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserPassword(Statement stmt, User user, String newPassword) throws SQLException {
		String statement = String.format("UPDATE user SET password = %s WHERE username = %s;", newPassword, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserRegion(Statement stmt, User user, String newRegion) throws SQLException {
		String statement = String.format("UPDATE user SET region = %s WHERE username = %s;", newRegion, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserProvince(Statement stmt, User user, String newProvince) throws SQLException {
		String statement = String.format("UPDATE user SET province = %s WHERE username = %s;", newProvince, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserCity(Statement stmt, User user, String newCity) throws SQLException {
		String statement = String.format("UPDATE user SET city = %s WHERE username = %s;", newCity, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserAddress(Statement stmt, User user, String newAddress) throws SQLException {
		String statement = String.format("UPDATE user SET address = %s WHERE username = %s;", newAddress, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Allergy statements
	 */
	public static int insertAllergy(Statement stmt, User user, String allergyName) throws SQLException {
		String statement = String.format("INSERT INTO allergy (user, name) VALUES (%s, %s);", user.getUsername(), allergyName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllergy(Statement stmt, User user, String allergyName) throws SQLException {
		String statement = String.format("DELETE FROM allergy WHERE user = %s AND name = %s;", user.getUsername(), allergyName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllAllergies(Statement stmt, User user) throws SQLException {
		String statement = String.format("DELETE FROM allergy WHERE user = %s;", user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateAllergy(Statement stmt, User user, String newAllergy) throws SQLException {
		String statement = String.format("UPDATE allergy SET name = %s WHERE user = %s;", newAllergy, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Cuisine statements
	 */
	public static int insertCuisine(Statement stmt, User user, String cuisineName) throws SQLException {
		String statement = String.format("INSERT INTO cuisine (user, name) VALUES (%s, %s);", user.getUsername(), cuisineName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteCuisine(Statement stmt, User user, String cuisineName) throws SQLException {
		String statement = String.format("DELETE FROM cuisine WHERE user = %s AND name = %s;", user.getUsername(), cuisineName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllCuisines(Statement stmt, User user) throws SQLException {
		String statement = String.format("DELETE FROM cuisine WHERE user = %s;", user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateCuisine(Statement stmt, User user, String newCuisine) throws SQLException {
		String statement = String.format("UPDATE cuisine SET name = %s WHERE user = %s;", newCuisine, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Event statements
	 */
	public static int insertEvent(Statement stmt, User user, String date, String time, String type, int guestsNum) throws SQLException {
		String statement = String.format("INSERT INTO event (owner, date, time, type, num_guests) VALUES (%s, %s, %s, %s, %d);",
				user.getUsername(), date, time, type, guestsNum);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteEvent(Statement stmt, User user, String date, String time) throws SQLException {
		String statement = String.format("DELETE FROM event WHERE owner = %s AND date = %s AND time = %s;", user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllEvents(Statement stmt, User user) throws SQLException {
		String statement = String.format("DELETE FROM event WHERE owner = %s;", user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateEventDate(Statement stmt, User user, String newDate) throws SQLException {
		String statement = String.format("UPDATE event SET date = %s WHERE owner = %s;", newDate, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	public static int updateEventTime(Statement stmt, User user, String newTime) throws SQLException {
		String statement = String.format("UPDATE event SET time = %s WHERE owner = %s;", newTime, user.getUsername());
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Dish statements
	 */
	public static int insertDish(Statement stmt, User user, String date, String time, String name, String course) throws SQLException {
		String statement = String.format("INSERT INTO dish (name, course, event_owner, event_date, event_time) VALUES (%s, %s, %s, %s, %s);",
				name, course, user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteDish(Statement stmt, User user, String date, String time, String name, String course) throws SQLException {
		String statement = String.format("DELETE FROM dish WHERE name = %s AND course = %s AND event_owner = %s AND event_date = %s AND event_time = %s;",
				name, course, user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllDishesFromCourse(Statement stmt, User user, String date, String time, String course) throws SQLException {
		String statement = String.format("DELETE FROM dish WHERE course = %s AND event_owner = %s AND event_date = %s AND event_time = %s;",
				course, user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllDishesFromEvent(Statement stmt, User user, String date, String time) throws SQLException {
		String statement = String.format("DELETE FROM dish WHERE event_owner = %s AND event_date = %s AND event_time = %s;",
				user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateDishName(Statement stmt, User user, String date, String time, String newName, String course) throws SQLException {
		String statement = String.format("UPDATE dish SET name = %s WHERE course = %s AND event_owner = %s AND event_date = %s AND event_time = %s;",
				newName, course, user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateDishCourse(Statement stmt, User user, String date, String time, String newCourse) throws SQLException {
		String statement = String.format("UPDATE dish SET course = %s WHERE event_owner = %s AND event_date = %s AND event_time = %s;",
				newCourse, user.getUsername(), date, time);
		return stmt.executeUpdate(statement);
	}
		
}
