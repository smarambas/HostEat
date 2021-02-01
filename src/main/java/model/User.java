package model;

import java.util.GregorianCalendar;

public class User {
	
	private UserType type;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private GregorianCalendar birthday;
	private String sex;
	private String region;
	private String province;
	private String city;
	private String address;
	private int rating;
	private int ratingsNum;
	private GuestStatus guestStatus;
	private PaymentStatus payStatus;
	
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

	public GregorianCalendar getBirthday() {
		return birthday;
	}

	public void setBirthday(int year, int month, int day) {
		this.birthday = new GregorianCalendar(year, month, day);
	}
	
	public void setBirthday(GregorianCalendar date) {
		this.birthday = date;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRatingsNum() {
		return ratingsNum;
	}

	public void setRatingsNum(int ratingsNum) {
		this.ratingsNum = ratingsNum;
	}

	public GuestStatus getGuestStatus() {
		return guestStatus;
	}

	public void setGuestStatus(GuestStatus guestStatus) {
		this.guestStatus = guestStatus;
	}

	public PaymentStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PaymentStatus payStatus) {
		this.payStatus = payStatus;
	}	
	
}
