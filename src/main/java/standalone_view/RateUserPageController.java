package standalone_view;

import java.io.IOException;
import bean.UserBean;
import control.RateUserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RateUserPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "You must select a vote first";
	private String errorLabelId = "errorLabel";
	
	private int vote = 0;
		
	@FXML private Button btnBack;
	@FXML private Button submitButton;
	
	@FXML private ToggleGroup radioGroup;
	@FXML private RadioButton oneButton;
	@FXML private RadioButton twoButton;
	@FXML private RadioButton threeButton;
	@FXML private RadioButton fourButton;
	@FXML private RadioButton fiveButton;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = null;
		
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			root = FXMLLoader.load(getClass().getResource("/standalone_view/RateGuestPage.fxml"));
		}
		else {
			root = FXMLLoader.load(getClass().getResource("/standalone_view/RateHostPage.fxml"));
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleRadioButtonAction(ActionEvent event) {
		if(event.getSource() == oneButton) {
			vote = 1;
		}
		else if(event.getSource() == twoButton) {
			vote = 2;
		}
		else if(event.getSource() == threeButton) {
			vote = 3;
		}
		else if(event.getSource() == fourButton) {
			vote = 4;
		}
		else if(event.getSource() == fiveButton) {
			vote = 5;
		}
	}
	
	@FXML
	private void handleSubmitButtonAction(ActionEvent event) throws IOException {
		UserBean user;
		
		if(vote == 0) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
		else {
			Stage stage = (Stage) submitButton.getScene().getWindow();
			Parent root = null;
			
			if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
				user = JoinedGuestsListPageController.getSelectedGuest();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/RateGuestPage.fxml"));
			}
			else {
				user = new UserBean();
				user.setUsername(GuestBaseController.getSelectedEvent().getEventOwner());
				root = FXMLLoader.load(getClass().getResource("/standalone_view/RateHostPage.fxml"));
			}
			
			try {
				RateUserController rateUserController = new RateUserController();
				rateUserController.rateUser(user, vote);
			} catch(Exception e) {
				e.printStackTrace();
				
				Label errorLabel = new Label("Ops, something went wrong");
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
			
			Scene scene = new Scene(root, 900, 600);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
	}
	
}
