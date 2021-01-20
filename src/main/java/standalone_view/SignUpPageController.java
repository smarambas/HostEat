package standalone_view;

import java.io.IOException;
import bean.UserBean;
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
	
	public void handleUserRadioButtonAction(ActionEvent event) {
		if(event.getSource() == btnRadioGuest) {
			userType = "GUEST";
		}
		else if(event.getSource() == btnRadioHost) {
			userType = "HOST";
		}
	}
	
	public void handleSignUpScreenButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		Boolean userSelection = false;
		UserBean userBean;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
		}
		else if(event.getSource() == btnSubmit) {
			if(userType == null) {
				userSelection = true;
				root = FXMLLoader.load(getClass().getResource("/standalone_view/UserSelectionError.fxml"));
			}
			else if(userType.equals("GUEST")) {
				stage = (Stage) btnSubmit.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
			}
			else if(userType.equals("HOST")) {
				stage = (Stage) btnSubmit.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
			}
		}
		
		if(userSelection.equals(Boolean.FALSE)) {
			Scene scene = new Scene(root, 700, 500);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		else {
			Scene scene = new Scene(root, 200, 100);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
	}
	
}
