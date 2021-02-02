package standalone_view;

import java.io.IOException;

import bean.CourseBean;
import bean.EventBean;
import bean.MenuBean;
import control.GetMenuController;
import exceptions.NoRecordFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResultMenuPageController {

	private String appStyle = "NewStyle.css";
	private String errorLabelId = "errorLabel";
	
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/ResultEventPage.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void initialize() {
		MenuBean menuBean; 
		EventBean eventBean = ResultEventPageController.getEventBean();
		
		try {
			GetMenuController getMenuController = new GetMenuController();
			menuBean = getMenuController.getMenu(eventBean);
			
			for(CourseBean courseBean : menuBean.getCoursesList()) {
				Label courseLabel = new Label(courseBean.getCourseName());
				courseLabel.setId("descriptionLabel");
				courseLabel.setAlignment(Pos.CENTER);
				
				centralVBox.getChildren().add(courseLabel);
				
				for(String dishString : courseBean.getDishes()) {
					Label dishLabel = new Label(dishString);
					dishLabel.setId("dishLabel");
					dishLabel.setAlignment(Pos.CENTER);
					dishLabel.setWrapText(true);
					
					centralVBox.getChildren().add(dishLabel);
				}
			}
		} catch (NoRecordFoundException nrfe) {
			Label errorLabel = new Label("No menu set for the event");
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		} catch (Exception e) {			
			Label errorLabel = new Label("Ops, something went wrong, please try again");
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
	}
	
}
