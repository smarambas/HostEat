package model.dao;

import java.sql.SQLException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.User;
import model.UserType;

public class UserDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	private static String selectString = "SELECT * FROM user WHERE username = ?;";
	
	private UserDAO() {}
	
	public static User retrieveUserByUsername(String username) throws SQLException, ClassNotFoundException, IOException {
		User newUser = null;
		
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				String usern = rs.getString("username");
				String passw = rs.getString("password");
				String email = rs.getString("email");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String sex = rs.getString("sex");
				String region = rs.getString("region");
				String province = rs.getString("province");
				String city = rs.getString("city");
				String address = rs.getString("address");
				
				GregorianCalendar birthday = new GregorianCalendar();
				birthday.setTime(rs.getTimestamp("birthday"));
				
				UserType type = UserType.valueOf(rs.getString("type").toUpperCase());
				
				int rating = rs.getInt("rating");
				int ratingsNum = rs.getInt("ratings_num");
				
				newUser = new User(type, usern, passw, name, surname, email);
				newUser.setSex(sex);
				newUser.setUserRegion(region);
				newUser.setUserProvince(province);
				newUser.setUserCity(city);
				newUser.setAddress(address);
				newUser.setBirthday(birthday);
				newUser.setRating(rating);
				newUser.setRatingsNum(ratingsNum);
			}
					
			return newUser;
		}
	}
	
	public static void saveUser(User user) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(!rs.next()) {
				query = "INSERT INTO user (username, email, password, type, name, surname, sex, region, province, city, address, birthday, rating, ratings_num) "
										+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, user.getEmail());
					ps.setString(3, user.getPassword());
					ps.setString(4, user.getType().toString().toUpperCase());
					ps.setString(5, user.getName());
					ps.setString(6, user.getSurname());
					ps.setString(7, user.getSex());
					ps.setString(8, user.getUserRegion());
					ps.setString(9, user.getUserProvince());
					ps.setString(10, user.getUserCity());
					ps.setString(11, user.getAddress());
					ps.setString(12, sdf.format(user.getBirthday().getTime()));
					ps.setInt(13, user.getRating());
					ps.setInt(14, user.getRatingsNum());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new DuplicateRecordException("ERROR: the record already exists");
			}
		}
	}
	
	public static void removeUser(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "DELETE FROM user WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserEmail(User user, String newEmail)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET email = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, newEmail);
					ps.setString(2, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserPassword(User user, String newPassword)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET password = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, newPassword);
					ps.setString(2, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserRegion(User user, String newRegion)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET region = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, newRegion);
					ps.setString(2, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserProvince(User user, String newProvince)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET province = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, newProvince);
					ps.setString(2, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserCity(User user, String newCity)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET city = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, newCity);
					ps.setString(2, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserAddress(User user, String newAddress)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET address = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, newAddress);
					ps.setString(2, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
	public static void updateUserRatings(User user, int newRating, int ratingsNum) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		cs = ConnectionSingleton.createConnection();
		
		String query = selectString;
		
		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "UPDATE user SET rating = ?, ratings_num = ? WHERE username = ?;";
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setInt(1, newRating);
					ps.setInt(2, ratingsNum);
					ps.setString(3, user.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
}
