package model;

public class Cuisine {

	private String cuisineName;
	
	public Cuisine(String c) {
		this.cuisineName = c;
	}

	public String getCuisine() {
		return cuisineName;
	}

	public void setCuisine(String name) {
		this.cuisineName = name;
	}
	
}
