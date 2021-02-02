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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HostEventPageController {

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
		ViewCommons viewCommons = new ViewCommons();
		viewCommons.handleButtonShowStage(btnBack, "/standalone_view/HostBase.fxml", 900, 600);
	}
	
	@FXML
	protected void initialize() {		
		eventBean = HostBaseController.getSelectedEvent();
		
		ViewCommons viewCommons = new ViewCommons();
		
		centralVBox.getChildren().addAll(
			viewCommons.addHBox("Date:", eventBean.getDateTime().substring(0, 10)),
			viewCommons.addHBox("Time:", eventBean.getDateTime().substring(11)),
			viewCommons.addHBox("Guests:", eventBean.getActualGuests()),
			viewCommons.addHBox("Region:", GUIController.getSessionBean().getUserBean().getReg()),
			viewCommons.addHBox("Province:", GUIController.getSessionBean().getUserBean().getProv()),
			viewCommons.addHBox("City:", GUIController.getSessionBean().getUserBean().getCity()),
			viewCommons.addHBox("Address:", GUIController.getSessionBean().getUserBean().getAddr())
		);
		
		if(eventBean.getBill() > 0) {
			centralVBox.getChildren().add(viewCommons.addHBox("Bill:", String.valueOf(eventBean.getBill())));
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
		
		bottomHBox.getChildren().add(addButtonsHBox(buttonList));
		
		deleteButtonSetActionHost(deleteEventButton);
		menuButtonSetAction(openMenuButton);
	}
	
	private HBox addButtonsHBox(List<Button> buttonsList) {
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
				
				ViewCommons viewCommons = new ViewCommons();
				viewCommons.handleButtonShowStage(button, "/standalone_view/HostBase.fxml", 900, 600);
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
				ViewCommons viewCommons = new ViewCommons();
				viewCommons.handleButtonShowStage(button, "/standalone_view/JoinedGuestsListPage.fxml", 900, 600);
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
				
				ViewCommons viewCommons = new ViewCommons();
				viewCommons.handleButtonShowStage(button, "/standalone_view/HostMenuPage.fxml", 900, 600);
			} catch (Exception e) {
				Label errorLabel = new Label("No menu found, sorry");
				errorLabel.setId(errorLabelId);
				centralVBox.getChildren().add(errorLabel);
			}
		});
	}

	public static void setMenuBean(MenuBean menuBean) {
		HostEventPageController.menuBean = menuBean;
	}
	
	public static MenuBean getMenuBean() {
		return menuBean;
	}
	
}
