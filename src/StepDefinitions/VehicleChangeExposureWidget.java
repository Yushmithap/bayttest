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
import Pojo.ChangesInExposureVehicleType;
import Pojo.Finances;
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

public class VehicleChangeExposureWidget extends APIUtils {
	public WebDriver driver;
	public RequestSpecification request2;
	public RequestSpecification req;
	public ResponseSpecification res;
	 public Response response;
	
	@Given("^RequestHeaders for Changes In exposure vehicle type API call$")
    public void requestheaders_for_changes_in_exposure_vehicle_type_api_call() throws Throwable {
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

    @When("^user calls ChangesInExposureVehicletypeAPI with Get http request$")
    public void user_calls_changesinexposurevehicletypeapi_with_get_http_request() throws Throwable {
    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
    	String accountNumber = activeSubmission.getSubmissionNumber();
  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    	response =request2.when().get("exposure/vehicle/" + accountNumber + "").then().spec(res).extract().response();
        
    }

    @Then("ChangesInExposureVehicletypeAPI call got success with status code {int}")
    public void changesinexposurevehicletypeapi_call_got_success_with_status_code_200(int int1) throws Throwable {
    	Assert.assertEquals(response.getStatusCode(), int1);
    }

    @And("^check if the response body and UI of Changes In exposure vehicle type matches$")
    public void check_if_the_response_body_and_ui_of_changes_in_exposure_vehicle_type_matches() throws Throwable {
    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
    	String accountNumber = activeSubmission.getSubmissionNumber();
    	
    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
		List<String> actualValues = dataInsight.verifyChangesInExposureVehicleTypeWidgetData();
	
		ChangesInExposureVehicleType[] changesInExposureVehicle =  request2.when().get("exposure/vehicle/" + accountNumber + "").as(ChangesInExposureVehicleType[].class);
		
		int count = changesInExposureVehicle.length;
		int countOfActualValues = actualValues.size();
		int i =0;
		int n=0;
		int o=0;
		int p=0;
		
	//vehicleType
			for(int j=0;j<countOfActualValues ;j=j+4) {
			Assert.assertEquals(actualValues.get(j), changesInExposureVehicle[i].getVehicleType() );
			i++;	    
			}
	
	//currentYearPower
			  for(int k=1;k<countOfActualValues ;k=k+4) {
			  Assert.assertEquals(actualValues.get(k),changesInExposureVehicle[n].getCurrentYearPower());
			  n++;
			  } 
		
	//previousYearPower		  
			  for(int l=2;l<countOfActualValues ;l=l+4) {
			  Assert.assertEquals(actualValues.get(l), changesInExposureVehicle[o].getPreviousYearPower() );
			  o++;
			  } 
	
	//change		  
			  for(int m=3;m<countOfActualValues ;m=m+4) {
			  Assert.assertEquals(actualValues.get(m), changesInExposureVehicle[p].getChange() );
			  p++;
			  }
			 
		
    }
    
    @After public void cleanUp() {
    	  System.out.println("Into the cleanUp method of LinkStatsStep...");
    	  releaseResources(this.driver); 
    }

}
