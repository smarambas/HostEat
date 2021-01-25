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
	
	@FXML private VBox centralVBox;
	
	@FXML private Button btnBack;
	
	@FXML
	public void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Parent root = null;
		
		if(event.getSource() == btnBack) {
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
		GUIController.getSessionBean().setSearchedList(new ArrayList<>());
		
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
	
}
