package standalone_view;

import bean.UserBean;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserPageController {

	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	
	@FXML private VBox centralVBox;
	
	@FXML
	protected void initialize() {
		UserBean userBean = GUIController.getSessionBean().getUserBean();
		
		Button modifyButton = new Button("Modify account");
		
		centralVBox.getChildren().addAll(
			addHBox("Name:", userBean.getName()),
			addHBox("Surname:", userBean.getSurname()),
			addHBox("Birthday:", userBean.getBirthDay().substring(0, 10)),
			addHBox("Sex:", userBean.getSex()),
			addHBox("Email:", userBean.getEmailAddr()),
			addHBox("Region:", userBean.getReg()),
			addHBox("Province", userBean.getProv()),
			addHBox("City:", userBean.getCity()),
			addHBox("Address:", userBean.getAddr()),
			modifyButton
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
