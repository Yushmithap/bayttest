package Pages;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import APIresources.APIUtils;


import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class LoginPage extends APIUtils {
	
	WebDriver driver ;
	public RequestSpecification buildingDataReq;
	public RequestSpecification req;
	public ResponseSpecification res;
	 public Response response;
		APIUtils utils = new APIUtils();
		Response result;

	public LoginPage(WebDriver driver) {
		
		 this.driver = driver;
	        PageFactory.initElements(driver, this);
	}
	
	//email locator
	By emailLocator = By.xpath("//input[@type='email']");
	
	//password locator
	By passwordLocator = By.xpath("//input[@type='password']");
	
	//submit  locator
	By submitLocator =  By.xpath("//input[@type='submit']");
	
	
	
	//enters email address
	public void enterEmail(String email) {
		
		driver.findElement(emailLocator).sendKeys(email);
		
	}
	
	//enters password
	public void enterPassword(String pwd) {
		
		driver.findElement(passwordLocator).sendKeys(pwd);
	}
	
	//enters submit button
	public void clickOnSubmit() {
		
		driver.findElement(submitLocator).click();
		
	}
	

	public void loginToApplication() throws InterruptedException, IOException {
		
    System.setProperty("webdriver.gecko.driver","/Users/yushmitha.pitchika/Documents/geckodriver-v0.32.2-win64/geckodriver.exe");
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("marionette", false);
		FirefoxOptions opt = new FirefoxOptions();
		opt.setPageLoadStrategy(PageLoadStrategy.NONE);
		opt.addArguments("--start-maximized");
		opt.merge(dc);
		FirefoxDriver driver =  new FirefoxDriver(opt);
          
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			this.driver = driver;
			PageFactory.initElements(driver, this);
			
		  driver.get(getGlobalValue("appURL"));

	   	Thread.sleep(10000);
		driver.navigate().refresh();
		Thread.sleep(10000);
		
	

}
	
	public void loginToEdgeApplication() throws InterruptedException, IOException {
		
	    System.setProperty("webdriver.edge.driver","C:\\Users\\yushmitha.pitchika\\Downloads\\edgedriver_win64\\msedgedriver.exe");
			
			
			EdgeOptions opt = new EdgeOptions();
			opt.setPageLoadStrategy(PageLoadStrategy.NONE);
			opt.addArguments("--start-maximized");
		//	opt.merge(dc);
			WebDriver driver =  new EdgeDriver(opt);    
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				this.driver = driver;
				PageFactory.initElements(driver, this);
				
			  driver.get(getGlobalValue("appURL"));

		   	Thread.sleep(10000);
			driver.navigate().refresh();
			Thread.sleep(10000);
			
		

	}
}
