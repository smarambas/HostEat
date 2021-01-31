package control;

import java.io.IOException;
import java.sql.SQLException;

import bean.NotificationBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.Notification;
import model.NotificationType;
import model.User;
import model.dao.NotificationDAO;
import model.dao.UserDAO;

public class DeleteNotificationController {

	public void deleteNotification(UserBean userBean, NotificationBean notificationBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException {
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		Notification notification = new Notification(user, notificationBean.getText(), NotificationType.valueOf(notificationBean.getType().toUpperCase()));
		
		NotificationDAO.deleteNotification(user, notification);
	}
}
