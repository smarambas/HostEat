package standalone_view;

import java.io.IOException;
import java.util.List;
import bean.NotificationBean;
import bean.UserBean;
import control.DeleteAllNotificationsController;
import control.DeleteNotificationController;
import control.GetNotificationsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NotificationPageController {
	
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	private String hostPageString = "/standalone_view/HostNotificationsPage.fxml";
	private String guestPageString = "/standalone_view/GuestNotificationsPage.fxml";

	private List<NotificationBean> notificationBeans;
	
	@FXML private Button btnRefresh;
	@FXML private Button deleteAllButton;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleRefreshButtonAction(ActionEvent event) throws IOException {
		try {
			GetNotificationsController getNotificationsController = new GetNotificationsController();
			notificationBeans = getNotificationsController.getNotifications(GUIController.getSessionBean().getUserBean());
			
			ViewCommons viewCommons = new ViewCommons();
			viewCommons.handleButtonUserDependantShowStage(btnRefresh, hostPageString, guestPageString, 900, 600);
		} catch (Exception e) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
	}
	
	@FXML
	private void handleDeleteAllButtonAction(ActionEvent event) throws IOException {
		try {
			DeleteAllNotificationsController deleteAllNotificationsController = new DeleteAllNotificationsController();
			deleteAllNotificationsController.deleteAllNotifications(GUIController.getSessionBean().getUserBean());
			
			ViewCommons viewCommons = new ViewCommons();
			viewCommons.handleButtonUserDependantShowStage(deleteAllButton, hostPageString, guestPageString, 900, 600);
		} catch (Exception e) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
	}
	
	private void deleteButtonSetAction(Button button, UserBean userBean, NotificationBean notificationBean) {
		button.setOnAction((ActionEvent event) -> {
			try {
				DeleteNotificationController deleteNotificationController = new DeleteNotificationController();
				deleteNotificationController.deleteNotification(userBean, notificationBean);
				
				ViewCommons viewCommons = new ViewCommons();
				viewCommons.handleButtonUserDependantShowStage(button, hostPageString, guestPageString, 900, 600);
			} catch (Exception e) {
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}
	
	@FXML
	private void initialize() {
		try {
			GetNotificationsController getNotificationsController = new GetNotificationsController();
			notificationBeans = getNotificationsController.getNotifications(GUIController.getSessionBean().getUserBean());
			
			Button deleteButton;
			
			for(NotificationBean nb : notificationBeans) {
				VBox vbox = new VBox();
				
				Label dateLabel = new Label(nb.getDate());
				dateLabel.setId("dataLabel");
				Label textLabel = new Label(nb.getText());
				textLabel.setId("notificationLabel");
				
				vbox.getChildren().addAll(dateLabel, textLabel);
				vbox.setAlignment(Pos.CENTER_LEFT);
				
				deleteButton = new Button("Delete");
				
				centralVBox.getChildren().addAll(addHBox(vbox, deleteButton), new Separator());
				
				deleteButtonSetAction(deleteButton, GUIController.getSessionBean().getUserBean(), nb);
			}
		} catch(Exception e) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
	}
	
	private HBox addHBox(VBox vbox, Button button) {
		HBox hbox = new HBox();
		
		hbox.getChildren().addAll(vbox, button);
		hbox.setAlignment(Pos.CENTER);
		
		return hbox;
	}
	
}
