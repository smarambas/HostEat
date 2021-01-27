package model.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.User;
import model.dao.queries.CRUDQueries;
import model.dao.queries.SimpleQueries;

public class FavoritesDAO {

	private static ConnectionSingleton cs;
	
	private FavoritesDAO() {}
	
	public static List<User> retrieveFavoritesByUsername(User user) throws SQLException, NoRecordFoundException, ClassNotFoundException, IOException {
		Statement stm = null;
		List<User> favList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectFavoritesByUsername(stm, user.getUsername());
		
		if(rs.first()) {
			rs.first();
			
			do {
				User savedUser = UserDAO.retrieveUserByUsername(rs.getString("favorite"));
				
				favList.add(savedUser);
			}
			while(rs.next());
		}
		
		return favList;
	}
	
	public static void saveFavorite(User user, User favorite) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectFavorite(stm, user.getUsername(), favorite.getUsername());
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.insertFavorite(stm, user.getUsername(), favorite.getUsername());
		}
		
		stm.close();
	}
	
	public static void removeFavorite(User user, User favorite) throws ClassNotFoundException, SQLException, IOException, NoRecordFoundException {
		Statement stm = null;
		String norecord = "ERROR: no record found";
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectFavorite(stm, user.getUsername(), favorite.getUsername());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteFavorite(stm, user.getUsername(), favorite.getUsername());
		}
		else {
			throw new NoRecordFoundException(norecord);
		}
		
		stm.close();
	}
	
}
