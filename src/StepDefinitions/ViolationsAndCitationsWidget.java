package StepDefinitions;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

public class ViolationsAndCitationsWidget extends APIUtils {
	
	public WebDriver driver;
	public RequestSpecification buildingDataReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	 public Response response;	
	 public String IdToken;
	 
	 @Given("^RequestHeaders for ViolationsAndCitations API call$")
	    public void requestheaders_for_violationsandcitations_api_call() throws Throwable {
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

	    @When("^user calls ViolationsAndCitations API with Get http request$")
	    public void user_calls_violationsandcitations_api_with_get_http_request() throws Throwable {
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("dnb/filing-and-events/"+accountNumber).then().log().all().extract().response();
	      
	    }

	    @Then("^ViolationsAndCitations API call got success with status code 200$")
	    public void violationsandcitations_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(),200);
	    }

	    @SuppressWarnings("rawtypes")
		@And("^check if the response body and UI of ViolationsAndCitations widget data matches$")
	    public void check_if_the_response_body_and_ui_of_violationsandcitations_widget_data_matches() throws Throwable {
	    	

	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> violations  = data.getViolationsAndCitations();
		    	
		    	
		    	response = request.when().get("dnb/filing-and-events/"+accountNumber).then().extract().response();
		    	JsonPath json = response.jsonPath();
		    	Map<String, String> mp = json.getMap("violations_citations");
		    	Set<Entry<String, String>> mpSet = mp.entrySet();
		    	Iterator<Entry<String, String>> it=mpSet.iterator();
		    	
		    	while(it.hasNext()) {
		    		for(int j=0;j<violations.size();j++) {
		    			Map.Entry mp2=(Map.Entry)it.next();    		
		    			Assert.assertEquals(violations.get(j), mp2.getValue().toString());	    			
		    		}
		    		
		    	}
		    	
		    	
	    	
	    	
	    }

}
