package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import bean.UserBean;
import exceptions.DuplicateRecordException;
import model.User;
import model.dao.FavoritesDAO;
import model.dao.UserDAO;
import standalone_view.GUIController;

public class SaveHostController {

	public void saveHost(UserBean userBean, UserBean favUserBean) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		User user = UserDAO.retrieveUserByUsername(userBean.getUsername());
		User favUser = UserDAO.retrieveUserByUsername(favUserBean.getUsername());
		
		FavoritesDAO.saveFavorite(user, favUser);
		
		UserBean tempUserBean = new UserBean();
		
		tempUserBean.setUsername(favUser.getUsername());
		tempUserBean.setName(favUser.getName());
		tempUserBean.setSurname(favUser.getSurname());
		tempUserBean.setBirthday(sdf.format(favUser.getBirthday().getTime()));
		tempUserBean.setSex(favUser.getSex());
		tempUserBean.setEmailAddr(favUser.getEmail());
		tempUserBean.setReg(favUser.getUserRegion());
		tempUserBean.setProv(favUser.getUserProvince());
		tempUserBean.setCity(favUser.getUserCity());
		tempUserBean.setRatings(favUser.getRating());
		
		GUIController.getSessionBean().getSavedHosts().add(tempUserBean);
	}
	
}
