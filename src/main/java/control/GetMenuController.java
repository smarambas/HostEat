package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import bean.CourseBean;
import bean.EventBean;
import bean.MenuBean;
import exceptions.NoRecordFoundException;
import model.Course;
import model.Menu;
import model.User;
import model.dao.MenuDAO;
import model.dao.UserDAO;

public class GetMenuController {

	public MenuBean getMenu(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String username = eventBean.getEventOwner();
		User user = UserDAO.retrieveUserByUsername(username);
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		Menu menu = MenuDAO.retrieveMenu(user, dateTime);
		
		MenuBean newMenuBean = new MenuBean();
		newMenuBean.setCoursesList(new ArrayList<>());
		
		for(Course course : menu.getCourses()) {
			CourseBean newCourseBean = new CourseBean();
			newCourseBean.setCourseName(course.getName());
			newCourseBean.setDishes(course.getDishes());
			
			newMenuBean.getCoursesList().add(newCourseBean);
		}
		
		return newMenuBean;
	}
	
}
