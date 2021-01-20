package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

public class UserBean {
	
	private String username;
	private String passw;
	private String name;
	private String surname;
	private String emailAddr;
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
	
	public boolean setName(String name) {
		if(validateString(name)) {
			this.name = name;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getEmailAddr() {
		return emailAddr;
	}
	
	public boolean setEmailAddr(String email) {
		if(validateEmail(email)) {
			this.emailAddr = email;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getSurname() {
		return surname;
	}
	
	public boolean setSurname(String surname) {
		if(validateString(surname)) {
			this.surname = surname;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getBirthDay() {
		return birthDay;
	}
	
	public boolean setBirthDay(String birthDay) {
		if(validateBirthday(birthDay)) {
			this.birthDay = birthDay;
			return true;
		}
		else {
			return false;
		}
		
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
	
	private boolean validateString(String string) {
		if(string == null || string.equals("")) {
			return false;
		}
		
		char[] chars = string.toCharArray();
		
		for(char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validateEmail(String email) {
		String emailRegex = ".+@.+\\..+";
                  
		Pattern pat = Pattern.compile(emailRegex); 
		
		if (email == null) {
			return false; 
		}
		
		return pat.matcher(email).matches(); 
	}
	
	private boolean validateBirthday(String date) {
		String format = "yyyy-MM-dd HH:mm";
		boolean isValid;
		GregorianCalendar now = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		GregorianCalendar dateCalendar = new GregorianCalendar();
		
		try {
			dateCalendar.setTime(sdf.parse(date));
			
			if(dateCalendar.getTime().compareTo(now.getTime()) <= 0) {
				isValid = false;
			}
			else {
				isValid = true;
			}
		} catch (ParseException e) {
			isValid = false;
		}
		
		return isValid;
	}
}
