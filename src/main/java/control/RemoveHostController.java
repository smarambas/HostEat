package control;

import java.io.IOException;
import java.sql.SQLException;
import bean.UserBean;
import exceptions.NoRecordFoundException;
import model.User;
import model.dao.FavoritesDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class RemoveHostController {

	public void removeHost(UserBean userBean, UserBean favUserBean) throws ClassNotFoundException, SQLException, NoRecordFoundException, IOException {
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		User favUser = UserDAO.retrieveUserByUsername(favUserBean.getUsername());
		
		FavoritesDAO.removeFavorite(user, favUser);
		
		GUIController.getSessionBean().removeFromSavedUser(favUser.getUsername());
	}
	
}
