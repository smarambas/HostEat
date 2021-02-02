package standalone_view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewCommons {
	
	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";

	public void handleButtonShowStage(Button button, String fxml, int width, int height) throws IOException {
		Stage stage = (Stage) button.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void handleButtonUserDependantShowStage(Button button, String hostPage, String guestPage, int width, int height) throws IOException {
		Stage stage = (Stage) button.getScene().getWindow();
		Parent root = null;
		
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			root = FXMLLoader.load(getClass().getResource(hostPage));
		}
		else {
			root = FXMLLoader.load(getClass().getResource(guestPage));
		}
		
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public HBox addHBox(String s, String data) {
		HBox hBox = new HBox();
		
		Label label = new Label(s);
		label.setId(descriptionString);
		
		Label dataLabel = new Label(data);
		dataLabel.setId(dataString);
		
		hBox.getChildren().addAll(label, dataLabel);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
}
