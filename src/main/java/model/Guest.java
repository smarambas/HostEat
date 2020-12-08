package model;

import java.util.ArrayList;
import java.util.List;

public class Guest extends User{

	private ArrayList<String> favCuisine;
	private ArrayList<String> allergies;
	
	public Guest(Usertype type, String username, String password, String name, String surname, String email) {
		super(type, username, password, name, surname, email);
	}

	public List<String> getFavCuisine() {
		return favCuisine;
	}

	public void addCuisine(String cuisine) {
		this.favCuisine.add(cuisine);
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void addAllergy(String allergy) {
		this.allergies.add(allergy);
	}	
	
}
