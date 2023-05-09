package Pages;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import com.testautomationguru.ocular.snapshot.Snap;

import AbstractComponents.PageAbstracts;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class DataInsightsWidgetPage {
	
	 public  WebDriver driver;
	 public RequestSpecification buildingDataReq;
		public RequestSpecification req;
		public ResponseSpecification res;
		 public Response response;
		 
	 
		public DataInsightsWidgetPage(WebDriver driver) {
				 this.driver = driver;
			        PageFactory.initElements(driver, this);
		}
	 
	 private Map<String, String> map;
	    
	    @FindBy(xpath="//lib-comparative-analytics/div")
	    private WebElement comparativeAnalyticsWidget;

	    @FindBy(xpath = "//lib-descriptive-information/div")
	    private WebElement descriptiveInformationWidget;

	    @FindBy(xpath = "//lib-operator-details/div")
	    private WebElement operatorDetailsWidget;
	    
	    @FindBy(xpath = "//lib-key-knowledge-links/div")
	    private WebElement keyKnowledgeLinksWidget;
	    
	    @FindBy(xpath = "//lib-historical-loss/div")
	    private WebElement historicalLossWidget;
	    
	    @FindBy(xpath = "//lib-historical-claims/div")
	    private WebElement historicalClaimsWidget;
	    
	    @FindBy(xpath = "//lib-changes-in-deductible-limit/div")
	    private WebElement changesInDeductibleWidget;
	    
	    @FindBy(xpath = "//lib-changes-in-exposure-occupancy/div")
	    private WebElement changesInExposureOccupancyWidget;
	    
	    @FindBy(xpath = "//lib-changes-in-exposure-vehicletype/div")
	    private WebElement changesInExposureVehicleTypeWidget;
	    
	    @FindBy(xpath = "//lib-historical-loss-ratio/div")
	    private WebElement historicalLossRatioWidget;
	        
	    @FindBy(xpath = "//lib-changes-in-exposure-location/div")
	    private WebElement changesInExposureLocationWidget;
	    
	    @FindBy(xpath = "//lib-vehicle-details/div")
	    private WebElement vehicleDetailsWidget;
	    
	    @FindBy(xpath = "//lib-changes-in-exposure-state/div")
	    private WebElement changesInExposureStateWidget;
	    
	    @FindBy(xpath = "//lib-changes-in-exposure-class-code/div")
	    private WebElement changesInExposureClassCodeWidget;
	    
	    @FindBy(xpath = "//lib-account-information-changes/div")
	    private WebElement changesInAccountInformationWidget;
	    
	    @FindBy(xpath = "//lib-facility-locations/div")
	    private WebElement facilityLocationsWidget;
	    
	    @FindBy(xpath = "//lib-filling-events-large/div")
	    private WebElement fillingEventsWidget;
	    
	    @FindBy(xpath = "//lib-family-tree/div")
	    private WebElement familyTreeWidget;
	    
	    @FindBy(xpath = "//lib-financials/div")
	    private WebElement financialsWidget;
	    
	    @FindBy(xpath = "//lib-db-rating/div")
	    private WebElement dbRatingWidget;
	    
	    @FindBy(xpath = "//lib-db-company-profile/div")
	    private WebElement dbCompanyProfileWidget;
	    
	    @FindBy(xpath = "//lib-business-activities/div")
	    private WebElement businessActivitiesWidget;
	    
	    @FindBy(xpath = "//lib-sic-naics/div")
	    private WebElement sicNaicsWidget;
	    
	    @FindBy(xpath = "//app-financial-strength-insights/div")
	    private WebElement financialStrengthsWidget;
	    
	    @FindBy(xpath = "//lib-viability-rating-small/div")
	    private WebElement viabilityRatingSmallWidget;
	    
	    @FindBy(xpath = "//lib-viability-rating/div")
	    private WebElement viabilityRatingLargeWidget;
	    
	    @FindBy(xpath = "//lib-filing-events/div[1]")
	    private WebElement fillingEventsWidgetSmall;
	    
	    @FindBy(xpath = "//lib-financial-ratios/div")
	    private WebElement financialRatioWidget;
	    
	    @FindBy(xpath = "//lib-company-financials/div")
	    private WebElement companyFinancialsWidget;
	    
	    @FindBy(xpath = "//lib-property-attributes/div")
	    private WebElement propertyAttributesWidget;
	    
	    @FindBy(xpath = "//lib-property-building-data-widget/div")
	    private WebElement propertyBuildingDataWidget;
	    
	

	       
	 	               
	
	

    
	
	 
    
    public boolean verifyComparativeAnalyticsWidgetIsDisplayed() throws IOException { 
		  
  	
  		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(comparativeAnalyticsWidget));
  		File file = comparativeAnalyticsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\comparativeAnalytics.png"));
  		 return comparativeAnalyticsWidget.isDisplayed();
    
    
    }
    
    public boolean verifyDescriptiveInformationWidgetIsDisplayed() throws IOException { 
		  
      	
  		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(descriptiveInformationWidget));
  		File file = descriptiveInformationWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\descriptiveInformation.png"));
  		 return descriptiveInformationWidget.isDisplayed();
    
    
    }
    
   public boolean verifyOperatorDetailsWidgetIsDisplayed() throws IOException { 
		  
      	
  		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(operatorDetailsWidget));
  		File file = operatorDetailsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\operatorDetailsWidget.png"));
  		 return operatorDetailsWidget.isDisplayed();
    
    }
   
   public boolean verifyKeyKnowledgeLinksWidgetIsDisplayed() throws IOException { 
		  
     	
 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(keyKnowledgeLinksWidget));
 		File file = keyKnowledgeLinksWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\keyKnowledgeLinksWidget.png"));
 		 return keyKnowledgeLinksWidget.isDisplayed();
   
   }
   
   public boolean verifyHistoricalLossWidgetIsDisplayed() throws IOException { 
		  
    	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(historicalLossWidget));
		File file = historicalLossWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\historicalLossWidget.png"));
		 return historicalLossWidget.isDisplayed();
  
  }
   
   public boolean verifyHistoricalClaimsWidgetIsDisplayed() throws IOException { 
		  
   	
 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
 		wait.until(ExpectedConditions.visibilityOf(historicalClaimsWidget));
 		File file = historicalClaimsWidget.getScreenshotAs(OutputType.FILE);
 		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\historicalClaimsWidget.png"));
 		 return historicalClaimsWidget.isDisplayed();
   
   }
   
   public boolean verifyChangesInExposureOccupancyWidgetIsDisplayed() throws IOException { 
		  
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(changesInExposureOccupancyWidget));
		File file = changesInExposureOccupancyWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInExposureOccupancyWidget.png"));
		 return changesInExposureOccupancyWidget.isDisplayed();

}

   
   public boolean verifyChangesInDeductibleWidgetIsDisplayed() throws IOException { 
		  	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(changesInDeductibleWidget));
		File file = changesInDeductibleWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInDeductibleWidget.png"));
		 return changesInDeductibleWidget.isDisplayed();
  
  }
   
   public boolean verifyChangesInExposureVehicleTypeWidgetIsDisplayed() throws IOException { 
 	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(changesInExposureVehicleTypeWidget));
		File file = changesInExposureVehicleTypeWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInExposureVehicleTypeWidget.png"));
		 return changesInExposureVehicleTypeWidget.isDisplayed();
  
  }
   
   public boolean verifyHistoricalLossRatioWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(historicalLossRatioWidget));
		File file =  historicalLossRatioWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\historicalLossRatioWidget.png"));
		 return  historicalLossRatioWidget.isDisplayed();
 
 }
   
   public boolean verifyChangesInExposureLocationWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(changesInExposureLocationWidget));
		File file =  changesInExposureLocationWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInExposureLocationWidget.png"));
		 return  changesInExposureLocationWidget.isDisplayed();

}
   
   public boolean vehicleDetailsWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(vehicleDetailsWidget));
		File file =  vehicleDetailsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\vehicleDetailsWidget.png"));
		 return  vehicleDetailsWidget.isDisplayed();

}
   
   public boolean verifyChangesInExposureStateWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(changesInExposureStateWidget));
		File file =  changesInExposureStateWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInExposureStateWidget.png"));
		 return  changesInExposureStateWidget.isDisplayed();

}
   
   public boolean verifyChangesInExposureClassCodeWidgetIsDisplayed() throws IOException { 
	   	
 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
 		wait.until(ExpectedConditions.visibilityOf(changesInExposureClassCodeWidget));
 		File file =  changesInExposureClassCodeWidget.getScreenshotAs(OutputType.FILE);
 		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInExposureClassCodeWidget.png"));
 		 return  changesInExposureClassCodeWidget.isDisplayed();
 		 
 }
   
   public boolean verifychangesInAccountInformationWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(changesInAccountInformationWidget));
		File file =  changesInAccountInformationWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\changesInAccountInformationWidget.png"));
		 return  changesInAccountInformationWidget.isDisplayed();
		 
}
   
   public boolean verifyFacilityLocationsWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(facilityLocationsWidget));
		File file =  facilityLocationsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\facilityLocationsWidget.png"));
		 return  facilityLocationsWidget.isDisplayed();
		 
}
   
   public boolean verifyFamilyTreeWidgetIsDisplayed() throws IOException { 
	   	
 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
 		wait.until(ExpectedConditions.visibilityOf(familyTreeWidget));
 		File file =  familyTreeWidget.getScreenshotAs(OutputType.FILE);
 		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\familyTreeWidget.png"));
 		 return  familyTreeWidget.isDisplayed();
 		 
 }
   
   
   
   public boolean verifyFinancialsWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(financialsWidget));
		File file =  financialsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\financialsWidget.png"));
		 return  financialsWidget.isDisplayed();
		 
}
   
   public boolean verifydbRatingWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(dbRatingWidget));
		File file =  dbRatingWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\dbRatingWidget.png"));
		 return  dbRatingWidget.isDisplayed();
		 
}
   
   public boolean verifydbCompanyProfileWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(dbCompanyProfileWidget));
		File file =  dbCompanyProfileWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\dbCompanyProfileWidget.png"));
		 return  dbCompanyProfileWidget.isDisplayed();
		 
}
   
   public boolean verifybusinessActivitiesWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(businessActivitiesWidget));
		File file =  businessActivitiesWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\businessActivitiesWidget.png"));
		 return  businessActivitiesWidget.isDisplayed();
		 
}
   
   public boolean verifysicNaicsWidgetIsDisplayed() throws IOException { 
	   	
 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
 		wait.until(ExpectedConditions.visibilityOf(sicNaicsWidget));
 		File file =  sicNaicsWidget.getScreenshotAs(OutputType.FILE);
 		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\sicNaicsWidget.png"));
 		 return  sicNaicsWidget.isDisplayed();
 		 
 }
    
   public boolean verifyfinancialStrengthsWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(financialStrengthsWidget));
		File file =  financialStrengthsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\financialStrengthsWidget.png"));
		 return  financialStrengthsWidget.isDisplayed();
		 
}
   
   public boolean verifyViabilityRatingSmallWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(viabilityRatingSmallWidget));
		File file =  viabilityRatingSmallWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\viabilityRatingSmallWidget.png"));
		 return  viabilityRatingSmallWidget.isDisplayed();
		 
}
   
   public boolean verifyViabilityRatingLargeWidgetIsDisplayed() throws IOException { 
	   	
 		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
 		wait.until(ExpectedConditions.visibilityOf(viabilityRatingLargeWidget));
 		File file =  viabilityRatingLargeWidget.getScreenshotAs(OutputType.FILE);
 		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\viabilityRatingLargeWidget.png"));
 		 return  viabilityRatingLargeWidget.isDisplayed();
 		 
 }
   
   public boolean verifyFillingEventsWidgetSmallsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(fillingEventsWidgetSmall));
		File file =  fillingEventsWidgetSmall.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\fillingEventsWidgetSmall.png"));
		 return  fillingEventsWidgetSmall.isDisplayed();
		 
}
   
   public boolean verifyFinancialRatioWidgetSmallsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(financialRatioWidget));
		File file =  financialRatioWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\financialRatioWidget.png"));
		 return  financialRatioWidget.isDisplayed();
		 
}
   
   public boolean verifyCompanyFinancialsWidgetIsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(companyFinancialsWidget));
		File file =  companyFinancialsWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\companyFinancialsWidget.png"));
		 return  companyFinancialsWidget.isDisplayed();
		 
}
   
   public boolean verifyPropertyAttributesWidgetlsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(propertyAttributesWidget));
		File file =  propertyAttributesWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\propertyAttributesWidget.png"));
		 return  propertyAttributesWidget.isDisplayed();
		 
}
   public boolean verifyPropertyBuildingDataWidgetlsDisplayed() throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(propertyBuildingDataWidget));
		File file =  propertyBuildingDataWidget.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\propertyBuildingDataWidget.png"));
		 return  propertyBuildingDataWidget.isDisplayed();
		 
}
   
   public boolean verifyDataInsightWidgetlsDisplayed(WebElement element) throws IOException { 
	   	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
		File file =  element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("C:\\Users\\yushmitha.pitchika\\eclipse-workspace\\UWPortal\\screenshots\\DataInsightsWidgets\\"+element+".png"));
		 return  element.isDisplayed();
		 
}
   
   public List<String> verifyChangesInExposureVehicleTypeWidgetData() throws JsonProcessingException {
	   
	   List<WebElement> vehicleTableHeaders =  driver.findElements(By.xpath("//lib-changes-in-exposure-vehicletype/div/descendant::table/thead/tr/th"));
	   
	   
	   List<String> headerValues = vehicleTableHeaders.stream().map(a->a.getAccessibleName()).collect(Collectors.toList());
	   
       List<WebElement> vehicleTablevalues =  driver.findElements(By.xpath("//lib-changes-in-exposure-vehicletype/div/descendant::table/tbody/tr/td"));
	   
	   List<String> tableValues = vehicleTablevalues.stream().map(a->a.getText().replaceAll(",", "")).collect(Collectors.toList());
	   
	   
	  
	  
	   
	    return tableValues;
	   
	   
	   
	  
	
	   
	   
   }
   
   public void clickOnButtonToChangeExposureLocation(String exposureParam) {
	   
	 driver.findElement(By.xpath("//lib-changes-in-exposure-location/div/descendant::button")).click();
	 driver.findElement(By.xpath("//div[contains(@class,'locationDropdown')]/ul/li/span[contains(text(),'"+exposureParam+"')]")).click();
 
	   
   }
   
 @SuppressWarnings("deprecation")
