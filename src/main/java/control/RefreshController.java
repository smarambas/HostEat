package control;

import bean.SessionBean;
import bean.UserBean;
import exceptions.WrongPasswordException;

public class RefreshController {

	public SessionBean refresh(UserBean userBean) throws WrongPasswordException {
		LogInController logInController = new LogInController();
		
		return logInController.logIn(userBean);
	}
	
}
