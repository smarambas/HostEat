package model.dao;

import java.sql.SQLException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.User;
import model.UserType;
import model.dao.queries.CRUDQueries;
import model.dao.queries.SimpleQueries;

public class UserDAO {

	private static ConnectionSingleton cs;
	private static String norecord = "ERROR: no record found";
	
	private UserDAO() {}
	
	public static User retrieveUserByUsername(String username) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		User newUser = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
						
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, username);
		
		if(!rs.first()) {
			throw new NoRecordFoundException(norecord);
		}
		else {			
			rs.first();
									
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
		
//		while(rs.next()) {
//			String usern = rs.getString("username");
//			String passw = rs.getString("password");
//			String email = rs.getString("email");
//			String name = rs.getString("name");
//			String surname = rs.getString("surname");
//			String sex = rs.getString("sex");
//			String region = rs.getString("region");
//			String province = rs.getString("province");
//			String city = rs.getString("city");
//			String address = rs.getString("address");
//			
//			GregorianCalendar birthday = new GregorianCalendar();
//			birthday.setTime(rs.getTimestamp("birthday"));
//			
//			UserType type = UserType.valueOf(rs.getString("type").toUpperCase());
//			
//			double rating = rs.getDouble("rating");
//			int ratingsNum = rs.getInt("ratings_num");
//			
//			newUser = new User(type, usern, passw, name, surname, email);
//			newUser.setSex(sex);
//			newUser.setRegion(region);
//			newUser.setProvince(province);
//			newUser.setCity(city);
//			newUser.setAddress(address);
//			newUser.setBirthday(birthday);
//			newUser.setRating(rating);
//			newUser.setRatingsNum(ratingsNum);
//		}
				
		rs.close();
		stm.close();
		
		return newUser;
	}
	
	public static void saveUser(User user) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertUser(stm, user);
		}
		
		stm.close();
	}
	
	public static void removeUser(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteUser(stm, user.getUsername());
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserEmail(User user, String newEmail)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserEmail(stm, user.getUsername(), newEmail);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserPassword(User user, String newPassword)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserPassword(stm, user.getUsername(), newPassword);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserRegion(User user, String newRegion)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserRegion(stm, user.getUsername(), newRegion);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserProvince(User user, String newProvince)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserProvince(stm, user.getUsername(), newProvince);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserCity(User user, String newCity)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserCity(stm, user.getUsername(), newCity);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserAddress(User user, String newAddress)throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserAddress(stm, user.getUsername(), newAddress);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
	public static void updateUserRatings(User user, int newRating, int ratingsNum) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.updateUserRating(stm, user.getUsername(), newRating, ratingsNum);
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
}
