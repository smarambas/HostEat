package standalone_view;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import bean.EventBean;
import bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GuestProfilePageController {

	private String descriptionString = "descriptionLabel";
	private String dataString = "dataLabel";
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";
		
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	@FXML private HBox bottomHBox;
	
	@FXML
	protected void initialize() throws ParseException {		
		UserBean guestBean = JoinedGuestsListPageController.getSelectedGuest();
		
		Button rateButton = new Button("Rate guest");
		
		centralVBox.getChildren().addAll(
			addHBox("Username:", guestBean.getUsername()),
			addHBox("Name:", guestBean.getName()),
			addHBox("Surname:", guestBean.getSurname()),
			addHBox("Sex:", guestBean.getSex()),
			addHBox("Birthday:", guestBean.getBirthday().substring(0, 10)),
			addHBox("Email:", guestBean.getEmailAddr()),
			addHBox("Region:", guestBean.getReg()),
			addHBox("Province:", guestBean.getProv()),
			addHBox("City:", guestBean.getCity()),
			addHBox("Rating:", String.valueOf((double) guestBean.getRatings() / guestBean.getRatingsNum()))
		);
		
		rateButtonSetAction(rateButton);
	}
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		ViewCommons viewCommons = new ViewCommons();
		viewCommons.handleButtonShowStage(btnBack, "/standalone_view/JoinedGuestsListPage.fxml", 900, 600);
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
	
	private void rateButtonSetAction(Button button) throws ParseException {
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		EventBean eventBean = HostBaseController.getSelectedEvent();
		
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar dateCal = new GregorianCalendar();
		dateCal.setTime(sdf.parse(eventBean.getDateTime()));
		
		long dateInMillis = dateCal.getTimeInMillis();
		long nowInMillis = now.getTimeInMillis();
		
		if(dateInMillis - nowInMillis < 0) {
			bottomHBox.getChildren().add(button);
			
			button.setOnAction((ActionEvent event) -> {
				try {
					ViewCommons viewCommons = new ViewCommons();
					viewCommons.handleButtonShowStage(button, "/standalone_view/RateGuestPage.fxml", 900, 600);
				} catch (Exception e) {
					Label errorLabel = new Label(errorLabelMsg);
					errorLabel.setId(errorLabelId);
					centralVBox.getChildren().add(errorLabel);
				}
			});
		}
	}
	
}
