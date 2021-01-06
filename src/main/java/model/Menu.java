package model;

import java.util.List;

public class Menu {

	private List<Course> courses;

	public List<Course> getCourses() {
		return courses;
	}
	
	public void addCourse(String c) {
		this.courses.add(new Course(c));
	}
}
