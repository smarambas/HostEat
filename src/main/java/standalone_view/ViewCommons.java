package standalone_view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewCommons {
	
	private String appStyle = "NewStyle.css";

	public void handleButtonShowStage(Button button, String fxml, int width, int height) throws IOException {
		Stage stage = (Stage) button.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(root, width, height);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
}