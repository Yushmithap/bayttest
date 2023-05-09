package StepDefinition2;

import static io.restassured.RestAssured.given;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v104.network.Network;
import org.openqa.selenium.devtools.v104.network.model.Request;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import APIresources.APIUtils;
import Pages.ActiveSubmissionsPage;
import Pages.DataInsightsWidgetPage;
import Pages.LoginPage;
import Pojo.AccountInformationChanges;
import Pojo.Assessed_Value;
import Pojo.BuildingDataRequest;
import Pojo.BuildingDataResponse;
import Pojo.ChangesInExposureLocation;
import Pojo.ChangesInExposureVehicleType;
import Pojo.DnBBusinessActivity;
import Pojo.DnBCompanyProfile;
import Pojo.DnBRating;
import Pojo.DnBSIC;
import Pojo.Equity;
import Pojo.ExposureClassCode;
import Pojo.ExposureState;
import Pojo.FacilityLocations;
import Pojo.Finances;
import Pojo.HistoricalLossRatioPremium;
import Pojo.HistoricalLossRun;
import Pojo.Improved_Value;
import Pojo.Land_Value;
import Pojo.Liabilities;
import Pojo.OccupancyChange;
import Pojo.OperatorDetails;
import Pojo.Owners;
import Pojo.PropertyAttributesRequest;
import Pojo.Questionaaire;
import Pojo.VehicleDetails;
import Pojo.Year;
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

public class InlandMarineWidgets extends APIUtils {
	
	public  WebDriver driver;
	public RequestSpecification buildingDataReq;
	public RequestSpecification propertyAttributeReq;
	public RequestSpecification req;
	public RequestSpecification req2;
	public ResponseSpecification res;
	public RequestSpecification request;
	public RequestSpecification request2;
	 public Response response;
	 LoginPage login = new LoginPage(driver);
	 public String[] occupancyType = {"square_footage","number_of_rooms"};
	
	public String IdToken ;
	 public ActiveSubmissionsPage activeSubmission ;
	 public String[] locationType = {"tiv","payroll","head_count","sales_revenue","number_of_vehicles"};
	
	
	 @Given("Login to UW portal and navigate to data insights of Inland Marine Submission")
	    public void buildingdatarequest_payload_for_building_data() throws Throwable {

		  System.setProperty("webdriver.edge.driver",getGlobalValue("edgePath"));
			
			
			EdgeOptions opt = new EdgeOptions();
			opt.setPageLoadStrategy(PageLoadStrategy.NONE);
			opt.addArguments("--start-maximized");
		//	opt.merge(dc);
			WebDriver driver =  new EdgeDriver(opt);    
		     driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			 this.driver = driver;
			 PageFactory.initElements(driver, this);
				
			  driver.get(getGlobalValue("appURL"));

		   	  Thread.sleep(20000);
	
				ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
				activeSubmission.clickOnActiveSubmissionTab();
				activeSubmission.expandActiveSubmissionAndClickOnApplication("Walmart Inc",7);
				Thread.sleep(5000);		
				 activeSubmission.clickOnDataInsights(); 
				 Thread.sleep(9000);
	  
			
		
	    }

	    @When("user calls BuildingDataAPI with post http request")
	    public void user_calls_something_with_post_http_request() throws Throwable {
              DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
			  
			  List<String> addressStrings = data.buildingDataAddress();
			  
			  System.out.println(addressStrings);
			  
			  BuildingDataRequest dataRequest = new BuildingDataRequest();
			  
			  dataRequest.setAccount_name(getGlobalValue("accountName").toUpperCase());
			  dataRequest.setStreet(addressStrings.get(0)+" "+addressStrings.get(1)+" "
			  +addressStrings.get(2)); dataRequest.setCity(addressStrings.get(3));
			  dataRequest.setState(addressStrings.get(4));
			  dataRequest.setZip_postal_code(addressStrings.get(5));
			  
			  req = new
			  RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(
			  ContentType.JSON).addHeader("Authorization", "Bearer "
			  +IdToken).build();
			  
			  buildingDataReq = given().spec(req).body(dataRequest);
	  	  res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response = buildingDataReq.when().post("precisely/building-data").then().spec(res).extract().response();
	      
	    }

