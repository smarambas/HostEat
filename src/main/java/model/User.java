package model;

import java.util.GregorianCalendar;

public class User {
	
	private UserType type;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private GregorianCalendar birthDate;
	private String sex;
	private String region;
	private String province;
	private String city;
	private String address;
	private double rating;
	private int ratingsNum;
	
	public User(UserType type, String username, String password, String name, String surname, String email) {
		this.type = type;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}
	
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public GregorianCalendar getAge() {
		return birthDate;
	}

	public void setAge(int year, int month, int day) {
		this.birthDate = new GregorianCalendar(year, month, day);
	}
	
	public void setAge(GregorianCalendar date) {
		this.birthDate = date;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getRatingsNum() {
		return ratingsNum;
	}

	public void setRatingsNum(int ratingsNum) {
		this.ratingsNum = ratingsNum;
	}	
	
}
