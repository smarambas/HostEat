package standalone_view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import bean.EventBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchResultsPageController {

	private String appStyle = "NewStyle.css";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	private static EventBean selectedEvent;
	
	@FXML private VBox centralVBox;
	
	@FXML private Button btnBack;
	
	@FXML
	public void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
			GUIController.getSessionBean().setSearchedList(new ArrayList<>());	//clear list
			centralVBox.getChildren().clear();
			stage = (Stage) btnBack.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("/standalone_view/SearchEventPage.fxml"));
		}
		
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {
		List<EventBean> eventList = GUIController.getSessionBean().getSearchedList();
		
		for(EventBean eventBean : eventList) {
			List<Node> nodeList = new ArrayList<>();
			
			Button openButton = new Button("Open event");
			nodeList.add(new Label(eventBean.getDateTime()));
			nodeList.add(new Label(eventBean.getRegionString()));
			nodeList.add(new Label(eventBean.getProvinceString()));
			nodeList.add(new Label(eventBean.getCityString()));
			
			centralVBox.getChildren().addAll(
				addHBox(nodeList, openButton),
				new Separator()
			);
			
			openButton.setOnAction((ActionEvent event) -> {
				Stage stage = (Stage) openButton.getScene().getWindow();
				setSelectedEvent(eventBean);
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/ResultEventPage.fxml"));
					Scene scene = new Scene(root, 900, 600);
					scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
					stage.setScene(scene);
					stage.show();
				} catch(IOException ioe) {
					Label errorLabel = new Label(errorLabelMsg);
					errorLabel.setId(errorLabelId);
					centralVBox.getChildren().add(errorLabel);
				}
			});
		}
	}
	
	private HBox addHBox(List<Node> nodeList, Button button) {
		HBox hBox = new HBox();
		
		for(Node node : nodeList) {
			node.setId("dataLabel");
			hBox.getChildren().add(node);
		}
		
		hBox.getChildren().add(button);
		
		hBox.setAlignment(Pos.CENTER);
		
		return hBox;
	}

	public static EventBean getSelectedEvent() {
		return selectedEvent;
	}

	public static void setSelectedEvent(EventBean selectedEvent) {
		SearchResultsPageController.selectedEvent = selectedEvent;
	}
	
}