public List<String> verifyChangesInExposureLocationTypeWidgetData() throws JsonProcessingException {
	 
	   
	   
	   List<WebElement> exposureLocationHeaders =  driver.findElements(By.xpath("//lib-changes-in-exposure-location/div/descendant::table/thead/tr/th"));
	   	   
	   List<String> headerValues = exposureLocationHeaders.stream().map(a->a.getAccessibleName()).collect(Collectors.toList());
	   
       List<WebElement> exposureLocationdata =  driver.findElements(By.xpath("//lib-changes-in-exposure-location/div/descendant::table/tbody/tr/td"));
	   
	   List<String> tableValues = exposureLocationdata.stream().map(b->b.getText().replaceAll("[\\$\\,]", "")).collect(Collectors.toList());
	   
	   
		/*
		 * int count = driver.findElements(By.xpath(
		 * "//lib-changes-in-exposure-location/div/descendant::table/tbody/tr")).size();
		 * Map<String, String> tableMap = new HashMap<String,String>();
		 * 
		 * for(int i=0;i<=count-1;i++){ for(int j=0;j<=headerValues.size()-1;j++) {
		 * 
		 * tableMap.put(headerValues.get(j), tableValues.get(j)); } }
		 * 
		 * String result = tableMap.entrySet() .stream() .map(entry -> entry.getKey() +
		 * " : " + entry.getValue()) .collect(Collectors.joining(", "));
		 */
	
	    return tableValues;
	   
	   
	   
	  
	
	   
	   
   }
   
 public List<String> verifyFacilityLocationsWidgetData()  {
 
	   List<WebElement> facilityLocationHeaders =  driver.findElements(By.xpath("//lib-facility-locations/descendant::div[@class='title-1']"));
	   	   
	   List<String> headerValues = facilityLocationHeaders.stream().map(a->a.getAccessibleName()).collect(Collectors.toList());
	   
       List<WebElement> facilityLocationData =  driver.findElements(By.xpath("//lib-facility-locations/descendant::div[@class='title-1']/following-sibling::div"));
	   
	   List<String> tableValues = facilityLocationData.stream().map(b->b.getText().replaceAll("-", "")).collect(Collectors.toList());

	    return tableValues;
   
 }
 
 public Boolean presenceOfSingleFacilityLocation() {

	//   WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	//	boolean isdisplay =  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//lib-facility-locations/descendant::div[text()='Location']")));
		  WebElement facilityLocation =  driver.findElement(By.xpath("//lib-facility-locations/descendant::div[text()='Location']"));
		  return facilityLocation.isDisplayed();
	   //  return isdisplay;
	   	 
 }
 
 public List<WebElement> clickToSelectFacilityLocation() {
	  List<WebElement> locationRadioButtons = driver.findElements(By.xpath("//lib-facility-locations/descendant::input[@type='radio']/following-sibling::label[contains(@for,'location')][1]"));
	 
	  return locationRadioButtons;
	  
}
 
 
 public List<String> getMultipleLocationTitles(){
	 
	 List<WebElement> facilityLocationTitles =  driver.findElements(By.xpath("//div[contains(@class,'location-title')]/label[2]"));
	// WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	// wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'location-title')]/label[2]")));
	  List<String> locationTitles = facilityLocationTitles.stream().map(a->a.getText()).collect(Collectors.toList());
	  System.out.println(locationTitles);
	  return locationTitles;
	 
 }
 
 public List<String> verifyMultipleFacilityLocationsWidgetData()  {
	 
	   
       List<WebElement> facilityLocationData =  driver.findElements(By.xpath("//lib-facility-locations/descendant::div[@class='element2']/span[@class='title-2']/following-sibling::span"));
	   
	   List<String> tableValues = facilityLocationData.stream().map(b->b.getText().replaceAll("-", "")).collect(Collectors.toList());	   

	    return tableValues;
 
}
 
 public void clickOnSatelliteViewOfLocation() {
	 WebElement element = driver.findElement(By.xpath("//input[@value = 'satellite_view']"));
	 
	 WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value = 'satellite_view']")));
	 JavascriptExecutor js = (JavascriptExecutor) driver;
	 js.executeScript("window.scrollBy(0,2000)");
	
	 element.click();
	 
	 
 }
 
 public List<String> verifVehicleDetailsWidgetData()  {
	   
	   List<WebElement> vehicleTableHeaders =  driver.findElements(By.xpath("//lib-vehicle-details/descendant::table/thead/tr/th"));
	   
	   
	   List<String> headerValues = vehicleTableHeaders.stream().map(a->a.getAccessibleName()).collect(Collectors.toList());
	   
     List<WebElement> vehicleTablevalues =  driver.findElements(By.xpath("//lib-vehicle-details/descendant::table/tbody/tr/td"));
	   
	   List<String> tableValues = vehicleTablevalues.stream().map(a->a.getText().replaceAll("[\\$\\,]", "")).collect(Collectors.toList());
   
	    return tableValues;
   
 }
 
 public List<String> buildingDataAddress(){
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//lib-property-building-data-widget/descendant::div[text()='Address']/following-sibling::div"))); 
	 WebElement address = driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::div[text()='Address']/following-sibling::div"));
	String addressText =  address.getText();
//	String stripText = addressText.trim();
	String[] arrayString = addressText.split("\\s+");
//	System.out.println("Address Array" + Arrays.toString(arrayString));
	ArrayList<String> addressStrings = new ArrayList<String>();    // 3966 Francis Ridge Hayward CA 6037 
	for(int i=0;i<arrayString.length;i++) {
		addressStrings.add(arrayString[i]);
	}
	return addressStrings;	 
 }
 
 public List<String> verifyBuildingDataWidget(){
	 
	 WebElement pbKey = driver.findElement(By.xpath("//lib-property-building-data-widget/div/div[1]/div[2]/div[2]"));
	 WebElement ownerName = driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::div[text()='Owners']/following-sibling::div"));
	List<WebElement> tableValues = driver.findElements(By.xpath("//lib-property-building-data-widget/descendant::table/tbody/tr/td"));
	WebElement replacementCost = driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::div[text()='Replacement Cost']/following-sibling::div"));
	
	List<WebElement> allWebElements = new ArrayList<WebElement>();
	allWebElements.add(0, pbKey);
	allWebElements.add(ownerName); //1 
	allWebElements.addAll(tableValues);  //3 4 5 7 8 9 11 12 13 15 16 17 indexes
	allWebElements.add(replacementCost); //18
	
	List<String> allValues = allWebElements.stream().map(s->s.getText().replaceAll("[\\$\\,\\--]", "")).collect(Collectors.toList());
	
	return allValues;

	//total 19 text values are returned
 }
 
 public String getpbKey() {
	 WebElement pbKey = driver.findElement(By.xpath("//lib-property-building-data-widget/div/div[1]/div[2]/div[2]"));
	 return pbKey.getText();
	 
 }
 
 public String getOwnerName(){
	 WebElement ownerName = driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::div[text()='Owners']/following-sibling::div"));
	 return ownerName.getText();
	 
 }
 
 public List<String> getBuildingDataTableValues(){
	 List<WebElement> tableValues2 = driver.findElements(By.xpath("//lib-property-building-data-widget/descendant::table/tbody/tr/td"));
	 WebElement tableElement= driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::table/tbody"));  
	 JavascriptExecutor jse = (JavascriptExecutor)driver; 
	 jse.executeScript("arguments[0].scrollIntoView(true)", tableElement);
	 WebElement tableElement2= driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::table/tbody")); 
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-property-building-data-widget/descendant::table/tbody/tr/td"));
	 
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,\\--]", "")).collect(Collectors.toList());
	 return allValues;
	 
 }
 
 public String getReplacementCost() {
		WebElement replacementCost = driver.findElement(By.xpath("//lib-property-building-data-widget/descendant::div[text()='Replacement Cost']/following-sibling::div"));
		String replacement = replacementCost.getText().replaceAll("[\\$\\,]", "");
		return replacement;
 }
 
 public List<String> getCompanyFinancialsAssets(){
	
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-company-financials/descendant::table[1]/tbody/tr/td[2]"));
	 
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]", "").split("\\.")[0]).collect(Collectors.toList());
	 return allValues;
 }
 
 public List<String> getCompanyFinancialsLiabilities(){
		
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-company-financials/descendant::table[2]/tbody/tr/td[2]"));
	 
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]", "").split("\\.")[0]).collect(Collectors.toList());
	 return allValues;
 }
 
 public List<String> getCompanyFinancialsEquity(){
		
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-company-financials/descendant::table[3]/tbody/tr/td[2]"));
	 
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]", "").split("\\.")[0]).collect(Collectors.toList());
	 return allValues;
 }
 
 public List<String> getFinancialRatios(){
	 List<WebElement> elements = new ArrayList<WebElement>();
	 WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//lib-financial-ratios/div/div[2]/div[1]/div[2]/p")));	 
	 for(int i=1; i<7;i++){
		
		 WebElement element = driver.findElement(By.xpath("//lib-financial-ratios/div/div[2]/div["+i+"]/div[2]/p"));
		 elements.add(element);
	 }
	 List<String> allValues = elements.stream().map(a->a.getText().replaceAll("[\\$\\,\\--]", "").replaceAll("()\\.0+$|(\\..+?)0+$", "$2")).collect(Collectors.toList());
	 return allValues;
	 
 }
 
 public List<String> getFinancials(){
	 List<WebElement> elements = driver.findElements(By.xpath("//lib-financials/descendant::p[@class='content-value']"));
	 List<String> values = elements.stream().map(a->a.getText().replaceAll("[\\$\\,\\(\\)]","")).collect(Collectors.toList());
	 return values;
	 
 }
 
 public String getknowledgeLinkHref() {
	 
	 WebElement element = driver.findElement(By.xpath("//div[contains(@class,'keylinks')]/a"));
	 String href = element.getAttribute("href");
	 return href.trim();
	 
 }
 
