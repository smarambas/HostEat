package standalone_view;

import java.io.IOException;
import java.util.List;
import bean.EventBean;
import control.RefreshController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HostBaseController {

	private String appStyle = "NewStyle.css";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	@FXML private Button btnRefresh;
	@FXML private Button btnEvent;
	
	@FXML private VBox centralVBox;
	
	private static EventBean selectedEvent;
	
	@FXML
	private void handleHostBaseNewEventButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnEvent) {
			stage = (Stage) btnEvent.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/NewEventPage.fxml"));
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleRefreshButtonAction(ActionEvent event) throws IOException {
		try {
			RefreshController refreshController = new RefreshController();
			GUIController.setSessionBean(refreshController.refresh(GUIController.getSessionBean().getUserBean()));
		} catch(Exception e) {
			Label errorLabel = new Label("Ops, something went wrong, please try again");
			errorLabel.setId("errorLabel");
			centralVBox.getChildren().add(errorLabel);
		}
		
		Stage stage = (Stage) btnRefresh.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		/*
		 * Print the list of created events, if it's not empty
		 */
		List<EventBean> eventBeanList = GUIController.getSessionBean().getEventBeanList();
		
		if(eventBeanList != null && !(eventBeanList.isEmpty())) {
			Label newLabel = new Label("Events created");
			newLabel.setId("titleLabel");
			centralVBox.getChildren().add(newLabel);
			
			for(EventBean e : eventBeanList) {
				HBox hbox = new HBox();
												
				Label dateLabel = new Label(e.getDateTime().substring(0, 10));
				Label timeLabel = new Label(e.getDateTime().substring(11));
				Label guestsLabel = new Label("Guests:  " + e.getActualGuests());
				Button openButton = new Button("Open event");
								
				openButton.setOnAction((ActionEvent event) -> {
					Stage stage = (Stage) btnEvent.getScene().getWindow();
					setSelectedEvent(e);
					try {
						Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostEventPage.fxml"));
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
				
				hbox.getChildren().addAll(dateLabel, timeLabel, guestsLabel, openButton);
				hbox.setAlignment(Pos.CENTER);
				
				Separator separator = new Separator();
				
				centralVBox.getChildren().addAll(hbox, separator);
			}
		}
	}

	public static EventBean getSelectedEvent() {
		return selectedEvent;
	}

	public static void setSelectedEvent(EventBean selectedEvent) {
		HostBaseController.selectedEvent = selectedEvent;
	}

}
