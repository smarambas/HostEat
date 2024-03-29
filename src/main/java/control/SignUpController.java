package control;

import bean.SessionBean;
import bean.UserBean;
import model.User;
import model.UserFactory;
import model.dao.UserDAO;

public class SignUpController {

	public SessionBean signUp(UserBean userBean) {
		SessionBean sessionBean = new SessionBean();
		UserFactory userFactory = new UserFactory();
		
		User user = userFactory.createUserFromBean(userBean);
		if(user == null) {
			sessionBean = null;
		}
		else {
			try {
				UserDAO.saveUser(user);
				sessionBean.setUserBean(userBean);
			} catch (Exception e) {
				sessionBean = null;
			}
		}
		
		return sessionBean;
	}
	
}
