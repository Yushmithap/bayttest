package StepDefinitions;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import APIresources.APIUtils;
import Pages.ActiveSubmissionsPage;
import Pages.DataInsightsWidgetPage;
import Pojo.ChangesInExposureLocation;
import Pojo.OccupancyChange;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;



public class OccupancyExposureChange extends APIUtils {
	
	public WebDriver driver;
	public RequestSpecification request;
	public RequestSpecification req;
	public RequestSpecification request2;
	public RequestSpecification req2;
	public ResponseSpecification res;
	 public Response response;
	 public String[] occupancyType = {"square_footage","number_of_rooms"};
	 public String IdToken ;
	
	 @Given("^RequestHeaders for OccupancyExposureChange API call$")
	    public void requestheaders_for_occupancyexposurechange_api_call() throws Throwable {
		 System.setProperty("webdriver.chrome.driver", getGlobalValue("chromePath"));
		 ChromeOptions options = new ChromeOptions();		 
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			//options.addArguments("user-data-dir=C:\\Users\\pulkit.parmar\\desktop\\User Data");
			//options.addArguments("profile-directory=Profile 3");
			options.addArguments("--start-maximized");
			WebDriver driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			this.driver = driver;
			PageFactory.initElements(driver, this);
			
		RestAssured.baseURI = "https://cognito-idp.us-east-2.amazonaws.com/";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("AuthFlow", "USER_PASSWORD_AUTH"); 
		requestParams.put("ClientId", "5dgm5ma9a9gjstrd7vrc7vfnv5"); 
		Map<String,String> AuthParameters = new HashMap<>();
		AuthParameters.put("USERNAME", "testuser");
		AuthParameters.put("PASSWORD", "Testuser@123");
		requestParams.put("AuthParameters" , AuthParameters);
		request.body(requestParams.toString());
		Response response =
	        RestAssured.given()
	            .header("x-amz-target","AWSCognitoIdentityProviderService.InitiateAuth")
	            .header("content-type","application/x-amz-json-1.1")
	            .baseUri("https://cognito-idp.us-east-2.amazonaws.com/")
	            .body(requestParams.toString())
	            .log()
	            .body()
	       .when()
	       .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("application/x-amz-json-1.1", ContentType.JSON)))
	       .post()
	        .then()
	            .log().all().extract().response();
	

	/*	//requestParams.put("ClientMetadata", "5dgm5ma9a9gjstrd7vrc7vfnv5");
		Response response = request.post("");
		JsonPath jsonPathEvaluator = response.jsonPath();*/
		System.out.println(response);
		JsonPath jsonPathEvaluator = response.jsonPath();
		String AccessToken = jsonPathEvaluator.getString("AuthenticationResult.AccessToken");
		IdToken = jsonPathEvaluator.getString("AuthenticationResult.IdToken");
		String RefreshToken = jsonPathEvaluator.getString("AuthenticationResult.RefreshToken");

		driver.get(getGlobalValue("appURL"));
	//	Thread.sleep(10000);
		WebStorage webStorage = (WebStorage) new Augmenter().augment(driver);
		LocalStorage local = webStorage.getLocalStorage(); 
		local.setItem("CognitoIdentityServiceProvider.2371dpc1omvsrm6ckqfo26b3a6.azure-ad-production_8dut4w-5kgza9qp6qh3v-zo8zyv_mjwpu_zzjug2kc8.refreshToken",RefreshToken);
		local.setItem("CognitoIdentityServiceProvider.2371dpc1omvsrm6ckqfo26b3a6.azure-ad-production_8dut4w-5kgza9qp6qh3v-zo8zyv_mjwpu_zzjug2kc8.idToken",IdToken);
		local.setItem("CognitoIdentityServiceProvider.2371dpc1omvsrm6ckqfo26b3a6.azure-ad-production_8dut4w-5kgza9qp6qh3v-zo8zyv_mjwpu_zzjug2kc8.accessToken",AccessToken);
		local.setItem("CognitoIdentityServiceProvider.2371dpc1omvsrm6ckqfo26b3a6.LastAuthUser", "azure-ad-production_8dut4w-5kgza9qp6qh3v-zo8zyv_mjwpu_zzjug2kc8");
		Thread.sleep(10000);

	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	  
	  activeSubmission.selectSubmissionInState7(getGlobalValue("accountName")); 
	  Thread.sleep(5000);
	  
	  activeSubmission.clickOnDataInsights(); 
	  Thread.sleep(9000);
		 
	    }

	    @When("^user calls OccupancyExposureChangeAPI with Get http request$")
	    public void user_calls_occupancyexposurechangeapi_with_get_http_request() throws Throwable {
	    	
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).
				  addQueryParam("type", occupancyType[0]).  
				  build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("exposure/occupancy/" + accountNumber + "").then().log().all().extract().response();
	    }

	    @Then("^OccupancyExposureChangeAPI call got success with status code 200$")
	    public void occupancyexposurechangeapi_call_got_success_with_status_code_200() throws Throwable {
	    	
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    	
	    }

	    @And("^check if the response body and UI of OccupancyExposureChange table matches$")
	    public void check_if_the_response_body_and_ui_of_occupancyexposurechange_table_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> occupancyData = dataInsight.getOccupancyChangeValues();
			
			
			OccupancyChange[] changesInOccupancy =  request.when().get("exposure/occupancy/" + accountNumber + "").as(OccupancyChange[].class);
		
			int countOfActualValues = occupancyData.size();
			int i =0;
			int n=0;
			int o=0;
			int p=0;
			
		//occupancy
				for(int j=0;j<countOfActualValues ;j=j+4) {
				Assert.assertEquals(occupancyData.get(j), changesInOccupancy[i].getOccupancy() );
				i++;	    
				}
		
		//current_sq_ft
				  for(int j=1;j<countOfActualValues ;j=j+4) {
					  Assert.assertEquals(occupancyData.get(j), changesInOccupancy[n].getCurrent_sq_ft());
				  n++;
				  } 
				  
		//previous_sq_ft
				  for(int j=2;j<countOfActualValues ;j=j+4) {
					  Assert.assertEquals(occupancyData.get(j), changesInOccupancy[o].getPrevious_sq_ft());
				  o++;
				  } 
				  
		//change
				  for(int j=3;j<countOfActualValues ;j=j+4) {
					  Assert.assertEquals(occupancyData.get(j), changesInOccupancy[p].getChange());
				  p++;
				  } 
				  
				//type = number_of_rooms
				   dataInsight.clickOnButtonToChangeOccupancy("Number of Rooms");
				   
				   Thread.sleep(3000);
				   
				 	DataInsightsWidgetPage dataInsight2 = new DataInsightsWidgetPage(driver);
					List<String> occupancyData2 = dataInsight2.getOccupancyChangeValues();
					
					
					req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL"))
							.setContentType(ContentType.JSON).
							 addQueryParam("type", occupancyType[1]).
							  addHeader("Authorization", "Bearer " +IdToken).
							  build();
						  request =  given().log().all().spec(req);
					
					OccupancyChange[] changesInOccupancy2 =  request.when().get("exposure/occupancy/"+accountNumber).as(OccupancyChange[].class);
				
					int countOfActualValues2 = occupancyData2.size();
					int i2 =0;
					int n2=0;
					int o2=0;
					int p2=0;
					
				//occupancy
						for(int j=0;j<countOfActualValues2 ;j=j+4) {
						Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[i2].getOccupancy() );
						i2++;	    
						}
				
				//current_sq_ft
						  for(int j=1;j<countOfActualValues2 ;j=j+4) {
							  Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[n2].getCurrent_number_of_rooms());
						  n2++;
						  } 
						  
				//previous_sq_ft
						  for(int j=2;j<countOfActualValues2 ;j=j+4) {
							  Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[o2].getPrevious_number_of_rooms());
						  o2++;
						  } 
						  
				//change
						  for(int j=3;j<countOfActualValues2 ;j=j+4) {
							  Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[p2].getChange());
						  p2++;
						  } 
						
				   
			
	    	
	    	
	    	
	    }


}
