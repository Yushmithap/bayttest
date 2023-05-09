package StepDefinitions;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import Pojo.FacilityLocationPhotoReference;
import io.cucumber.java.After;
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
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class FacilityLocationPhotoReferenceWidget extends APIUtils {
	
	public WebDriver driver;
	public RequestSpecification request2;
	public RequestSpecification req;
	public Response res;
	 public Response response;
	 
	
	 @Given("^RequestHeaders for Facility Location Photo Reference API call$")
	    public void requestheaders_for_facility_location_photo_reference_api_call() throws Throwable {
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

	    @When("^user calls FacilityLocationPhotoReferenceAPI with Post http request$")
	    public Response user_calls_facilitylocationphotoreferenceapi_with_get_http_request() throws Throwable {
	    	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	String accountName = "Spectrum Label Corporation";
			
			if(data.presenceOfSingleFacilityLocation()==true) {
				
			List<String> facilityLocationTitle = data.verifyFacilityLocationsWidgetData();		
			
			
			String jsonPayload = accountName.toUpperCase()+", "+facilityLocationTitle.get(0);	
			System.out.println(jsonPayload);
			FacilityLocationPhotoReference payload = new FacilityLocationPhotoReference();
			payload.setLocation(jsonPayload);
			
			 res = request2.body(payload).when().post("widget/premise/photo-reference").then().log().all().extract().response();
			// Thread.sleep(3000);
			
			return res;
		
			
			
			}
			else{
				List<String> multiFacilityLocation = data.getMultipleLocationTitles();
				int k = -1;
				
				for(int j=0;j<multiFacilityLocation.size();j++) {
					String jsonPayload = accountName.toUpperCase()+", "+multiFacilityLocation.get(0);
					System.out.println(jsonPayload);
					FacilityLocationPhotoReference payload = new FacilityLocationPhotoReference();
					payload.setLocation(jsonPayload);
					
				//	RestAssured.baseURI =  "https://api.uw-dev.cognitiveinsurance.accenture.com";
					
					 res = request2.body(payload).when().post("widget/premise/photo-reference").then().log().all().extract().response();
						
					
					List<WebElement> facilitySelect = data.clickToSelectFacilityLocation();
					k++;
					facilitySelect.get(k).click();
					Thread.sleep(3000);
				

				}
				return res;
			}
	    	
	    	
	    }

	    @Then("^FacilityLocationPhotoReferenceAPI call got success with status code 200$")
	    public void facilitylocationphotoreferenceapi_call_got_success_with_status_code_200() throws Throwable {
	    	
	    	Assert.assertEquals(res.getStatusCode(),200);
	    }
	    
	    @After public void cleanUp() {
	    	  System.out.println("Into the cleanUp method of LinkStatsStep...");
	    	  releaseResources(this.driver); 
	    }

	

}
