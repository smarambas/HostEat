package model;

import java.util.ArrayList;
import java.util.List;

public class Host extends User{
	
	private String address;
	private ArrayList<String> favCuisine;
	
	public Host(Usertype type, String username, String password, String name, String surname, String email) {
		super(type, username, password, name, surname, email);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<String> getFavCuisine() {
		return favCuisine;
	}

	public void addCuisine(String cuisine) {
		this.favCuisine.add(cuisine);
	}
	
}
