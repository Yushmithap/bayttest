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
import Pojo.FacilityLocations;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class FacilityLocation extends APIUtils {
	public WebDriver driver;
	public RequestSpecification request2;
	public RequestSpecification req;
	public ResponseSpecification res;
	 public Response response;
	
	 @Given("^RequestHeaders for Facility Location API call$")
	    public void requestheaders_for_facility_location_api_call() throws Throwable {

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
		String IdToken = jsonPathEvaluator.getString("AuthenticationResult.IdToken");
		String RefreshToken = jsonPathEvaluator.getString("AuthenticationResult.RefreshToken");

		driver.get("https://uw-dev.cognitiveinsurance.accenture.com/landing");
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
	
			  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +jsonPathEvaluator.getString("AuthenticationResult.IdToken")).build();
					  
			  request2 =  given().spec(req);
		 
	       
	    }

	    @When("^user calls FacilityLocationAPI with Get http request$")
	    public void user_calls_facilitylocationapi_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("widget/submission/" + accountNumber + "/premise").then().spec(res).extract().response();
	    	
	    }

	    @Then("FacilityLocationAPI call got success with status code {int}")
	    public void facilitylocationapi_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("^check if the response body and UI of Facility Location matches$")
	    public void check_if_the_response_body_and_ui_of_facility_location_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();

			FacilityLocations[] facilityLocations = request2.when().log().all().get("widget/submission/" + accountNumber + "/premise").as(FacilityLocations[].class);
			
		//	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
		//	   List<String> actualLocationValues = dataInsight.verifyFacilityLocationsWidgetData();
	//		 List<String> actualLocationTitles = dataInsight.getMultipleLocationTitles()  ;
	//	   List<String> actualLocationMultipleValues = dataInsight.verifyMultipleFacilityLocationsWidgetData();
		  
			int countOfObjects = facilityLocations.length;
		
			if(countOfObjects==1) {
				DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
				   List<String> actualLocationValues = dataInsight.verifyFacilityLocationsWidgetData();
				   
				  Assert.assertTrue(actualLocationValues.get(0).contains(facilityLocations[0].getStreet()+", "+facilityLocations[0].getCity()+", "+facilityLocations[0].getState()));
				  Assert.assertEquals(actualLocationValues.get(1), facilityLocations[0].getPremise_flag());    
				  Assert.assertTrue(actualLocationValues.get(2).equalsIgnoreCase(facilityLocations[0].getOwnership_type()));
				  Assert.assertEquals(actualLocationValues.get(3), facilityLocations[0].getOccupancy_perc());
				 Assert.assertEquals(actualLocationValues.get(4), facilityLocations[0].getYear());
				 Assert.assertEquals(actualLocationValues.get(5), facilityLocations[0].getEmployee_count());
				 Assert.assertTrue(actualLocationValues.get(6).equalsIgnoreCase(facilityLocations[0].getNature_of_business()));
		}
		else if(countOfObjects>1) {
			DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			 List<String> actualLocationTitles = dataInsight.getMultipleLocationTitles()  ;
			   List<String> actualLocationMultipleValues = dataInsight.verifyMultipleFacilityLocationsWidgetData();
				  int i=0;
				  int i2 = 0;
				  int i3 = 0;
				  int i4 = 0;
				  int i5 = 0;
				  int i6 = 0;
				  int i7 = 0;
			      
				   for(int j=0;j<actualLocationTitles.size();j++) {
		   	  Assert.assertTrue(actualLocationTitles.get(j).contains(facilityLocations[i].getStreet()+", "+facilityLocations[i].getCity()+", "+facilityLocations[i].getState()+", "+facilityLocations[i].getCountry()+", "+facilityLocations[i].getZip_postal_code()));
		   	  i++;
				   }
				   for(int j=0;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i2].getPremise_flag());  
				   i2++;
				}
			 for(int j=1;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i3].getOwnership_type() );
			 i3++;
	     	}
			 for(int j=2;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i4].getOccupancy_perc());
			  i4++;
			 }
			 for(int j=3;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i5].getYear());
			  i5++;
			 }
			 for(int j=4;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i6].getNature_of_business());
			  i6++;
			 }
			 for(int j=5;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i7].getEmployee_count());
			i7++;
			 }
			   }
	    }
	    
	    @After public void cleanUp() {
	    	  System.out.println("Into the cleanUp method of LinkStatsStep...");
	    	  releaseResources(this.driver); 

	    }
}
