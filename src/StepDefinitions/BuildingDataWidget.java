package StepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asynchttpclient.Request;
import org.json.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import APIresources.APIPayloads;
import APIresources.APIUtils;
import Pages.ActiveSubmissionsPage;
import Pages.DataInsightsWidgetPage;
import Pages.LoginPage;
import Pojo.Assessed_Value;
import Pojo.BuildingDataRequest;
import Pojo.BuildingDataResponse;
import Pojo.ChangesInExposureVehicleType;
import Pojo.Equity;
import Pojo.Finances;
import Pojo.Improved_Value;
import Pojo.Land_Value;
import Pojo.Liabilities;
import Pojo.Owners;
import Pojo.Year;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
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
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BuildingDataWidget extends APIUtils {
	
	public WebDriver driver;
	public RequestSpecification buildingDataReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	 public Response response;

	
	
	
	 @Given("BuildingDataRequest Payload for building data")
	    public void buildingdatarequest_payload_for_building_data() throws Throwable {

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
	  
			  DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
			  
			  List<String> addressStrings = data.buildingDataAddress();
			  
			  System.out.println(addressStrings);
			  
			  BuildingDataRequest dataRequest = new BuildingDataRequest();
			  
			  dataRequest.setAccount_name(getGlobalValue("accountName").toUpperCase());
			  dataRequest.setStreet(addressStrings.get(0)+" "+addressStrings.get(1)+" "
			  +addressStrings.get(2)); dataRequest.setCity(addressStrings.get(3));
			  dataRequest.setState(addressStrings.get(4));
			  dataRequest.setZip_postal_code(addressStrings.get(5));
			  
			  req = new
			  RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(
			  ContentType.JSON).addHeader("Authorization", "Bearer "
			  +jsonPathEvaluator.getString("AuthenticationResult.IdToken")).build();
			  
			  buildingDataReq = given().spec(req).body(dataRequest);
			  
			 
	    	
	    }

	    @When("user calls BuildingDataAPI with post http request")
	    public void user_calls_something_with_post_http_request() throws Throwable {
	  	  res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response = buildingDataReq.when().post("precisely/building-data").then().spec(res).extract().response();
	      
	    }

	    @Then("BuildingDataAPI call got success with status code {int}")
	    public void api_call_got_success_with_something_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }


	    @And("check if the response body and UI of Building data matches")
	    public void check_if_the_response_body_and_uw_portal_building_data_matches() throws Throwable {
	    	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> buildingDataUIValues = data.getBuildingDataTableValues();
	    	System.out.println(buildingDataUIValues);
	    	
	    	BuildingDataResponse buildingDataResponse =  buildingDataReq.when().post("precisely/building-data").as(BuildingDataResponse.class);
	    	
	 //pbKey
			
			Assert.assertEquals(data.getpbKey(), buildingDataResponse.getPbKey());  				

	//ownerName
			Owners[] owner = buildingDataResponse.getOwners();
			
			Assert.assertEquals(data.getOwnerName(), owner[0].getOwnerName());  				

		//year	
			  Year year = buildingDataResponse.getYear(); 
			  Assert.assertEquals(buildingDataUIValues.get(1), year.getTax_year());  		
			  Assert.assertEquals(buildingDataUIValues.get(2), year.getMarket_year());
			  Assert.assertEquals(buildingDataUIValues.get(3), year.getAppraised_year());
			  
	//assedssed value		  
			   Assessed_Value assessed_Value = buildingDataResponse.getAssessed_value();
			  Assert.assertEquals(buildingDataUIValues.get(5),assessed_Value.getTax_value());
			  Assert.assertEquals(buildingDataUIValues.get(6),assessed_Value.getMarket_value()); 
			  Assert.assertEquals(buildingDataUIValues.get(7),assessed_Value.getAppraised_value());

			  //land value
			   Land_Value land_value = buildingDataResponse.getLand_value();
			  Assert.assertEquals(buildingDataUIValues.get(9),
			  land_value.getTax_land_value());
			  Assert.assertEquals(buildingDataUIValues.get(10),land_value.
			  getMarket_land_value());
			  Assert.assertEquals(buildingDataUIValues.get(11),
			  land_value.getAppraised_land_value());
		
			  //improved value
			 Improved_Value improved_value =
			  buildingDataResponse.getImproved_value();
			  Assert.assertEquals(buildingDataUIValues.get(13),
			  improved_value.getAssessedValueImp());
			  Assert.assertEquals(buildingDataUIValues.get(14),improved_value.
			  getMarketValueImp());
			  Assert.assertEquals(buildingDataUIValues.get(15),
			  improved_value.getAppraised_imp_value());
			  
			  //Replacement cost
			  
			  Assert.assertEquals(data.getReplacementCost(),
			  buildingDataResponse.getReplacement_cost());
			
	
	}
	    


  @After public void cleanUp() {
  System.out.println("Into the cleanUp method of LinkStatsStep...");
  releaseResources(this.driver); 
  
  }
  }
 
	
	
	


