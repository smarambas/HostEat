package standalone_view;

import java.io.IOException;
import java.util.List;
import bean.EventBean;
import control.RefreshController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HostBaseController {

	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	private static EventBean selectedEvent;
	
	@FXML private Button btnRefresh;
	@FXML private Button btnEvent;
	
	@FXML private VBox centralVBox;
	
	@FXML
	private void handleHostBaseNewEventButtonAction(ActionEvent event) throws IOException {
		ViewCommons viewCommons = new ViewCommons();
		viewCommons.handleButtonShowStage(btnEvent, "/standalone_view/NewEventPage.fxml", 900, 600);
	}
	
	@FXML
	private void handleRefreshButtonAction(ActionEvent event) throws IOException {
		try {
			RefreshController refreshController = new RefreshController();
			GUIController.setSessionBean(refreshController.refresh(GUIController.getSessionBean().getUserBean()));
			
			ViewCommons viewCommons = new ViewCommons();
			viewCommons.handleButtonShowStage(btnRefresh, "/standalone_view/HostBase.fxml", 900, 600);
		} catch(Exception e) {
			Label errorLabel = new Label("Ops, something went wrong, please try again");
			errorLabel.setId("errorLabel");
			centralVBox.getChildren().add(errorLabel);
		}
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
					setSelectedEvent(e);
					try {
						ViewCommons viewCommons = new ViewCommons();
						viewCommons.handleButtonShowStage(openButton, "/standalone_view/HostEventPage.fxml", 900, 600);
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
