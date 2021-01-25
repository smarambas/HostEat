package standalone_view;

import java.io.IOException;
import java.time.LocalDate;
import bean.EventBean;
import control.SearchEventController;
import exceptions.NoRecordFoundException;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchEventPageController {

	private String appStyle = "NewStyle.css";
	
	@FXML private VBox centralVBox;
	
	@FXML private Button btnBack;
	@FXML private Button searchButton;
	@FXML private TextField regionField;
	@FXML private TextField provinceField;
	@FXML private TextField cityField;
	@FXML private DatePicker datePicker;
	@FXML private ChoiceBox<String> hoursChoiceBox;
	@FXML private ChoiceBox<String> minutesChoiceBox;
	
	@FXML
	public void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/GuestBase.fxml"));
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void handleSearchButtonAction(ActionEvent event) throws IOException {
		EventBean eventBean = new EventBean();
		
		try {
			eventBean.setRegionString(regionField.getText());
			eventBean.setProvinceString(provinceField.getText());
			eventBean.setCityString(cityField.getText());
			eventBean.setDateTime(datePicker.getValue().toString() + " " +
					  			  hoursChoiceBox.getValue() + ":" + 
					  			  minutesChoiceBox.getValue());
			
			SearchEventController searchEventController = new SearchEventController();
			GUIController.setSessionBean(searchEventController.searchEvent(eventBean));
			
			Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/SearchResultsPage.fxml"));
			Scene scene = new Scene(root, 900, 600);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			Stage stage = (Stage) searchButton.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			
		} catch(IllegalArgumentException iae) {
			Label errorLabel = new Label("Please, insert at least one research field");
			errorLabel.setId("errorLabel");
			centralVBox.getChildren().add(errorLabel);
		} catch (NoRecordFoundException nrfe) {
			nrfe.printStackTrace();
			
			Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/NoResultFoundErrorPage.fxml"));
			Scene scene = new Scene(root, 400, 100);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
			
			Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/GenericErrorPage.fxml"));
			Scene scene = new Scene(root, 400, 100);
			scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
	}
	
	@FXML
	protected void initialize() {
		datePicker.setDayCellFactory(picker -> new DateCell() {
        	@Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
		
		datePicker.setValue(LocalDate.now());
		
		hoursChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", 
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "00")
		);
		
		hoursChoiceBox.setValue("00");
		
		minutesChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
			"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", 
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", 
			"24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", 
			"36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", 
			"48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59")
		);
		
		minutesChoiceBox.setValue("00");
		
		centralVBox.getChildren().addAll(
			addHBox("Hours:", hoursChoiceBox),
			addHBox("Minutes:", minutesChoiceBox)
		);
	}
	
	private HBox addHBox(String s, Node node) {
		HBox hBox = new HBox();
		
		Label label = new Label(s);
		
		hBox.getChildren().addAll(label, node);
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}
	
}
