package model;

import java.util.List;

public class Menu {

	private List<Course> courses;
		
	public List<Course> getCourses() {
		return courses;
	}
	
	public void addCourse(Course c) {
		this.courses.add(c);
	}
	
	public boolean containsCourse(String courseName) {
		boolean result = false;
		int size = this.courses.size();
		
		for(int i = 0; i < size; i++) {
			if(this.courses.get(i).getName().equals(courseName)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public Course getCourse(String courseName) {
		if(containsCourse(courseName)) {
			int size = this.courses.size();
			
			for(int i = 0; i < size; i++) {
				if(this.courses.get(i).getName().equals(courseName)) {
					return this.courses.get(i);
				}
			}
		}
		
		return null;
	}
}
