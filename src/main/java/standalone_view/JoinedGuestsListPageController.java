package standalone_view;

import java.io.IOException;
import java.util.List;
import bean.EventBean;
import bean.UserBean;
import control.AcceptGuestController;
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
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	private EventBean eventBean;
	private static UserBean selectedGuest;
	
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
				
				Label nameLabel = new Label(ub.getName() + " " + ub.getSurname());
				nameLabel.setId("dataLabel");
				Label statusLabel = new Label(ub.getGuestStatus());
				
				Button acceptButton = new Button("Accept guest");
				Button denyButton = new Button("Deny guest");
				Button viewButton = new Button("View profile");
				
				if(!(ub.getPayStatus().equals("NOSET"))) {
					Label payLabel = new Label(ub.getPayStatus());
					hbox.getChildren().addAll(nameLabel, statusLabel, payLabel, acceptButton, denyButton, viewButton);
				}
				else {
					hbox.getChildren().addAll(nameLabel, statusLabel, acceptButton, denyButton, viewButton);
				}
				
				hbox.setAlignment(Pos.CENTER);
				hbox.setSpacing(10);
				
				centralVBox.getChildren().addAll(hbox, new Separator());
				
				acceptButtonSetActionHost(ub, acceptButton);
				denyButtonSetActionHost(ub, denyButton);
				viewButtonSetActionHost(ub, viewButton);
			}
		} catch (Exception e) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
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
	
	private void viewButtonSetActionHost(UserBean guestBean ,Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				setSelectedGuest(guestBean);
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestProfilePage.fxml"));
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
	
	private void acceptButtonSetActionHost(UserBean guestBean ,Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				AcceptGuestController acceptGuestController = new AcceptGuestController();
				acceptGuestController.acceptGuest(guestBean, eventBean);
				
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

	public static UserBean getSelectedGuest() {
		return selectedGuest;
	}

	public static void setSelectedGuest(UserBean selectedGuest) {
		JoinedGuestsListPageController.selectedGuest = selectedGuest;
	}
	
}
