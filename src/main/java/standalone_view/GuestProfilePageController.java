package standalone_view;

import java.io.IOException;
import bean.UserBean;
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

public class GuestProfilePageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/JoinedGuestsListPage.fxml"));
		Scene scene = new Scene(root, 900, 600);
		scene.getStylesheets().add(getClass().getResource(appStyle).toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void initialize() {		
		UserBean guestBean = JoinedGuestsListPageController.getSelectedGuest();
		
		centralVBox.getChildren().addAll(
			addHBox("Username:", guestBean.getUsername()),
			addHBox("Name:", guestBean.getName()),
			addHBox("Surname:", guestBean.getSurname()),
			addHBox("Sex:", guestBean.getSex()),
			addHBox("Birthday:", guestBean.getBirthDay().substring(0, 10)),
			addHBox("Email:", guestBean.getEmailAddr()),
			addHBox("Region:", guestBean.getReg()),
			addHBox("Province:", guestBean.getProv()),
			addHBox("City:", guestBean.getCity()),
			addHBox("Rating:", String.valueOf(guestBean.getRatings()))
		);
		
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
	
}