public String getknowledgeLinkText() {
	 
	 WebElement element = driver.findElement(By.xpath("//div[contains(@class,'keylinks')]/a/span[2]"));
	 String text = element.getText();
	 return text;
	 
 }

public List<String> getOperatorDetailsValues(){
	
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-operator-details/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;
}

public String noDataAvailable() {
	
	return driver.findElement(By.xpath("//p[contains(text(),'No Data Available')]")).getText();
	
}

public boolean isKnowledgeKeyLinkExists() {
	return driver.findElement(By.xpath("//a[contains(@href,'www')]")).isDisplayed();
}

public List<String> getHistoricalLossRunValues(){
	
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-historical-loss/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]","").replaceAll("()\\.0+$|(\\..+?)0+$", "$2")).collect(Collectors.toList());
	 return allValues;
}


public List<String> getOccupancyChangeValues() {
	
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-changes-in-exposure-occupancy/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]","")).collect(Collectors.toList());
	 return allValues;
	
}

public void clickOnButtonToChangeOccupancy(String exposureParam) {
	   
	 driver.findElement(By.xpath("//lib-changes-in-exposure-occupancy/descendant::button")).click();
	 driver.findElement(By.xpath("//lib-changes-in-exposure-occupancy/descendant::li[2]/span[text()='"+exposureParam+"']")).click();

	   
}

