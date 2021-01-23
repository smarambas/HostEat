package standalone_view;

import java.io.IOException;

import bean.EventBean;
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

public class EventPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	
	@FXML private Button btnBack;
	@FXML private VBox centralVBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
			}
			else if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("GUEST")) {
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
			}
		}
		
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		EventBean eventBean;
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			eventBean = HostBaseController.getSelectedEvent();
			
			HBox hBox = new HBox();
			Label label = new Label("Date:");
			label.setId(descriptionString);
			Label dataLabel = new Label(eventBean.getDateTime().substring(0, 10));
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Time:");
			label.setId(descriptionString);
			dataLabel = new Label(eventBean.getDateTime().substring(11));
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Guests:");
			label.setId(descriptionString);
			dataLabel = new Label(eventBean.getActualGuests());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Region:");
			label.setId(descriptionString);
			dataLabel = new Label(GUIController.getSessionBean().getUserBean().getReg());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Province:");
			label.setId(descriptionString);
			dataLabel = new Label(GUIController.getSessionBean().getUserBean().getProv());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("City:");
			label.setId(descriptionString);
			dataLabel = new Label(GUIController.getSessionBean().getUserBean().getCity());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Address:");
			label.setId(descriptionString);
			dataLabel = new Label(GUIController.getSessionBean().getUserBean().getAddr());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			Button openMenuButton = new Button("Open menu");
			Button deleteEventButton = new Button("Delete event");
			hBox.getChildren().addAll(openMenuButton, deleteEventButton);
			hBox.setAlignment(Pos.CENTER);
			hBox.setSpacing(20);
			centralVBox.getChildren().add(hBox);
		}
		else {
			eventBean = GuestBaseController.getSelectedEvent();
			
			HBox hBox = new HBox();
			Label label = new Label("Date:");
			label.setId(descriptionString);
			Label dataLabel = new Label(eventBean.getDateTime().substring(0, 10));
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Time:");
			label.setId(descriptionString);
			dataLabel = new Label(eventBean.getDateTime().substring(11));
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Guests:");
			label.setId(descriptionString);
			dataLabel = new Label(eventBean.getActualGuests());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("State:");
			label.setId(descriptionString);
			dataLabel = new Label(eventBean.getGuestStatus());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			label = new Label("Payment:");
			label.setId(descriptionString);
			dataLabel = new Label(eventBean.getPayStatus());
			dataLabel.setId(dataString);
			hBox.getChildren().addAll(label, dataLabel);
			hBox.setAlignment(Pos.CENTER);
			centralVBox.getChildren().add(hBox);
			
			hBox = new HBox();
			Button payHostButton = new Button("Pay host");
			Button openMenuButton = new Button("Open menu");
			Button deleteEventButton = new Button("Delete event");
			hBox.getChildren().addAll(payHostButton, openMenuButton, deleteEventButton);
			hBox.setAlignment(Pos.CENTER);
			hBox.setSpacing(20);
			centralVBox.getChildren().add(hBox);
		}
		
	}
}
