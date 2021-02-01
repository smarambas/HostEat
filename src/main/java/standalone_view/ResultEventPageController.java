package standalone_view;

import java.io.IOException;
import bean.EventBean;
import control.JoinEventController;
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

public class ResultEventPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	private static EventBean eventBean;
	
	@FXML private Button btnBack;
	@FXML private Button joinButton;
	@FXML private Button profileButton;
	@FXML private Button menuButton;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/SearchResultsPage.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleJoinButtonAction(ActionEvent event) throws IOException {
		if(!(GUIController.getSessionBean().containsEventBean(eventBean))) {
			if(eventBean.getGuestsNumber() < eventBean.getMaxGuestsNumber()) {
				JoinEventController joinEventController = new JoinEventController();
				try {
					GUIController.setSessionBean(joinEventController.joinEvent(eventBean));
				} catch (Exception e) {
					Label errorLabel = new Label(errorLabelMsg);
					errorLabel.setId(errorLabelId);
					centralVBox.getChildren().add(errorLabel);
				}
				
				Stage stage = (Stage) joinButton.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			}
			else {
				Label errorLabel = new Label("You can't join a full event, sorry");
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		}
		else {
			Label errorLabel = new Label("You already joined that event");
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
	}
	
	@FXML
	private void handleProfileButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) profileButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostProfilePage.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void handleMenuButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) menuButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/ResultMenuPage.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		setEventBean(SearchResultsPageController.getSelectedEvent());
		
		boolean isPaymentRequired = false;
		
		centralVBox.getChildren().addAll(
			addHBox("Owner:", eventBean.getEventOwner()),
			addHBox("Date:", eventBean.getDateTime().substring(0, 10)),
			addHBox("Time:", eventBean.getDateTime().substring(11)),
			addHBox("Guests:", eventBean.getActualGuests())
		);
		
		isPaymentRequired = (eventBean.getBill() > 0);
		if(isPaymentRequired) {
			centralVBox.getChildren().add(addHBox("Bill:", String.valueOf(eventBean.getBill())));
		}
		
		centralVBox.getChildren().addAll(
			addHBox("Region:", eventBean.getRegionString()),
			addHBox("Province:", eventBean.getProvinceString()),
			addHBox("City:", eventBean.getCityString())
		);		
	}
	
	private HBox addHBox(String s, String data) {
		HBox hBox = new HBox();
		
		Label label = new Label(s);
		label.setId(descriptionString);
		label.setAlignment(Pos.CENTER);
		
		Label dataLabel = new Label(data);
		dataLabel.setId(dataString);
		dataLabel.setAlignment(Pos.CENTER);
		
		hBox.getChildren().addAll(label, dataLabel);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}

	public static EventBean getEventBean() {
		return eventBean;
	}

	public static void setEventBean(EventBean eventBean) {
		ResultEventPageController.eventBean = eventBean;
	}
	
}
