package standalone_view;

import java.io.IOException;
import bean.CourseBean;
import bean.MenuBean;
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

public class MenuPageController {

	private String appStyle = "NewStyle.css";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
		
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = null;
		
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			root = FXMLLoader.load(getClass().getResource("/standalone_view/HostEventPage.fxml"));
		}
		else {
			root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestEventPage.fxml"));
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void initialize() {	
		MenuBean menuBean;
		
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			menuBean = HostEventPageController.getMenuBean();
		}
		else {
			menuBean = GuestEventPageController.getMenuBean();
		}
		
		for(CourseBean cb : menuBean.getCoursesList()) {
			Label courseLabel = new Label(cb.getCourseName());
			courseLabel.setId("descriptionLabel");
			courseLabel.setAlignment(Pos.CENTER);
			
			centralVBox.getChildren().add(courseLabel);
			
			for(String dish : cb.getDishes()) {
				Label dishLabel = new Label(dish);
				dishLabel.setId("dataLabel");
				dishLabel.setAlignment(Pos.CENTER);
				
				centralVBox.getChildren().add(dishLabel);
			}
		}
		
		if(GUIController.getSessionBean().getUserBean().getUserType().equalsIgnoreCase("HOST")) {
			Button modifyMenuButton = new Button("Modify menu");
			
			bottomHBox.getChildren().add(modifyMenuButton);
		}
	}
}
