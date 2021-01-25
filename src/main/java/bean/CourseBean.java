package bean;

import java.util.ArrayList;
import java.util.List;

public class CourseBean {

	private String courseName;
	private List<String> dishesList;
	
	public CourseBean() {
		dishesList = new ArrayList<>();
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<String> getDishes() {
		return dishesList;
	}
	public void setDishes(List<String> dishes) {
		this.dishesList = dishes;
	}
	
}
