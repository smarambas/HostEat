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
				
		if(event.getSource() == btnHome) {
			stage = (Stage) btnHome.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
		}
		else if(event.getSource() == btnUser) {
			stage = (Stage) btnUser.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/UserPage.fxml"));
		}
		else if(event.getSource() == btnNotifications) {
			stage = (Stage) btnNotifications.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/NotificationsPage.fxml"));
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
