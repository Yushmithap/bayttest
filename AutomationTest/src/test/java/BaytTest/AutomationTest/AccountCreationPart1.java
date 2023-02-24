package BaytTest.AutomationTest;

import org.testng.annotations.Test;

import BaytTest.Utilities.BaytPage;
import BaytTest.Utilities.Utilities;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class AccountCreationPart1 extends Utilities {

	public WebDriver driver;

	@BeforeMethod
	public void beforeMethod() throws InterruptedException {

		System.setProperty("webdriver.gecko.driver",
				"/Users/yushmitha.pitchika/Documents/geckodriver-v0.32.2-win64/geckodriver.exe");

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("marionette", false);
		FirefoxOptions opt = new FirefoxOptions();
		opt.setPageLoadStrategy(PageLoadStrategy.NONE);
		opt.addArguments("--start-maximized");
		opt.merge(dc);
		FirefoxDriver driver = new FirefoxDriver(opt);

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		this.driver = driver;
		PageFactory.initElements(driver, this);

		driver.get("https://www.bayt.com/");

		Thread.sleep(3000);
	}

	@Test
	public void validateRegistration() throws InterruptedException, IOException {

		BaytPage page = new BaytPage(driver);

		page.clickOnAboutUsLink();

		page.selectJobTitle();

		Thread.sleep(4000);

		page.clickOnApply();

		Thread.sleep(3000);

		takeScreenShot(driver, "./screenshots/registration.png");

		page.fillRegistrationForm();

//validates already registered error message	     

		page.duplicateEmailError();

		takeScreenShot(driver, "./screenshots/error.png");

//enter dynamic email.address
		Thread.sleep(4000);

		page.enterValidEmailAddres();

		Assert.assertEquals(page.getTellUsAboutYourself(), "Tell us about yourself");

		takeScreenShot(driver, "./screenshots/tellme.png");

	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

}
