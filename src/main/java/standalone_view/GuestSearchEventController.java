package standalone_view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GuestSearchEventController {

	private String appStyle = "NewStyle.css";
	
	@FXML private Button btnBack;
	@FXML private Button btnEvent;
	
	public void handleGuestBaseSearchEventButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnEvent) {
			stage = (Stage) btnEvent.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/SearchEventPage.fxml"));
		}
		
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void handleSearchEventPageButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
		}
		
		Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
