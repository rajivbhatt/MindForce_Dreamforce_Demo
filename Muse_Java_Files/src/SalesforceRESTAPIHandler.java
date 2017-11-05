import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class SalesforceRESTAPIHandler {
	
	public static void pushDataToSalesforce(float concentrationLevel ){
		try {
		HttpClient httpClient = new DefaultHttpClient();
	      HttpGet httpGetRequest = new HttpGet("https://musedemo-developer-edition.ap5.force.com/services/apexrest/concentration?level="+concentrationLevel);
	      
			HttpResponse httpResponse = httpClient.execute(httpGetRequest);
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
				
	     		
	}
	
	public static void pushNavigationDataToSalesforce(String directionStr ){
		try {
		HttpClient httpClient = new DefaultHttpClient();
	      HttpGet httpGetRequest = new HttpGet("https://musedemo-developer-edition.ap5.force.com/services/apexrest/NavigationDemo?direction="+directionStr);
	      
			HttpResponse httpResponse = httpClient.execute(httpGetRequest);
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
				
	     		
	}
	
	public static void pushBlinkDataToSalesforce( ){
		try {
		HttpClient httpClient = new DefaultHttpClient();
	      HttpGet httpGetRequest = new HttpGet("https://musedemo-developer-edition.ap5.force.com/services/apexrest/blinkDemo");
	      
			HttpResponse httpResponse = httpClient.execute(httpGetRequest);
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
				
	     		
	}

}
