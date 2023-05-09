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
import Pojo.ExposureClassCode;
import Pojo.ExposureState;
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

public class ChangesInExposureState extends APIUtils {
	public WebDriver driver;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	public RequestSpecification request2;
	 public Response response;
	public String IdToken;	
	
	  @Given("^RequestHeaders for ChangeInExposureState API call$")
	    public void requestheaders_for_changeinexposurestate_api_call() throws Throwable {
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

	    @When("^user calls ChangeInExposureState API with Get http request$")
	    public void user_calls_changeinexposurestate_api_with_get_http_request() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addQueryParam("type", "tiv"). 
				  addHeader("ContentType", "json/text"). addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("exposure/state/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^ChangeInExposureState API call got success with status code 200$")
	    public void changeinexposurestate_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of ChangeInExposureState widget data matches$")
	    public void check_if_the_response_body_and_ui_of_changeinexposurestate_widget_data_matches() throws Throwable {
	    	
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> actualDataOfTIV = dataInsight.getChangesInExposureStateValues();
			
			
			ExposureState[] exposureState =  request.when().get("exposure/state/"+accountNumber).as(ExposureState[].class);
		
			int countOfActualValues = actualDataOfTIV.size();
			int i =0;
			int n=0;
			int o=0;
			int p=0;
			
		//state
				for(int j=0;j<countOfActualValues ;j=j+4) {
				Assert.assertEquals(actualDataOfTIV.get(j), exposureState[i].getState());
				i++;	    
				}
		
		//current_tiv
				  for(int k=1;k<countOfActualValues ;k=k+4) {
				  Assert.assertEquals(actualDataOfTIV.get(k),exposureState[n].getCurrent_tiv());
				  n++;
				  } 
			
		//previous_tiv
				  for(int l=2;l<countOfActualValues ;l=l+4) {
				  Assert.assertEquals(actualDataOfTIV.get(l), exposureState[o].getPrevious_tiv());
				  o++;
				  } 
				  
		//change		  
				  for(int m=3;m<countOfActualValues ;m=m+4) {
				  Assert.assertEquals(actualDataOfTIV.get(m), exposureState[p].getChange());
				  p++;
				  }
		
	
	    dataInsight.clickOnStateDropdown("Payroll");
			    		   
	   Thread.sleep(3000);
	   
	   DataInsightsWidgetPage dataInsight2 = new DataInsightsWidgetPage(driver);
		List<String> actualDataOfPayroll = dataInsight2.getChangesInExposureStateValues();
		
		  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type","payroll").addHeader("Authorization", "Bearer " +IdToken).build();
		  
		  request =  given().spec(req);
		
			ExposureState[] exposureState2 =  request.when().get("exposure/state/"+accountNumber).as(ExposureState[].class);
			
			int countOfActualValues2 = actualDataOfPayroll.size();
			int q =0;
			int r=0;
			int s=0;
			int t=0;
			
		//state
				for(int j=0;j<countOfActualValues2 ;j=j+4) {
				Assert.assertEquals(actualDataOfPayroll.get(j), exposureState2[q].getState());
				q++;	    
				}
		
		//current_tiv
				  for(int k=1;k<countOfActualValues2 ;k=k+4) {
				  Assert.assertEquals(actualDataOfPayroll.get(k),exposureState2[r].getCurrent_payroll());
				  r++;
				  } 
			
		//previous_tiv
				  for(int l=2;l<countOfActualValues2 ;l=l+4) {
				  Assert.assertEquals(actualDataOfPayroll.get(l), exposureState2[s].getPrevious_payroll());
				  s++;
				  } 
				  
		//change		  
				  for(int m=3;m<countOfActualValues2 ;m=m+4) {
				  Assert.assertEquals(actualDataOfPayroll.get(m), exposureState2[t].getChange());
				  t++;
				  }
	    	
				  dataInsight.clickOnStateDropdown("Head Count");
	    		   
				   Thread.sleep(3000);
				   
				   DataInsightsWidgetPage dataInsight3 = new DataInsightsWidgetPage(driver);
					List<String> actualDataOfHeadCount = dataInsight3.getChangesInExposureStateValues();
					
					  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type","head_count").addHeader("Authorization", "Bearer " +IdToken).build();
					  
					  request =  given().spec(req);
					
						ExposureState[] exposureState3 =  request.when().get("exposure/state/"+accountNumber).as(ExposureState[].class);
						
						int countOfActualValues3 = actualDataOfHeadCount.size();
						int q2 =0;
						int r2=0;
						int s2=0;
						int t2=0;
						
					//state
							for(int j=0;j<countOfActualValues3 ;j=j+4) {
							Assert.assertEquals(actualDataOfHeadCount.get(j), exposureState3[q2].getState());
							q2++;	    
							}
					
					//current_headcount
							  for(int k=1;k<countOfActualValues3 ;k=k+4) {
							  Assert.assertEquals(actualDataOfHeadCount.get(k),exposureState3[r2].getCurrent_head_count());
							  r2++;
							  } 
						
					//previous_headcount
							  for(int l=2;l<countOfActualValues3 ;l=l+4) {
							  Assert.assertEquals(actualDataOfHeadCount.get(l), exposureState3[s2].getPrevious_head_count());
							  s2++;
							  } 
							  
					//change		  
							  for(int m=3;m<countOfActualValues3 ;m=m+4) {
							  Assert.assertEquals(actualDataOfHeadCount.get(m), exposureState3[t2].getChange());
							  t2++;
							  }
				    	
							  dataInsight.clickOnStateDropdown("Sales/Revenue");
				    		   
							   Thread.sleep(3000);
							   
							   DataInsightsWidgetPage dataInsight4 = new DataInsightsWidgetPage(driver);
								List<String> actualDataOfSalesRevenue = dataInsight4.getChangesInExposureStateValues();
								
								  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type","sales_revenue").addHeader("Authorization", "Bearer " +IdToken).build();
								  
								  request =  given().spec(req);
								
									ExposureState[] exposureState4 =  request.when().get("exposure/state/"+accountNumber).as(ExposureState[].class);
									
									int countOfActualValues4 = actualDataOfSalesRevenue.size();
									int q3 =0;
									int r3=0;
									int s3=0;
									int t3=0;
									
								//state
										for(int j=0;j<countOfActualValues4 ;j=j+4) {
										Assert.assertEquals(actualDataOfSalesRevenue.get(j), exposureState4[q3].getState());
										q3++;	    
										}
								
								//current_salesrevenue
										  for(int k=1;k<countOfActualValues4 ;k=k+4) {
										  Assert.assertEquals(actualDataOfSalesRevenue.get(k),exposureState4[r3].getCurrent_sales_revenue());
										  r3++;
										  } 
									
								//previous_headcount
										  for(int l=2;l<countOfActualValues4 ;l=l+4) {
										  Assert.assertEquals(actualDataOfSalesRevenue.get(l), exposureState4[s3].getPrevious_sales_revenue());
										  s3++;
										  } 
										  
								//change		  
										  for(int m=3;m<countOfActualValues4 ;m=m+4) {
										  Assert.assertEquals(actualDataOfSalesRevenue.get(m), exposureState4[t3].getChange());
										  t3++;
										  }
							    	
										  dataInsight.clickOnStateDropdown("Number of Vehicles");
							    		   
										   Thread.sleep(3000);
										   
										   DataInsightsWidgetPage dataInsight5 = new DataInsightsWidgetPage(driver);
											List<String> actualDataOfNumberOfVehicles = dataInsight5.getChangesInExposureStateValues();
											
											  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type","number_of_vehicles").addHeader("Authorization", "Bearer " +IdToken).build();
											  
											  request =  given().spec(req);
											
												ExposureState[] exposureState5 =  request.when().get("exposure/state/"+accountNumber).as(ExposureState[].class);
												
												int countOfActualValues5 = actualDataOfNumberOfVehicles.size();
												int q4 =0;
												int r4=0;
												int s4=0;
												int t4=0;
												
											//state
													for(int j=0;j<countOfActualValues5 ;j=j+4) {
													Assert.assertEquals(actualDataOfNumberOfVehicles.get(j), exposureState5[q4].getState());
													q4++;	    
													}
											
											//current_headcount
													  for(int k=1;k<countOfActualValues5 ;k=k+4) {
													  Assert.assertEquals(actualDataOfNumberOfVehicles.get(k),exposureState5[r4].getCurrent_number_of_vehicles());
													  r4++;
													  } 
												
											//previous_headcount
													  for(int l=2;l<countOfActualValues5 ;l=l+4) {
													  Assert.assertEquals(actualDataOfNumberOfVehicles.get(l), exposureState5[s4].getPrevious_number_of_vehicles());
													  s4++;
													  } 
													  
											//change		  
													  for(int m=3;m<countOfActualValues5 ;m=m+4) {
													  Assert.assertEquals(actualDataOfNumberOfVehicles.get(m), exposureState5[t4].getChange());
													  t4++;
													  }
										    	
	    	
	    }


}
