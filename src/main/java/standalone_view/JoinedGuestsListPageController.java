package standalone_view;

import java.io.IOException;
import java.util.List;
import bean.EventBean;
import bean.UserBean;
import control.DenyGuestController;
import control.GetJoinedGuestsController;
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

public class JoinedGuestsListPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	private EventBean eventBean;
	
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostEventPage.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {		
		eventBean = HostBaseController.getSelectedEvent();
		GetJoinedGuestsController getJoinedGuestsController = new GetJoinedGuestsController();
		List<UserBean> guestsList;
		
		try {
			guestsList = getJoinedGuestsController.getJoinedGuests(eventBean);
			
			for(UserBean ub : guestsList) {
				HBox hbox = new HBox();
				
				Label usernameLabel = new Label(ub.getUsername());
				Label ratingLabel = new Label(String.valueOf(ub.getRatings()));
				Label statusLabel = new Label(ub.getGuestStatus());
				
				Button acceptButton = new Button("Accept guest");
				Button denyButton = new Button("Deny guest");
				
				if(!(ub.getPayStatus().equals("NOSET"))) {
					Label payLabel = new Label(ub.getPayStatus());
					hbox.getChildren().addAll(usernameLabel, ratingLabel, statusLabel, payLabel, acceptButton, denyButton);
				}
				else {
					hbox.getChildren().addAll(usernameLabel, ratingLabel, statusLabel, acceptButton, denyButton);
				}
				
				hbox.setAlignment(Pos.CENTER);
				hbox.setSpacing(20);
				
				centralVBox.getChildren().addAll(hbox, new Separator());
				
				denyButtonSetActionHost(ub, denyButton);
			}
		} catch (Exception e) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
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
	
	private void denyButtonSetActionHost(UserBean guestBean ,Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				DenyGuestController denyGuestController = new DenyGuestController();
				GUIController.setSessionBean(denyGuestController.denyGuest(guestBean, eventBean));
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/JoinedGuestsListPage.fxml"));
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