public List<String> getHistoricalLossRatioValues(){
	//replaceFirst("()\\.0+$|(\\..+?)0+$", "$2").replaceAll("\\.0*$|(\\.\\d*?)0+$", "$1"
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-historical-loss-ratio/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]","").replaceAll("()\\.0+$|(\\..+?)0+$", "$2")).collect(Collectors.toList());
	 return allValues;
}

public List<String> getAccountInfoChangesValues(){
	
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-account-information-changes/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;
}

public List<String> getChangesInExposureStateValues(){
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-changes-in-exposure-state/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]","")).collect(Collectors.toList());
	 return allValues;	
}

public List<String> getChangesInExposureClassCodeValues(){
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-changes-in-exposure-class-code/descendant::table/tbody/tr/td"));
	 List<String> allValues = tableValues.stream().map(a->a.getText().replaceAll("[\\$\\,]","")).collect(Collectors.toList());
	 return allValues;	
}

public void clickOnClassCodeDropdown(String param) {
	WebElement element = driver.findElement(By.xpath("//lib-changes-in-exposure-class-code/descendant::button"));	
	element.click();
	WebElement element2 = driver.findElement(By.xpath("//lib-changes-in-exposure-class-code/descendant::ul/li/span[contains(text(),'"+param+"')]"));
	element2.click();	
}

