package control;

import java.io.IOException;
import java.sql.SQLException;
import bean.SessionBean;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.User;
import model.dao.FavoritesDAO;
import model.dao.UserDAO;

public class RemoveHostController {

	public SessionBean removeHost(SessionBean session, UserBean userBean, UserBean favUserBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException {
		SessionBean sessionBean = session;
		
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		User favUser = UserDAO.retrieveUserByUsername(favUserBean.getUsername());
		
		FavoritesDAO.removeFavorite(user, favUser);
		
		sessionBean.removeFromSavedUser(favUser.getUsername());
		
		return sessionBean;
	}
	
}
