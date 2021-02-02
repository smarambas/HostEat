package standalone_view;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.google.maps.GeoApiContext;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.StaticMapsRequest.Markers;
import com.google.maps.StaticMapsRequest.Markers.MarkersSize;
import com.google.maps.StaticMapsRequest.StaticMapType;
import com.google.maps.model.Size;
import bean.EventBean;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HostLocationPageController {
	
	private String errorLabelMsg = "Ops, something went wrong, please try again";
	private String errorLabelId = "errorLabel";

	private static EventBean eventBean;
	
	private static final String API_KEY = "AIzaSyDr2a8rEFBOGJ1xyodMMkE97F-rbZPCJXw";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 400;
	
	@FXML private Button btnBack;
	
	@FXML private VBox centralVBox;
	
	@FXML
	private void handleBackButtonAction(ActionEvent event) throws IOException {
		ViewCommons viewCommons = new ViewCommons();
		viewCommons.handleButtonShowStage(btnBack, "/standalone_view/GuestEventPage.fxml", 900, 600);
	}
	
	@FXML
	protected void initialize() {
		String location = eventBean.getCityString() + ", " + eventBean.getAddressString();
		
		Label locationLabel = new Label(location);
		locationLabel.setId("dataLabel");
		locationLabel.setMaxWidth(WIDTH);
		locationLabel.setAlignment(Pos.CENTER);
		
		GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();
		StaticMapsRequest request = StaticMapsApi.newRequest(context, new Size(WIDTH, HEIGHT));
		request.center(location);
		request.zoom(16);
		request.language("it");
		request.maptype(StaticMapType.roadmap);
		request.scale(1);
		
		Markers markers = new Markers();
	    markers.size(MarkersSize.normal);
	    markers.color("red");
	    markers.addLocation(location);
	    
	    request.markers(markers);
	    
	    try {
			ByteArrayInputStream bais = new ByteArrayInputStream(request.await().imageData);
			BufferedImage img = ImageIO.read(bais);
			Image image = SwingFXUtils.toFXImage(img, null);
			
			ImageView map = new ImageView();
			map.setImage(image);
			map.autosize();
			map.setPickOnBounds(true);
			map.setPreserveRatio(true);
			
			centralVBox.getChildren().addAll(locationLabel ,map);
	    } catch(Exception e) {
	    	Label errorLabel = new Label(errorLabelMsg);
			errorLabel.setId(errorLabelId);
			centralVBox.getChildren().add(errorLabel);
	    }
	}
	
	public static EventBean getEventBean() {
		return eventBean;
	}

	public static void setEventBean(EventBean eventBean) {
		HostLocationPageController.eventBean = eventBean;
	}
	
}