public void clickOnStateDropdown(String param) {
	WebElement element = driver.findElement(By.xpath("//lib-changes-in-exposure-state/descendant::button"));	
	element.click();
	WebElement element2 = driver.findElement(By.xpath("//lib-changes-in-exposure-state/descendant::ul/li/span[contains(text(),'"+param+"')]"));
	element2.click();	
}

public List<String> getFilingEventsGeneral() {
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-filling-events-large/div/div[2]/div[1]/div/div/div/div/div"));
	 List<String> allValues = tableValues.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;		
}

public List<String> getFilingEventsFinancials() {
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-filling-events-large/div/div[2]/div[2]/div/div/div/div/div"));
	 List<String> allValues = tableValues.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;		
}

public List<String> getFilingEventsViolations() {
	 List<WebElement> tableValues = driver.findElements(By.xpath("//lib-filling-events-large/div/div[2]/div[3]/div/div/div/div/div"));
	 List<String> allValues = tableValues.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;		
}

public List<String> getDnBRatingValues() {
	WebElement element = driver.findElement(By.xpath("//lib-db-rating/descendant::div[@class='uil-chip-title']"));
	WebElement element2 = driver.findElement(By.xpath("//lib-db-rating/descendant::div[@class='popover-body'][1]"));
	WebElement element3 = driver.findElement(By.xpath("//lib-db-rating/descendant::div[@class='popover-body'][3]"));
	
	 List<WebElement> elements = new ArrayList<WebElement>();
	 elements.add(element);
	 elements.add(element2);
	 elements.add(element3);
	 
	 List<String> allValues = elements.stream().map(s->s.getText()).collect(Collectors.toList());
	 
	 return allValues;
	
}

