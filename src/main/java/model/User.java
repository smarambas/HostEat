package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private Usertype type;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private int age;
	private String sex;
	private String region;
	private String province;
	private String city;
	private String address;
	private ArrayList<String> favCuisine;
	private ArrayList<String> allergies;
	
	public User(Usertype type, String username, String password, String name, String surname, String email) {
		this.type = type;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	
	public Usertype getType() {
		return type;
	}

	public void setType(Usertype type) {
		this.type = type;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public List<String> getAllergies() {
		return allergies;
	}

	public void addAllergy(String allergy) {
		this.allergies.add(allergy);
	}	
	
}
