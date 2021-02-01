package bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class UserBean {
	
	private String userType;
	private String username;
	private String passw;
	private String name;
	private String surname;
	private String emailAddr;
	private String birthday;
	private String sex;
	private String reg;
	private String prov;
	private String city;
	private String addr;
	private int ratings;
	private int ratingsNum;
	private String guestStatus;
	private String payStatus;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

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
	
	public String getBirthday() {
		return birthday;
	}
	
	public boolean setBirthday(String birthDay) {
		if(validateBirthday(birthDay)) {
			this.birthday = birthDay;
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
	
	public boolean setReg(String reg) {
		if(validateString(reg)) {
			this.reg = reg;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getProv() {
		return prov;
	}
	
	public boolean setProv(String prov) {
		if(validateString(prov)) {
			this.prov = prov;
			return true;
		}
		else {
			return false;
		} 
	}
	
	public String getCity() {
		return city;
	}
	
	public boolean setCity(String city) {
		if(validateString(city)) {
			this.city = city;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getAddr() {
		return addr;
	}
	
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public int getRatings() {
		return ratings;
	}
	
	public void setRatings(int ratings) {
		this.ratings = ratings;
	}
	
	public int getRatingsNum() {
		return ratingsNum;
	}

	public void setRatingsNum(int ratingsNum) {
		this.ratingsNum = ratingsNum;
	}

	public String getGuestStatus() {
		return guestStatus;
	}

	public void setGuestStatus(String guestStatus) {
		this.guestStatus = guestStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	public boolean validateString(String string) {
		if(string == null || string.equals("")) {
			return false;
		}
		
		char[] chars = string.toCharArray();
		
		for(char c : chars) {
			if(!(Character.isLetter(c)) && !(Character.isWhitespace(c))) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validateEmail(String email) {
		String emailRegex = ".+@.+\\..+";
                  
		Pattern pat = Pattern.compile(emailRegex); 
		
		if (email == null) {
			return false; 
		}
		
		return pat.matcher(email).matches(); 
	}
	
	public boolean validateBirthday(String date) {
		String format = "yyyy-MM-dd HH:mm";
		boolean isValid;
		GregorianCalendar now = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		GregorianCalendar dateCalendar = new GregorianCalendar();
		
		try {
			dateCalendar.setTime(sdf.parse(date));
			
			if(dateCalendar.getTime().compareTo(now.getTime()) >= 0) {
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