public List<String> getDnBCompanyProfileData(){
	List<WebElement> elements = driver.findElements(By.xpath("//lib-db-company-profile/div/div/div[2]/div/span"));
	 List<String> allValues = elements.stream().map(a->a.getText().replaceAll("[\\)]","")).collect(Collectors.toList());
	 return allValues;	
	
	
}

public List<String> getDnBBusinessActivityData(){
	List<WebElement> elements = driver.findElements(By.xpath("//lib-business-activities/div/div/div/div/div/div/span"));
	elements.add(driver.findElement(By.xpath("//lib-business-activities/div/div/div[2]/p")));
	 List<String> allValues = elements.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;	

}

public List<String> getDnBSICValues(){
	List<WebElement> elements = driver.findElements(By.xpath("//lib-sic-naics/descendant::table/tbody/tr/td"));
	 List<String> allValues = elements.stream().map(a->a.getText()).collect(Collectors.toList());
	 return allValues;	
}

public List<String> getPropertyAttributesTableValues(int tableNumber){
	// List<WebElement> tableValues = driver.findElements(By.xpath("//lib-property-attributes/descendant::table["+tableNumber+"]/tbody/tr/td[2]"));
//	 WebElement tableElement= driver.findElement(By.xpath("//lib-property-attributes/descendant::table["+tableNumber+"]/tbody"));  
//	 JavascriptExecutor jse = (JavascriptExecutor)driver; 
//	 jse.executeScript("arguments[0].scrollIntoView(true)", tableElement);
//	 WebElement tableElement2= driver.findElement(By.xpath("//lib-property-attributes/descendant::table["+tableNumber+"]/tbody")); 
	 List<WebElement> tableValues2 = driver.findElements(By.xpath("//lib-property-attributes/descendant::table["+tableNumber+"]/tbody/tr/td[2]"));
 
	 List<String> allValues = tableValues2.stream().map(a->a.getText().replaceAll("[\\$\\,]", "")).collect(Collectors.toList());
	 return allValues;
	 
}

