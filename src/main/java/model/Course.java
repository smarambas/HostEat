package model;

import java.util.ArrayList;
import java.util.List;

public class Course {

	private String name;
	private List<String> dishes;
	
	public Course() {
		this.dishes = new ArrayList<>();
	}
	
	public Course(String n) {
		this.name = n;
		this.dishes = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDishes() {
		return dishes;
	}

	public void addDish(String d) {
		this.dishes.add(d);
	}
	
}
