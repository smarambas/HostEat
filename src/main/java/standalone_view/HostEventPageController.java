package standalone_view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bean.EventBean;
import bean.MenuBean;
import control.DeleteEventController;
import control.GetMenuController;
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

public class HostEventPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	private String openString = "Open menu";
	private String removeString = "Remove event";
	
	private EventBean eventBean;
	private static MenuBean menuBean;
	
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {		
		eventBean = HostBaseController.getSelectedEvent();
		
		centralVBox.getChildren().addAll(
			addHBox("Date:", eventBean.getDateTime().substring(0, 10)),
			addHBox("Time:", eventBean.getDateTime().substring(11)),
			addHBox("Guests:", eventBean.getActualGuests()),
			addHBox("Region:", GUIController.getSessionBean().getUserBean().getReg()),
			addHBox("Province:", GUIController.getSessionBean().getUserBean().getProv()),
			addHBox("City:", GUIController.getSessionBean().getUserBean().getCity()),
			addHBox("Address:", GUIController.getSessionBean().getUserBean().getAddr())
		);
		
		if(eventBean.getBill() > 0) {
			centralVBox.getChildren().add(addHBox("Bill:", String.valueOf(eventBean.getBill())));
		}
		
		List<Button> buttonList = new ArrayList<>();
		
		Button guestsListButton;
		Button openMenuButton;
		Button deleteEventButton;
		
		if(eventBean.getGuestsNumber() > 0) {
			guestsListButton = new Button("View guests");
			buttonList.add(guestsListButton);
			
			openMenuButton = new Button(openString);
			buttonList.add(openMenuButton);
			
			deleteEventButton = new Button(removeString);
			buttonList.add(deleteEventButton);
			
			guestsListButtonSetAction(guestsListButton);
		}
		else {
			openMenuButton = new Button(openString);
			buttonList.add(openMenuButton);
			
			deleteEventButton = new Button(removeString);
			buttonList.add(deleteEventButton);
		}
		
		bottomHBox.getChildren().add(addButtons(buttonList));
		
		deleteButtonSetActionHost(deleteEventButton);
		menuButtonSetAction(openMenuButton);
	}
	
	private HBox addHBox(String s, String data) {
		HBox hBox = new HBox();
		
		Label label = new Label(s);
		label.setId(descriptionString);
		
		Label dataLabel = new Label(data);
		dataLabel.setId(dataString);
		
		hBox.getChildren().addAll(label, dataLabel);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
	private HBox addButtons(List<Button> buttonsList) {
		HBox hBox = new HBox();
		
		for(Button b : buttonsList) {
			hBox.getChildren().add(b);
		}
		
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(20);
		
		return hBox;
	}
	
	private void deleteButtonSetActionHost(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				DeleteEventController deleteEventController = new DeleteEventController();
				deleteEventController.deleteEvent(eventBean);
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}
	
	private void guestsListButtonSetAction(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/JoinedGuestsListPage.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Label errorLabel = new Label(errorLabelMsg);
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}
	
	private void menuButtonSetAction(Button button) {
		button.setOnAction((ActionEvent event) -> {
			try {
				GetMenuController getMenuController = new GetMenuController();
				setMenuBean(getMenuController.getMenu(eventBean));
				
				Stage stage = (Stage) button.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/HostMenuPage.fxml"));
				Scene scene = new Scene(root, 900, 600);
				scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				Label errorLabel = new Label("No menu found, sorry");
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}

	public static MenuBean getMenuBean() {
		return menuBean;
	}

	public static void setMenuBean(MenuBean menuBean) {
		HostEventPageController.menuBean = menuBean;
	}
	
}
