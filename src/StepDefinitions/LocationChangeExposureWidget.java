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

public class LocationChangeExposureWidget extends APIUtils {
	public WebDriver driver;
	public RequestSpecification request2;
	public RequestSpecification req;
	public ResponseSpecification res;
	 public Response response;
	 public String[] locationType = {"tiv","payroll","head_count","sales_revenue","number_of_vehicles"};
	 public String IdToken ;
	 
	  @Given("^RequestHeaders for Changes In exposure location API call$")
	    public void requestheaders_for_changes_in_exposure_location_api_call() throws Throwable {
			
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
	
			  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addQueryParam("type",locationType[0]).addHeader("Authorization", "Bearer " +jsonPathEvaluator.getString("AuthenticationResult.IdToken")).build();
					  
			  request2 =  given().spec(req);
	    }

	    @When("^user calls ChangesInExposureLocationAPI with Get http request$")
	    public void user_calls_changesinexposurelocationapi_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("exposure/location/" + accountNumber + "").then().spec(res).extract().response();
	    }

	    @Then("ChangesInExposureLocationAPI call got success with status code {int}")
	    public void changesinexposurelocationapi_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("^check if the response body and UI of Changes In exposure location matches$")
	    public void check_if_the_response_body_and_ui_of_changes_in_exposure_location_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> actualDataOfTIV = dataInsight.verifyChangesInExposureLocationTypeWidgetData();
			
			
			ChangesInExposureLocation[] changesInExposureLocation =  request2.when().get("exposure/location/" + accountNumber + "").as(ChangesInExposureLocation[].class);
		
			int count = changesInExposureLocation.length;
			int countOfActualValues = actualDataOfTIV.size();
			int i =0;
			int n=0;
			int o=0;
			int p=0;
			
		//locationTIV
				for(int j=0;j<countOfActualValues ;j=j+4) {
				Assert.assertEquals(actualDataOfTIV.get(j), changesInExposureLocation[i].getLocation_tiv() );
				i++;	    
				}
		
		//currentTIV
				  for(int k=1;k<countOfActualValues ;k=k+4) {
				  Assert.assertEquals(actualDataOfTIV.get(k),changesInExposureLocation[n].getCurrent_tiv());
				  n++;
				  } 
			
		//previousTIV
				  for(int l=2;l<countOfActualValues ;l=l+4) {
				  Assert.assertEquals(actualDataOfTIV.get(l), changesInExposureLocation[o].getPrevious_tiv() );
				  o++;
				  } 
		
		//changeTIV		  
				  for(int m=3;m<countOfActualValues ;m=m+4) {
				  Assert.assertEquals(actualDataOfTIV.get(m), changesInExposureLocation[p].getChange_tiv() );
				  p++;
				  }
		
		//type = Payroll
	   dataInsight.clickOnButtonToChangeExposureLocation("Payroll");
	   
	   Thread.sleep(2000);
	   
	   DataInsightsWidgetPage dataInsight2 = new DataInsightsWidgetPage(driver);
		List<String> actualDataOfPayroll = dataInsight2.verifyChangesInExposureLocationTypeWidgetData();
		
		  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type",locationType[1]).addHeader("Authorization", "Bearer " +IdToken).build();
		  
		  request2 =  given().spec(req);
		
		ChangesInExposureLocation[] changesInExposureLocation2 =  request2.when().get("exposure/location/" + accountNumber + "").as(ChangesInExposureLocation[].class);
		
		int count2 = changesInExposureLocation.length;
		int countOfActualValues2 = actualDataOfPayroll.size();
		int i2 =0;
		int n2=0;
		int o2=0;
		int p2=0;
		
	//locationPayroll
			for(int j=0;j<countOfActualValues2 ;j=j+4) {
			Assert.assertEquals(actualDataOfPayroll.get(j), changesInExposureLocation2[i2].getLocation_payroll());
			i2++;	    
			}
	
	//currentPayroll
			  for(int k=1;k<countOfActualValues2 ;k=k+4) {
			  Assert.assertEquals(actualDataOfPayroll.get(k),changesInExposureLocation2[n2].getCurrent_payroll());
			  n2++;
			  } 
		
	//previousPayroll
			  for(int l=2;l<countOfActualValues2 ;l=l+4) {
			  Assert.assertEquals(actualDataOfPayroll.get(l), changesInExposureLocation2[o2].getPrevious_payroll() );
			  o2++;
			  } 
	
	//changePayroll	  
			  for(int m=3;m<countOfActualValues ;m=m+4) {
			  Assert.assertEquals(actualDataOfPayroll.get(m), changesInExposureLocation2[p2].getChange_payroll());
			  p2++;
			  }
	
				//type = head_count
			   dataInsight.clickOnButtonToChangeExposureLocation("Head Count");
			   
			   Thread.sleep(2000);
			   
			   DataInsightsWidgetPage dataInsight3 = new DataInsightsWidgetPage(driver);
				List<String> actualDataOfHeadCount = dataInsight3.verifyChangesInExposureLocationTypeWidgetData();
				  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type",locationType[2]).addHeader("Authorization", "Bearer " +IdToken).build();
				  
				  request2 =  given().spec(req);
			   
				ChangesInExposureLocation[] changesInExposureLocation3 =  request2.when().get("exposure/location/" + accountNumber + "").as(ChangesInExposureLocation[].class);
				
				int count3 = changesInExposureLocation.length;
				int countOfActualValues3 = actualDataOfHeadCount.size();
				int i3 =0;
				int n3=0;
				int o3=0;
				int p3=0;
				
			//locationHeadcount
					for(int j=0;j<countOfActualValues3 ;j=j+4) {
					Assert.assertEquals(actualDataOfHeadCount.get(j), changesInExposureLocation3[i3].getLocation_head_count());
					i3++;	    
					}
			
			//currentHeadcount
					  for(int k=1;k<countOfActualValues3 ;k=k+4) {
					  Assert.assertEquals(actualDataOfHeadCount.get(k),changesInExposureLocation3[n3].getCurrent_head_count());
					  n3++;
					  } 
				
			//previousHeadCount
					  for(int l=2;l<countOfActualValues3 ;l=l+4) {
					  Assert.assertEquals(actualDataOfHeadCount.get(l), changesInExposureLocation3[o3].getPrevious_head_count() );
					  o3++;
					  } 
			
			//changeHeadCount
					  for(int m=3;m<countOfActualValues3 ;m=m+4) {
					  Assert.assertEquals(actualDataOfHeadCount.get(m), changesInExposureLocation3[p3].getChange_head_count());
					  p3++;
					  }
			
						//type = sales_revenue
					   dataInsight.clickOnButtonToChangeExposureLocation("Sales/Revenue");
					   
					   Thread.sleep(2000);
					   
					   DataInsightsWidgetPage dataInsight4 = new DataInsightsWidgetPage(driver);
						List<String> actualDataOfSalesRevenue = dataInsight4.verifyChangesInExposureLocationTypeWidgetData();
						  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type",locationType[3]).addHeader("Authorization", "Bearer " +IdToken).build();
						  
						  request2 =  given().spec(req);
					   
						ChangesInExposureLocation[] changesInExposureLocation4 =  request2.when().get("exposure/location/" + accountNumber + "").as(ChangesInExposureLocation[].class);
							
						int count4 = changesInExposureLocation.length;
						int countOfActualValues4 = actualDataOfSalesRevenue.size();
						int i4 =0;
						int n4=0;
						int o4=0;
						int p4=0;
						
					//locationSalesRevenue
							for(int j=0;j<countOfActualValues4 ;j=j+4) {
							Assert.assertEquals(actualDataOfSalesRevenue.get(j), changesInExposureLocation4[i4].getLocation_sales_revenue());
							i4++;	    
							}
					
					//currentSalesRevenue
							  for(int k=1;k<countOfActualValues4 ;k=k+4) {
							  Assert.assertEquals(actualDataOfSalesRevenue.get(k),changesInExposureLocation4[n4].getCurrent_sales_revenue());
							  n4++;
							  } 
						
					//previousSalesRevenue
							  for(int l=2;l<countOfActualValues4 ;l=l+4) {
							  Assert.assertEquals(actualDataOfSalesRevenue.get(l), changesInExposureLocation4[o4].getPrevious_sales_revenue());
							  o4++;
							  } 
					
					//changeSalesRevenue
							  for(int m=3;m<countOfActualValues4 ;m=m+4) {
							  Assert.assertEquals(actualDataOfSalesRevenue.get(m), changesInExposureLocation4[p4].getChange_sales_revenue());
							  p4++;
							  }				  

								//type = number_of_vehicles
							   dataInsight.clickOnButtonToChangeExposureLocation("Number of Vehicles");
							   
							   Thread.sleep(2000);
							   
							   DataInsightsWidgetPage dataInsight5 = new DataInsightsWidgetPage(driver);
								List<String> actualDataOfnoOfVehicles = dataInsight5.verifyChangesInExposureLocationTypeWidgetData();
								
								  req = new RequestSpecBuilder().setBaseUri("https://api.uw-dev.cognitiveinsurance.accenture.com").setContentType(ContentType.JSON).addQueryParam("type",locationType[4]).addHeader("Authorization", "Bearer " +IdToken).build();
								  
								  request2 =  given().spec(req);
								ChangesInExposureLocation[] changesInExposureLocation5 =  request2.when().get("exposure/location/" + accountNumber + "").as(ChangesInExposureLocation[].class);
								
								int count5 = changesInExposureLocation.length;
								int countOfActualValues5 = actualDataOfnoOfVehicles.size();
								int i5 =0;
								int n5=0;
								int o5=0;
								int p5=0;
								
							//locationNoOfVehicles
									for(int j=0;j<countOfActualValues5 ;j=j+4) {
									Assert.assertEquals(actualDataOfnoOfVehicles.get(j), changesInExposureLocation5[i5].getLocation_number_of_vehicles());
									i5++;	    
									}
							
							//currentNoOfVehicles
									  for(int k=1;k<countOfActualValues5 ;k=k+4) {
									  Assert.assertEquals(actualDataOfnoOfVehicles.get(k),changesInExposureLocation5[n5].getCurrent_number_of_vehicles());
									  n5++;
									  } 
								
							//previousNoOfVehicles
									  for(int l=2;l<countOfActualValues5 ;l=l+4) {
									  Assert.assertEquals(actualDataOfnoOfVehicles.get(l), changesInExposureLocation5[o5].getPrevious_number_of_vehicles());
									  o5++;
									  } 
							
							//changeNoOfVehicles
									  for(int m=3;m<countOfActualValues5 ;m=m+4) {
									  Assert.assertEquals(actualDataOfnoOfVehicles.get(m), changesInExposureLocation5[p5].getChange_number_of_vehicles());
									  p5++;
									  }				  


								
	    }
	    
	    @After public void cleanUp() {
	    	  System.out.println("Into the cleanUp method of LinkStatsStep...");
	    	  releaseResources(this.driver); 
	    }
}
