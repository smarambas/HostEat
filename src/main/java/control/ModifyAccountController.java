package control;

import java.io.IOException;
import java.sql.SQLException;
import bean.SessionBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.User;
import model.dao.UserDAO;

public class ModifyAccountController {

	public SessionBean modifyAccount(SessionBean session, UserBean userBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException {
		SessionBean sessionBean = session;
		String username = sessionBean.getUserBean().getUsername();
		User user = UserDAO.retrieveUserByUsername(username);
				
		if(userBean.getEmailAddr() != null && userBean.getEmailAddr().length() > 0) {
			UserDAO.updateUserEmail(user, userBean.getEmailAddr());
			sessionBean.getUserBean().setEmailAddr(userBean.getEmailAddr());
		}
		if(userBean.getPassw() != null && userBean.getPassw().length() > 0) {
			UserDAO.updateUserPassword(user, userBean.getPassw());
			sessionBean.getUserBean().setPassw(userBean.getPassw());
		}
		if(userBean.getReg() != null && userBean.getReg().length() > 0) {
			UserDAO.updateUserRegion(user, userBean.getReg());
			sessionBean.getUserBean().setReg(userBean.getReg());
		}
		if(userBean.getProv() != null && userBean.getProv().length() > 0) {
			UserDAO.updateUserProvince(user, userBean.getProv());
			sessionBean.getUserBean().setProv(userBean.getProv());
		}
		if(userBean.getCity() != null && userBean.getCity().length() > 0) {
			UserDAO.updateUserCity(user, userBean.getCity());
			sessionBean.getUserBean().setCity(userBean.getCity());
		}
		if(userBean.getAddr() != null && userBean.getAddr().length() > 0) {
			UserDAO.updateUserAddress(user, userBean.getAddr());
			sessionBean.getUserBean().setAddr(userBean.getAddr());
		}
				
		return sessionBean;
	}
	
}
