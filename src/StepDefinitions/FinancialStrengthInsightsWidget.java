package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

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
import junit.framework.Assert;

public class FinancialStrengthInsightsWidget extends APIUtils {
	public WebDriver driver;
	public RequestSpecification buildingDataReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	 public Response response;
	 public String IdToken;
	
	
	 @Given("^RequestHeaders for FinancialStrengthInsights API call$")
	    public void requestheaders_for_financialstrengthinsights_api_call() throws Throwable {
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

	    @When("^user calls FinancialStrengthInsights API with Get http request$")
	    public void user_calls_financialstrengthinsights_api_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("dnb/financial-strength-insight/"+accountNumber).then().log().all().extract().response();
	    	
	    }

	    @Then("^FinancialStrengthInsights API call got success with status code 200$")
	    public void financialstrengthinsights_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of FinancialStrengthInsights widget data matches$")
	    public void check_if_the_response_body_and_ui_of_financialstrengthinsights_widget_data_matches() throws Throwable {
	     	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	     	Thread.sleep(4000);
	    	List<String> financialStrengthValues = data.getFinancialStrengthInsights();
	    	System.out.println(financialStrengthValues);
	    	
	     response =  request.when().get("dnb/financial-strength-insight/"+accountNumber).then().extract().response();
	     JsonPath json = response.jsonPath();
	     
	     //small financial insights 
	     Assert.assertEquals(financialStrengthValues.get(0), json.getString("delinquency_score.delienquency_score"));
	     Assert.assertEquals(financialStrengthValues.get(1), json.getString("failure_score.failure_score"));
	     Assert.assertEquals(financialStrengthValues.get(2), json.getString("financial_stress_score"));
	     Assert.assertEquals(financialStrengthValues.get(3), json.getString("paydex_score").replace(".0", ""));
	     Assert.assertEquals(financialStrengthValues.get(4), json.getString("cyber_risk").replace(".0", ""));
	     Assert.assertEquals(financialStrengthValues.get(5), json.getString("fraud_risk"));
	     
	     //lay-off scores
	     Assert.assertTrue(financialStrengthValues.get(12).contains(json.getString("layoff_score.class_score")));
	     Assert.assertTrue(financialStrengthValues.get(12).contains(json.getString("layoff_score.class_score_description")));
	     Assert.assertTrue(financialStrengthValues.get(13).contains(json.getString(("layoff_score.peer_class_score"))));
	     Assert.assertTrue(financialStrengthValues.get(13).contains(json.getString(("layoff_score.peer_class_score_description"))));
	     Assert.assertEquals(financialStrengthValues.get(15), json.getString("layoff_score.probability"));
	     Assert.assertEquals(financialStrengthValues.get(17), json.getString("layoff_score.national_risk_percentile"));
	     Assert.assertEquals(financialStrengthValues.get(19), json.getString("layoff_score.peer_risk_percentile"));
	     
	     //large financial strength insights
	     Assert.assertTrue(json.getString("delinquency_score.delienquency_score_description").contains(financialStrengthValues.get(20)));
	     Assert.assertTrue(json.getString("failure_score.failure_score_description").contains(financialStrengthValues.get(21)));
	     Assert.assertTrue(json.getString("layoff_score.class_score_description").contains(financialStrengthValues.get(22)));
	     Assert.assertTrue(json.getString("layoff_score.peer_class_score_description").contains(financialStrengthValues.get(23)));
	     Assert.assertEquals(financialStrengthValues.get(24), json.getString("layoff_score.probability"));
	     Assert.assertEquals(financialStrengthValues.get(25), json.getString("layoff_score.national_risk_percentile"));
	     Assert.assertEquals(financialStrengthValues.get(26), json.getString("layoff_score.peer_risk_percentile"));
	     
	     
	     
	   
	    	
	    }


}
