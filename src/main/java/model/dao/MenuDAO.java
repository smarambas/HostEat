package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Course;
import model.Menu;
import model.User;
import model.queries.CRUDQueries;
import model.queries.SimpleQueries;

public class MenuDAO {

	private static ConnectionSingleton cs;
	
	private MenuDAO() {}
	
	public static Menu retrieveMenu(User user, String date, String time) throws SQLException, ClassNotFoundException, NoRecordFoundException {
		Statement stm = null;
		Menu menu = new Menu();
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs = SimpleQueries.selectMenuByUsernameDateTime(stm, user.getUsername(), date, time);
		
		if(!rs.first()) {
			throw new NoRecordFoundException("ERROR: no record found");
		}
		else {
			rs.first();
			do {
				Course newCourse = new Course(rs.getString("course"));
				newCourse.addDish(rs.getString("name"));
				
				menu.addCourse(newCourse);
			}
			while(rs.next());			
		}
		
		rs.close();
		stm.close();
		
		return menu;
	}
	
	public static void saveMenu(User user, Menu menu, String date, String time) throws SQLException, ClassNotFoundException, DuplicateRecordException {
		Statement stm = null;
		
		cs = ConnectionSingleton.createConnection();
		
		stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
	
		ResultSet rs = SimpleQueries.selectMenuByUsernameDateTime(stm, user.getUsername(), date, time);
		
		if(rs.first()) {
			throw new DuplicateRecordException("ERROR: the user already exists");
		}
		else {
			List<Course> courses = menu.getCourses();
			int coursesSize = courses.size();
			
			for(int i = 0; i < coursesSize; i++) {
				String courseName = courses.get(i).getName();				
				int dishSize = courses.get(i).getDishes().size();
				
				for(int j = 0; j < dishSize; j++) {
					rs.close();
					stm.close();
					stm = cs.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					
					CRUDQueries.insertDish(stm, user, date, time, courses.get(i).getDishes().get(j), courseName);
				}
			}
		}
	
		stm.close();
	
	}
	
	
	
}
