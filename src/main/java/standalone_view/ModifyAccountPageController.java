package standalone_view;

import java.io.IOException;
import bean.UserBean;
import control.ModifyAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModifyAccountPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	
	@FXML private Button btnBack;
	@FXML private VBox centralVBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostUserPage.fxml"));
			}
			else if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("GUEST")) {
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestUserPage.fxml"));
			}
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		Button submitButton = new Button("Submit");
				
		TextField emailField = new TextField();
		emailField.setPromptText("email");
		TextField passwField = new TextField();
		passwField.setPromptText("password");
		TextField regionField = new TextField();
		regionField.setPromptText("region");
		TextField provinceField = new TextField();
		provinceField.setPromptText("province");
		TextField cityField = new TextField();
		cityField.setPromptText("city");
		TextField addressField = new TextField();
		addressField.setPromptText("address");
		
		centralVBox.getChildren().addAll(
			new Separator(),
			addHBox("New email:", emailField),
			addHBox("New password:", passwField),
			addHBox("New region:", regionField),
			addHBox("New province:", provinceField),
			addHBox("New city:", cityField),
			addHBox("New address:", addressField),
			submitButton
		);
		
		submitButton.setOnAction((ActionEvent event) -> {
			UserBean userBean = new UserBean();
			Parent root = null;
			Stage stage = (Stage) submitButton.getScene().getWindow();
			
			userBean.setEmailAddr(emailField.getText());
			userBean.setPassw(passwField.getText());
			userBean.setReg(regionField.getText());
			userBean.setProv(provinceField.getText());
			userBean.setCity(cityField.getText());
			userBean.setAddr(addressField.getText());
			
			ModifyAccountController modifyAccountController = new ModifyAccountController();
			try {
				GUIController.setSessionBean(modifyAccountController.modifyAccount(GUIController.getSessionBean(), userBean));
				
				if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
					root = FXMLLoader.load(getClass().getResource("/standalone_view/HostUserPage.fxml"));
				}
				else {
					root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestUserPage.fxml"));
				}
				
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Label errorLabel = new Label("The changes have not been made, please try again.");
				errorLabel.setId("errorLabel");
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}
	
	private HBox addHBox(String s, TextField field) {
		HBox hBox = new HBox();
		
		Label label = new Label(s);
		label.setId(descriptionString);
		
		hBox.getChildren().addAll(label, field);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
}
