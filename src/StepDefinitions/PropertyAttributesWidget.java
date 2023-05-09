package StepDefinitions;

import static io.restassured.RestAssured.given;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import APIresources.APIUtils;
import Pages.ActiveSubmissionsPage;
import Pages.DataInsightsWidgetPage;
import Pojo.Assessed_Value;
import Pojo.BuildingDataRequest;
import Pojo.BuildingDataResponse;
import Pojo.Improved_Value;
import Pojo.Land_Value;
import Pojo.Owners;
import Pojo.PropertyAttributesRequest;
import Pojo.Year;
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
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PropertyAttributesWidget extends APIUtils {
	public WebDriver driver;
	public RequestSpecification propertyAttributeReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	public RequestSpecification request;
	 public Response response;
	 public String IdToken;
	

	
	  @Given("^PropertyAttributes Payload for property Attributes$")
	    public void propertyattributes_payload_for_property_attributes() throws Throwable {
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

	    @When("^user calls PropertyAttributes API with Post http request$")
	    public void user_calls_propertyattributes_api_with_post_http_request() throws Throwable {
	    	 DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
			  
			  List<String> addressStrings = data.propertyAttributesAddress();
			  
			  System.out.println(addressStrings);
			  
			  PropertyAttributesRequest dataRequest = new PropertyAttributesRequest();
			  	 
			  dataRequest.setStreet(addressStrings.get(0)+" "+addressStrings.get(1)+" "
			  +addressStrings.get(2)); 
			  dataRequest.setCity(addressStrings.get(3));
			  dataRequest.setState(addressStrings.get(4));
			  dataRequest.setZip_postal_code(addressStrings.get(5));
			  
			  req = new
			  RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(
			  ContentType.JSON).addHeader("Authorization", "Bearer "
			  +IdToken).build();
			  
			  propertyAttributeReq = given().spec(req).body(dataRequest);
	  	  res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response = propertyAttributeReq.when().post("precisely/property-attributes").then().spec(res).extract().response();
	      
	    }

	    @Then("^PropertyAttributes API call got success with status code 200$")
	    public void propertyattributes_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @SuppressWarnings("unchecked")
		@And("^check if the response body and UI of PropertyAttributes widget data matches$")
	    public void check_if_the_response_body_and_ui_of_propertyattributes_widget_data_matches() throws Throwable {
	    	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> propertyAttributesDataUIValues = data.getPropertyAttributesTableValues(1);
	    	propertyAttributesDataUIValues.removeAll(Collections.singleton("--"));
	    	System.out.println(propertyAttributesDataUIValues);
	    	
	    	response =  propertyAttributeReq.when().post("precisely/property-attributes").then().extract().response();
	   
	    	
	    	JsonPath json = response.jsonPath();
	    	
	 //pbKey
			
			Assert.assertTrue(data.getpbKey2().contains(json.getString("pbKey"))); 
			
	//address		
			Assert.assertTrue(json.getString("situsAddress.mainAddressLine").contains(data.propertyAttributesAddress().get(0)));
	    	
	    	Map<String, String> mp = json.getMap("general_property");
	    	mp.values().removeAll(Collections.singleton(null));
	    	System.out.println(mp);
	    	
	    	Set<Entry<String, String>> mpSet = mp.entrySet();    	
	    	
	    	
	    	Iterator<Entry<String, String>> it=mpSet.iterator();
	    	
	    	
	    	while(it.hasNext()) {
	    		for(int j=0;j<propertyAttributesDataUIValues.size();j++) {
	    			@SuppressWarnings("rawtypes")
					Map.Entry mpEnt=(Map.Entry)it.next();  
	    			
	    			Assert.assertEquals(propertyAttributesDataUIValues.get(j).replace("sq. ft.", "").replace("acres", "").trim(), mpEnt.getValue().toString());	    			
	    		}
	    		
	    	}
	    	
	   
data.changeTableOfPropertyAttributes(2);
Thread.sleep(2000);			
			List<String> propertyAttributesDataUIValues2 = data.getPropertyAttributesTableValues(2);
		 	propertyAttributesDataUIValues2.removeAll(Collections.singleton("--"));
	    	System.out.println(propertyAttributesDataUIValues2);
	    	
	    	
	    	response =  propertyAttributeReq.when().post("precisely/property-attributes").then().extract().response();
	    	
	    	JsonPath json2 = response.jsonPath();
	    	
	    	Map<String, String> mp2 = json2.getMap("bath_bedrooms");
	    	mp2.values().removeAll(Collections.singleton(null));
	    	System.out.println(mp2);
	    	
	    	Set<Entry<String, String>> mpSet2 = mp2.entrySet();    	
	
	    	
	    	Iterator<Entry<String, String>> it2=mpSet2.iterator();
	    	
	    	
	    	while(it2.hasNext()) {
	    		for(int j=0;j<propertyAttributesDataUIValues2.size();j++) {
	    			@SuppressWarnings("rawtypes")
					Map.Entry mpEnt2=(Map.Entry)it2.next();  
	    			
	    			Assert.assertEquals(propertyAttributesDataUIValues2.get(j).trim(), mpEnt2.getValue().toString());	    			
	    		}
	    		
	    	}
	    	
	    	
data.changeTableOfPropertyAttributes(3);
Thread.sleep(2000);			
			List<String> propertyAttributesDataUIValues3 = data.getPropertyAttributesTableValues(3);
			propertyAttributesDataUIValues3.removeAll(Collections.singleton("--"));
	    	System.out.println(propertyAttributesDataUIValues3);
	    	
	    	
	    	response =  propertyAttributeReq.when().post("precisely/property-attributes").then().extract().response();
	    	
	    	JsonPath json3 = response.jsonPath();
	    	
	    	Map<String, String> mp3 = json3.getMap("additional");
	    	mp3.values().removeAll(Collections.singleton(null));
	    	mp3.keySet().removeAll(Collections.singleton("value"));
	    	System.out.println(mp3);
	    	
	    	Set<Entry<String, String>> mpSet3 = mp3.entrySet();    	
	
	    	
	    	Iterator<Entry<String, String>> it3=mpSet3.iterator();
	    	
	    	
	    	while(it3.hasNext()) {
	    		for(int j=0;j<propertyAttributesDataUIValues3.size();j++) {
	    			@SuppressWarnings("rawtypes")
					Map.Entry mpEnt3=(Map.Entry)it3.next();  
	    			
	    			Assert.assertEquals(propertyAttributesDataUIValues3.get(j).trim(), mpEnt3.getValue().toString());	    			
	    		}
	    		
	    	}
	    	
	    
	    	
data.changeTableOfPropertyAttributes(4);
Thread.sleep(2000);
			
			List<String> propertyAttributesDataUIValues4 = data.getPropertyAttributesTableValues(4);
			propertyAttributesDataUIValues4.removeAll(Collections.singleton("--"));
	    	System.out.println(propertyAttributesDataUIValues4);
	    	
	    	response =  propertyAttributeReq.when().post("precisely/property-attributes").then().extract().response();
	    	
	    	JsonPath json4 = response.jsonPath();
	    	
	    	Map<String, String> mp4 = json4.getMap("building_information");
	    	mp4.values().removeAll(Collections.singleton(null));
	    	mp4.keySet().removeAll(Collections.singleton("value"));
	    	System.out.println(mp4);
	    	
	    	Set<Entry<String, String>> mpSet4 = mp4.entrySet();    	
	
	    	
	    	Iterator<Entry<String, String>> it4=mpSet4.iterator();
	    	
	    	
	    	while(it4.hasNext()) {
	    		for(int j=0;j<propertyAttributesDataUIValues4.size();j++) {
	    			@SuppressWarnings("rawtypes")
					Map.Entry mpEnt4=(Map.Entry)it4.next();  
	    			
	    			Assert.assertEquals(propertyAttributesDataUIValues4.get(j).replace("sq. ft.", "").trim(), mpEnt4.getValue().toString());	    			
	    		}
	    		
	    	}
	    	

	    	
data.changeTableOfPropertyAttributes(5);
Thread.sleep(2000);
			
			List<String> propertyAttributesDataUIValues5 = data.getPropertyAttributesTableValues(5);
			propertyAttributesDataUIValues5.removeAll(Collections.singleton("--"));
	    	System.out.println(propertyAttributesDataUIValues5);
	    	
	    	response =  propertyAttributeReq.when().post("precisely/property-attributes").then().extract().response();
	    	
	    	JsonPath json5 = response.jsonPath();
	    	
	     	Map<String, String> mp5 = json5.getMap("building_features_improvements.buildgFeaturesSqFt");
	    	mp5.values().removeAll(Collections.singleton(null));
	    	mp5.keySet().removeAll(Collections.singleton("description"));
	    	System.out.println(mp5);
	    	
	    	Set<Entry<String, String>> mpSet5 = mp5.entrySet();    	
	
	    	
	    	Iterator<Entry<String, String>> it5=mpSet5.iterator();
	    	
	    	
	    	while(it5.hasNext()) {
	    		for(int j=0;j<propertyAttributesDataUIValues5.size();j++) {
	    			@SuppressWarnings("rawtypes")
					Map.Entry mpEnt5=(Map.Entry)it5.next();  
	    			
	    			Assert.assertEquals(propertyAttributesDataUIValues5.get(j).replace("sq. ft.", "").trim(), mpEnt5.getValue().toString());	    			
	    		}
	    		
	    	}
	    	
			
	
	    }

}
