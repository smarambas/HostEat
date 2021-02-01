package standalone_view;

import java.io.IOException;
import bean.EventBean;
import bean.UserBean;
import control.GetUserController;
import control.RemoveHostController;
import control.SaveHostController;
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

public class HostProfilePageController {

	private String appStyle = "NewStyle.css";
	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
	
	private static UserBean userBean = null;
		
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
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
	protected void initialize() {
		if(userBean == null) {
			EventBean eventBean = ResultEventPageController.getEventBean();
			setUserBean(new UserBean());
			getUserBean().setUsername(eventBean.getEventOwner());
		}
		
		Button saveButton = new Button("Save host");
		Button removeButton = new Button("Remove host");
		
		try {
			GetUserController getUserController = new GetUserController();
			setUserBean(getUserController.getUser(userBean));
		} catch (Exception e) {
			Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
		}
		
		centralVBox.getChildren().addAll(
			addHBox("Username:", userBean.getUsername()),
			addHBox("Name:", userBean.getName()),
			addHBox("Surname:", userBean.getSurname()),
			addHBox("Sex:", userBean.getSex()),
			addHBox("Birthday:", userBean.getBirthday().substring(0, 10)),
			addHBox("Email:", userBean.getEmailAddr()),
			addHBox("Region:", userBean.getReg()),
			addHBox("Province:", userBean.getProv()),
			addHBox("City:", userBean.getCity()),
			addHBox("Rating:", String.valueOf(userBean.getRatings()))
		);
		
		if(GUIController.getSessionBean().containsSavedUser(userBean.getUsername())) {
			bottomHBox.getChildren().add(removeButton);
		}
		else {
			bottomHBox.getChildren().add(saveButton);
		}
		
		final UserBean favUserBean = userBean;
		setUserBean(null);
		
		saveButton.setOnAction((ActionEvent event) -> {
			UserBean guestBean = GUIController.getSessionBean().getUserBean();
			
			try {
				SaveHostController saveHostController = new SaveHostController();
				saveHostController.saveHost(guestBean, favUserBean);
								
				Stage stage = (Stage) saveButton.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/FavoritesPage.fxml"));
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
		
		removeButton.setOnAction((ActionEvent event) -> {
			UserBean guestBean = GUIController.getSessionBean().getUserBean();
			
			try {
				RemoveHostController removeHostController = new RemoveHostController();
				removeHostController.removeHost(guestBean, favUserBean);
				
				Stage stage = (Stage) removeButton.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/standalone_view/FavoritesPage.fxml"));
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

	public static UserBean getUserBean() {
		return userBean;
	}

	public static void setUserBean(UserBean userBean) {
		HostProfilePageController.userBean = userBean;
	}
	
}
