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

public class ViabilityRatingWidget extends APIUtils{
	public WebDriver driver;
	public RequestSpecification propertyAttributeReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	 public Response response;
	 public String IdToken;
	
	   @Given("^RequestHeaders for ViabilityRating API call$")
	    public void requestheaders_for_viabilityrating_api_call() throws Throwable {
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

	    @When("^user calls ViabilityRating API with Get http request$")
	    public void user_calls_viabilityrating_api_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("dnb/financial-strength-insight/"+accountNumber).then().log().all().extract().response();

	    }

	    @Then("^ViabilityRating API call got success with status code 200$")
	    public void viabilityrating_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of ViabilityRating widget data matches$")
	    public void check_if_the_response_body_and_ui_of_viabilityrating_widget_data_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	     	Thread.sleep(4000);
	    	List<String> viabilityRatingValues = data.getViabilityRatingValues();
	    	System.out.println(viabilityRatingValues);
	    	
	     response =  request.when().get("dnb/financial-strength-insight/"+accountNumber).then().extract().response();
	     JsonPath json = response.jsonPath();
	/*  Map<String,String> str1 =  json.getMap("viability_rating");
	    
	 Set<Entry<String, String>> set1 = str1.entrySet();
	 System.out.println(set1);
	 Iterator<Entry<String, String>> iterate =set1.iterator();
	 
	 while(iterate.hasNext()) {
		 for(int j=0;j<viabilityRatingValues.size();j++) {
			 @SuppressWarnings("rawtypes")
			Map.Entry mp=(Map.Entry)iterate.next();
			 Assert.assertEquals(viabilityRatingValues.get(j), mp.getValue());
		 }
		 
	 }*/
	    
	     
	     Assert.assertEquals(viabilityRatingValues.get(0), json.getString("viability_rating.viability_rating"));
	     Assert.assertEquals(viabilityRatingValues.get(1), json.getString("viability_rating.viability_score"));
	     Assert.assertEquals(viabilityRatingValues.get(2), json.getString("viability_rating.portfolio_comparison"));
	     Assert.assertEquals(viabilityRatingValues.get(3), json.getString("viability_rating.data_depth"));
	     Assert.assertEquals(viabilityRatingValues.get(4), json.getString("viability_rating.organization_profile_rating"));
	     Assert.assertEquals(viabilityRatingValues.get(5), json.getString("viability_rating.risk_level"));
	     Assert.assertEquals(viabilityRatingValues.get(6), json.getString("viability_rating.percentage_of_total"));
	     Assert.assertEquals(viabilityRatingValues.get(7), json.getString("viability_rating.out_of_business_bad_rate"));
	     Assert.assertEquals(viabilityRatingValues.get(8), json.getString("viability_rating.cumulative_out_of_business_bad_rate"));
	     Assert.assertEquals(viabilityRatingValues.get(9), json.getString("viability_rating.risk_level"));
	     Assert.assertEquals(viabilityRatingValues.get(15), json.getString("viability_rating.description"));
	     Assert.assertEquals(viabilityRatingValues.get(10).replaceAll("[\\$\\,]", ""), json.getString("viability_rating.maximum_recommended_limit.value"));
	     Assert.assertEquals(viabilityRatingValues.get(11), json.getString("viability_rating.model_segment"));
	     Assert.assertEquals(viabilityRatingValues.get(12), json.getString("viability_rating.percentage_of_total"));
	     Assert.assertEquals(viabilityRatingValues.get(13), json.getString("viability_rating.out_of_business_bad_rate"));
	     Assert.assertEquals(viabilityRatingValues.get(14), json.getString("viability_rating.segment_bad_rate"));
	     Assert.assertEquals(viabilityRatingValues.get(16), json.getString("viability_rating.financial_data"));
	     Assert.assertEquals(viabilityRatingValues.get(17), json.getString("viability_rating.trade_data"));
	     Assert.assertEquals(viabilityRatingValues.get(18), json.getString("viability_rating.organization_size"));
	     Assert.assertEquals(viabilityRatingValues.get(19), json.getString("viability_rating.years_in_business"));
	     Assert.assertEquals(viabilityRatingValues.get(20), json.getString("viability_rating.viability_rating"));
	     Assert.assertEquals(viabilityRatingValues.get(21), json.getString("viability_rating.viability_score"));
	     Assert.assertEquals(viabilityRatingValues.get(22), json.getString("viability_rating.portfolio_comparison"));
	     Assert.assertEquals(viabilityRatingValues.get(23), json.getString("viability_rating.data_depth"));
	     Assert.assertEquals(viabilityRatingValues.get(24), json.getString("viability_rating.organization_profile_rating"));
	     
    
	     
	    }


}
