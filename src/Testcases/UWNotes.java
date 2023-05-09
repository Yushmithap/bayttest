package Testcases;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import APIresources.APIUtils;
import Pages.ActiveSubmissionsPage;
import Pages.DataInsightsWidgetPage;
import Pages.LoginPage;
import Pages.UWNotesPage;
import Pojo.FacilityLocations;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UWNotes extends APIUtils {
	
	public WebDriver driver;
	


	@Test(enabled = true, priority=1)
	public void validateAddingUWNotes() throws InterruptedException, ParseException, IOException {
		
		  System.setProperty("webdriver.edge.driver",getGlobalValue("edgePath"));
			
			EdgeOptions opt = new EdgeOptions();
			opt.setPageLoadStrategy(PageLoadStrategy.NONE);
			opt.addArguments("--start-maximized");
			WebDriver driver =  new EdgeDriver(opt);    
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				this.driver = driver;
				PageFactory.initElements(driver, this);
				
		
		  driver.get(getGlobalValue("appURL"));

	   	  Thread.sleep(20000);
	
		
		ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		activeSubmission.clickOnActiveSubmissionTab();
		activeSubmission.expandActiveSubmissionAndClickOnApplication("Palos Garza Forwarding Inc",4);
		Thread.sleep(5000);
		
		UWNotesPage uwnote = new UWNotesPage(driver);
		uwnote.clickOnUWNotes();
		uwnote.enterNotesInTextArea("This is automated entry of notes to add");
		uwnote.clickOnAddNotes();
		Assert.assertTrue(uwnote.isViewNotesDisplayed());
		uwnote.clickOnCloseIcon();
		Thread.sleep(2000);
	
	}
	
	@Test(enabled = true, priority=2)
	public void validateClearingUWNotes() throws InterruptedException, ParseException, IOException {

		  
		  UWNotesPage uwnote = new UWNotesPage(driver); 
		  Thread.sleep(2000);
		  uwnote.clickOnUWNotes();
		 
		uwnote.enterNotesInTextArea("This is automated entry of notes to clear");
		uwnote.clickOnclearNotes();
		Thread.sleep(2000);
		uwnote.clickOnCloseIcon();
		Thread.sleep(2000);
		this.driver.quit();

		
	}
	
	@Test(enabled = true, priority=3)
	public void validateDeleteUWNotes() throws InterruptedException, ParseException, IOException {
		
		  System.setProperty("webdriver.edge.driver",getGlobalValue("edgePath"));
			
			EdgeOptions opt = new EdgeOptions();
			opt.setPageLoadStrategy(PageLoadStrategy.NONE);
			opt.addArguments("--start-maximized");
			WebDriver driver =  new EdgeDriver(opt);    
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				this.driver = driver;
				PageFactory.initElements(driver, this);
				
		
		  driver.get(getGlobalValue("appURL"));

	   	  Thread.sleep(20000);
	
		  ActiveSubmissionsPage activeSubmission = new ActiveSubmissionsPage(driver);
		  activeSubmission.clickOnActiveSubmissionTab();
		  activeSubmission.expandActiveSubmissionAndClickOnApplication("Walmart Inc",3);
		  Thread.sleep(5000);
		 
		UWNotesPage uwnote = new UWNotesPage(driver);
		  Thread.sleep(2000);
		uwnote.clickOnUWNotes();
		uwnote.enterNotesInTextArea("This is automated entry of notes to delete");
		uwnote.clickOnAddNotes();
		String actual  =  uwnote.deleteNotes();
		Assert.assertEquals(actual, "Note Deleted Successfully");
		uwnote.clickOnCloseIcon();
		Thread.sleep(2000);
		
	}
	
	@Test(enabled = true, priority=4)
	public void validateCancelDeleteUWNotes() throws InterruptedException, ParseException, IOException {
		
		
		UWNotesPage uwnote = new UWNotesPage(driver);
    	Thread.sleep(2000);
	    uwnote.clickOnUWNotes();
		uwnote.enterNotesInTextArea("This is automated entry of notes to cancel delete");
		uwnote.clickOnAddNotes();
		uwnote.cancelNotesDelete();
		Assert.assertTrue(uwnote.isViewNotesDisplayed());
		uwnote.clickOnCloseIcon();
	
	}
	

}
