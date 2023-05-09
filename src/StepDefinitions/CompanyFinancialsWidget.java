package StepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
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
import Pojo.Assets;
import Pojo.BuildingDataRequest;
import Pojo.BuildingDataResponse;
import Pojo.Company_Financials;
import Pojo.Equity;
import Pojo.Finances;
import Pojo.Liabilities;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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

public class CompanyFinancialsWidget extends APIUtils {
	public WebDriver driver;
	public RequestSpecification request2;
	public RequestSpecification req;
	public ResponseSpecification res;
	 public Response response;

		
	
	 @Given("RequestHeaders for Company Financials API call")
	    public void requestheaders_for_something_call() throws Throwable {
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

	    @When("user calls CompanyFinancialsAPI with Get http request")
	    public void user_calls_financialratiosapi_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	  res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("dnb/financial-ratios/"+accountNumber+"").then().spec(res).extract().response();
	    }

	    @Then("CompanyFinancialsAPI call got success with status code {int}")
	    public void api_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("check if the response body and UI of Company Financials matches")
	    public void check_if_the_response_body_and_ui_of_company_financials_matches() throws Throwable {
	    
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> assets = data.getCompanyFinancialsAssets();
	    	List<String> liabilities = data.getCompanyFinancialsLiabilities();
	    	List<String> equities = data.getCompanyFinancialsEquity();
	    	
	    	Finances finances =  request2.when().get("dnb/financial-ratios/"+accountNumber+"").as(Finances.class);
	   
//Assets	    	
        	//Assets asset = new Assets();
	    	Assert.assertEquals(assets.get(0),finances.getCompany_financials().getAssets().getCash_and_liquid_assests());
	    	Assert.assertEquals(assets.get(1), finances.getCompany_financials().getAssets().getAccounts_receivable());
	    	Assert.assertEquals(assets.get(2), finances.getCompany_financials().getAssets().getInventory());
	    	Assert.assertEquals(assets.get(3), finances.getCompany_financials().getAssets().getOther_receivable());
	    	Assert.assertEquals(assets.get(4), finances.getCompany_financials().getAssets().getTotal_current_assets());
	    	Assert.assertEquals(assets.get(5), finances.getCompany_financials().getAssets().getTangible_assests());
	    	Assert.assertEquals(assets.get(6), finances.getCompany_financials().getAssets().getCapital_stock());
	    	Assert.assertEquals(assets.get(7), finances.getCompany_financials().getAssets().getFixed_assests());
	    	Assert.assertEquals(assets.get(8), finances.getCompany_financials().getAssets().getTotal_long_term_assests());
	    	Assert.assertEquals(assets.get(9), finances.getCompany_financials().getAssets().getIntangible_assests());
	    	Assert.assertEquals(assets.get(10), finances.getCompany_financials().getAssets().getTotal_assest());

//liabilities
	    	Liabilities liability = new Liabilities();
	    	Assert.assertEquals(liabilities.get(0),finances.getCompany_financials().getLiabilities().getAccount_payable());
	    	Assert.assertEquals(liabilities.get(1), finances.getCompany_financials().getLiabilities().getOther_current_liabilities());
	    	Assert.assertEquals(liabilities.get(2), finances.getCompany_financials().getLiabilities().getTotal_current_liabilities());
	    	Assert.assertEquals(liabilities.get(3), finances.getCompany_financials().getLiabilities().getLong_term_debt());
	    	Assert.assertEquals(liabilities.get(4), finances.getCompany_financials().getLiabilities().getTotal_long_term_liabilities());
	    	Assert.assertEquals(liabilities.get(5), finances.getCompany_financials().getLiabilities().getTotal_liabilities());
	    	
//equity
	    	Equity equity = new Equity();
	    	Assert.assertEquals(equities.get(0),finances.getCompany_financials().getEquity().getRetained_earnings());
	    	Assert.assertEquals(equities.get(1), finances.getCompany_financials().getEquity().getTangible_net_worth());
	    	Assert.assertEquals(equities.get(2), finances.getCompany_financials().getEquity().getTotal_equity());
	    	Assert.assertEquals(equities.get(3), finances.getCompany_financials().getEquity().getTotal_liabillity_equity());
	   
	    }
	    
	    @After public void cleanUp() {
	    	  System.out.println("Into the cleanUp method of LinkStatsStep...");
	    	  releaseResources(this.driver); 
	    }
}