public String getpbKey2() {
	 WebElement pbKey = driver.findElement(By.xpath("//lib-property-attributes/div/div/div/div/div/p"));
	 return pbKey.getText();
	 
}


public void changeTableOfPropertyAttributes(int tableNumber) {
	WebElement element = driver.findElement(By.xpath("//lib-property-attributes/descendant::li["+tableNumber+"]/a"));
	element.click();
	
}

public List<String> propertyAttributesAddress(){
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//lib-property-attributes/div/div/div/div[2]/div"))); 
 WebElement address = driver.findElement(By.xpath("//lib-property-attributes/div/div/div/div[2]/div"));
String addressText =  address.getText().replaceAll(",","");
String[] arrayString = addressText.split("\\s+");
ArrayList<String> addressStrings = new ArrayList<String>();    // 3966 Francis Ridge Hayward CA 6037 
for(int i=0;i<arrayString.length;i++) {
	addressStrings.add(arrayString[i]);
}
return addressStrings;	 
}

public List<String> getFamilyTreeValues(){
	List<WebElement> value1 = driver.findElements(By.xpath("//lib-family-tree/div/div/div[2]"));
	String headQuartersHeading = value1.get(1).getText();
	
	String primaryAddress = driver.findElement(By.xpath("//lib-family-tree/div/div/div[3]")).getText();
	
	List<WebElement> subsidiary = driver.findElements(By.xpath("//span[@class='corporateLinkage']/following-sibling::span"));
	 List<String> allSubsidiaryValues = subsidiary.stream().map(a->a.getText()).collect(Collectors.toList());
	 
	 List<WebElement> subsidiaryInsuredCompany = driver.findElements(By.xpath("//span[contains(@class,'insuredComapanyHead')]/following-sibling::span"));
	 List<String> subsidiaryInsuredCompanyValue = subsidiaryInsuredCompany.stream().map(a->a.getText()).collect(Collectors.toList());
	 
	 List<String> allValues = new ArrayList<String>();
	 allValues.add(headQuartersHeading);
	 allValues.add(primaryAddress);
	 allValues.addAll(allSubsidiaryValues);
	 allValues.addAll(subsidiaryInsuredCompanyValue);
	 
	 return allValues;
	 
}

