package bean;

import java.util.List;

public class UserBean {

	private String username;
	private String passw;
	private String name;
	private String surname;
	private String email;
	private String birthDay;
	private String sex;
	private String reg;
	private String prov;
	private String city;
	private String addr;
	private double ratings;
	private List<String> favCuisines;
	private List<String> allergies;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassw() {
		return passw;
	}
	
	public void setPassw(String passw) {
		this.passw = passw;
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
	
	public String getBirthDay() {
		return birthDay;
	}
	
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getReg() {
		return reg;
	}
	
	public void setReg(String reg) {
		this.reg = reg;
	}
	
	public String getProv() {
		return prov;
	}
	
	public void setProv(String prov) {
		this.prov = prov;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAddr() {
		return addr;
	}
	
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public double getRatings() {
		return ratings;
	}
	
	public void setRatings(double ratings) {
		this.ratings = ratings;
	}
	
	public List<String> getFavCuisines() {
		return favCuisines;
	}
	
	public void setFavCuisines(List<String> favCuisines) {
		this.favCuisines = favCuisines;
	}
	
	public List<String> getAllergies() {
		return allergies;
	}
	
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	
}
