package standalone_view;

import java.io.IOException;
import bean.SessionBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GUIController {

	private String appStyle = "NewStyle.css";
	
	private static SessionBean sessionBean;
	
	@FXML private Button btnSignUp;
	@FXML private Button btnLogIn;
	@FXML private Button btnOk;
	
	@FXML private HeaderController headerController;
	@FXML private LogInPageController logInPageController;
	@FXML private SignUpPageController signUpPageController;
	@FXML private HostBaseController hostBaseController;
	@FXML private NewEventPageController newEventPageController;
	@FXML private GuestBaseController guestBaseController;
	@FXML private SearchEventPageController searchEventController;
	
	public static SessionBean getSessionBean() {
		return sessionBean;
	}

	public static void setSessionBean(SessionBean sessionBean) {
		GUIController.sessionBean = sessionBean;
	}

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
				
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleOkButtonAction(ActionEvent event) {
		Stage stage = new Stage();
		
		if(event.getSource() == btnOk) {
			stage = (Stage) btnOk.getScene().getWindow();
		}
		
		stage.close();
	}
	
}
