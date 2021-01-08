package model.dao;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.User;
import model.queries.CRUDQueries;
import model.queries.SimpleQueries;
import model.Usertype;

public class UserDAO {

	private static ConnectionSingleton cs;
	
	private UserDAO() {}
	
	public static List<User> retrieveUserByUsername(String username) throws Exception {
		Statement stm = null;
		List<User> userList = new ArrayList<>();
		
		try {
			cs = ConnectionSingleton.createConnection();
			
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rs = SimpleQueries.selectUserByUsername(stm, username);
			
			if(!rs.first()) {
				//throw new Exception("No user found matching username: " + username);
				System.out.println("No user found matching username: " + username);
			}
									
			rs.first();
			do {
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
				int age = rs.getInt("age");
				Usertype type = Usertype.valueOf(rs.getString("type").toUpperCase());
				
				User newUser = new User(type, usern, passw, name, surname, email);
				newUser.setSex(sex);
				newUser.setRegion(region);
				newUser.setProvince(province);
				newUser.setCity(city);
				newUser.setAddress(address);
				newUser.setAge(age);
				
				userList.add(newUser);
			}while(rs.next());
			
			rs.close();
		} finally {
			try {
				if(stm != null) {
					stm.close();
				}
			} catch(SQLException sqle) {
				
			}
		}
		
		return userList;
	}
	
	public static void saveUser(User user) throws Exception {
		Statement stm = null;
		
		try {
			cs = ConnectionSingleton.createConnection();
			
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
			
			if(rs.next()) {
				throw new DuplicateRecordException("ERROR: the user already exists");
			}
			else {
				rs.close();
				stm.close();
				stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				
				CRUDQueries.insertUser(stm, user);
			}
		} finally {
			try {
				if(stm != null) {
					stm.close();
				}
			} catch(SQLException sqle) {
				
			}
		}
	}
	
	public static void removeUser(User user) throws Exception {
		Statement stm = null;
		
		try {
			cs = ConnectionSingleton.createConnection();
			
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rs = SimpleQueries.selectUserByUsername(stm, user.getUsername());
			
			if(rs.next()) {
				rs.close();
				stm.close();
				stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				
				CRUDQueries.deleteUser(stm, user);
			}
			else {
				throw new NoRecordFoundException("ERROR: no record found");
			}
		} finally {
			try {
				if(stm != null) {
					stm.close();
				}
			} catch(SQLException sqle) {
				
			}
		}
	}
	
}
