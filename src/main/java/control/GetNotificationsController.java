package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import bean.NotificationBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.Notification;
import model.User;
import model.dao.NotificationDAO;
import model.dao.UserDAO;

public class GetNotificationsController {

	public List<NotificationBean> getNotifications(UserBean userBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		List<NotificationBean> result = new ArrayList<>();
		
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		
		List<Notification> notifications = NotificationDAO.retrieveNotificationsByUser(user);
		
		for(Notification n : notifications) {
			NotificationBean notificationBean = new NotificationBean(userBean, n.getText(), n.getType().toString().toUpperCase(), sdf.format(n.getDate().getTime()));
			result.add(notificationBean);
		}
		
		return result;
	}
}