public List<String> getFinancialStrengthInsights(){
	
	List<WebElement> smallFinancialInsights = driver.findElements(By.xpath("//lib-financial-strength/descendant::p/span[contains(@class,'score-text-value')]"));
	List<String> smallFinancialInsightsValues = smallFinancialInsights.stream().map(a->a.getText()).collect(Collectors.toList());
	 
	String fraudRiskValue = driver.findElement(By.xpath("//lib-financial-strength/descendant::div[contains(@class,'risk-details')]/div/div/p[contains(@class,'score-text-value')]")).getText();
	
	List<WebElement> layOffScores = driver.findElements(By.xpath("//lib-layoff-score/div/div/div/div"));
	List<String> layOffScoresValues =  layOffScores.stream().map(a->a.getText()).collect(Collectors.toList());
	
	List<WebElement> appFinancialInsights = driver.findElements(By.xpath("//app-financial-strength-insights/descendant::div[@class='popover-body']"));
	appFinancialInsights.addAll(driver.findElements(By.xpath("//app-financial-strength-insights/descendant::div/span[@class='riskIncidence-txt']")));
	List<String> appFinancialInsightsValues = appFinancialInsights.stream().map(a->a.getText().replaceAll("Average", "Moderate")).collect(Collectors.toList());
	
	List<String> allValues = new ArrayList<String>();
	allValues.addAll(smallFinancialInsightsValues);
	allValues.add(fraudRiskValue);
	allValues.addAll(layOffScoresValues);
	allValues.addAll(appFinancialInsightsValues);
	
	return allValues;
	
}

public List<String> getViabilityRatingValues(){
	
	String viabilityratingSmall = driver.findElement(By.xpath("//lib-viability-rating-small/descendant::div[@class='rating-cloumn']")).getText();
	String viabilityscoreSmall = driver.findElement(By.xpath("//lib-viability-rating-small/descendant::div[contains(@class,'vulnerablescorebackground')]")).getText();
	String portfolioscoreSmall = driver.findElement(By.xpath("//lib-viability-rating-small/descendant::div[contains(@class,'portfolioscorebackground')]")).getText();
	String dataScoreSmall = driver.findElement(By.xpath("//lib-viability-rating-small/descendant::div[contains(@class,'datascorebackground')]")).getText();
	String organizationScoreSmall = driver.findElement(By.xpath("//lib-viability-rating-small/descendant::div[contains(@class,'organizationscoreebackground')]")).getText();
	List<WebElement> viabilityRatingLarge = driver.findElements(By.xpath("//lib-viability-rating/div/div/div/div/div/span[2]"));
	List<String> viabilityRatingLargeValues = viabilityRatingLarge.stream().map(s->s.getText()).collect(Collectors.toList());
	String viabilityratingLarge = driver.findElement(By.xpath("//lib-viability-rating/descendant::div[@class='rating-cloumn']")).getText();
	String viabilityscoreLarge = driver.findElement(By.xpath("//lib-viability-rating/descendant::div[contains(@class,'vulnerabilityscorebackground')]")).getText();
	String portfolioscoreLarge = driver.findElement(By.xpath("//lib-viability-rating/descendant::div[contains(@class,'portfoliocountbackground')]")).getText();
	String dataScoreLarge = driver.findElement(By.xpath("//lib-viability-rating/descendant::div[contains(@class,'datadepthscorebackground')][1]")).getText();
	String organizationScoreLarge = driver.findElement(By.xpath("//lib-viability-rating/descendant::div[contains(@class,'datadepthscorebackground')][2]")).getText();
	
	List<String> allValues = new ArrayList<String>();
	allValues.add(viabilityratingSmall);
	allValues.add(viabilityscoreSmall);
	allValues.add(portfolioscoreSmall);
	allValues.add(dataScoreSmall);
	allValues.add(organizationScoreSmall);
	allValues.addAll(viabilityRatingLargeValues);
	allValues.add(viabilityratingLarge);
	allValues.add(viabilityscoreLarge);
	allValues.add(portfolioscoreLarge);
	allValues.add(dataScoreLarge);
	allValues.add(organizationScoreLarge);
	
	
	return allValues;
	
}

public List<String> getViolationsAndCitations(){
	
	List<WebElement> elements = driver.findElements(By.xpath("//lib-violation-citation/div/div/div/div[2]/p"));
	List<String> allValues = elements.stream().map(s->s.getText()).collect(Collectors.toList());
	
	return allValues;
	
}

public List<String> getQuestionnaireValues(){	
	List<WebElement> elements = driver.findElements(By.xpath("//lib-descriptive-information/div/div/div[2]/div/li/span"));
	List<String> allValues = elements.stream().map(s->s.getText().replaceAll("-", "")).collect(Collectors.toList());
	return allValues;
}

public List<String> getHistoricalClaimsValues(){
	List<WebElement> elements = driver.findElements(By.xpath("//lib-historical-claims/descendant::table/tbody/tr/td"));
	List<String> allValues = elements.stream().map(s->s.getText()).collect(Collectors.toList());
	return allValues;

	
}

}
