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

public class FilingAndEventsWidget extends APIUtils{
	
	
	public WebDriver driver;
	public RequestSpecification buildingDataReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	 public Response response;	
	 public String IdToken;
	
	 @Given("^RequestHeaders for FilingAndEvents API call$")
	    public void requestheaders_for_filingandevents_api_call() throws Throwable {
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

	    @When("^user calls FilingAndEvents API with Get http request$")
	    public void user_calls_filingandevents_api_with_get_http_request() throws Throwable {
	    	
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("dnb/filing-and-events/"+accountNumber).then().log().all().extract().response();
	      
	    }

	    @Then("^FilingAndEvents API call got success with status code 200$")
	    public void filingandevents_api_call_got_success_with_status_code_200() throws Throwable {

	    	Assert.assertEquals(response.getStatusCode(),200);
	        
	    }

	    @And("^check if the response body and UI of FilingAndEvents widget data matches$")
	    public void check_if_the_response_body_and_ui_of_filingandevents_widget_data_matches() throws Throwable {
	    	
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> general = data.getFilingEventsGeneral();
		    	List<String> financials = data.getFilingEventsFinancials();
		    	List<String> violations  = data.getFilingEventsViolations();
		    	
		    	System.out.println(general);
		    	System.out.println(financials);
		    	System.out.println(violations);
		    	
		    	response = request.when().get("dnb/filing-and-events/"+accountNumber).then().extract().response();
		    	JsonPath json = response.jsonPath();
		    		
		    	Assert.assertEquals(general.get(2), json.getString("general_events.legal_events"));
		    	Assert.assertEquals(general.get(3), json.getString("general_events.legal_open_status"));
				  Assert.assertEquals(general.get(8),
				  json.getString("general_events.lawsuits"));
				  Assert.assertEquals(general.get(9),
				  json.getString("general_events.lawsuits_active_or_on-going"));
				  Assert.assertEquals(general.get(10),
				  json.getString("general_events.lawsuits_open_status"));
				  Assert.assertEquals(general.get(11),
				  json.getString("general_events.lawsuits_last_12_months"));
				  Assert.assertEquals(general.get(15),
				  json.getString("general_events.judgements"));
				  Assert.assertEquals(general.get(16),
				  json.getString("general_events.judgements_active_or_on-going"));
				  Assert.assertEquals(general.get(17),
				  json.getString("general_events.judgements_last_12_months"));
				  Assert.assertEquals(general.get(20),
				  json.getString("general_events.financial_crime"));
				  Assert.assertEquals(general.get(21),
				  json.getString("general_events.financial_crime_active_or_on-going"));
				  Assert.assertEquals(general.get(24),
				  json.getString("general_events.debarments"));
				  Assert.assertEquals(general.get(25),
				  json.getString("general_events.debarments_active_or_on-going"));
				  Assert.assertEquals(general.get(27),
				  json.getString("general_events.total_number_of_all_legal_events"));
				  Assert.assertEquals(general.get(29),
				  json.getString("general_events.subject_to_other_legal_actions"));
				  
				  
				  Assert.assertEquals(financials.get(4), json.getString("financial_events.liens")); 
				  Assert.assertEquals(financials.get(5), json.getString("financial_events.liens_open_status"));
				  Assert.assertEquals(financials.get(6), json.getString("financial_events.liens_number_in_open_status"));
				  Assert.assertEquals(financials.get(7), json.getString("financial_events.liens_last_12_months"));
				  Assert.assertEquals(financials.get(11), json.getString("financial_events.bankruptcy"));
				  Assert.assertEquals(financials.get(12), json.getString("financial_events.bankruptcy_active_or_on-going"));
				  Assert.assertEquals(financials.get(13), json.getString("financial_events.bankruptcy_last_12_months"));
				  Assert.assertEquals(financials.get(16), json.getString("financial_events.financial_embarrassment"));
				  Assert.assertEquals(financials.get(17), json.getString("financial_events.financial_embarrassment_active_or_on-going"));
				  Assert.assertEquals(financials.get(22), json.getString("financial_events.claims"));
				  Assert.assertEquals(financials.get(23), json.getString("financial_events.claims_active_or_on-going"));
				  Assert.assertEquals(financials.get(24), json.getString("financial_events.claims_number_in_open_status"));
				  Assert.assertEquals(financials.get(25), json.getString("financial_events.claims_last_12_months"));
				  Assert.assertEquals(financials.get(27), json.getString("financial_events.insolvency"));
				  Assert.assertEquals(financials.get(29), json.getString("financial_events.liquidation"));
				  Assert.assertEquals(financials.get(31), json.getString("financial_events.suspension_of_payments"));
				  
				  Assert.assertEquals(violations.get(1), json.getString("violations_citations.environmental_protection_agency")); 
				  Assert.assertEquals(violations.get(3), json.getString("violations_citations.occupational_safety_and_health_administration"));
				  Assert.assertEquals(violations.get(5), json.getString("violations_citations.government_control_list"));
				  Assert.assertEquals(violations.get(7), json.getString("violations_citations.canadian_environmental_protection_agency"));
				  
				  Assert.assertEquals(violations.get(9), json.getString("financing_events.financing_events_with_open_status")); 
				  Assert.assertEquals(violations.get(11), json.getString("financing_events.financing_mortages_or_charges_recorded_with_dnb_in_last_120_months"));
				  Assert.assertEquals(violations.get(13), json.getString("financing_events.financing_liens_recorded_in_last_12_months"));
				  Assert.assertEquals(violations.get(15), json.getString("financing_events.financing_events_covered_by_this_summary"));
				  
				  Assert.assertEquals(violations.get(17), json.getString("significant_events.changes_to_location_atleast_ince_to_a_different_address"));
				  Assert.assertEquals(violations.get(19), json.getString("significant_events.events_impacting_ability_to_trade"));
				  Assert.assertEquals(violations.get(21), json.getString("significant_events.operational_changes"));
				  Assert.assertEquals(violations.get(23), json.getString("significant_events.disaster"));
				  Assert.assertEquals(violations.get(25), json.getString("significant_events.burglary_or_theft"));
				  Assert.assertEquals(violations.get(27), json.getString("significant_events.fire"));
				  
			  
				      	
  	
	       
	    }

}
