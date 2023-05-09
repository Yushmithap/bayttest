package APIresources;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import Pages.ActiveSubmissionsPage;
import Pages.DataInsightsWidgetPage;
import Pojo.BuildingDataRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class APIPayloads extends APIUtils {
	
	public WebDriver driver;
	


public BuildingDataRequest buildingDataPayload() throws IOException, InterruptedException  {
	
	DataInsightsWidgetPage dataInsightsPage = new DataInsightsWidgetPage(driver);
    
     
	  List<String> addressStrings = dataInsightsPage.buildingDataAddress();
	  
	  System.out.println(addressStrings);
	  
	  BuildingDataRequest dataRequest = new BuildingDataRequest();
	
	  dataRequest.setAccount_name(getGlobalValue("accountName").toUpperCase());
	  dataRequest.setStreet(addressStrings.get(0)+" "+addressStrings.get(1)+" "+addressStrings.get(2)); 
	  dataRequest.setCity(addressStrings.get(3));
	  dataRequest.setState(addressStrings.get(4));
	  dataRequest.setZip_postal_code(addressStrings.get(5));
	  
	  return dataRequest;
}

	

}
