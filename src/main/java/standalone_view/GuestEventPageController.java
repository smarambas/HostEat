package standalone_view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bean.EventBean;
import control.DeleteJoinedEventController;
import control.PayHostController;
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

public class GuestEventPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	private String openString = "Open menu";
	private String removeString = "Remove event";
	
	private EventBean eventBean;
	
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {		
		eventBean = GuestBaseController.getSelectedEvent();
		boolean isAccepted = false;
		boolean isPaymentRequired = false;
				
		centralVBox.getChildren().addAll(
				addHBox("Event owner:", eventBean.getEventOwner()),
				addHBox("Date:", eventBean.getDateTime().substring(0, 10)),
				addHBox("Time:", eventBean.getDateTime().substring(11)),
				addHBox("Guests:", eventBean.getActualGuests()),
				addHBox("Region:", eventBean.getRegionString()),
				addHBox("Province:", eventBean.getProvinceString()),
				addHBox("City:", eventBean.getCityString())
		);
		
		isAccepted = eventBean.getGuestStatus().equalsIgnoreCase("ACCEPTED");
		if(isAccepted) {
			centralVBox.getChildren().add(
				addHBox("Address:", eventBean.getAddressString())
			);
		}
		
		centralVBox.getChildren().add(addHBox("State:", eventBean.getGuestStatus()));
		
		isPaymentRequired = !(eventBean.getPayStatus().equalsIgnoreCase("NOSET"));
		if(isPaymentRequired) {
			centralVBox.getChildren().addAll(
				addHBox("Payment:", eventBean.getPayStatus()),
				addHBox("Bill:", String.valueOf(eventBean.getBill()))
			);
		}
		
		isPaymentRequired = !(eventBean.getPayStatus().equalsIgnoreCase("NOSET") || 
				  			  eventBean.getPayStatus().equalsIgnoreCase("PAID"));
		
		List<Button> buttonList = new ArrayList<>();
		
		Button payHostButton;
		Button openMenuButton;
		Button deleteEventButton;
		Button viewLocationButton;
		
		if(isPaymentRequired && !isAccepted) {
			payHostButton = new Button("Pay host");
			buttonList.add(payHostButton);
			
			openMenuButton = new Button(openString);
			buttonList.add(openMenuButton);
			
			deleteEventButton = new Button(removeString);
			buttonList.add(deleteEventButton);
			
			bottomHBox.getChildren().add(addButtons(buttonList));
			
			payButtonSetAction(payHostButton);
		}
		else if(!isPaymentRequired && !isAccepted) {
			openMenuButton = new Button(openString);
			buttonList.add(openMenuButton);
			
			deleteEventButton = new Button(removeString);
			buttonList.add(deleteEventButton);

			bottomHBox.getChildren().add(addButtons(buttonList));
		}
		else {
			viewLocationButton = new Button("View location");
			buttonList.add(viewLocationButton);
			
			openMenuButton = new Button(openString);
			buttonList.add(openMenuButton);
			
			deleteEventButton = new Button(removeString);
			buttonList.add(deleteEventButton);

			bottomHBox.getChildren().add(addButtons(buttonList));
			
			viewLocationButtonSetAction(viewLocationButton);
		}
		
		deleteButtonSetActionGuest(deleteEventButton);
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
	
	private void deleteButtonSetActionGuest(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				DeleteJoinedEventController deleteJoinedEventController = new DeleteJoinedEventController();
				deleteJoinedEventController.deleteEvent(eventBean);
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
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
	
	private void payButtonSetAction(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				PayHostController payHostController = new PayHostController();
				payHostController.payHost(eventBean);
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestEventPage.fxml"));
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
	
	private void viewLocationButtonSetAction(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				HostLocationPageController.setEventBean(eventBean);
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostLocationPage.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}
	
}
