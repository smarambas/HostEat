package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Allergy;
import model.User;
import model.queries.CRUDQueries;
import model.queries.SimpleQueries;

public class AllergyDAO {

	private static ConnectionSingleton cs;
	
	private AllergyDAO() {}
	
	public static List<Allergy> retrieveAllergiesByUsername(User user) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		Statement stm = null;
		List<Allergy> allergyList = new ArrayList<>();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectAllergiesByUsername(stm, user.getUsername());
		
		if(!rs.first()) {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		else {
			rs.first();
			do {
				String name = rs.getString("name");
				
				Allergy newAllergy = new Allergy(name);
				allergyList.add(newAllergy);
			}
			while(rs.next());
		}
		
		rs.close();
		stm.close();
		
		return allergyList;
	}
	
	public static void saveAllergy(User user, Allergy allergy) throws SQLException, ClassNotFoundException, DuplicateRecordException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectAllergy(stm, user.getUsername(), allergy.getName());
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the record already exists");
		}
		else {
			rs.close();
			stm.close();
			
			CRUDQueries.insertAllergy(stm, user, allergy.getName());
		}
		
		stm.close();
	}
	
	public static void removeAllergy(User user, Allergy allergy) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectAllergy(stm, user.getUsername(), allergy.getName());
		
		if(rs.first()) {
			rs.close();
			stm.close();
			stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			CRUDQueries.deleteAllergy(stm, user, allergy.getName());
		}
		else {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		
		stm.close();		
	}
	
}
