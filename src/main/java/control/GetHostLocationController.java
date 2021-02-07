package control;

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
import com.google.maps.errors.ApiException;
import com.google.maps.model.Size;

public class GetHostLocationController {
	
	private static final String API_KEY = "AIzaSyDr2a8rEFBOGJ1xyodMMkE97F-rbZPCJXw";

	public BufferedImage getHostLocation(String location, int width, int height) throws ApiException, InterruptedException, IOException {
		BufferedImage image;
		
		GeoApiContext context = new GeoApiContext.Builder().apiKey(API_KEY).build();
		StaticMapsRequest request = StaticMapsApi.newRequest(context, new Size(width, height));
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
	    
	    ByteArrayInputStream bais = new ByteArrayInputStream(request.await().imageData);
		image = ImageIO.read(bais);
		
		return image;
	}

	public static String getApiKey() {
		return API_KEY;
	}
	
}
