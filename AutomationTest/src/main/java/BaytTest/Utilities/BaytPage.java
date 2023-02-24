package BaytTest.Utilities;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaytPage {
	
	public  WebDriver driver;

	public BaytPage(WebDriver driver) {
		 this.driver = driver;
	        PageFactory.initElements(driver, this);
	}
	
	
	public void clickOnAboutUsLink() {
		
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'About Us')]"));
		JavascriptExecutor jse2 = (JavascriptExecutor) driver;
		jse2.executeScript("arguments[0].scrollIntoView()", element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		
	}
	
	public void selectJobTitle() {
		
		List<WebElement> elements = driver.findElements(By.xpath("//a[@data-js-aid='jobTitle']"));
		JavascriptExecutor jse3 = (JavascriptExecutor) driver;
		jse3.executeScript("arguments[0].scrollIntoView()", elements.get(0));
		JavascriptExecutor executor2 = (JavascriptExecutor) driver;
		executor2.executeScript("arguments[0].click();", elements.get(0));
	}
	
	public void clickOnApply() {
		
		Set<String> windows = driver.getWindowHandles();
		System.out.println(windows);
		for (String window : windows) {
			driver.switchTo().window(window);
			if (driver.getCurrentUrl().contains("jobs")) {

				// clicks on apply button
				WebElement element3 = driver.findElement(By.xpath("//span[@id='apply_1']//a[@id='applyLink_1']"));
				JavascriptExecutor executor3 = (JavascriptExecutor) driver;
				executor3.executeScript("arguments[0].click();", element3);
			}
		}
		
	}
	
	public void fillRegistrationForm() throws InterruptedException {
		
		// fill the form with already registered mail ID
				WebElement firstName = driver.findElement(By.xpath("//input[@placeholder='First Name']"));
				firstName.sendKeys("Yushmitha");
				WebElement lastName = driver.findElement(By.xpath("//input[@placeholder='Last Name']"));
				lastName.sendKeys("Pitchika");
				WebElement emailAddress = driver.findElement(By.xpath("//input[@placeholder='Email Address']"));
				emailAddress.sendKeys("yushmitha.pitchika@gmail.com");
				WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
				password.sendKeys("jrdht567,kgk#");
				WebElement mobile = driver.findElement(By.xpath("//input[@placeholder='Your mobile number']"));
				mobile.sendKeys("7093170671");
				Thread.sleep(2000);
				WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
				JavascriptExecutor executor3 = (JavascriptExecutor) driver;
				executor3.executeScript("arguments[0].click();", submit);
	}
	
	public void duplicateEmailError() {
		WebElement element5 = driver.findElement(By.xpath("//div[contains(text(),'This email is already registered.')]"));
		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait5.until(ExpectedConditions.visibilityOf(element5));
		element5.isDisplayed();
	}
	
	public void enterValidEmailAddres() {
		WebElement emailAddress2 = driver.findElement(By.cssSelector("#JsApplicantRegisterForm_email"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '';", emailAddress2);
		emailAddress2.sendKeys("testmail@gmail.com");
		
		WebElement submit2 = driver.findElement(By.xpath("//button[@type='submit']"));
		JavascriptExecutor executor4 = (JavascriptExecutor) driver;
		executor4.executeScript("arguments[0].click();", submit2);
	}
	
	public String getTellUsAboutYourself() {
		WebElement element6 = driver.findElement(By.xpath("//p[text()='Tell us about yourself']"));
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait6.until(ExpectedConditions.visibilityOf(element6));
		return element6.getText();
		
		
	}
	
	public void loginPageHeading() {
		
		WebElement element = driver.findElement(By.xpath("//div/div/h1"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
		element.isDisplayed();
	}
	
	public void loginToApplication() {
		WebElement emailAddress = driver.findElement(By.xpath("//input[@placeholder='Email Address or Username']"));
		emailAddress.sendKeys("testmail@gmail.com");
		WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		password.sendKeys("jrdht567,kgk#");
		WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", submit);
	}
	
	public void clickOnThreeDots() {
		WebElement element2 = driver.findElement(By.xpath("//li[7]/a/i"));
		JavascriptExecutor executor2 = (JavascriptExecutor) driver;
		executor2.executeScript("arguments[0].click();", element2);
	}
	
	public void clickOnAccountSettings() {

		WebElement accountSettings = driver.findElement(By.xpath("//ul/li/a[contains(text(),'Account Settings')]"));
		JavascriptExecutor executor3 = (JavascriptExecutor) driver;
		executor3.executeScript("arguments[0].click();", accountSettings);
	}
	
	public void myAccountHeading() {
		WebElement element3 = driver.findElement(By.xpath("//h1[contains(text(),'My Account')]"));
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait2.until(ExpectedConditions.visibilityOf(element3));
		element3.isDisplayed();
	}
	
	public void clickOnDeleteMyAccount() {
		
		WebElement deleteAccount = driver.findElement(By.xpath("//a[contains(text(),'Delete My Account')]"));
		JavascriptExecutor executor4 = (JavascriptExecutor) driver;
		executor4.executeScript("arguments[0].click();", deleteAccount);
		
	}
	
	public String getDangerMessage(){
		WebElement element5 = driver.findElement(By.xpath("//div[contains(@class,'danger')]"));
		WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait4.until(ExpectedConditions.visibilityOf(element5));
		String dangerText = element5.getText();
		return dangerText;
	}
	
	public void clickOnConfirmDelete() {
		WebElement element6 = driver.findElement(By.xpath("//button[@onClick='confirmDelete()']"));
		JavascriptExecutor executor5 = (JavascriptExecutor) driver;
		executor5.executeScript("arguments[0].click();", element6);
	}
	
	public void clickOnYesDelete() {
		
		WebElement element7 = driver.findElement(By.xpath("//div/button[contains(text(),'Yes')]"));
		JavascriptExecutor executor6 = (JavascriptExecutor) driver;
		executor6.executeScript("arguments[0].click();", element7);
		
	}
	
	public void enterJobTitle(String title) {
		WebElement element = driver.findElement(By.xpath("//input[@id='text_search1']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(title);
        
	}
	
	public void selectCountry() {
		WebElement element2 = driver.findElement(By.xpath("//input[@id='search_country2__r']"));
		JavascriptExecutor executor1 = (JavascriptExecutor) driver;
		executor1.executeScript("arguments[0].click();", element2);

		WebElement element3 = driver.findElement(
				By.xpath("//body/div[contains(@class,'popover')]/descendant::input[@placeholder='Search ...']"));
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait3.until(ExpectedConditions.visibilityOf(element3));
		element3.sendKeys("UAE");

		WebElement element4 = driver.findElement(By.xpath("//li[@class='is-active']/a"));
		JavascriptExecutor executor2 = (JavascriptExecutor) driver;
		executor2.executeScript("arguments[0].click();", element4);
	}
	
	public void clickOnSearchSubmit() {
		WebElement element5 = driver.findElement(By.xpath("//button[@id='search_icon_submit_1']"));
		JavascriptExecutor executor3 = (JavascriptExecutor) driver;
		executor3.executeScript("arguments[0].click();", element5);
	}
	
	public void clickOnEasyApply() {
		WebElement easyApply = driver.findElement(By.xpath("//a[2][@id='applyLink_1']"));
		JavascriptExecutor executor4 = (JavascriptExecutor) driver;
		executor4.executeScript("arguments[0].click();", easyApply);
	}
	
	public String identifyCreateAccountPage() {
		
		WebElement element6 = driver.findElement(By.xpath("//h5"));
		WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait6.until(ExpectedConditions.visibilityOf(element6));
		String createPage = element6.getText();
		return createPage;
	}

	
	

}
