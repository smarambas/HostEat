package standalone_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUILauncher extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
    public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/FirstScreen.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource("NewStyle.css").toExternalForm());
		stage.setTitle("HostEat");
        stage.setScene(scene);
        stage.show();          
	}
	
}
