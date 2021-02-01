package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.UserBean;

public class UserFactory {
	
	public User createUserFromBean(UserBean userBean) {
		
		String format = "yyyy-MM-dd HH:mm";
		
		UserType userType = UserType.valueOf(userBean.getUserType().toUpperCase());
		
		User newUser = new User(userType, 
								userBean.getUsername(),
								userBean.getPassw(), 
								userBean.getName(), 
								userBean.getSurname(), 
								userBean.getEmailAddr());
		
		newUser.setSex(userBean.getSex());
		newUser.setUserRegion(userBean.getReg());
		newUser.setUserProvince(userBean.getProv());
		newUser.setUserCity(userBean.getCity());
		newUser.setAddress(userBean.getAddr());
		newUser.setRating(0);
		newUser.setRatingsNum(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		GregorianCalendar date = new GregorianCalendar();
		try {
			date.setTime(sdf.parse(userBean.getBirthday()));
			newUser.setBirthday(date);
		} catch(ParseException e) {
			newUser = null;
		}
		
		return newUser;
	}
}
