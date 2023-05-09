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
import Pojo.ChangesInExposureLocation;
import Pojo.ExposureClassCode;
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

public class ChangesInExposureClassCode extends APIUtils {
	public WebDriver driver;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	public RequestSpecification request2;
	 public Response response;
	public String IdToken;	
	
	 @Given("^RequestHeaders for ChangeInExposureClassCode API call$")
	    public void requestheaders_for_changeinexposureclasscode_api_call() throws Throwable {
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

	    @When("^user calls ChangeInExposureClassCode API with Get http request$")
	    public void user_calls_changeinexposureclasscode_api_with_get_http_request() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addQueryParam("type", "payroll"). 
				  addHeader("ContentType", "json/text"). addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("exposure/class-code/"+accountNumber).then().log().all().extract().response();
	      
	    }

	    @Then("^ChangeInExposureClassCode API call got success with status code 200$")
	    public void changeinexposureclasscode_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of ChangeInExposureClassCode widget data matches$")
	    public void check_if_the_response_body_and_ui_of_changeinexposureclasscode_widget_data_matches() throws Throwable {
	    	
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> actualDataOfPayroll = dataInsight.getChangesInExposureClassCodeValues();
			
			
			ExposureClassCode[] exposureClassCode =  request.when().get("exposure/class-code/"+accountNumber).as(ExposureClassCode[].class);
		
			int countOfActualValues = actualDataOfPayroll.size();
			int i =0;
			int n=0;
			int o=0;
			int p=0;
			
		//class code
				for(int j=0;j<countOfActualValues ;j=j+4) {
				Assert.assertEquals(actualDataOfPayroll.get(j), exposureClassCode[i].getClass_code());
				i++;	    
				}
		
		//current_payroll
				  for(int k=1;k<countOfActualValues ;k=k+4) {
				  Assert.assertEquals(actualDataOfPayroll.get(k),exposureClassCode[n].getCurrent_payroll());
				  n++;
				  } 
			
		//previous_payroll
				  for(int l=2;l<countOfActualValues ;l=l+4) {
				  Assert.assertEquals(actualDataOfPayroll.get(l), exposureClassCode[o].getPrevious_payroll());
				  o++;
				  } 
				  
		//change		  
				  for(int m=3;m<countOfActualValues ;m=m+4) {
				  Assert.assertEquals(actualDataOfPayroll.get(m), exposureClassCode[p].getChange());
				  p++;
				  }
		
		//type = headcount
	   dataInsight.clickOnClassCodeDropdown("Head Count");
	   
	   Thread.sleep(3000);
	   
	   DataInsightsWidgetPage dataInsight2 = new DataInsightsWidgetPage(driver);
		List<String> actualDataOfHeadCount = dataInsight2.getChangesInExposureClassCodeValues();
		
		  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type","head_count").addHeader("Authorization", "Bearer " +IdToken).build();
		  
		  request2 =  given().spec(req);
		
			ExposureClassCode[] exposureClassCode2 =  request2.when().get("exposure/class-code/"+accountNumber).as(ExposureClassCode[].class);
			
			int countOfActualValues2 = actualDataOfHeadCount.size();
			int q =0;
			int r=0;
			int s=0;
			int t=0;
			
		//class code
				for(int j=0;j<countOfActualValues2 ;j=j+4) {
				Assert.assertEquals(actualDataOfHeadCount.get(j), exposureClassCode2[q].getClass_code());
				q++;	    
				}
		
		//current_headcount
				  for(int k=1;k<countOfActualValues2 ;k=k+4) {
				  Assert.assertEquals(actualDataOfHeadCount.get(k),exposureClassCode2[r].getCurrent_head_count());
				  r++;
				  } 
			
		//previous_headcount
				  for(int l=2;l<countOfActualValues2 ;l=l+4) {
				  Assert.assertEquals(actualDataOfHeadCount.get(l), exposureClassCode2[s].getPrevious_head_count());
				  s++;
				  } 
				  
		//change		  
				  for(int m=3;m<countOfActualValues2 ;m=m+4) {
				  Assert.assertEquals(actualDataOfHeadCount.get(m), exposureClassCode2[t].getChange());
				  t++;
				  }
	

	    }

}