	    @Then("BuildingDataAPI call got success with status code {int}")
	    public void api_call_got_success_with_something_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }


	    @And("check if the response body and UI of Building data matches")
	    public void check_if_the_response_body_and_uw_portal_building_data_matches() throws Throwable {
	    	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> buildingDataUIValues = data.getBuildingDataTableValues();
	    	System.out.println(buildingDataUIValues);
	    	
	    	BuildingDataResponse buildingDataResponse =  buildingDataReq.when().post("precisely/building-data").as(BuildingDataResponse.class);
	    	
	 //pbKey
			
			Assert.assertEquals(data.getpbKey(), buildingDataResponse.getPbKey());  				

	//ownerName
			Owners[] owner = buildingDataResponse.getOwners();
			
			Assert.assertEquals(data.getOwnerName(), owner[0].getOwnerName());  				

		//year	
			  Year year = buildingDataResponse.getYear(); 
			  Assert.assertEquals(buildingDataUIValues.get(1), year.getTax_year());  		
			  Assert.assertEquals(buildingDataUIValues.get(2), year.getMarket_year());
			  Assert.assertEquals(buildingDataUIValues.get(3), year.getAppraised_year());
			  
	//assedssed value		  
			   Assessed_Value assessed_Value = buildingDataResponse.getAssessed_value();
			  Assert.assertEquals(buildingDataUIValues.get(5),assessed_Value.getTax_value());
			  Assert.assertEquals(buildingDataUIValues.get(6),assessed_Value.getMarket_value()); 
			  Assert.assertEquals(buildingDataUIValues.get(7),assessed_Value.getAppraised_value());

			  //land value
			   Land_Value land_value = buildingDataResponse.getLand_value();
			  Assert.assertEquals(buildingDataUIValues.get(9),
			  land_value.getTax_land_value());
			  Assert.assertEquals(buildingDataUIValues.get(10),land_value.
			  getMarket_land_value());
			  Assert.assertEquals(buildingDataUIValues.get(11),
			  land_value.getAppraised_land_value());
		
			  //improved value
			 Improved_Value improved_value =
			  buildingDataResponse.getImproved_value();
			  Assert.assertEquals(buildingDataUIValues.get(13),
			  improved_value.getAssessedValueImp());
			  Assert.assertEquals(buildingDataUIValues.get(14),improved_value.
			  getMarketValueImp());
			  Assert.assertEquals(buildingDataUIValues.get(15),
			  improved_value.getAppraised_imp_value());
			  
			  //Replacement cost
			  
			  Assert.assertEquals(data.getReplacementCost(),
			  buildingDataResponse.getReplacement_cost());
			
	
	}
	  
	    @When("user calls KnowledgeLinkAPI of queryparams {string} and {string} with Get http request")
	    public void user_calls_knowledgelinkapi_of_queryparams_something_and_something_with_get_http_request(String application_id, String business_rule_type) throws Throwable {
		 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	String rule_type = "Knowledge Link";
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addQueryParam(application_id, accountNumber).
			  addQueryParam(business_rule_type, rule_type). 
			  addHeader("ContentType", "json/text"). addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("appetiterules/GetRulesOutcome").then().log().all().extract().response();
		  
		 
		 
	    }

	    @Then("^KnowledgeLinkAPI call got success with status code 200$")
	    public void knowledgelinkapi_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    	System.out.println(response.getBody().asString());
	        
	    }
	    
	    @And("API Response {string} is {string}")
	    public void api_response_something_is_something(String status, String value) throws Throwable {
	    	     
	    	     JsonPath json = response.jsonPath();
	    	     String value2 = json.getString(status);
	    	 	Assert.assertEquals(value2, value);
	       
	    }


	    @And("^check if response matches with UI of Key Knowledge Link$")
	    public void check_if_response_matches_with_ui_of_key_knowledge_link() throws Throwable {
	    	  DataInsightsWidgetPage insightpage = new DataInsightsWidgetPage(driver);
	    	 try { 
		    	if(insightpage.isKnowledgeKeyLinkExists()) {
		    	
		    	  String linkName =  insightpage.getknowledgeLinkText();
		    	  System.out.println(linkName);
		    	  String linkHref = insightpage.getknowledgeLinkHref();
		    	  System.out.println(linkHref);
		    	   JsonPath json = response.jsonPath();
		    	     String hrefText = json.getString("message[0]."+linkName);
		    	 	Assert.assertEquals(linkHref, hrefText.trim());
		    	 	
		    	}
		
		  else {
		  
				Assert.assertEquals(insightpage.noDataAvailable(),"No Data Available");  
		  }
	    	 }
	    	 catch(Exception e) {
	    		 System.out.println("element not located exception found");
	    	 }
		 
	    	 
	    }
	 
//COMPANYFINANCIALS API
	    @When("user calls CompanyFinancialsAPI with Get http request")
	    public void user_calls_companyfinancialratiosapi_with_get_http_request() throws Throwable {
	    	
			  req2 = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +IdToken).build();
			  
			  request2 =  given().spec(req);
	    	 activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	  res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("dnb/financial-ratios/"+accountNumber+"").then().spec(res).extract().response();
	    }

	    @Then("CompanyFinancialsAPI call got success with status code {int}")
	    public void comapanyfinancial_api_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("check if the response body and UI of Company Financials matches")
	    public void check_if_the_response_body_and_ui_of_company_financials_matches() throws Throwable {
	    
	    	 activeSubmission = new ActiveSubmissionsPage(driver);
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

//FACILITYLOCATIONSAPI
	    @When("^user calls FacilityLocationAPI with Get http request$")
	    public void user_calls_facilitylocationapi_with_get_http_request() throws Throwable {
	  	  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +IdToken).build();
		  
		  request2 =  given().spec(req);
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("widget/submission/" + accountNumber + "/premise").then().spec(res).extract().response();
	    	
	    }

	    @Then("FacilityLocationAPI call got success with status code {int}")
	    public void facilitylocationapi_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("^check if the response body and UI of Facility Location matches$")
	    public void check_if_the_response_body_and_ui_of_facility_location_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();

			FacilityLocations[] facilityLocations = request2.when().log().all().get("widget/submission/" + accountNumber + "/premise").as(FacilityLocations[].class);
			
		//	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
		//	   List<String> actualLocationValues = dataInsight.verifyFacilityLocationsWidgetData();
	//		 List<String> actualLocationTitles = dataInsight.getMultipleLocationTitles()  ;
	//	   List<String> actualLocationMultipleValues = dataInsight.verifyMultipleFacilityLocationsWidgetData();
		  
			int countOfObjects = facilityLocations.length;
		
			if(countOfObjects==1) {
				DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
				   List<String> actualLocationValues = dataInsight.verifyFacilityLocationsWidgetData();
				   
				  Assert.assertTrue(actualLocationValues.get(0).contains(facilityLocations[0].getStreet()+", "+facilityLocations[0].getCity()+", "+facilityLocations[0].getState()));
				  Assert.assertEquals(actualLocationValues.get(1), facilityLocations[0].getPremise_flag());    
				  Assert.assertTrue(actualLocationValues.get(2).equalsIgnoreCase(facilityLocations[0].getOwnership_type()));
				  Assert.assertEquals(actualLocationValues.get(3), facilityLocations[0].getOccupancy_perc());
				 Assert.assertEquals(actualLocationValues.get(4), facilityLocations[0].getYear());
				 Assert.assertEquals(actualLocationValues.get(5), facilityLocations[0].getEmployee_count());
				 Assert.assertTrue(actualLocationValues.get(6).equalsIgnoreCase(facilityLocations[0].getNature_of_business()));
		}
		else if(countOfObjects>1) {
			DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			 List<String> actualLocationTitles = dataInsight.getMultipleLocationTitles()  ;
			   List<String> actualLocationMultipleValues = dataInsight.verifyMultipleFacilityLocationsWidgetData();
				  int i=0;
				  int i2 = 0;
				  int i3 = 0;
				  int i4 = 0;
				  int i5 = 0;
				  int i6 = 0;
				  int i7 = 0;
			      
				   for(int j=0;j<actualLocationTitles.size();j++) {
		   	  Assert.assertTrue(actualLocationTitles.get(j).contains(facilityLocations[i].getStreet()+", "+facilityLocations[i].getCity()+", "+facilityLocations[i].getState()+", "+facilityLocations[i].getCountry()+", "+facilityLocations[i].getZip_postal_code()));
		   	  i++;
				   }
				   for(int j=0;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i2].getPremise_flag());  
				   i2++;
				}
			 for(int j=1;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i3].getOwnership_type() );
			 i3++;
	     	}
			 for(int j=2;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i4].getOccupancy_perc());
			  i4++;
			 }
			 for(int j=3;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i5].getYear());
			  i5++;
			 }
			 for(int j=4;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i6].getNature_of_business());
			  i6++;
			 }
			 for(int j=5;j<actualLocationMultipleValues.size();j=j+6) {
			  Assert.assertEquals(actualLocationMultipleValues.get(j), facilityLocations[i7].getEmployee_count());
			i7++;
			 }
			   }
	    }
	    
