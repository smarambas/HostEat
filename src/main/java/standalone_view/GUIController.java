package standalone_view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class GUIController {

	private String userType = null;
	private String appStyle = "NewStyle.css";
	
	@FXML private Button btnSignUp;
	@FXML private Button btnLogIn;
	@FXML private Button btnBack;
	@FXML private Button btnSubmit;
	@FXML private RadioButton btnRadioHost;
	@FXML private RadioButton btnRadioGuest;
	@FXML private ToggleGroup radioGroup;
	@FXML private Button btnOk;
	
	@FXML private HeaderController headerController;
	
	
	@FXML
	private void handleFirstScreenButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnSignUp) {
			stage = (Stage) btnSignUp.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/SignUpScreen.fxml"));
		}
		else if(event.getSource() == btnLogIn) {
			stage = (Stage) btnLogIn.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/LogInScreen.fxml"));
		}
				
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleLogInScreenButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
		}
		else if(event.getSource() == btnSubmit) {
			stage = (Stage) btnSubmit.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/LogInScreen.fxml"));
		}
		
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleSignUpScreenButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		Boolean userSelection = false;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
		}
		else if(event.getSource() == btnSubmit) {
			if(userType == null) {
				userSelection = true;
				root = FXMLLoader.load(getClass().getResource("/standalone_view/UserSelectionError.fxml"));
			}
			else if(userType.equals("Guest")) {
				stage = (Stage) btnSubmit.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
			}
			else if(userType.equals("Host")) {
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
	
	@FXML
	private void handleUserRadioButtonAction(ActionEvent event) {
		if(event.getSource() == btnRadioGuest) {
			userType = "Guest";
		}
		else if(event.getSource() == btnRadioHost) {
			userType = "Host";
		}
	}
	
	@FXML
	private void handleOkButtonAction(ActionEvent event) {
		Stage stage = new Stage();
		
		if(event.getSource() == btnOk) {
			stage = (Stage) btnOk.getScene().getWindow();
		}
		
		stage.close();
	}
	
	@FXML
	private void handleHomepageButtonAction() throws IOException {
		headerController.handleHomepageButtonAction(null);
	}
}
