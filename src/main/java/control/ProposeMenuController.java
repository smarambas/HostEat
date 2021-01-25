package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import bean.CourseBean;
import bean.EventBean;
import bean.MenuBean;
import exceptions.DuplicateRecordException;
import exceptions.NoRecordFoundException;
import model.Course;
import model.Menu;
import model.User;
import model.dao.MenuDAO;
import model.dao.UserDAO;

public class ProposeMenuController {

	public void proposeMenu(EventBean eventBean, MenuBean menuBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException, DuplicateRecordException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		String username = eventBean.getEventOwner();
		User user = UserDAO.retrieveUserByUsername(username);
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		Menu newMenu = new Menu();
		
		for(CourseBean cb : menuBean.getCoursesList()) {
			Course newCourse = new Course();
			newCourse.setName(cb.getCourseName());
			
			for(String dish : cb.getDishes()) {
				newCourse.addDish(dish);
			}
			
			newMenu.addCourse(newCourse);
		}
		
		MenuDAO.saveMenu(user, newMenu, dateTime);
	}
	
}
