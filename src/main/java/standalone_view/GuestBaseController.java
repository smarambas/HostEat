package standalone_view;

import java.io.IOException;
import java.util.List;

import bean.EventBean;
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

public class GuestBaseController {

	private String appStyle = "NewStyle.css";
	
	@FXML private Button btnEvent;
	@FXML private VBox centralVBox;
	
	public void handleGuestBaseSearchEventButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnEvent) {
			stage = (Stage) btnEvent.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/SearchEventPage.fxml"));
		}
		
		Scene scene = new Scene(root, 700, 500);
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
			Label newLabel = new Label("Events joined");
			newLabel.setId("titleLabel");
			centralVBox.getChildren().add(newLabel);
			
			for(EventBean e : eventBeanList) {
				HBox hbox = new HBox();
								
				Label dateLabel = new Label(e.getDateTime().substring(0, 10));
				Label timeLabel = new Label(e.getDateTime().substring(11));
				Label guestsLabel = new Label("Guests:  " + e.getActualGuests());
				Label statusLabel = new Label(e.getGuestStatus());
				Button openButton = new Button("Open event");
				
				if(e.getBill() > 0) {
					Label paymentLabel = new Label(e.getPayStatus());
					hbox.getChildren().addAll(dateLabel, timeLabel, guestsLabel, statusLabel, paymentLabel, openButton);
				}
				else {
					hbox.getChildren().addAll(dateLabel, timeLabel, guestsLabel, statusLabel, openButton);
				}
				
				hbox.setAlignment(Pos.CENTER);
				
				Separator separator = new Separator();
				
				centralVBox.getChildren().addAll(hbox, separator);
			}
		}
	}
}
