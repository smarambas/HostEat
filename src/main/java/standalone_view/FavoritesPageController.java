package standalone_view;

import java.util.ArrayList;
import bean.UserBean;
import control.GetHostEventsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FavoritesPageController {

	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
		
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	protected void initialize() {
		GUIController.getSessionBean().setSearchedList(new ArrayList<>());	//clear list
		SearchResultsPageController.setSelectedEvent(null);
		
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
					ViewCommons viewCommons = new ViewCommons();
					viewCommons.handleButtonShowStage(openButton, "/standalone_view/HostProfilePage.fxml", 900, 600);
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
					GUIController.setSessionBean(getHostEventsController.getHostEvents(GUIController.getSessionBean() ,ub));
					
					ViewCommons viewCommons = new ViewCommons();
					viewCommons.handleButtonShowStage(eventsButton, "/standalone_view/SearchResultsPage.fxml", 900, 600);
				} catch (Exception e) {
					Label errorLabel = new Label(errorLabelMsg);
					errorLabel.setId(errorLabelId);
					centralVBox.getChildren().add(errorLabel);
				}
			});
		}
	}
	
}
