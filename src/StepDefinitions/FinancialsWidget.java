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

public class FinancialsWidget extends APIUtils {
	public WebDriver driver;
	public RequestSpecification request2;
	public RequestSpecification req;
	public ResponseSpecification res;
	 public Response response;
	
	 
	  @Given("RequestHeaders for Financials API call")
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

	    @When("user calls FinancialsAPI with Get http request")
	    public void user_calls_financialratiosapi_with_get_http_request() throws Throwable {
	   	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("dnb/financial-ratios/"+accountNumber+"").then().spec(res).extract().response();
	    }

	    @Then("FinancialsAPI call got success with status code {int}")
	    public void api_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("check if the response body and UI of Financials matches")
	    public void check_if_the_response_body_and_ui_of_financials_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> financials = data.getFinancials();
	    	
	    	Finances finances =  request2.when().get("dnb/financial-ratios/"+accountNumber+"").as(Finances.class);
	    	
	        Assert.assertEquals(financials.get(0),formatValue(Float.parseFloat(finances.getFinancial().getSales_revenues())).replace(" ", ""));
	     	Assert.assertEquals(financials.get(1).replace(".0", ""), formatValue(Float.parseFloat(finances.getFinancial().getCost_of_sales())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(2), formatValue(Float.parseFloat(finances.getFinancial().getWorking_capital())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(3), formatValue(Float.parseFloat(finances.getFinancial().getDividends())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(4), formatValue(Float.parseFloat(finances.getFinancial().getGross_profit())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(5), formatValue(Float.parseFloat(finances.getFinancial().getProfit_before_taxes())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(6),formatValue(Float.parseFloat(finances.getFinancial().getOperating_profit())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(7), formatValue(Float.parseFloat(finances.getFinancial().getNet_worth())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(8), formatValue(Float.parseFloat(finances.getFinancial().getNet_income())).replace(" ", ""));
	    	Assert.assertEquals(financials.get(9), formatValue(Float.parseFloat(finances.getFinancial().getNet_assets())).replace(" ", ""));
	  
	      
	    }
	    
	    @After public void cleanUp() {
	    	  System.out.println("Into the cleanUp method of LinkStatsStep...");
	    	  releaseResources(this.driver); 
	    }
}
