package standalone_view;

import java.io.IOException;
import bean.UserBean;
import control.LogInController;
import exceptions.WrongPasswordException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInPageController {
	
	private String appStyle = "NewStyle.css";
	
	@FXML private Button btnBack;
	@FXML private Button btnSubmit;
	@FXML private TextField usernameTextField;
	@FXML private PasswordField passwordField;

	public void handleLogInScreenButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		UserBean userBean;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
		}
		else if(event.getSource() == btnSubmit) {
			String username = usernameTextField.getText();
			String password = passwordField.getText();
			
			userBean = new UserBean();
			userBean.setUsername(username);
			userBean.setPassw(password);
			
			LogInController logInController = new LogInController();
			try {
				GUIController.setSessionBean(logInController.logIn(userBean));
			} catch (WrongPasswordException e) {
				GUIController.setSessionBean(null);
			}
			
			if(GUIController.getSessionBean() == null) {
				root = FXMLLoader.load(getClass().getResource("/standalone_view/UserCredentialsError.fxml"));
				Scene scene = new Scene(root, 350, 100);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			}
			else {
				if(GUIController.getSessionBean().getUserType().equals("HOST")) {
					stage = (Stage) btnSubmit.getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
				}
				else if(GUIController.getSessionBean().getUserType().equals("GUEST")) {
					stage = (Stage) btnSubmit.getScene().getWindow();
					root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
				}
			}
		}
		
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
