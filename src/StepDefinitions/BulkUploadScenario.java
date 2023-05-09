package StepDefinitions;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Pages.HomePage;
import Pages.LoginPage;
import Pages.RulesManagementPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BulkUploadScenario {
	WebDriver driver;

	    @Given("I landed on UW portal")
	    public void I_landed_on_UW_portal(){
	    	System.setProperty("webdriver.chrome.driver", "/Users/yushmitha.pitchika/chromedriver_win32/chromedriver.exe");

			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=C:\\Users\\yushmitha.pitchika\\desktop\\User Data");
			options.addArguments("profile-directory=Profile 1");
			options.addArguments("--start-maximized");
			WebDriver driver = new ChromeDriver(options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			this.driver = driver;
			PageFactory.initElements(driver, this);
	    	
	    	driver.get("https://uw-dev.cognitiveinsurance.accenture.com/");	
	    	
	    }
	    
	    
	    @Given("^Logged in with (.+) and (.+) into UW Portal$")
	    public void login_with_username_and_password(String username, String password) throws InterruptedException {
	    	
	    	//create an object for login page
			LoginPage login = new LoginPage(driver);			
			//create an object for home page
			HomePage home = new HomePage(driver);			
			login.enterEmail(username);			
			login.clickOnSubmit();			
			Thread.sleep(5000);			
			login.enterPassword(password);			
			login.clickOnSubmit();			
			Thread.sleep(2000);			
			System.out.println(home.getHeading());
	    			
		}
	    
	    @When("I navigate to Rules Management")
	    public void navigate_to_rules_management() throws IOException, InterruptedException {
	    	RulesManagementPage rulesManagement = new RulesManagementPage(driver);

			rulesManagement.mouseOverAccentureIcon();
			Thread.sleep(2000);

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

			rulesManagement.mouseOverRulesManagementAndClick();
			Thread.sleep(3000);

			Assert.assertEquals(rulesManagement.getRulesManagementText(), "Rules Management");
	    	
	    }
	    
	    @And("clicked on Bulk Upload button")
	    public void clicked_on_bulk_upload_button() {
	    	
	    	RulesManagementPage rulesManagement = new RulesManagementPage(driver);
	    	
	    	rulesManagement.clickOnBulkUpload();
	    	
	    }
	    
	    @And("uploaded the bulkupload file")
	    public void uploaded_the_bulk_upload_file() throws InterruptedException {
	    	
	    	RulesManagementPage rulesManagement = new RulesManagementPage(driver);
			rulesManagement.browseTheBulkUploadFile("C:/Users/yushmitha.pitchika/Documents/Rules_Template.xlsm");
	    	   	
	    }
	    
	    @Then("I validate the toaster message displayed as {string}")
	    public void toaster_message_displayed(String string) throws InterruptedException {
	     	RulesManagementPage rulesManagement = new RulesManagementPage(driver);
	     	Thread.sleep(2000);
			Assert.assertEquals(rulesManagement.toasterMessageForBulkUpload(), string);    	
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	

}
