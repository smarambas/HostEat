package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import bean.UserBean;
import model.User;
import model.dao.UserDAO;

public class GetUserController {

	public UserBean getUser(UserBean userBean) throws ClassNotFoundException, SQLException, IOException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		UserBean result = new UserBean();
		User user;
		
		user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		result.setUsername(user.getUsername());
		result.setName(user.getName());
		result.setSurname(user.getSurname());
		result.setEmailAddr(user.getEmail());
		result.setBirthday(sdf.format(user.getBirthday().getTime()));
		result.setReg(user.getUserRegion());
		result.setProv(user.getUserProvince());
		result.setCity(user.getUserCity());
		result.setRatings(user.getRating());
		result.setRatingsNum(user.getRatingsNum());
		result.setSex(user.getSex());
		
		return result;
	}
}
