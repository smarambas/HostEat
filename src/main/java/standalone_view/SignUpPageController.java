package standalone_view;

import java.io.IOException;
import bean.SessionBean;
import bean.UserBean;
import control.SignUpController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SignUpPageController {
	
	private String appStyle = "NewStyle.css";
	private String userType = null;
	
	@FXML private Button btnBack;
	@FXML private Button btnSubmit;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private TextField nameField;
	@FXML private TextField surnameField;
	@FXML private TextField emailField;
	@FXML private DatePicker birthdayField;
	@FXML private ChoiceBox<String> sexChoice;
	@FXML private TextField regionField;
	@FXML private TextField provinceField;
	@FXML private TextField cityField;
	@FXML private TextField addressField;
	@FXML private RadioButton btnRadioGuest;
	@FXML private RadioButton btnRadioHost;
	@FXML private ToggleGroup radioGroup;
	
	@FXML
	public void handleUserRadioButtonAction(ActionEvent event) {
		if(event.getSource() == btnRadioGuest) {
			userType = "GUEST";
		}
		else if(event.getSource() == btnRadioHost) {
			userType = "HOST";
		}
	}
	
	@FXML
	public void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void handleSignUpScreenButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		boolean inputError = false;
		boolean checkInput = false;
		UserBean userBean = new UserBean();
		SignUpController signUpController = new SignUpController();
		SessionBean sessionBean = new SessionBean();
		
		if(userBean.setName(nameField.getText()) &&
		   userBean.setSurname(surnameField.getText()) &&
		   userBean.setEmailAddr(emailField.getText()) &&
		   userBean.setBirthDay(birthdayField.getValue().toString() + " 00:00") &&
		   userBean.setReg(regionField.getText()) &&
		   userBean.setProv(provinceField.getText()) &&
		   userBean.setCity(cityField.getText())) {
			
			checkInput = true;
			userBean.setUsername(usernameField.getText());
			userBean.setPassw(passwordField.getText());
			userBean.setSex(sexChoice.getValue());
			userBean.setAddr(addressField.getText());		
		}
		
		if(checkInput) {
			if(userType == null) {
				inputError = true;
				root = FXMLLoader.load(getClass().getResource("/standalone_view/UserSelectionError.fxml"));
			}
			else if(userType.equals("GUEST")) {
				userBean.setUserType(userType);
				sessionBean = signUpController.signUp(userBean);
				stage = (Stage) btnSubmit.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
			}
			else if(userType.equals("HOST")) {
				userBean.setUserType(userType);
				sessionBean = signUpController.signUp(userBean);
				stage = (Stage) btnSubmit.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
			}
		}
		else {
			inputError = true;
			root = FXMLLoader.load(getClass().getResource("/standalone_view/SignUpInputError.fxml"));
		}
		
		if(!inputError) {
			if(sessionBean == null) {
				GUIController.setSessionBean(null);
				stage = (Stage) btnSubmit.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/SignUpError.fxml"));
				Scene scene = new Scene(root, 300, 300);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			}
			else {
				GUIController.setSessionBean(sessionBean);
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			}
		}
		else {
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
	}
	
}
