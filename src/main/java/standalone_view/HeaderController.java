package standalone_view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HeaderController {

	private String appStyle = "NewStyle.css";
	
	@FXML private Parent header;
	@FXML private Button btnHome;
	@FXML private Button btnUser;
	@FXML private Button btnNotifications;
	@FXML private Button btnFavorites;	//only for the guest
	
	public void handleHomepageButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		boolean isHost;
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			isHost = true;
		}
		else {
			isHost = false;
		}
				
		if(event.getSource() == btnHome) {
			if(isHost) {
				stage = (Stage) btnHome.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
			}
			else {
				stage = (Stage) btnHome.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
			}
		}
		else if(event.getSource() == btnUser) {
			if(isHost) {
				stage = (Stage) btnUser.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostUserPage.fxml"));
			}
			else {
				stage = (Stage) btnUser.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestUserPage.fxml"));
			}
		}
		else if(event.getSource() == btnNotifications) {
			if(isHost) {
				stage = (Stage) btnNotifications.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/HostNotificationsPage.fxml"));
			}
			else {
				stage = (Stage) btnNotifications.getScene().getWindow();
				root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestNotificationsPage.fxml"));
			}
		}
		else if(event.getSource() == btnFavorites) {
			stage = (Stage) btnNotifications.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/FavoritesPage.fxml"));
		}
		
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
