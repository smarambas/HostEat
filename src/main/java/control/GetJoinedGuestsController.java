package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import bean.EventBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.Event;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;

public class GetJoinedGuestsController {

	public List<UserBean> getJoinedGuests(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		List<UserBean> result = new ArrayList<>();
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
		Event event = EventDAO.retrieveEventByUsernameDateTime(host, dateTime);
		
		List<User> guestsList = JoinedEventDAO.retrieveJoinedGuests(host, event);
		
		for(User u : guestsList) {
			UserBean guestBean = new UserBean();
			
			guestBean.setUsername(u.getUsername());
			guestBean.setName(u.getName());
			guestBean.setSurname(u.getSurname());
			guestBean.setEmailAddr(u.getEmail());
			guestBean.setBirthday(sdf.format(u.getBirthday().getTime()));
			guestBean.setReg(u.getUserRegion());
			guestBean.setProv(u.getUserProvince());
			guestBean.setCity(u.getUserCity());
			guestBean.setRatings(u.getRating());
			guestBean.setRatingsNum(u.getRatingsNum());
			guestBean.setSex(u.getSex());
			guestBean.setGuestStatus(u.getGuestStatus().toString().toUpperCase());
			guestBean.setPayStatus(u.getPayStatus().toString().toUpperCase());
			
			result.add(guestBean);
		}
		
		return result;
	}
	
}
