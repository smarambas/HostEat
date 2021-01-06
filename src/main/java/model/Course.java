package model;

import java.util.List;

public class Course {

	private String name;
	private List<String> dishes;
	
	public Course(String n) {
		this.name = n;
	}

	public List<String> getDishes() {
		return dishes;
	}

	public void addDish(String d) {
		this.dishes.add(d);
	}
	
}