//FINANCIALRATIOAPI	    
	    @When("user calls FinancialRatiosAPI with Get http request")
	    public void user_calls_financialratiosapi_with_get_http_request() throws Throwable {
	    	  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +IdToken).build();
	  
			  request2 =  given().spec(req);
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("dnb/financial-ratios/"+accountNumber+"").then().spec(res).extract().response();
	    	
	    }

	    @Then("FinancialRatiosAPI call got success with status code {int}")
	    public void financialratiosapi_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("check if the response body and UI of Financial Ratio matches")
	    public void check_if_the_response_body_and_ui_of_financial_ratio_matches() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> financialRatios = data.getFinancialRatios();
	    	
	    	Finances finances =  request2.when().get("dnb/financial-ratios/"+accountNumber+"").as(Finances.class);
	    	
	    	Assert.assertEquals(financialRatios.get(0),finances.getFinancial_ratios().getCurrent_ratio());
	    	Assert.assertEquals(financialRatios.get(1), finances.getFinancial_ratios().getAcid_test_ratio());
	    	Assert.assertEquals(financialRatios.get(2), finances.getFinancial_ratios().getTotal_indebtedness());
	    	Assert.assertEquals(financialRatios.get(3), finances.getFinancial_ratios().getCurrent_liabilities_over_net_worth());
	    	Assert.assertEquals(financialRatios.get(4), finances.getFinancial_ratios().getLiabilities_over_net_worth());
	    	Assert.assertEquals(financialRatios.get(5), finances.getFinancial_ratios().getQuick_ratio());

	    }
	
	    //FINANCIALS API
	    @When("user calls FinancialsAPI with Get http request")
	    public void user_calls_financialsapi_with_get_http_request() throws Throwable {
	    	 req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +IdToken).build();
	    	 request2 =  given().spec(req);
	   	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("dnb/financial-ratios/"+accountNumber+"").then().spec(res).extract().response();
	    }

	    @Then("FinancialsAPI call got success with status code {int}")
	    public void financialsapi_call_got_success_with_status_code_200(int int1) throws Throwable {
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
	
	 //CHANGES IN EXPOSURE LOCATION   
	    @When("^user calls ChangesInExposureLocationAPI with Get http request$")
	    public void user_calls_changesinexposurelocationapi_with_get_http_request() throws Throwable {
	    	  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addQueryParam("type",locationType[0]).addHeader("Authorization", "Bearer " +IdToken).build();
			  
			  request2 =  given().spec(req);
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
	   
	   Thread.sleep(4000);
	   
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
			   
			   Thread.sleep(4000);
			   
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
					   
					   Thread.sleep(4000);
					   
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
							   
							   Thread.sleep(4000);
							   
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
	
//CHANGES IN EXPOSURE VEHICLE	    
	    @When("^user calls ChangesInExposureVehicletypeAPI with Get http request$")
	    public void user_calls_changesinexposurevehicletypeapi_with_get_http_request() throws Throwable {
	  	  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +IdToken).build();
		  
		  request2 =  given().spec(req);
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("exposure/vehicle/" + accountNumber + "").then().spec(res).extract().response();
	        
	    }

	    @Then("ChangesInExposureVehicletypeAPI call got success with status code {int}")
	    public void changesinexposurevehicletypeapi_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("^check if the response body and UI of Changes In exposure vehicle type matches$")
	    public void check_if_the_response_body_and_ui_of_changes_in_exposure_vehicle_type_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> actualValues = dataInsight.verifyChangesInExposureVehicleTypeWidgetData();
		
			ChangesInExposureVehicleType[] changesInExposureVehicle =  request2.when().get("exposure/vehicle/" + accountNumber + "").as(ChangesInExposureVehicleType[].class);
			
			int count = changesInExposureVehicle.length;
			int countOfActualValues = actualValues.size();
			int i =0;
			int n=0;
			int o=0;
			int p=0;
			
		//vehicleType
				for(int j=0;j<countOfActualValues ;j=j+4) {
				Assert.assertEquals(actualValues.get(j), changesInExposureVehicle[i].getVehicleType() );
				i++;	    
				}
		
		//currentYearPower
				  for(int k=1;k<countOfActualValues ;k=k+4) {
				  Assert.assertEquals(actualValues.get(k),changesInExposureVehicle[n].getCurrentYearPower());
				  n++;
				  } 
			
		//previousYearPower		  
				  for(int l=2;l<countOfActualValues ;l=l+4) {
				  Assert.assertEquals(actualValues.get(l), changesInExposureVehicle[o].getPreviousYearPower() );
				  o++;
				  } 
		
		//change		  
				  for(int m=3;m<countOfActualValues ;m=m+4) {
				  Assert.assertEquals(actualValues.get(m), changesInExposureVehicle[p].getChange() );
				  p++;
				  }
				 
			
	    }
	
