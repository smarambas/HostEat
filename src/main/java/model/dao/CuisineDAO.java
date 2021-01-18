package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Cuisine;
import model.User;
import model.dao.queries.CRUDQueries;
import model.dao.queries.SimpleQueries;

public class CuisineDAO {

	private static ConnectionSingleton cs;
	
	private CuisineDAO() {}
	
	public static List<Cuisine> retrieveCuisinesByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		Statement stm = null;
		List<Cuisine> cuisineList = new ArrayList<>();
		
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectCuisinesByUsername(stm, user.getUsername());
		
		if(!rs.first()) {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		else {
			rs.first();
			do {
				String name = rs.getString("name");
				
				Cuisine newCuisine = new Cuisine(name);
				cuisineList.add(newCuisine);
			}
			while(rs.next());
		}
		
		rs.close();
		stm.close();	
		
		return cuisineList;		
	}
	
	public static void saveCuisine(User user, Cuisine cuisine) throws SQLException, ClassNotFoundException, DuplicateRecordException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectCuisine(stm, user.getUsername(), cuisine.getCuisine());
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			
			CRUDQueries.insertCuisine(stm, user.getUsername(), cuisine.getCuisine());
		}
		
		stm.close();
	}
	
	public static void removeCuisine(User user, Cuisine cuisine) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectCuisine(stm, user.getUsername(), cuisine.getCuisine());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteCuisine(stm, user.getUsername(), cuisine.getCuisine());
		}
		else {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		
		stm.close();
	}
	
}
