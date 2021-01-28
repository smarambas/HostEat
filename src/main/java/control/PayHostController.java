package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.SessionBean;
import exceptions.NoRecordFoundException;
import model.Event;
import model.PaymentStatus;
import model.User;
import model.dao.EventDAO;
import model.dao.JoinedEventDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class PayHostController {

	public SessionBean payHost(EventBean eventBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException, ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = GUIController.getSessionBean();
		String username = sessionBean.getUserBean().getUsername();
		User user = UserDAO.retrieveUserByUsername(username);
		
		User host = UserDAO.retrieveUserByUsername(eventBean.getEventOwner());
		
		GregorianCalendar dateTime = new GregorianCalendar();
		dateTime.setTime(sdf.parse(eventBean.getDateTime()));
		
		Event event = EventDAO.retrieveEventByUsernameDateTime(host, dateTime);
		
		event.setPayStatus(PaymentStatus.PAID);
		
		JoinedEventDAO.updateJoinedEventPaymentStatus(user, event);
		
		for(int i = 0; i < sessionBean.getEventBeanList().size(); i++) {
			if(sessionBean.getEventBeanList().get(i).getDateTime().equals(eventBean.getDateTime())) {
				sessionBean.getEventBeanList().get(i).setPayStatus(PaymentStatus.PAID.toString().toUpperCase());
				break;
			}
		}
		
		return sessionBean;
	}
	
}