//VEHICLE DETAILS
	    @When("^user calls VehicleDetailsAPI with Get http request$")
	    public void user_calls_vehicledetailsapi_with_get_http_request() throws Throwable {
	  	  req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).setContentType(ContentType.JSON).addHeader("Authorization", "Bearer " +IdToken).build();
		  
		  request2=  given().spec(req);
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	  	    res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	    	response =request2.when().get("widget/vehicle/" + accountNumber + "").then().spec(res).extract().response();
	    }

	    @Then("VehicleDetailsAPI call got success with status code {int}")
	    public void vehicledetailsapi_call_got_success_with_status_code_200(int int1) throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), int1);
	    }

	    @And("^check if the response body and UI of Vehicle Details matches$")
	    public void check_if_the_response_body_and_ui_of_vehicle_details_matches() throws Throwable {
	    	
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> actualValues = dataInsight.verifVehicleDetailsWidgetData();
			
	  VehicleDetails[] vehicleDetails = request2.when().get("widget/vehicle/" + accountNumber + "").as(VehicleDetails[].class);
	  
		int count = vehicleDetails.length;
		int countOfActualValues = actualValues.size();
		int i =0;
		int i2=0;
		int i3=0;
		int i4=0;
		int i5=0;
		int i6 = 0;
		int i7 = 0;
		int i8 = 0;
		int i9 = 0;
		int i10 = 0;
		int i11 = 0;
		
	//vin
			for(int j=0;j<countOfActualValues ;j=j+11) {
			Assert.assertEquals(actualValues.get(j), vehicleDetails[i].getVin() );
			i++;	    
			}
	
	//make_model
			  for(int k=1;k<countOfActualValues ;k=k+11) {
			  Assert.assertEquals(actualValues.get(k),vehicleDetails[i2].getMake_model());
			  i2++;
			  } 
		
	//type	  
			  for(int l=2;l<countOfActualValues ;l=l+11) {
			  Assert.assertEquals(actualValues.get(l), vehicleDetails[i3].getType() );
			  i3++;
			  } 
	
	//physical damage		  
			  for(int m=3;m<countOfActualValues ;m=m+11) {
			  Assert.assertEquals(actualValues.get(m), vehicleDetails[i4].getPhysical_damage() );
			  i4++;
			  }
			  
	//collision
			  for(int n=4;n<countOfActualValues ;n=n+11) {
				  Assert.assertEquals(actualValues.get(n), vehicleDetails[i5].getCollision() );
				  i5++;
				  }
			  
	//seat capacity
			  for(int o=5;o<countOfActualValues ;o=o+11) {
				  Assert.assertEquals(actualValues.get(o), vehicleDetails[i6].getSeat_capacity());
				  i6++;
				  }
		
	//operating radius
			  for(int p=6;p<countOfActualValues ;p=p+11) {
				  Assert.assertEquals(actualValues.get(p), vehicleDetails[i7].getOperating_radius() );
				  i7++;
				  }
			  
	//drive to work
			  for(int q=7;q<countOfActualValues ;q=q+11) {
				  Assert.assertEquals(actualValues.get(q), vehicleDetails[i8].getDrive_to_work() );
				  i8++;
				  }	
			  
	//vehicle use
			  for(int r=8;r<countOfActualValues ;r=r+11) {
				  Assert.assertEquals(actualValues.get(r), vehicleDetails[i9].getVehicle_use() );
				  i9++;
				  }	
			  
	//cost
			  for(int s=9;s<countOfActualValues ;s=s+11) {
				  Assert.assertEquals(actualValues.get(s), vehicleDetails[i10].getCost());
				  i10++;
				  }	
			  
	//premium
			  for(int t=10;t<countOfActualValues ;t=t+11) {
				  Assert.assertEquals(actualValues.get(t), vehicleDetails[i11].getPremium());
				  i11++;
				  }	

	    }
	    
