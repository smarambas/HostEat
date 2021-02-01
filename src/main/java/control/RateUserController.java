package control;

import java.io.IOException;
import java.sql.SQLException;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.User;
import model.dao.UserDAO;

public class RateUserController {

	public void rateUser(UserBean userBean, int rating) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException {
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		UserDAO.updateUserRatings(user, rating, user.getRatingsNum() + 1);
	}
}
