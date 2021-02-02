package standalone_view;

import java.io.IOException;
import bean.EventBean;
import control.CreateEventController;
import exceptions.WrongDateException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewEventPageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	
	@FXML private Button btnBack;
	@FXML private VBox centralVBox;
	
	@FXML
	public void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/HostBase.fxml"));
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	protected void initialize() {
		Button submitButton = new Button("Submit");
				
		DatePicker datePicker = new DatePicker();
		datePicker.setPromptText("date");

		ChoiceBox hoursChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", 
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00")
		);
		
		ChoiceBox minutesChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
			"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", 
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", 
			"24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", 
			"36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", 
			"48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59")
		);
		
		ChoiceBox guestsChoiceBox = new ChoiceBox(FXCollections.observableArrayList(
			"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
		);
		
		TextField billField = new TextField();
		billField.setPromptText("bill");
		
		centralVBox.getChildren().addAll(
			addHBox("Date:", datePicker),
			addHBox("Hours:", hoursChoiceBox),
			addHBox("Minutes:", minutesChoiceBox),
			addHBox("Guests:", guestsChoiceBox),
			addHBox("Bill:", billField),
			submitButton
		);
		
		submitButton.setOnAction((ActionEvent event) -> {
			EventBean eventBean = new EventBean();
			
			try {
				eventBean.setDateTime(datePicker.getValue().toString() + " " +
									  hoursChoiceBox.getValue().toString() + ":" + 
									  minutesChoiceBox.getValue().toString());
				
				eventBean.setMaxGuestsNumber(Integer.valueOf(guestsChoiceBox.getValue().toString()));
				
				if(Double.parseDouble(billField.getText()) > 0) {
					eventBean.setBill(Double.parseDouble(billField.getText()));
				}
				else {
					eventBean.setBill(0);
				}
				
				CreateEventController createEventController = new CreateEventController();
			
				GUIController.setSessionBean(createEventController.createEvent(eventBean));
				
				ViewCommons viewCommons = new ViewCommons();
				viewCommons.handleButtonShowStage(submitButton, "/standalone_view/ProposeMenuPage.fxml", 900, 600);
			} catch (WrongDateException wde) {
				Label errorLabel = new Label("The date picked is in the past, try again");
				errorLabel.setId("errorLabel");
				centralVBox.getChildren().add(errorLabel);
			} catch (Exception e) {
				Label errorLabel = new Label("The event was not created, please try again.");
				errorLabel.setId("errorLabel");
				centralVBox.getChildren().add(errorLabel);
			}
		});
		
	}
	
	private HBox addHBox(String s, Node node) {
		HBox hBox = new HBox();
		
		Label label = new Label(s);
		label.setId(descriptionString);
		
		hBox.getChildren().addAll(label, node);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
}
