package standalone_view;

import java.io.IOException;
import java.util.ArrayList;
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
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		EventBean eventBean;
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			eventBean = HostBaseController.getSelectedEvent();
			
			centralVBox.getChildren().addAll(
				addHBox("Date:", eventBean.getDateTime().substring(0, 10)),
				addHBox("Time:", eventBean.getDateTime().substring(11)),
				addHBox("Guests:", eventBean.getActualGuests()),
				addHBox("Region:", GUIController.getSessionBean().getUserBean().getReg()),
				addHBox("Province:", GUIController.getSessionBean().getUserBean().getProv()),
				addHBox("City:", GUIController.getSessionBean().getUserBean().getCity()),
				addHBox("Address:", GUIController.getSessionBean().getUserBean().getAddr())
			);
			
			List<Button> buttonList = new ArrayList<>();
			
			Button openMenuButton = new Button("Open menu");
			buttonList.add(openMenuButton);
			Button deleteEventButton = new Button("Delete event");
			buttonList.add(deleteEventButton);
			
			centralVBox.getChildren().add(addButtons(buttonList));
		}
		else {
			eventBean = GuestBaseController.getSelectedEvent();
			boolean isAccepted = false;
			boolean isPaymentRequired = false;
			
			centralVBox.getChildren().addAll(
					addHBox("Date:", eventBean.getDateTime().substring(0, 10)),
					addHBox("Time:", eventBean.getDateTime().substring(11)),
					addHBox("Guests:", eventBean.getActualGuests()),
					addHBox("State:", eventBean.getGuestStatus())
			);
			
			isPaymentRequired = !(eventBean.getPayStatus().equalsIgnoreCase("NOSET"));
			if(isPaymentRequired) {
				centralVBox.getChildren().add(addHBox("Payment:", eventBean.getPayStatus()));
			}
			
			isAccepted = eventBean.getGuestStatus().equalsIgnoreCase("ACCEPTED");
			if(isAccepted) {
				centralVBox.getChildren().addAll(
					addHBox("Region:", eventBean.getRegionString()),
					addHBox("Province:", eventBean.getProvinceString()),
					addHBox("City:", eventBean.getCityString()),
					addHBox("Address:", eventBean.getAddressString())
				);
			}
			
			isPaymentRequired = !(eventBean.getPayStatus().equalsIgnoreCase("NOSET") || 
					  			  eventBean.getPayStatus().equalsIgnoreCase("PAID"));
			
			List<Button> buttonList = new ArrayList<>();
			
			if(isPaymentRequired && !isAccepted) {
				Button payHostButton = new Button("Pay host");
				buttonList.add(payHostButton);
				Button openMenuButton = new Button("Open menu");
				buttonList.add(openMenuButton);
				Button deleteEventButton = new Button("Delete event");
				buttonList.add(deleteEventButton);
				
				centralVBox.getChildren().add(addButtons(buttonList));
			}
			else if(!isPaymentRequired && !isAccepted) {
				Button openMenuButton = new Button("Open menu");
				buttonList.add(openMenuButton);
				Button deleteEventButton = new Button("Delete event");
				buttonList.add(deleteEventButton);

				centralVBox.getChildren().add(addButtons(buttonList));
			}
			else {
				Button viewLocationButton = new Button("View location");
				buttonList.add(viewLocationButton);
				Button openMenuButton = new Button("Open menu");
				buttonList.add(openMenuButton);
				Button deleteEventButton = new Button("Delete event");
				buttonList.add(deleteEventButton);

				centralVBox.getChildren().add(addButtons(buttonList));
			}
		}
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
	
	private HBox addButtons(List<Button> buttonsList) {
		HBox hBox = new HBox();
		
		for(Button b : buttonsList) {
			hBox.getChildren().add(b);
		}
		
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(20);
		
		return hBox;
	}
}
