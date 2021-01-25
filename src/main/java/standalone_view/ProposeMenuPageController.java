package standalone_view;

import java.io.IOException;
import bean.CourseBean;
import bean.EventBean;
import bean.MenuBean;
import control.ProposeMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProposeMenuPageController {
	
	private String appStyle = "NewStyle.css";
	private String errorString = "errorLabel";
	
	private Label errorLabel = new Label("Please insert a name for the course");
	private Label errorLabel1 = new Label("Please insert a name for the dish");
	private Label errorLabel2 = new Label("Please insert first a course");

	@FXML private VBox centralVBox;
	@FXML private Button addCourseButton;
	@FXML private Button addDishButton;
	@FXML private Button confirmButton;
	@FXML private TextField addCourseField;
	@FXML private TextField addDishField;
	
	private MenuBean newMenu = new MenuBean();
	private CourseBean newCourseBean = null;
	
	@FXML
	private void handleAddCourseButtonAction(ActionEvent event) {
		Label courseNameLabel;
		errorLabel.setId(errorString);
		errorLabel.setAlignment(Pos.CENTER);
		
		if(addCourseField.getText().length() > 0) {
			if(centralVBox.getChildren().contains(errorLabel) || centralVBox.getChildren().contains(errorLabel1) || centralVBox.getChildren().contains(errorLabel2)) {
				centralVBox.getChildren().remove(errorLabel);
				centralVBox.getChildren().remove(errorLabel1);
				centralVBox.getChildren().remove(errorLabel2);
			}
			
			courseNameLabel = new Label(addCourseField.getText());
			courseNameLabel.setId("descriptionLabel");
			courseNameLabel.setAlignment(Pos.CENTER);
			
			centralVBox.getChildren().add(courseNameLabel);
			
			newCourseBean = new CourseBean();
			newCourseBean.setCourseName(addCourseField.getText());
			if(!(newMenu.getCoursesList().contains(newCourseBean))) {
				newMenu.getCoursesList().add(newCourseBean);
			}
			
			addCourseField.clear();
		}
		else {
			if(!(centralVBox.getChildren().contains(errorLabel))) {
				centralVBox.getChildren().add(errorLabel);
			}
		}
	}
	
	@FXML
	private void handleAddDishButtonAction(ActionEvent event) {
		Label dishNameLabel;
		errorLabel1.setId(errorString);
		errorLabel1.setAlignment(Pos.CENTER);
		errorLabel2.setId(errorString);
		errorLabel2.setAlignment(Pos.CENTER);
		
		if(newCourseBean == null) {
			if(!(centralVBox.getChildren().contains(errorLabel2))) {
				centralVBox.getChildren().add(errorLabel2);
			}
		}
		else {
			if(addDishField.getText().length() > 0) {
				if(centralVBox.getChildren().contains(errorLabel) || centralVBox.getChildren().contains(errorLabel1) || centralVBox.getChildren().contains(errorLabel2)) {
					centralVBox.getChildren().remove(errorLabel);
					centralVBox.getChildren().remove(errorLabel1);
					centralVBox.getChildren().remove(errorLabel2);
				}
				
				dishNameLabel = new Label(addDishField.getText());
				dishNameLabel.setId("dishLabel");
				dishNameLabel.setAlignment(Pos.CENTER);
				
				centralVBox.getChildren().add(dishNameLabel);
				
				newCourseBean.getDishes().add(addDishField.getText());
				
				addDishField.clear();
			}
			else {
				if(!(centralVBox.getChildren().contains(errorLabel1))) {
					centralVBox.getChildren().add(errorLabel1);
				}
			}
		}
		
	}
	
	@FXML
	private void handleConfirmButtonAction(ActionEvent event) throws IOException {
		int eventsNum = GUIController.getSessionBean().getEventBeanList().size();
		EventBean eventBean = GUIController.getSessionBean().getEventBeanList().get(eventsNum - 1);	//last added event
		
		try {
			ProposeMenuController proposeMenuController = new ProposeMenuController();
			proposeMenuController.proposeMenu(eventBean, newMenu);
			
			Stage stage = (Stage) confirmButton.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
			Scene scene = new Scene(root, 900, 600);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GenericErrorPage.fxml"));
			Scene scene = new Scene(root, 350, 100);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		
	}
	
}
