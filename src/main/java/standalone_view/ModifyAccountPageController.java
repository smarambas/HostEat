package standalone_view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
		
		Scene scene = new Scene(root, 1080, 720);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		Button submitButton = new Button("Submit");
		
		List<TextField> list = new ArrayList<>();
		
		TextField emailField = new TextField();
		emailField.setPromptText("email");
		list.add(emailField);
		TextField passwField = new TextField();
		passwField.setPromptText("password");
		list.add(passwField);
		TextField regionField = new TextField();
		regionField.setPromptText("region");
		list.add(regionField);
		TextField provinceField = new TextField();
		provinceField.setPromptText("province");
		list.add(provinceField);
		TextField cityField = new TextField();
		cityField.setPromptText("city");
		list.add(cityField);
		TextField addressField = new TextField();
		addressField.setPromptText("address");
		list.add(addressField);
		
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
				GUIController.setSessionBean(modifyAccountController.modifyAccount(userBean));
				
				if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
					root = FXMLLoader.load(getClass().getResource("/standalone_view/HostUserPage.fxml"));
				}
				else {
					root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestUserPage.fxml"));
				}
				
				Scene scene = new Scene(root, 1080, 720);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Label errorLabel = new Label("The changes have not been made, please try again.");
				errorLabel.setId("errorLabel");
				centralVBox.getChildren().add(errorLabel);
			}
		});
		
		centralVBox.getChildren().addAll(
			new Separator(),
			addHBox("New email:", list.get(0)),
			addHBox("New password:", list.get(1)),
			addHBox("New region:", list.get(2)),
			addHBox("New province:", list.get(3)),
			addHBox("New city:", list.get(4)),
			addHBox("New address:", list.get(5)),
			submitButton
		);
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
