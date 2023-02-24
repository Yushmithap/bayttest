package BaytTest.AutomationTest;

import java.awt.Dimension;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import BaytTest.Utilities.BaytPage;
import BaytTest.Utilities.Utilities;

public class MobileAppPart3 extends Utilities {

	public WebDriver driver;

	@BeforeMethod
	public void beforeMethod() throws InterruptedException {

		System.setProperty("webdriver.gecko.driver",
				"/Users/yushmitha.pitchika/Documents/geckodriver-v0.32.2-win64/geckodriver.exe");

		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("marionette", false);
		FirefoxOptions opt = new FirefoxOptions();
		opt.setPageLoadStrategy(PageLoadStrategy.NONE);
		// opt.addArguments("--start-maximized");
		opt.merge(dc);
		FirefoxDriver driver = new FirefoxDriver(opt);
		org.openqa.selenium.Dimension dimension = new org.openqa.selenium.Dimension(300, 700);
		driver.manage().window().setSize(dimension);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		this.driver = driver;
		PageFactory.initElements(driver, this);

		driver.get("https://www.bayt.com");

		Thread.sleep(3000);
	}

	@Test
	public void deleteAccount() throws InterruptedException, IOException {

		// to turn to mobile dimensions

		takeScreenShot(driver, "./screenshots/searchPage.png");

		// enter job search in mobile site
	BaytPage page = new BaytPage(driver);
	page.enterJobTitle("Quality Assurance Engineer");

    page.selectCountry();

	page.clickOnSearchSubmit();

    page.clickOnEasyApply();

		// indentify create account page
		Assert.assertEquals(page.identifyCreateAccountPage(), "Let's Start By Creating Your Account");
		takeScreenShot(driver, "./screenshots/createPage.png");

	}

	@AfterMethod
	public void afterMethod() {
		driver.close();
	}

}
