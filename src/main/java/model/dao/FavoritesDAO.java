package model.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.User;

public class FavoritesDAO {

	private static ConnectionSingleton cs;
	
	private FavoritesDAO() {}
	
	public static List<User> retrieveFavoritesByUsername(User user) throws SQLException, ClassNotFoundException, IOException {
		List<User> favList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM saved_user WHERE user = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				do {
					User savedUser = UserDAO.retrieveUserByUsername(rs.getString("favorite"));
					
					favList.add(savedUser);
				}
				while(rs.next());
			}
		}
		
		return favList;
	}
	
	public static void saveFavorite(User user, User favorite) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM saved_user WHERE user = ? AND favorite = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, favorite.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(!rs.next()) {
				query = "INSERT INTO saved_user (user, favorite) VALUES (?, ?);";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, favorite.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new DuplicateRecordException("ERROR: the record already exists");
			}
		}
	}
	
	public static void removeFavorite(User user, User favorite) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		String norecord = "ERROR: no record found";
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT * FROM saved_user WHERE user = ? AND favorite = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, favorite.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				query = "DELETE FROM saved_user WHERE user = ? AND favorite = ?;";
				
				try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
					ps.setString(1, user.getUsername());
					ps.setString(2, favorite.getUsername());
					
					ps.executeUpdate();
				}
			}
			else {
				throw new NoRecordFoundException(norecord);
			}
		}
	}
	
}
