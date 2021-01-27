package standalone_view;

import bean.UserBean;
import javafx.event.ActionEvent;
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

public class FavoritesPageController {

	private String appStyle = "NewStyle.css";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
		
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	protected void initialize() {
		for(UserBean ub : GUIController.getSessionBean().getSavedHosts()) {
			Button openButton = new Button("View profile");
		
			HBox hBox = new HBox();
			hBox.getChildren().addAll(
				new Label(ub.getName()),
				new Label(ub.getSurname()),
				new Label(ub.getReg()),
				new Label(ub.getProv())
			);
			hBox.setAlignment(Pos.CENTER);
			
			centralVBox.getChildren().addAll(
				hBox,
				openButton
			);
			
			openButton.setOnAction((ActionEvent event) -> {
				HostProfilePageController.setUserBean(ub);
				
				try {
					Stage stage = (Stage) openButton.getScene().getWindow();
					Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostProfilePage.fxml"));
					Scene scene = new Scene(root, 900, 600);
					scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					Label errorLabel = new Label(errorLabelMsg);
					errorLabel.setId(errorLabelId);
					centralVBox.getChildren().add(errorLabel);
				}
			});
		}
	}
	
}
