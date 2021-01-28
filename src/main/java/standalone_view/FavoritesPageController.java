package standalone_view;

import java.util.ArrayList;
import bean.UserBean;
import control.GetHostEventsController;
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
		GUIController.getSessionBean().setSearchedList(new ArrayList<>());	//clear list
		
		for(UserBean ub : GUIController.getSessionBean().getSavedHosts()) {
			Button openButton = new Button("View profile");
			Button eventsButton = new Button("View events");
			
			Label locationLabel = new Label(ub.getReg() + " " + "(" + ub.getProv() + ")");
			locationLabel.setId("dataLabel");
		
			HBox hBox = new HBox();
			hBox.getChildren().addAll(
				new Label(ub.getName() + " " + ub.getSurname()),
				locationLabel,
				openButton,
				eventsButton
			);
			hBox.setAlignment(Pos.CENTER);
			hBox.setSpacing(40);
			
			centralVBox.getChildren().add(hBox);
			
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
			
			eventsButton.setOnAction((ActionEvent event) -> {
				HostProfilePageController.setUserBean(ub);
				
				try {
					GetHostEventsController getHostEventsController = new GetHostEventsController();
					GUIController.setSessionBean(getHostEventsController.getHostEvents(ub));
					
					Stage stage = (Stage) eventsButton.getScene().getWindow();
					Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/SearchResultsPage.fxml"));
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
