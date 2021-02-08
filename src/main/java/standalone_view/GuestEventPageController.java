package standalone_view;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import bean.EventBean;
import bean.MenuBean;
import control.DeleteJoinedEventController;
import control.GetMenuController;
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
	private static MenuBean menuBean;
	
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
	protected void initialize() throws ParseException {		
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
		Button rateButton = new Button("Rate host");
		
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
			rateButtonSetAction(rateButton, eventBean);
		}
		
		deleteButtonSetActionGuest(deleteEventButton);
		menuButtonSetAction(openMenuButton);
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
	
	private void rateButtonSetAction(Button button, EventBean eventBean) throws ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		GregorianCalendar nowCalendar = new GregorianCalendar();
		GregorianCalendar date = new GregorianCalendar();
		date.setTime(sdf.parse(eventBean.getDateTime()));
		
		long dateInMillis = date.getTimeInMillis();
		long nowInMillis = nowCalendar.getTimeInMillis();
		
		if(dateInMillis - nowInMillis < 0) {
			bottomHBox.getChildren().add(button);
			
			button.setOnAction((ActionEvent event) -> {
				try {
					ViewCommons commons = new ViewCommons();
					commons.handleButtonShowStage(button, "/standalone_view/RateHostPage.fxml", 900, 600);
				} catch (Exception e) {
					Label errorLabel = new Label(errorLabelMsg);
					errorLabel.setId(errorLabelId);
					centralVBox.getChildren().add(errorLabel);
				}
			});
		}
	}
	
	private void deleteButtonSetActionGuest(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				DeleteJoinedEventController deleteJoinedEventController = new DeleteJoinedEventController();
				GUIController.setSessionBean(deleteJoinedEventController.deleteEvent(GUIController.getSessionBean(), eventBean));
				
				ViewCommons commons = new ViewCommons();
				commons.handleButtonShowStage(button, "/standalone_view/GuestBase.fxml", 900, 600);
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
				GUIController.setSessionBean(payHostController.payHost(GUIController.getSessionBean(), eventBean));
				
				ViewCommons commons = new ViewCommons();
				commons.handleButtonShowStage(button, "/standalone_view/GuestEventPage.fxml", 900, 600);
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
				
				ViewCommons commons = new ViewCommons();
				commons.handleButtonShowStage(button, "/standalone_view/HostLocationPage.fxml", 900, 600);
			} catch (Exception e) {
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}
	
	private void menuButtonSetAction(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				GetMenuController getMenuController = new GetMenuController();
				setMenuBean(getMenuController.getMenu(eventBean));
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestMenuPage.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Label errorLabel = new Label("No menu found, sorry");
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}

	public static MenuBean getMenuBean() {
		return menuBean;
	}

	public static void setMenuBean(MenuBean menuBean) {
		GuestEventPageController.menuBean = menuBean;
	}
	
}
