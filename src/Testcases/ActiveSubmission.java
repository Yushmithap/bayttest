package Testcases;

import org.testng.annotations.Test;

import com.google.common.base.Optional;

import APIresources.APIUtils;
import Pages.ActiveSubmissionsPage;
import Pages.HomePage;
import Pages.LoginPage;
import Utilities.Listeners;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import Pages.ActiveSubmissionsPage;
import testData.SubmissionInfoData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v104.network.Network;
import org.openqa.selenium.devtools.v104.network.model.ConnectionType;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class ActiveSubmission extends APIUtils {

	public  WebDriver driver;

	@BeforeClass
	public void testSetUp() throws Throwable {
	
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

		   	  Thread.sleep(30000);
	}


	
	@Test(description = "Validate user able to complete Submission for all LOBs", enabled = true, dataProvider = "getAccountName")
	public void tc1_verifyUserCompletesSubmissionForAllLoBs(HashMap<String,String> map) throws InterruptedException {
		try {
			ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
			activeSubmission.clickOnActiveSubmissionTab();
			activeSubmission.expandActiveSubmissionAndClickOnApplication(map.get("accountName"),3);

			//Initial Review
			Assert.assertEquals(activeSubmission.getStateOfSubmissionAs("Initial Review", 2).contains("Initial Review"),true);
			activeSubmission.processTheInitialReviewState();
    		Thread.sleep(5000);

           //Triage Review
			Assert.assertEquals(activeSubmission.getStateOfSubmissionAs("Triage Review", 3).contains("Triage Review"),true);
			activeSubmission.processTheTriageReviewState();
			Thread.sleep(5000);
			activeSubmission.clickOnProceedToNextStep();
			Thread.sleep(3000);
		
			//Risk Evaluation
			Assert.assertEquals(activeSubmission.getStateOfSubmissionAs("Risk Evaluation", 4).contains("Risk Evaluation"),true);
			Thread.sleep(3000);
			driver.navigate().refresh();
		    Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);
			activeSubmission.clickOnProceedToNextStep();
        	Thread.sleep(5000);
			
			//Quote Generation
	    	Assert.assertEquals(activeSubmission.getStateOfSubmissionAs("Quote Generation", 5).contains("Quote Generation"),true);
			Thread.sleep(3000);
			activeSubmission.processTheQuoteGenerationState();	
			Thread.sleep(3000);
	
			//Binder Generation
			Assert.assertEquals(activeSubmission.getStateOfSubmissionAs("Binder Generation", 6).contains("Binder Generation"),true);
			activeSubmission.clickOnProceedToNextStep();
			Thread.sleep(3000);

			//Policy Issuance
			Assert.assertEquals(activeSubmission.getStateOfSubmissionAs("Policy Issuance", 7).contains("Policy Issuance"),true);
			Thread.sleep(3000);
			activeSubmission.clickOnProceedToNextStep();
			Thread.sleep(5000);
			activeSubmission.clickOnProceedToNextStep();
			Thread.sleep(5000);
			activeSubmission.clickOnProceedToNextStep();
			Thread.sleep(5000);
			activeSubmission.clickOnProceedToNextStep();
			Thread.sleep(9000);
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}

			//After submission
		//	activeSubmission.getActiveSubmissionTitle();
		//	Thread.sleep(3000);
	

	}

	



	@AfterClass
	public void quitBrowser() {
	     driver.quit();
	}
	
	
	@DataProvider
	public Object[] getAccountName() {
		
		HashMap<String , String> map1 = new HashMap<String, String>();
		map1.put("accountName", "Palos Garza Forwarding Inc");
		
		HashMap<String , String> map2 = new HashMap<String, String>();
		map2.put("accountName", "Spectrum Label Corporation");
		
		HashMap<String , String> map3 = new HashMap<String, String>();
     	map3.put("accountName", "Walmart Inc");
	
    	HashMap<String , String> map4 = new HashMap<String, String>();
		map4.put("accountName", "Mcdonalds Corporation");
		
    	HashMap<String , String> map5 = new HashMap<String, String>();
    	map5.put("accountName", "Los Angeles World Airports");

	 return new Object[] {map3,map5};
		
	}

}
