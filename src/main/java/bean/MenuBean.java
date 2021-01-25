package bean;

import java.util.ArrayList;
import java.util.List;

public class MenuBean {

	private List<CourseBean> coursesList;
	
	public MenuBean() {
		coursesList = new ArrayList<>();
	}

	public List<CourseBean> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<CourseBean> coursesList) {
		this.coursesList = coursesList;
	}
	
}
