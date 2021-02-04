package control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import bean.SessionBean;
import bean.UserBean;
import exceptions.DuplicateRecordException;
import model.User;
import model.dao.FavoritesDAO;
import model.dao.UserDAO;

public class SaveHostController {

	public SessionBean saveHost(SessionBean session, UserBean userBean, UserBean favUserBean) throws ClassNotFoundException, SQLException, IOException, DuplicateRecordException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		SessionBean sessionBean = session;
		
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
		
		sessionBean.getSavedHosts().add(tempUserBean);
		
		return sessionBean;
	}
	
}
