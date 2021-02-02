package model.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Course;
import model.Menu;
import model.User;

public class MenuDAO {

	private static ConnectionSingleton cs;
	
	private MenuDAO() {}
	
	public static Menu retrieveMenu(User user, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, NoRecordFoundException, IOException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Menu menu = new Menu();
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT name, course FROM dish WHERE event_owner = ? AND event_date = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, sdf.format(dateTime.getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				do {
					String courseName = rs.getString("course");
					String dishName = rs.getString("name");
					
					Course newCourse = menu.getCourse(courseName);
					
					if(newCourse != null) {
						//the menu already contains the course
						newCourse.addDish(dishName);
					}
					else {
						newCourse = new Course(courseName);
						newCourse.addDish(dishName);
						menu.addCourse(newCourse);
					}
				}
				while(rs.next());	
			}
			else {
				throw new NoRecordFoundException("ERROR: no record found");
			}
		}
		
		return menu;
	}
	
	public static void saveMenu(User user, Menu menu, GregorianCalendar dateTime) throws SQLException, ClassNotFoundException, DuplicateRecordException, IOException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		cs = ConnectionSingleton.createConnection();
		
		String query = "SELECT name, course FROM dish WHERE event_owner = ? AND event_date = ?;";

		try(PreparedStatement preparedStatement = cs.getConnection().prepareStatement(query)) {
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, sdf.format(dateTime.getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			
			if(!rs.next()) {
				List<Course> courses = menu.getCourses();
				int coursesSize = courses.size();
				
				query = "INSERT INTO dish (name, course, event_owner, event_date) VALUES (?, ?, ?, ?);";
				
				for(int i = 0; i < coursesSize; i++) {
					String courseName = courses.get(i).getName();				
					int dishSize = courses.get(i).getDishes().size();
					
					for(int j = 0; j < dishSize; j++) {
						
						try(PreparedStatement ps = cs.getConnection().prepareStatement(query)) {
							ps.setString(1, courses.get(i).getDishes().get(j));
							ps.setString(2, courseName);
							ps.setString(3, user.getUsername());
							ps.setString(4, sdf.format(dateTime.getTime()));
							
							ps.executeUpdate();
						}
					}
				}
			}
			else {
				throw new DuplicateRecordException("ERROR: the record already exists");
			}
		}
	}
	
}
