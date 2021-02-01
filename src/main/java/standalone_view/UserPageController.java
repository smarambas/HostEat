package standalone_view;

import java.io.IOException;
import javafx.event.ActionEvent;
import bean.UserBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPageController {
	
	private String appStyle = "NewStyle.css";

	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	protected void initialize() {
		UserBean userBean = GUIController.getSessionBean().getUserBean();
		
		Button modifyButton = new Button("Modify account");
		Button logoutButton = new Button("Log out");
		
		modifyButton.setOnAction((ActionEvent event) -> {
			try {
				Stage stage = (Stage) modifyButton.getScene().getWindow();
				Parent root;
								
				if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
					root = FXMLLoader.load(getClass().getResource("/standalone_view/HostModifyAccountPage.fxml"));
				}
				else {
					root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestModifyAccountPage.fxml"));
				}
								
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch(IOException ioe) {
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
		
		logoutButton.setOnAction((ActionEvent event) -> {
			try {
				GUIController.setSessionBean(null);
				Stage stage = (Stage) logoutButton.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch(IOException ioe) {
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
		
		centralVBox.getChildren().addAll(
			addHBox("Name:", userBean.getName()),
			addHBox("Surname:", userBean.getSurname()),
			addHBox("Birthday:", userBean.getBirthday().substring(0, 10)),
			addHBox("Sex:", userBean.getSex()),
			addHBox("Email:", userBean.getEmailAddr()),
			addHBox("Region:", userBean.getReg()),
			addHBox("Province", userBean.getProv()),
			addHBox("City:", userBean.getCity()),
			addHBox("Address:", userBean.getAddr()),
			addHBox("Rating:", String.valueOf((double) userBean.getRatings() / userBean.getRatingsNum()))
		);
		
		bottomHBox.getChildren().addAll(modifyButton, logoutButton);
	}
	
	private HBox addHBox(String s, String data) {
		HBox hBox = new HBox();
		Label label = new Label(s);
		label.setId(descriptionString);
		Label dataLabel = new Label(data);
		dataLabel.setId(dataString);
		hBox.getChildren().addAll(label, dataLabel);
		hBox.setAlignment(Pos.CENTER);
		return hBox;
	}
	
}
