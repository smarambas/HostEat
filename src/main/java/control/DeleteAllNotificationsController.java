package control;

import java.io.IOException;
import java.sql.SQLException;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.User;
import model.dao.NotificationDAO;
import model.dao.UserDAO;

public class DeleteAllNotificationsController {

	public void deleteAllNotifications(UserBean userBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException {
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		NotificationDAO.deleteAllNotifications(user);
	}
}
