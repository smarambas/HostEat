package model.dao.queries;

import java .sql.*;
import java.text.SimpleDateFormat;

import bean.EventBean;
import model.Event;
import model.User;

public class CRUDQueries {
	
	private CRUDQueries() {}
	
	/*
	 * User statements
	 */
	public static int insertUser(Statement stmt, User user) throws SQLException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String statement = String.format("INSERT INTO user (username, email, password, type, name, surname, sex, region, province, city, address, birthday, rating, ratings_num) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d);", 
		user.getUsername(), user.getEmail(), user.getPassword(), user.getType().toString(), user.getName(), user.getSurname(), user.getSex(), user.getRegion(), user.getProvince(), user.getCity(), user.getAddress(), sdf.format(user.getBirthday().getTime()), user.getRating(), user.getRatingsNum());
		
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteUser(Statement stmt, String username) throws SQLException {
		String statement = String.format("DELETE FROM user WHERE username = '%s';", username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserEmail(Statement stmt, String username, String newEmail) throws SQLException {
		String statement = String.format("UPDATE user SET email = '%s' WHERE username = '%s';", newEmail, username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserPassword(Statement stmt, String username, String newPassword) throws SQLException {
		String statement = String.format("UPDATE user SET password = '%s' WHERE username = '%s';", newPassword, username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserRegion(Statement stmt, String username, String newRegion) throws SQLException {
		String statement = String.format("UPDATE user SET region = '%s' WHERE username = '%s';", newRegion, username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserProvince(Statement stmt, String username, String newProvince) throws SQLException {
		String statement = String.format("UPDATE user SET province = '%s' WHERE username = '%s';", newProvince, username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserCity(Statement stmt, String username, String newCity) throws SQLException {
		String statement = String.format("UPDATE user SET city = '%s' WHERE username = '%s';", newCity, username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserAddress(Statement stmt, String username, String newAddress) throws SQLException {
		String statement = String.format("UPDATE user SET address = '%s' WHERE username = '%s';", newAddress, username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateUserRating(Statement stmt, String username, int rating, int ratingsNum) throws SQLException {
		String statement = String.format("UPDATE user SET rating = '%d', ratings_num = '%d' WHERE username = '%s';", rating, ratingsNum, username);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Allergy statements
	 */
	public static int insertAllergy(Statement stmt, String username, String allergyName) throws SQLException {
		String statement = String.format("INSERT INTO allergy (user, name) VALUES ('%s', '%s');", username, allergyName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllergy(Statement stmt, String username, String allergyName) throws SQLException {
		String statement = String.format("DELETE FROM allergy WHERE user = '%s' AND name = '%s';", username, allergyName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllAllergies(Statement stmt, String username) throws SQLException {
		String statement = String.format("DELETE FROM allergy WHERE user = '%s';", username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateAllergy(Statement stmt, String username, String newAllergy) throws SQLException {
		String statement = String.format("UPDATE allergy SET name = '%s' WHERE user = '%s';", newAllergy, username);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Cuisine statements
	 */
	public static int insertCuisine(Statement stmt, String username, String cuisineName) throws SQLException {
		String statement = String.format("INSERT INTO cuisine (user, name) VALUES ('%s', '%s');", username, cuisineName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteCuisine(Statement stmt, String username, String cuisineName) throws SQLException {
		String statement = String.format("DELETE FROM cuisine WHERE user = '%s' AND name = '%s';", username, cuisineName);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllCuisines(Statement stmt, String username) throws SQLException {
		String statement = String.format("DELETE FROM cuisine WHERE user = '%s';", username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateCuisine(Statement stmt, String username, String newCuisine) throws SQLException {
		String statement = String.format("UPDATE cuisine SET name = '%s' WHERE user = '%s';", newCuisine, username);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Event statements
	 */
	public static int insertEvent(Statement stmt, String username, String date, int maxGuestsNum, double bill) throws SQLException {
		String statement = String.format("INSERT INTO event (owner, date, max_num_guests, payment_bill) VALUES ('%s', '%s', %d, %f);",
				username, date, maxGuestsNum, bill);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteEvent(Statement stmt, String username, String date) throws SQLException {
		String statement = String.format("DELETE FROM event WHERE owner = '%s' AND date = '%s';", username, date);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllEvents(Statement stmt, String username) throws SQLException {
		String statement = String.format("DELETE FROM event WHERE owner = '%s';", username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateEventDate(Statement stmt, String username, String oldDate, String newDate) throws SQLException {
		String statement = String.format("UPDATE event SET date = '%s' WHERE owner = '%s' AND date = '%s';", newDate, username, oldDate);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Joined Event statements
	 */
	public static int insertJoinedEvent(Statement stmt, String guest, Event event, String eventDate) throws SQLException {
		String statement = String.format("INSERT INTO joined_event (guest, event_owner, event_date, guest_status, payment_status, host_rated, guest_rated) VALUES ('%s', '%s', '%s', '%s', '%s', '%d', '%d');",
				guest, event.getOwner(), eventDate, event.getGuestStatus().toString().toUpperCase(), event.getPayStatus().toString().toUpperCase(), event.getHostRated(), event.getGuestRated());
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteJoinedGuest(Statement stmt, String username, String owner, String dateTime) throws SQLException {
		String statement = String.format("DELETE FROM joined_event WHERE guest = '%s' AND event_owner = '%s' AND event_date = '%s';", username, owner, dateTime);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllJoinedEvents(Statement stmt, String username) throws SQLException {
		String statement = String.format("DELETE FROM joined_event WHERE guest = '%s';", username);
		return stmt.executeUpdate(statement);
	}
	
	public static int updatePaymentStatus(Statement stmt, String guest, String eventOwner, String eventDate, String newPaymentStatus) throws SQLException {
		String statement = String.format("UPDATE joined_event SET payment_status = '%s' WHERE guest = '%s' AND event_owner = '%s' AND event_date = '%s';", newPaymentStatus, guest, eventOwner, eventDate);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateGuestStatus(Statement stmt, String guest, String eventOwner, String eventDate, String newGuestStatus) throws SQLException {
		String statement = String.format("UPDATE joined_event SET guest_status = '%s' WHERE guest = '%s' AND event_owner = '%s' AND event_date = '%s';", newGuestStatus, guest, eventOwner, eventDate);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateGuestRated(Statement stmt, String guest, String eventOwner, String eventDate, int guestRated) throws SQLException {
		String statement = String.format("UPDATE joined_event SET guest_rated = '%d' WHERE guest = '%s' AND event_owner = '%s' AND event_date = '%s';", guestRated, guest, eventOwner, eventDate);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateHostRated(Statement stmt, String guest, String eventOwner, String eventDate, int hostRated) throws SQLException {
		String statement = String.format("UPDATE joined_event SET host_rated = '%d' WHERE guest = '%s' AND event_owner = '%s' AND event_date = '%s';", hostRated, guest, eventOwner, eventDate);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Dish statements
	 */
	public static int insertDish(Statement stmt, String username, String dateTime, String name, String course) throws SQLException {
		String statement = String.format("INSERT INTO dish (name, course, event_owner, event_date) VALUES ('%s', '%s', '%s', '%s');",
				name, course, username, dateTime);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteDish(Statement stmt, String username, String date, String time, String name, String course) throws SQLException {
		String statement = String.format("DELETE FROM dish WHERE name = '%s' AND course = '%s' AND event_owner = '%s' AND event_date = '%s' AND event_time = '%s';",
				name, course, username, date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllDishesFromCourse(Statement stmt, String username, String date, String time, String course) throws SQLException {
		String statement = String.format("DELETE FROM dish WHERE course = '%s' AND event_owner = '%s' AND event_date = '%s' AND event_time = '%s';",
				course, username, date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllDishesFromEvent(Statement stmt, String username, String date, String time) throws SQLException {
		String statement = String.format("DELETE FROM dish WHERE event_owner = '%s' AND event_date = '%s' AND event_time = '%s';",
				username, date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateDishName(Statement stmt, String username, String date, String time, String newName, String course) throws SQLException {
		String statement = String.format("UPDATE dish SET name = '%s' WHERE course = '%s' AND event_owner = '%s' AND event_date = '%s' AND event_time = '%s';",
				newName, course, username, date, time);
		return stmt.executeUpdate(statement);
	}
	
	public static int updateDishCourse(Statement stmt, String username, String date, String time, String newCourse) throws SQLException {
		String statement = String.format("UPDATE dish SET course = '%s' WHERE event_owner = '%s' AND event_date = '%s' AND event_time = '%s';",
				newCourse, username, date, time);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Favorites statements
	 */
	public static int insertFavorite(Statement stmt, String username, String favorite) throws SQLException {
		String statement = String.format("INSERT INTO saved_user (user, favorite) VALUES ('%s', '%s');", username, favorite);
		return stmt.executeUpdate(statement);
	}	
	
	public static int deleteFavorite(Statement stmt, String username, String favorite) throws SQLException {
		String statement = String.format("DELETE FROM saved_user WHERE user = '%s' AND favorite = '%s';", username, favorite);
		return stmt.executeUpdate(statement);
	}
	
	/*
	 * Notification statements
	 */
	public static int insertNotification(Statement stmt, String username, String text, String type, String date) throws SQLException {
		String statement = String.format("INSERT INTO notification (user, text, date, type) VALUES ('%s', '%s', '%s', '%s');", username, text, date, type);
		return stmt.executeUpdate(statement);
	}	
	
	public static int deleteNotification(Statement stmt, String username, String text) throws SQLException {
		String statement = String.format("DELETE FROM notification WHERE user = '%s' AND text = '%s';", username, text);
		return stmt.executeUpdate(statement);
	}
	
	public static int deleteAllNotifications(Statement stmt, String username) throws SQLException {
		String statement = String.format("DELETE FROM notification WHERE user = '%s';", username);
		return stmt.executeUpdate(statement);
	}
	
}