//OPERATOR DETAILS	    
	    @When("^user calls OperatorDetailsAPI with Get http request$")
	    public void user_calls_operatordetailsapi_with_get_http_request() throws Throwable {
		  
		  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("widget/operator/"+accountNumber).then().log().all().extract().response();
    
	    }

	    @Then("^OperatorDetailsAPI call got success with status code 200$")
	    public void operatordetailsapi_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of Operator Details matches$")
	    public void check_if_the_response_body_and_ui_of_operator_details_matches() throws Throwable {
	    	
	    	   ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> operatorDetails = data.getOperatorDetailsValues();
		    	
		    	OperatorDetails[] operatorDetailsPojo =  request.when().get("widget/operator/"+accountNumber).as(OperatorDetails[].class);
		    	
		    	int countOfObjects = operatorDetailsPojo.length;
		    	int countOfValues = operatorDetails.size();
		    	int i =0;
				int n=0;
				int o=0;
				int p=0;
				int q=0;
		    	
				//operator name
		    		for(int j=0;j<countOfValues;j=j+5) {
		    			Assert.assertEquals(operatorDetails.get(j), operatorDetailsPojo[i].getOperator_name());	
		    			i++;
		    		}
		    //operator licence id
		    		for(int k=1;k<countOfValues;k=k+5) {
		    			Assert.assertEquals(operatorDetails.get(k), operatorDetailsPojo[n].getOperator_licence_id());	
		    			n++;
		    		}
		    		
		    //years of experience
		    		for(int l=2;l<countOfValues;l=l+5) {
		    			Assert.assertEquals(operatorDetails.get(l), operatorDetailsPojo[o].getYears_of_experience());	
		    			o++;
		    		}
		    		
		    //operator age
		    		for(int m=3;m<countOfValues;m=m+5) {
		    			Assert.assertEquals(operatorDetails.get(m), operatorDetailsPojo[p].getOperator_age());	
		    			p++;
		    		}
		    		
		   //Prior accidents
		    		for(int s=4;s<countOfValues;s=s+5) {
		    			Assert.assertEquals(operatorDetails.get(s), operatorDetailsPojo[q].getViolation_count());	
		    			q++;	
		    		}

		    		
		    		
		    		
		    	}
	    
	    @When("^user calls HistoricalLossRunAPI with Get http request$")
	    public void user_calls_historicallossrunapi_with_get_http_request() throws Throwable {
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("historical/loss-run/"+accountNumber).then().log().all().extract().response();
			  
	    }

	    @Then("^HistoricalLossRunAPI call got success with status code 200$")
	    public void historicallossrunapi_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);    	
	    }

	    @And("^check if the response body and UI of Historical Loss Run table matches$")
	    public void check_if_the_response_body_and_ui_of_historical_loss_run_table_matches() throws Throwable {
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> historicalLossRunValues = data.getHistoricalLossRunValues();
		    	
		    	HistoricalLossRun[] historicalLossRunPojo =  request.when().get("historical/loss-run/"+accountNumber).as(HistoricalLossRun[].class);
		    	
		    	int countOfObjects = historicalLossRunPojo.length;
		    	int countOfValues = historicalLossRunValues.size();
		    	int i =0;
				int n=0;
				int o=0;
				int p=0;
				int q=0;
				int r=0;
				int s=0;
		    	
				//policy_year
		    		for(int j=countOfValues-7;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[i].getPolicy_year());	
		    			i++;
		    		}
		    //total_open_claims
		    		for(int j=countOfValues-6;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[n].getTotal_open_claims());	
		    			n++;
		    		}
		    		
		    //total_closed_claims
		    		for(int j=countOfValues-5;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[o].getTotal_closed_claims());	
		    			o++;
		    		}
		    		
		    //total_paid
		    		for(int j=countOfValues-4;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[p].getTotal_incurred());	
		    			p++;
		    		}
		    		
		   //total_recovered
		    		for(int j=countOfValues-3;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[q].getTotal_paid());	
		    			q++;
		    		}
		    		
		  //total_outstanding
		    		for(int j=countOfValues-2;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[r].getTotal_outstanding());	
		    			r++;
		    		}
		    		
		   //total_incurred
		    		for(int j=countOfValues-1;j>-1;j=j-7) {
		    			Assert.assertEquals(historicalLossRunValues.get(j), historicalLossRunPojo[s].getTotal_recovered());	
		    			s++;
		    		}
  	
	    	
	    }
	    
	    //changes in exposure occupancy
	    @When("^user calls OccupancyExposureChangeAPI with Get http request$")
	    public void user_calls_occupancyexposurechangeapi_with_get_http_request() throws Throwable {
	    	
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).
				  addQueryParam("type", occupancyType[0]).  
				  build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("exposure/occupancy/" + accountNumber + "").then().log().all().extract().response();
	    }

	    @Then("^OccupancyExposureChangeAPI call got success with status code 200$")
	    public void occupancyexposurechangeapi_call_got_success_with_status_code_200() throws Throwable {
	    	
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    	
	    }

	    @And("^check if the response body and UI of OccupancyExposureChange table matches$")
	    public void check_if_the_response_body_and_ui_of_occupancyexposurechange_table_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	    	DataInsightsWidgetPage dataInsight = new DataInsightsWidgetPage(driver);
			List<String> occupancyData = dataInsight.getOccupancyChangeValues();
			
			
			OccupancyChange[] changesInOccupancy =  request.when().get("exposure/occupancy/" + accountNumber + "").as(OccupancyChange[].class);
		
			int countOfActualValues = occupancyData.size();
			int i =0;
			int n=0;
			int o=0;
			int p=0;
			
		//occupancy
				for(int j=0;j<countOfActualValues ;j=j+4) {
				Assert.assertEquals(occupancyData.get(j), changesInOccupancy[i].getOccupancy() );
				i++;	    
				}
		
		//current_sq_ft
				  for(int j=1;j<countOfActualValues ;j=j+4) {
					  Assert.assertEquals(occupancyData.get(j), changesInOccupancy[n].getCurrent_sq_ft());
				  n++;
				  } 
				  
		//previous_sq_ft
				  for(int j=2;j<countOfActualValues ;j=j+4) {
					  Assert.assertEquals(occupancyData.get(j), changesInOccupancy[o].getPrevious_sq_ft());
				  o++;
				  } 
				  
		//change
				  for(int j=3;j<countOfActualValues ;j=j+4) {
					  Assert.assertEquals(occupancyData.get(j), changesInOccupancy[p].getChange());
				  p++;
				  } 
				  
				//type = number_of_rooms
				   dataInsight.clickOnButtonToChangeOccupancy("Number of Rooms");
				   
				   Thread.sleep(3000);
				   
				 	DataInsightsWidgetPage dataInsight2 = new DataInsightsWidgetPage(driver);
					List<String> occupancyData2 = dataInsight2.getOccupancyChangeValues();
					
					
					req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL"))
							.setContentType(ContentType.JSON).
							 addQueryParam("type", occupancyType[1]).
							  addHeader("Authorization", "Bearer " +IdToken).
							  build();
						  request =  given().log().all().spec(req);
					
					OccupancyChange[] changesInOccupancy2 =  request.when().get("exposure/occupancy/"+accountNumber).as(OccupancyChange[].class);
				
					int countOfActualValues2 = occupancyData2.size();
					int i2 =0;
					int n2=0;
					int o2=0;
					int p2=0;
					
				//occupancy
						for(int j=0;j<countOfActualValues2 ;j=j+4) {
						Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[i2].getOccupancy() );
						i2++;	    
						}
				
				//current_sq_ft
						  for(int j=1;j<countOfActualValues2 ;j=j+4) {
							  Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[n2].getCurrent_number_of_rooms());
						  n2++;
						  } 
						  
				//previous_sq_ft
						  for(int j=2;j<countOfActualValues2 ;j=j+4) {
							  Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[o2].getPrevious_number_of_rooms());
						  o2++;
						  } 
						  
				//change
						  for(int j=3;j<countOfActualValues2 ;j=j+4) {
							  Assert.assertEquals(occupancyData2.get(j), changesInOccupancy2[p2].getChange());
						  p2++;
						  } 
						
	    	
	    }
	    
	   //changes in deductible/limit
	    @When("^user calls ChangeInDeductibleOrLimit with Get http request$")
	    public void user_calls_changeindeductibleorlimit_with_get_http_request() throws Throwable {
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken). 
				  build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("exposure/deductible/" + accountNumber + "").then().log().all().extract().response();
	    	
	    }

	    @Then("^ChangeInDeductibleOrLimit call got success with status code 200$")
	    public void changeindeductibleorlimit_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }
	    
	    @When("^user calls HistoricalLossRatioAPI with Get http request$")
	    public void user_calls_historicallossratioapi_with_get_http_request() throws Throwable {
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("historical/loss-ratio/"+accountNumber).then().log().all().extract().response();
	    	
	    }

	    @Then("^HistoricalLossRatioAPI call got success with status code 200$")
	    public void historicallossratioapi_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);    		    	
	    }

	    @And("^check if the response body and UI of Historical Loss Ratio table matches$")
	    public void check_if_the_response_body_and_ui_of_historical_loss_ratio_table_matches() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> historicalLossRatioValues = data.getHistoricalLossRatioValues();
		    	HistoricalLossRatioPremium[] historicalLossRatioPojo =  request.when().get("historical/loss-ratio/"+accountNumber).as(HistoricalLossRatioPremium[].class);
		    	
		    	int countOfObjects = historicalLossRatioPojo.length;
		    	int countOfValues = historicalLossRatioValues.size()-4;
		    	int i =0;
				int n=0;
				int o=0;
				int p=0;

		    	
				//policy_year
		    		for(int j=countOfValues-4;j>-1;j=j-4) {
		    			Assert.assertEquals(historicalLossRatioValues.get(j), historicalLossRatioPojo[i].getPolicy_year());	
		    			i++;
		    		}
		    //loss_ratio
		    		for(int j=countOfValues-3;j>-1;j=j-4) {
		    			Assert.assertEquals(historicalLossRatioValues.get(j), String.format("%.2f", Double.parseDouble(historicalLossRatioPojo[n].getLoss_ratio())).replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1"));	
		    			n++;
		    		}
		    		
		    //combined_ratio
		    		for(int j=countOfValues-2;j>-1;j=j-4) {
		    			Assert.assertEquals(historicalLossRatioValues.get(j),  String.format("%.2f",Double.parseDouble(historicalLossRatioPojo[o].getCombined_ratio())).replaceFirst("\\.0*$|(\\.\\d*?)0+$", "$1"));	
		    			o++;
		    		}
		    		
		    //premium_paid
		    		for(int j=countOfValues-1;j>-1;j=j-4) {
		    			Assert.assertEquals(historicalLossRatioValues.get(j), historicalLossRatioPojo[p].getPremium_paid());	
		    			p++;
		    		}
		    		 	
	    	
	    }
	
	    @When("^user calls AccountInfoChangeAPI with Get http request$")
	    public void user_calls_accountinfochangeapi_with_get_http_request() throws Throwable {
	  	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("widget/changes-account-info/"+accountNumber).then().log().all().extract().response();
  
	    	
	    }

	    @Then("^AccountInfoChangeAPI call got success with status code 200$")
	    public void accountinfochangeapi_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of AccountInfoChange table matches$")
	    public void check_if_the_response_body_and_ui_of_accountinfochange_table_matches() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> accountChange = data.getAccountInfoChangesValues();
		    	
		    	AccountInformationChanges accountChangePojo =  request.when().get("widget/changes-account-info/"+accountNumber).as(AccountInformationChanges.class);
		    	
				
			//	current brokerage
	    			Assert.assertEquals(accountChange.get(2), accountChangePojo.getCurrent().getAgency_brokerage_firm());	
	    			
	    	//current address		
	    			Assert.assertEquals(accountChange.get(5), accountChangePojo.getCurrent().getAddress());	
	    		
	    	//contact name	
	    			Assert.assertEquals(accountChange.get(8), accountChangePojo.getCurrent().getContact_name());	
	    			
	    	//contact
	    			Assert.assertEquals(accountChange.get(11), accountChangePojo.getCurrent().getContact());
	    			
	    	//phone
	    			Assert.assertEquals(accountChange.get(14), accountChangePojo.getCurrent().getPhone());
	    			
	    	//fax
	    			Assert.assertEquals(accountChange.get(17), accountChangePojo.getCurrent().getFax());			
		    	
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
	    
	    @When("^user calls DnBRating API with Get http request$")
	    public void user_calls_dnbrating_api_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("dnb/rating/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^DnBRating API call got success with status code 200$")
	    public void dnbrating_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of DnBRating widget data matches$")
	    public void check_if_the_response_body_and_ui_of_dnbrating_widget_data_matches() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> dnbValues = data.getDnBRatingValues();
		    	
		     	DnBRating dnbRatingPojo =  request.when().get("dnb/rating/"+accountNumber).as(DnBRating.class);
		     	
		     	Assert.assertEquals(dnbValues.get(0), dnbRatingPojo.getRating());
		    	Assert.assertEquals(dnbValues.get(1), dnbRatingPojo.getFinancialStrength());
		    	Assert.assertEquals(dnbValues.get(2), dnbRatingPojo.getRiskSegment());
			    	
	    }

	    @When("^user calls DescriptiveInformation API with Get http request$")
	    public void user_calls_descriptiveinformation_api_with_get_http_request() throws Throwable {
	    	  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("widget/questionnaire/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^DescriptiveInformation API call got success with status code 200$")
	    public void descriptiveinformation_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of DescriptiveInformation widget data matches$")
	    public void check_if_the_response_body_and_ui_of_descriptiveinformation_widget_data_matches() throws Throwable {
	    	
	   	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	     	Thread.sleep(2000);
	    	List<String> questionsValues = data.getQuestionnaireValues();	   
	    	System.out.println(questionsValues);
	    	
	    	Questionaaire[] questionaaire = request.when().get("widget/questionnaire/"+accountNumber).as(Questionaaire[].class);
	    	
	    	List<String> answerValues = new ArrayList<String>();
	    	
	    	for(int i=0;i<questionaaire.length;i++) {
	    		answerValues.add(questionaaire[i].getResponse_text().replace("0", ""));
	    	}
	    	
	    	
	    	
	    	System.out.println(answerValues);
	    	
	    	Assert.assertEquals(questionsValues.get(0), answerValues.get(8));
	    	Assert.assertEquals(questionsValues.get(1), answerValues.get(1));
	    	Assert.assertEquals(questionsValues.get(2), answerValues.get(6));
	    	Assert.assertEquals(questionsValues.get(3), answerValues.get(2));    	
	    	Assert.assertEquals(questionsValues.get(4), answerValues.get(5));
	    	Assert.assertEquals(questionsValues.get(5), answerValues.get(3));
	    	Assert.assertEquals(questionsValues.get(6), answerValues.get(7));
	    	Assert.assertEquals(questionsValues.get(7), answerValues.get(0));
	    	Assert.assertEquals(questionsValues.get(8), answerValues.get(9));
	    	Assert.assertEquals(questionsValues.get(9), answerValues.get(4));
	    	
    
	    
	    }
	    
	    @When("^user calls HistoricalClaims API with Get http request$")
	    public void user_calls_historicalclaims_api_with_get_http_request() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("historical/loss-run/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^HistoricalClaims API call got success with status code 200$")
	    public void historicalclaims_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);    
	    }

	    @And("^check if the response body and UI of HistoricalClaims widget data matches$")
	    public void check_if_the_response_body_and_ui_of_historicalclaims_widget_data_matches() throws Throwable {
	    	
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		    	
		     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
		    	List<String> historicalClaimsValues = data.getHistoricalClaimsValues();
		    	
		    	HistoricalLossRun[] historicalLossRunPojo =  request.when().get("historical/loss-run/"+accountNumber).as(HistoricalLossRun[].class);
		    	
		    	int countOfObjects = historicalLossRunPojo.length;
		    	int countOfValues = historicalClaimsValues.size();
		    	int i =0;
				int n=0;
				int o=0;
				
		    	
				//policy_year
		    		for(int j=countOfValues-3;j>-1;j=j-3) {
		    			Assert.assertEquals(historicalClaimsValues.get(j), historicalLossRunPojo[i].getPolicy_year());	
		    			i++;
		    		}
		    //total_open_claims
		    		for(int j=countOfValues-2;j>-1;j=j-3) {
		    			Assert.assertEquals(historicalClaimsValues.get(j), historicalLossRunPojo[n].getTotal_open_claims());	
		    			n++;
		    		}
		    		
		    //total_closed_claims
		    		for(int j=countOfValues-1;j>-1;j=j-3) {
		    			Assert.assertEquals(historicalClaimsValues.get(j), historicalLossRunPojo[o].getTotal_closed_claims());	
		    			o++;
		    		}  		

	    }
	    
	    @When("^user calls FamilyTree API with Get http request$")
	    public void user_calls_familytree_api_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("dnb/family-tree/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^FamilyTree API call got success with status code 200$")
	    public void familytree_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of FamilyTree widget data matches$")
	    public void check_if_the_response_body_and_ui_of_familytree_widget_data_matches() throws Throwable {
	    	
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> familyTreeValues = data.getFamilyTreeValues();
	    	System.out.println(familyTreeValues);
	    	
	     response =  request.when().get("dnb/family-tree/"+accountNumber).then().extract().response();
	     JsonPath json = response.jsonPath();
	     
	     Assert.assertEquals(familyTreeValues.get(0), json.getString("primary_name"));
	     Assert.assertTrue(familyTreeValues.get(1).equalsIgnoreCase(json.getString("primary_address.addressLocality.name").toLowerCase()+", "+json.getString("primary_address.addressCountry.name")+", "+json.getString("primary_address.postalCode")));
	    	
		Assert.assertEquals(familyTreeValues.get(2).toUpperCase(), json.getString("children[0].primary_name"));
	    Assert.assertTrue(familyTreeValues.get(3).equalsIgnoreCase(json.getString("children[0].primary_address.addressLocality.name")+", "+json.getString("children[0].primary_address.addressCountry.name")+", "+json.getString("children[0].primary_address.postalCode")));
    	
		Assert.assertEquals(familyTreeValues.get(familyTreeValues.size()-2).toUpperCase(), json.getString("children[0].children[0].primary_name"));
	    Assert.assertTrue(familyTreeValues.get(familyTreeValues.size()-1).equalsIgnoreCase(json.getString("children[0].children[0].primary_address.addressLocality.name")+", "+json.getString("children[0].children[0].primary_address.addressCountry.name")+", "+json.getString("children[0].children[0].primary_address.postalCode")));
    	
	    
		Assert.assertEquals(familyTreeValues.get(4).toUpperCase(), json.getString("children[0].children[0].children[0].primary_name"));
	    Assert.assertTrue(familyTreeValues.get(5).equalsIgnoreCase(json.getString("children[0].children[0].children[0].primary_address.addressLocality.name")+", "+json.getString("children[0].children[0].children[0].primary_address.addressCountry.name")+", "+json.getString("children[0].children[0].children[0].primary_address.postalCode")));
    	
	    
		Assert.assertEquals(familyTreeValues.get(6).toUpperCase(), json.getString("children[0].children[0].children[1].primary_name"));
	    Assert.assertTrue(familyTreeValues.get(7).equalsIgnoreCase(json.getString("children[0].children[0].children[1].primary_address.addressLocality.name")+", "+json.getString("children[0].children[0].children[1].primary_address.addressCountry.name")+", "+json.getString("children[0].children[0].children[1].primary_address.postalCode")));
    	
			
		Assert.assertEquals(familyTreeValues.get(8).toUpperCase(), json.getString("children[0].children[1].primary_name"));
	    Assert.assertTrue(familyTreeValues.get(9).equalsIgnoreCase(json.getString("children[0].children[1].primary_address.addressLocality.name")+", "+json.getString("children[0].children[1].primary_address.addressCountry.name")+", "+json.getString("children[0].children[1].primary_address.postalCode")));
    	
		Assert.assertEquals(familyTreeValues.get(10).toUpperCase(), json.getString("children[0].children[2].primary_name"));
	    Assert.assertTrue(familyTreeValues.get(11).equalsIgnoreCase(json.getString("children[0].children[2].primary_address.addressLocality.name")+", "+json.getString("children[0].children[2].primary_address.addressCountry.name")+", "+json.getString("children[0].children[2].primary_address.postalCode")));
    		
	    	
	    	
	    }
 
	    @When("^user calls DnBCompanyProfile API with Get http request$")
	    public void user_calls_dnbcompanyprofile_api_with_get_http_request() throws Throwable {
	    	 ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		    	String accountNumber = activeSubmission.getSubmissionNumber();
		 
		    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
				  addHeader("ContentType", "json/text"). 
				  addHeader("Authorization", "Bearer " +IdToken).build();
			  request =  given().log().all().spec(req);
			  response =request.when().get("dnb/company-profile/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^DnBCompanyProfile API call got success with status code 200$")
	    public void dnbcompanyprofile_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of DnBCompanyProfile widget data matches$")
	    public void check_if_the_response_body_and_ui_of_dnbcompanyprofile_widget_data_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> companyProfileData = data.getDnBCompanyProfileData();
	    	System.out.println(companyProfileData);
	    	
	    	DnBCompanyProfile companyProfilePojo =  request.when().get("dnb/company-profile/"+accountNumber).as(DnBCompanyProfile .class);
	    	
			
		//	legal form
    			Assert.assertEquals(companyProfileData.get(1), companyProfilePojo.getLegal_form());	
    			
    	//address
    			Assert.assertEquals(companyProfileData.get(3).replaceAll(" ", ""), companyProfilePojo.getAddress().replaceAll(" ", ""));	
    			
    	//contact_name
    			Assert.assertEquals(companyProfileData.get(5).toUpperCase(), companyProfilePojo.getContact_name());
    			
    	//contact
    			Assert.assertEquals(companyProfileData.get(7).replace("-", ""), companyProfilePojo.getContact());
    			
    	//history_record
    			Assert.assertEquals(companyProfileData.get(9), companyProfilePojo.getHistory_record());
    			
    	//annual_sales
    			Assert.assertEquals(companyProfileData.get(11), companyProfilePojo.getAnnual_sales());
    	
    	
    			Assert.assertEquals(companyProfileData.get(13), companyProfilePojo.getOwnership());
    			Assert.assertEquals(companyProfileData.get(15), companyProfilePojo.getState_of_incorporation());
    			
    			String dateString = companyProfileData.get(17);
    			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    			String dateInString = companyProfileData.get(17);
    			Date date = formatter.parse(dateInString);
    			System.out.println(date.toString());
    		//	Date date = new SimpleDateFormat("yyyy-mm-dd").parse(dateString);
    		//	Assert.assertEquals(String.format(dateString, new SimpleDateFormat("yyyy-MM-dd")), companyProfilePojo.getIncoporated_date());
    			Assert.assertEquals(companyProfileData.get(19), companyProfilePojo.getLob());
    			Assert.assertEquals(companyProfileData.get(21), companyProfilePojo.getAge());
    			Assert.assertEquals(companyProfileData.get(23), companyProfilePojo.getEmployees());
   	
	    }
	    
	    @When("^user calls DnBBusinessActivity API with Get http request$")
	    public void user_calls_dnbbusinessactivity_api_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("dnb/business-activity/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^DnBBusinessActivity API call got success with status code 200$")
	    public void dnbbusinessactivity_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of DnBBusinessActivity widget data matches$")
	    public void check_if_the_response_body_and_ui_of_dnbbusinessactivity_widget_data_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> businessActivityData = data.getDnBBusinessActivityData();
	    	System.out.println(businessActivityData);
	    	
	    	DnBBusinessActivity dnbBusinessActivityPojo =  request.when().get("dnb/business-activity/"+accountNumber).as(DnBBusinessActivity .class);
	    	
			
		//	description
				Assert.assertEquals(businessActivityData.get(0).replaceAll(" ", ""), dnbBusinessActivityPojo.getDescription().replaceAll(" ", ""));	
				
		//financial_status
				Assert.assertEquals(businessActivityData.get(1), dnbBusinessActivityPojo.getFinancial_status());	
				
		//financial_condition
				Assert.assertEquals(businessActivityData.get(2), dnbBusinessActivityPojo.getFinancial_condition());
				
		//seasonality
				Assert.assertEquals(businessActivityData.get(3).replaceAll("-", ""), dnbBusinessActivityPojo.getSeasonality());
				
		//facility
				Assert.assertEquals(businessActivityData.get(4), dnbBusinessActivityPojo.getFacility());
				
		//location
				Assert.assertEquals(businessActivityData.get(5).replaceAll(" ", ""), dnbBusinessActivityPojo.getLocation().replaceAll(" ", ""));
				   	
	    }
	    
	    @When("^user calls DnBSIC API with Get http request$")
	    public void user_calls_dnbsic_api_with_get_http_request() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	 
	    	req = new RequestSpecBuilder(). setBaseUri(getGlobalValue("baseURL")).
			  addHeader("ContentType", "json/text"). 
			  addHeader("Authorization", "Bearer " +IdToken).build();
		  request =  given().log().all().spec(req);
		  response =request.when().get("dnb/naic-sic/"+accountNumber).then().log().all().extract().response();
	    }

	    @Then("^DnBSIC API call got success with status code 200$")
	    public void dnbsic_api_call_got_success_with_status_code_200() throws Throwable {
	    	Assert.assertEquals(response.getStatusCode(), 200);
	    }

	    @And("^check if the response body and UI of DnBSIC widget data matches$")
	    public void check_if_the_response_body_and_ui_of_dnbsic_widget_data_matches() throws Throwable {
	    	ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
	    	String accountNumber = activeSubmission.getSubmissionNumber();
	    	
	     	DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
	    	List<String> dnbSICValues = data.getDnBSICValues();
	    	System.out.println(dnbSICValues);
	    	
	    	DnBSIC dnbSICPojo =  request.when().get("dnb/naic-sic/"+accountNumber).as(DnBSIC .class);
	    	
			
		//	naic code
    			Assert.assertEquals(dnbSICValues.get(0), dnbSICPojo.getNaic_code());	
    			
    	//naic_description		
    			Assert.assertEquals(dnbSICValues.get(1), dnbSICPojo.getNaic_description());
    			
    	//sic code		
    			Assert.assertEquals(dnbSICValues.get(2), dnbSICPojo.getSic_code());
    			
    	//sic description
    			Assert.assertEquals(dnbSICValues.get(3), dnbSICPojo.getSic_description());
    	
	    	
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
