package Pages;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v104.console.Console;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import AbstractComponents.PageAbstracts;
import Testcases.RulesManagement;

public class RulesManagementPage extends PageAbstracts{
	
	WebDriver driver;

	public RulesManagementPage(WebDriver driver) {
		super(driver);
		this.driver = driver;

	}

	//locator for Rules Management icon
	public By rulesManagementIcon = new By.ByXPath("//*[text()='Rules Management']");
	
	//locator of create business rule button
	By createBusinessRuleButton = new By.ByXPath("//span[text() = 'Create Business Rule']");
	
	//locator of Rules Management heading
	public By rulesManagementHeading = new By.ByXPath("//div[contains(text(),'Rules Management')]");
	
	//locator for search 
	public By searchBoxLocator = new By.ByXPath("//div/input[@type='search']");
	
	//locator of Create Business Rule Heading
		By createBusinessRuleHeading = new By.ByXPath("//h4[contains(text(),'Create Business Rule')]");
		
	//locator for Enter Rule Group Name
		By ruleGroupNameLocator = new By.ByXPath("//input[@placeholder='Enter Rule Group Name']");
		
	//locator for Rule Type
		By ruleTypeLocator = new By.ByXPath("//button/span[contains(text(),' Select Rule Type')]");
		
	//locator for Location
		By locationLocator = new By.ByXPath("//span[contains(text(),'Select Location')]");
		
	//locator for Line of Business
		By lobLocator = new By.ByXPath("//span[contains(text(),'Select Line of Business')]");
		
	//locator for sub LoB
		By subLoBLocator = new By.ByXPath("//span[text()=' Select Sub Line of Business']");
		
	//locator for All Products
		By allProductsLocator = new By.ByXPath("//span[text()=' All Products']");
		
	//locator for allTransactions 
		By allTransactionsLocator = new By.ByXPath("//span[text()='All Transactions']");
		
	//locator for start effective date
		By selectEffectiveStartDateLocator = new By.ByXPath("//app-date-picker[@placeholder='Select Effective Start Date']/descendant::span[contains(@class,'uil-dl-icon-Calendar')]");
		
		
	//locator for start effective date panel
		By startEffectiveDatePanelLocator = new By.ByXPath("//mat-datepicker-content[contains(@class,'mat-datepicker-content')]");
		
	//locator for Time zone
	//	By timeZoneLocator = new By.ByXPath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']");
		By timeZoneLocator = new By.ByXPath("//button/span[contains(text(),'Kolkata')]/following-sibling::span");
	//locator for Rule Nameo
		By ruleNameLocator = new By.ByXPath("//input[@placeholder='Enter Rule name']");
		
	//locator of Save button
		By saveButtonLocator = new By.ByXPath("//button[contains(text(),'Save')]");
		
	//disappearing notification Text message after save the rule
		By savedNotificationLocator = new By.ByXPath("//*[contains(text(),'Business rule created sucessfully!')]");
		
		//toaster message after bulk upload of rules
				By savedNotificationLocatorForBulk = new By.ByXPath("//*[contains(text(),'Business rules created sucessfully!')]");
		
		//disappearing notification text message for edit rule
		By editNotificationLocator = new By.ByXPath("//*[contains(text(),'Business rule edited sucessfully!')]");
		
		//disappearing notification text message after delete rule
		By deleteNotificationLocator = new By.ByXPath("//*[contains(text(),'Rule deleted successfully')]");
		
		//toaster message locator for status update
		By  statusUpdateLocator = new By.ByXPath("//*[contains(text(),'Business rule status got updated sucessfully!')]");
		
		//locator for mat cell rule name text
		By matcellRuleNameLocator = new By.ByXPath("//mat-cell[contains(text(),'')]");
		
		//locator for actions icon
		By actionIcon = new By.ByXPath("//mat-table[1]/mat-row[1]/mat-cell[10]/button[contains(@class,'ope_actions_button')]/span[1]");
		
		//edit rule button
		By editRuleButtonLocator = new By.ByXPath("//button/span[contains(text(),'Edit Rule')]");
		
		//delete rule button locator
		By deleteRuleButtonLocator = new By.ByXPath("//button/span[contains(text(),'Delete Rule')]");	
		
		//locator for bulk upload button
		By bulkUploadButtonLocator = new By.ByXPath("//button/span[contains(text(),'Bulk Upload Rules')]");
		
		By accentureIcon = new By.ByXPath("//div[@class='mat-list-item-content']");
	
	
	public void mouseOverRulesManagementAndClick() {

	//	driver.findElement(rulesManagementIcon).click();
		WebElement element = driver.findElement(rulesManagementIcon);
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));

			JavascriptExecutor execute= (JavascriptExecutor)driver;
			execute.executeScript("arguments[0].click();", element);
		
	}
	
	 public void mouseOverAccentureIcon() {
  	   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  		wait.until(ExpectedConditions.elementToBeClickable(accentureIcon));
  	   PageFactory.initElements(driver, this);
  	   Actions a = new Actions(driver);	
  	   a.moveToElement(driver.findElement(accentureIcon)).build().perform();
		
	}
	
	public void clickOnCreateBusinessRuleButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Thread.sleep(10000);
		wait.until(ExpectedConditions.elementToBeClickable(createBusinessRuleButton));
		driver.findElement(createBusinessRuleButton).click();
		
	}
	
	public String getRulesManagementText() throws IOException {
		WebElement rulesHeading = driver.findElement(rulesManagementHeading);
		File file = rulesHeading.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("rulesH.png"));
		
		
		String rulesMText = rulesHeading.getText();
		return rulesMText;
		
	}
	
	public String getCreateBusinessRuleText() {
		
		String createBText = driver.findElement(createBusinessRuleHeading).getText();
		return createBText;
		
	}
	
	public void createBusinessRuleReferral(String ruleGroupName, String ruleTypeText, String locationType, String lobText, String subLoBText, String allProductsText, String allTransactionsText, String monthYearDateText, String timeZoneText, CharSequence ruleNameText, String columnNameText, String operationNameText, CharSequence valueText, String referralCodeText, CharSequence rationalAreaText) throws InterruptedException {
		
		// to type rule name
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(ruleGroupNameLocator));
		driver.findElement(ruleGroupNameLocator).sendKeys(ruleGroupName);
		
		// to select rule type
		driver.findElement(ruleTypeLocator).click();
		driver.findElement(By.xpath("//span[contains(text(),'"+ruleTypeText+"')]")).click();
		
		// to select location
		driver.findElement(locationLocator).click();
		driver.findElement(By.xpath("//span[contains(text(),'"+locationType+"')]")).click();
		
		//to select line of business
		driver.findElement(lobLocator).click();
		driver.findElement(By.xpath("//span[contains(text(),'"+lobText+"')]")).click();
		
		//to select sub lob
		driver.findElement(subLoBLocator).click();
		driver.findElement(By.xpath("//span[contains(text(),'"+subLoBText+"')]")).click();
		
		//to select product
		driver.findElement(allProductsLocator).click();
		driver.findElement(By.xpath("//span[contains(text(),'"+allProductsText+"')]")).click();
		
		//to select transactions
		//WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
		//wait2.until(ExpectedConditions.presenceOfElementLocated(allTransactionsLocator));
		driver.findElement(By.xpath("//span[contains(text(),'All Transactions')]")).click();
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'"+allTransactionsText+"')]")));
		driver.findElement(By.xpath("//span[contains(text(),'"+allTransactionsText+"')]")).click();
		
		
		
		//to input date
	//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).sendKeys(monthYearDateText);
	//	clickOnToday();
		
		datePicker(monthYearDateText);
		
		/*
		 * WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(5)); //
		 * wait3.until(ExpectedConditions.(By.
		 * cssSelector("body:nth-child(2) div.cdk-overlay-container:nth-child(9) > div.cdk-overlay-backdrop.mat-overlay-transparent-backdrop.mat-datepicker-0-backdrop.cdk-overlay-backdrop-showing"
		 * ))); wait3.until(ExpectedConditions.elementToBeClickable(timeZoneLocator));
		 * ((JavascriptExecutor)driver).executeScript("arguments[0].click();",
		 * driver.findElement(timeZoneLocator));
		 */
		
		Thread.sleep(1000);
		
		driver.findElement(timeZoneLocator).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys(timeZoneText);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(text(),'"+timeZoneText+"')]")).click();
		
	
			// to enter rule name
			driver.findElement(ruleNameLocator).sendKeys(ruleNameText);
			
			//to select column name
			driver.findElement(By.xpath("//span[contains(text(),'Select Column Name')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::input[@type='search']")).sendKeys(columnNameText);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'"+columnNameText+"')]")).click();
			
			//to select operation
			driver.findElement(By.xpath("//span[contains(text(),'Select Operation')]")).click();
			driver.findElement(By.xpath("//span[text()='"+operationNameText+"']")).click();
			
			//to enter value
			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).sendKeys(valueText);
			
			//to select referal code
			driver.findElement(By.xpath("//span[contains(text(),' Select Referral Code')]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//app-single-dropdown/descendant::span[contains(text(),'"+referralCodeText+"')]")).click();
			
			//to enter text area
			driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).sendKeys(rationalAreaText);
			
			
			driver.findElement(saveButtonLocator).click();
		
		
		
		
	}
	
	
	
public void createBusinessRuleKnowledgeLink(String ruleGroupName, String ruleTypeText, String locationType, String lobText, String subLoBText, String allProductsText, String allTransactionsText, String monthYearDateText, String timeZoneText, CharSequence ruleNameText, String columnNameText, String operationNameText, String valueText, String linkLabelText, String knowledgeLinkText, CharSequence selectOneColumnname, CharSequence rationalAreaText) throws InterruptedException {
		
			// to type rule name
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.presenceOfElementLocated(ruleGroupNameLocator));
			driver.findElement(ruleGroupNameLocator).sendKeys(ruleGroupName);
			
			// to select rule type
			driver.findElement(ruleTypeLocator).click();
			driver.findElement(By.xpath("//span[contains(text(),'"+ruleTypeText+"')]")).click();
			
			// to select location
			driver.findElement(locationLocator).click();
			driver.findElement(By.xpath("//span[contains(text(),'"+locationType+"')]")).click();
			
			//to select line of business
			driver.findElement(lobLocator).click();
			driver.findElement(By.xpath("//span[contains(text(),'"+lobText+"')]")).click();
			
			//to select sub lob
			driver.findElement(subLoBLocator).click();
			driver.findElement(By.xpath("//span[contains(text(),'"+subLoBText+"')]")).click();
			
			//to select product
			driver.findElement(allProductsLocator).click();
			driver.findElement(By.xpath("//span[contains(text(),'"+allProductsText+"')]")).click();
			
			//to select transactions
			//WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
			//wait2.until(ExpectedConditions.presenceOfElementLocated(allTransactionsLocator));
			driver.findElement(By.xpath("//span[contains(text(),'All Transactions')]")).click();
			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'"+allTransactionsText+"')]")));
			driver.findElement(By.xpath("//span[contains(text(),'"+allTransactionsText+"')]")).click();
			
			
			//to input date
		//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).sendKeys(monthYearDateText);\
			datePicker(monthYearDateText);
			
			Thread.sleep(1000);
			
			//to select timezone
			driver.findElement(timeZoneLocator).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//button/following-sibling::ul/li/div/div/div[2]/input[@type='search']")).sendKeys(timeZoneText);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[contains(text(),'"+timeZoneText+"')]")).click();
			
		
				// to enter rule name
				driver.findElement(ruleNameLocator).sendKeys(ruleNameText);
				
				//to select column name
				driver.findElement(By.xpath("//span[contains(text(),'Select Column Name')]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::input[@type='search']")).sendKeys(columnNameText);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//span[contains(text(),'"+columnNameText+"')]")).click();
				
				//to select operation
				driver.findElement(By.xpath("//span[contains(text(),'Select Operation')]")).click();
				driver.findElement(By.xpath("//span[text()='"+operationNameText+"']")).click();
				
				//to enter value
				driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).sendKeys(valueText);
				
				//enter link label text
				driver.findElement(By.xpath("//input[@placeholder='Enter Link Label']")).sendKeys(linkLabelText);
				
			//enter knowledge link
			driver.findElement(By.xpath("//input[@placeholder='Enter Knowledge Link']")).sendKeys(knowledgeLinkText);
			
			
			
			//click on Add Param
			driver.findElement(By.xpath("//span[text()='Add Parameter']")).click();
			
			//select one dropdown
			driver.findElement(By.xpath("//button/span[contains(text(),'Select One')]/following-sibling::span[@class='caret']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//app-knowledge-link/descendant::input[@type='search']")).sendKeys(selectOneColumnname);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//app-knowledge-link/descendant::span[contains(text(),'"+selectOneColumnname+"')]")).click();
				
			driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).sendKeys(rationalAreaText);
			Thread.sleep(1000);
			
			driver.findElement(saveButtonLocator).click();
	
	}

public void editBusinessRuleRiskAppetite(String monthYearDateText, String timeZoneText, CharSequence ruleNameText, String columnNameText, String operationNameText, CharSequence valueText, String riskAppetiteText, CharSequence rationalAreaText ) throws InterruptedException {
	
	
	
	//to input date
	//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).clear();
	//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).sendKeys(monthYearDateText);
	datePicker(monthYearDateText);
	
	
	//to select time-zone
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']")));
		driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys(timeZoneText);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(text(),'"+timeZoneText+"')]")).click();
		
	
			// to enter rule
		    driver.findElement(ruleNameLocator).clear();
			driver.findElement(ruleNameLocator).sendKeys(ruleNameText);
			
			//to select column name
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::span[@class='caret']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::input[@type='search']")).sendKeys(columnNameText);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'"+columnNameText+"')]")).click();
			
			//to select operation
			
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Operation']/descendant::span[@class='caret']")).click();
			driver.findElement(By.xpath("//span[text()='"+operationNameText+"']")).click();
			
			Thread.sleep(1000);
			//to enter value
			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).clear();
			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).sendKeys(valueText);
			
			//to select Risk Appetite
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Appetite']/descendant::span[@class='caret']")).click();
			driver.findElement(By.xpath("//span[contains(text(),'"+riskAppetiteText+"')]")).click();
	
			// to clear and enter rationale
			driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).clear();
			driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).sendKeys(rationalAreaText);
	
			driver.findElement(saveButtonLocator).click();

}

public void createBusinessRuleRiskAppetite(String ruleGroupName, String ruleTypeText, String locationType, String lobText, String subLoBText, String allProductsText, String allTransactionsText, String monthYearDateText, String timeZoneText, CharSequence ruleNameText, String columnNameText, String operationNameText, String valueText, String appetiteText, CharSequence rationalTextArea ) throws InterruptedException {
	
	// to type rule name
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
				wait.until(ExpectedConditions.presenceOfElementLocated(ruleGroupNameLocator));
				driver.findElement(ruleGroupNameLocator).sendKeys(ruleGroupName);
				
				// to select rule type
				driver.findElement(ruleTypeLocator).click();
				driver.findElement(By.xpath("//span[contains(text(),'"+ruleTypeText+"')]")).click();
				
				// to select location
				driver.findElement(locationLocator).click();
				driver.findElement(By.xpath("//span[contains(text(),'"+locationType+"')]")).click();
				
				//to select line of business
				driver.findElement(lobLocator).click();
				driver.findElement(By.xpath("//span[contains(text(),'"+lobText+"')]")).click();
				
				//to select sub lob
				driver.findElement(subLoBLocator).click();
				driver.findElement(By.xpath("//span[contains(text(),'"+subLoBText+"')]")).click();
				
				//to select product
				driver.findElement(allProductsLocator).click();
				driver.findElement(By.xpath("//span[contains(text(),'"+allProductsText+"')]")).click();
				
				//to select transactions
				//WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
				//wait2.until(ExpectedConditions.presenceOfElementLocated(allTransactionsLocator));
				driver.findElement(By.xpath("//span[contains(text(),'All Transactions')]")).click();
				WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(20));
				wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'"+allTransactionsText+"')]")));
				driver.findElement(By.xpath("//span[contains(text(),'"+allTransactionsText+"')]")).click();
				
				
				//to input date
				//driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).sendKeys(monthYearDateText);
				datePicker(monthYearDateText);
				
				//to select timezone
				driver.findElement(timeZoneLocator).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@type='search']")).sendKeys(timeZoneText);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'"+timeZoneText+"')]")).click();
				
			
					// to enter rule name
					driver.findElement(ruleNameLocator).sendKeys(ruleNameText);
					
					//to select column name
					driver.findElement(By.xpath("//span[contains(text(),'Select Column Name')]")).click();
					Thread.sleep(2000);
					driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::input[@type='search']")).sendKeys(columnNameText);
					Thread.sleep(2000);
					driver.findElement(By.xpath("//span[contains(text(),'"+columnNameText+"')]")).click();
					
					//to select operation
					driver.findElement(By.xpath("//span[contains(text(),'Select Operation')]")).click();
					driver.findElement(By.xpath("//span[text()='"+operationNameText+"']")).click();
					
					//to enter value
					driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).sendKeys(valueText);
					
	
	    // to select Appetite
		driver.findElement(By.xpath("//span[contains(text(),'Select Appetite')]")).click();
		driver.findElement(By.xpath("//span[contains(text(), '"+appetiteText+"')]")).click();
		
		// to enter rationale 
		driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).sendKeys(rationalTextArea);
			
		driver.findElement(saveButtonLocator).click();
	
}

public void editBusinessRuleKnowledgeLink(String monthYearDateText, String timeZoneText, CharSequence ruleNameText, String columnNameText, String operationNameText, CharSequence valueText,String linkLabelText, CharSequence knowledgeLinkText, CharSequence selectOneColumnname1, CharSequence selectOneColumnname2, CharSequence rationalAreaText ) throws InterruptedException {
	
	
	
	//to input date
	//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).clear();
	//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).sendKeys(monthYearDateText);
		datePicker(monthYearDateText);
	
	
	//to select time-zone
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']")));
		driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys(timeZoneText);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[contains(text(),'"+timeZoneText+"')]")).click();
		
	
			// to enter rule
		    driver.findElement(ruleNameLocator).clear();
			driver.findElement(ruleNameLocator).sendKeys(ruleNameText);
			
			//to select column name
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::span[@class='caret']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::input[@type='search']")).sendKeys(columnNameText);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'"+columnNameText+"')]")).click();
			
			//to select operation
			
			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Operation']/descendant::span[@class='caret']")).click();
			driver.findElement(By.xpath("//span[text()='"+operationNameText+"']")).click();
			
			//to enter value
			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).clear();
			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).sendKeys(valueText);
			
			//enter link label text
			driver.findElement(By.xpath("//input[@placeholder='Enter Link Label']")).clear();
			driver.findElement(By.xpath("//input[@placeholder='Enter Link Label']")).sendKeys(linkLabelText);
			
	//enter knowledge link
			driver.findElement(By.xpath("//input[@placeholder='Enter Knowledge Link']")).clear();	
	        driver.findElement(By.xpath("//input[@placeholder='Enter Knowledge Link']")).sendKeys(knowledgeLinkText);
	        
	      //select one dropdown
	    	driver.findElement(By.xpath("//app-knowledge-link/descendant::span[@class='caret']")).click();
	    	Thread.sleep(2000);
	    	driver.findElement(By.xpath("//app-knowledge-link/descendant::input[@type='search']")).sendKeys(selectOneColumnname1);
	    	Thread.sleep(2000);
	    	driver.findElement(By.xpath("//app-knowledge-link/descendant::span[contains(text(),'"+selectOneColumnname1+"')]")).click();
	    		    
	
	//click on Add Param
	driver.findElement(By.xpath("//span[text()='Add Parameter']")).click();
	
	//select one dropdown
	driver.findElement(By.xpath("//button/span[contains(text(),'Select One')]/following-sibling::span[@class='caret']")).click();
//	Thread.sleep(1000);
	
	WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
	////app-knowledge-link/descendant::input[@type='search']
	wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//app-knowledge-link/descendant::input[@type='search'][2]"))).sendKeys(selectOneColumnname2);
	Thread.sleep(2000);
	driver.findElement(By.xpath("//app-knowledge-link/descendant::span[contains(text(),'"+selectOneColumnname2+"')]")).click();
	
	driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).clear();
	driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).sendKeys(rationalAreaText);
	
	driver.findElement(saveButtonLocator).click();
////tbody/tr[1]/td[1]/div[1]/div[3]/div[1]/app-knowledge-link[1]/div[3]/div[3]/app-single-dropdown[1]/div[1]/div[1]/div[1]/ul[1]/li[1]/div[1]/div[1]/div[2]/input[1]
}	
	
	
	public void datePicker(String monthDateYear) throws InterruptedException {
		
		//to open the calendar
				driver.findElement(selectEffectiveStartDateLocator).click();
			//	new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(startEffectiveDatePanelLocator));
		
		        String[] date_mm_dd_yyyy = monthDateYear.split("-");
		       String[] year = date_mm_dd_yyyy[2].split("'");
		      //  String[] month = date_mm_dd_yyyy[0].split("'");
		        
		        //to select calendar
		        if(driver.findElement(By.xpath("//button[@aria-label='Choose month and year']")).getText().contains(year[0])==true){
		     	
		        	driver.findElement(By.xpath("//button[@aria-label='Choose month and year']")).click();
		        	List<WebElement> yearsText = driver.findElements(By.xpath("//mat-multi-year-view/table/tbody/tr/td/div[1]"));
		        	int count = yearsText.size();
		        	for(int i=0; i<count;i++) {
		        		String yearText = yearsText.get(i).getText();
		        		if(yearText.equalsIgnoreCase(year[0])) {
		        			driver.findElements(By.xpath("//mat-multi-year-view/table/tbody/tr/td/div[1]")).get(i).click();
		        			break;
		        		}
		        		
		        	}
		        }else {
		        	
		        	driver.findElement(By.xpath("//button[@aria-label='Choose month and year']")).click();
		        	
		        	while(!driver.findElement(By.xpath("//mat-multi-year-view/table/tbody")).getText().contains(year[0])) {
						
		        		driver.findElement(By.xpath("//button[@aria-label='Next 24 years']")).click();
						
		        	}
		        	
		        	List<WebElement> yearsText = driver.findElements(By.xpath("//mat-multi-year-view/table/tbody/tr/td/div[1]"));
		        	int count = yearsText.size();
		        	for(int i=0; i<count;i++) {
		        		String yearText = yearsText.get(i).getText();
		        		if(yearText.equalsIgnoreCase(year[0])) {
		        			yearsText.get(i).click();
		        			break;
		        		}
		        		
		        	}
   	
		        }
		        
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'"+date_mm_dd_yyyy[0]+"')]")));
		    ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(text(),'"+date_mm_dd_yyyy[0]+"')]")));
		        
		        //to select month
		       List<WebElement> monthsText = driver.findElements(By.xpath("//mat-year-view/table/tbody/tr/td"));
		       int count = monthsText.size();
		       for(int i=0; i<count; i++) {
		    	   String monthText = monthsText.get(i).getText();
		    	   if(monthText.equalsIgnoreCase(date_mm_dd_yyyy[0])) {	   
		    		   monthsText.get(i).click();
		    		   break;
		    	   }
		       }
		    	   
		    	   driver.findElement(By.xpath("//div[contains(text(),'"+date_mm_dd_yyyy[1]+"')]")).click();
					
				
		       
		       
		        
	}
	
		     public String savedNotificationText() {
		    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10) );
		    	 wait.until(ExpectedConditions.visibilityOfElementLocated(savedNotificationLocator));
		    	 
		    	 String notification = driver.findElement(savedNotificationLocator).getText();
		    	 return notification;
		    	 
		    	 
		     }
		     
		     public String editedNotificationText() {
		    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10) );
		    	 wait.until(ExpectedConditions.visibilityOfElementLocated(editNotificationLocator));
		    	 
		    	 String notification = driver.findElement(editNotificationLocator).getText();
		    	 return notification;
		    	 
		    	 
		     }
		     
		     
		     public void typeInSearch(String ruleNameText2) {
		
		    	WebElement element =  driver.findElement(searchBoxLocator);
		    	 
		    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		 		wait.until(ExpectedConditions.visibilityOf(element));
		 		
		 		JavascriptExecutor js = (JavascriptExecutor)driver;
				  js.executeScript("arguments[0].value='"+ruleNameText2+"';", element);
		    	
		    	 
		     }
		        
		      public String getMatCellRuleName() {
		    	  
		    	  String ruleNameCellText = driver.findElement(By.xpath("//mat-cell[@role='cell'][1]")).getText();
		    	  return ruleNameCellText;	    	  
		    	  
		      }
		      
		      public void clickOnActionsIcon() {
		    	  
		    	  driver.findElement(actionIcon).click();
		    	  
		      }
		      
		      public void clickOnEditRuleButton() {
		    	  driver.findElement(editRuleButtonLocator).click();
		      }
		      
		      public void editBusinessRuleReferral(String monthYearDateText, String timeZoneText, CharSequence ruleNameText, String columnNameText, String operationNameText, CharSequence valueText, String referralCodeText, CharSequence rationalAreaText) throws InterruptedException {
		  		
		    	 
		  			//   driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).clear();
									/*
									 * //to input date Actions action = new Actions(driver);
									 * action.moveToElement(inputDatebox).doubleClick().sendKeys(Keys.chord(Keys.
									 * CONTROL, "a", Keys.DELETE)).build().perform();
									 */
		  			   
		  	//	driver.findElement(By.xpath("//input[@data-placeholder='Select Effective Start Date']")).sendKeys(monthYearDateText);
		    	  datePicker(monthYearDateText);
		  		
		  	//to select time-zone
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']")));
				driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Timezone']/descendant::span[@class='caret']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//input[@type='search']")).sendKeys(timeZoneText);
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[contains(text(),'"+timeZoneText+"')]")).click();
		  		
		  	
		  			// to enter rule
		  		    driver.findElement(ruleNameLocator).clear();
		  			driver.findElement(ruleNameLocator).sendKeys(ruleNameText);
		  			
		  			//to select column name
		  			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::span[@class='caret']")).click();
		  			Thread.sleep(2000);
		  			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Column Name']/descendant::input[@type='search']")).sendKeys(columnNameText);
		  			Thread.sleep(2000);
		  			driver.findElement(By.xpath("//span[contains(text(),'"+columnNameText+"')]")).click();
		  			
		  			//to select operation
		  			
		  			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Operation']/descendant::span[@class='caret']")).click();
		  			driver.findElement(By.xpath("//span[text()='"+operationNameText+"']")).click();
		  			
		  			//to enter value
		  			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).clear();
		  			driver.findElement(By.xpath("//input[@placeholder='Enter Value']")).sendKeys(valueText);
		  			
		  			//to select referal code
		  			driver.findElement(By.xpath("//app-single-dropdown[@placeholder='Select Referral Code']/descendant::span[@class='caret']")).click();
		  			Thread.sleep(1000);
		  			driver.findElement(By.xpath("//app-single-dropdown/descendant::span[contains(text(),'"+referralCodeText+"')]")).click();
		  			
		  			//to enter text area
		  			driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).clear();
		  			driver.findElement(By.xpath("//textarea[@id='rationaleTextArea']")).sendKeys(rationalAreaText);
		  			
		  			
		  			driver.findElement(saveButtonLocator).click();
		  		
		  		
		  		
		  		
		  	}
		      
		      public void deleteBusinessRule() {
		    	  
		    	  driver.findElement(deleteRuleButtonLocator).click();
		    	  
		    	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)) ;
		    	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h1[contains(text(),'Delete Rules')]")));
		    	  
		    	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/p[contains(text(),'delete the selected rule(s)')]")));
		    	  
		    	  WebElement yesDelete = driver.findElement(By.xpath("//button[contains(text(),'Yes, delete')]"));
		    	  yesDelete.click();		    	  
		    		  
		    	  }
		      
		      public String deletedNotificationText() {
		    	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		    	  wait.until(ExpectedConditions.visibilityOfElementLocated(deleteNotificationLocator));
		    	  String notifyText = driver.findElement(deleteNotificationLocator).getText();
		    	  return notifyText;
		    	  
		      }
		      
		      public String statusUpdateNotificationText() {
		    	  String notifyText = driver.findElement(statusUpdateLocator).getText();
		    	  return notifyText;
		      }
		      
		      public String fetchStatus() {
		    	  String statusText = driver.findElement(By.xpath("//mat-table[1]/mat-row[1]/mat-cell[9]")).getText();
		    	  return statusText;
		  			
		      }
		      
		      public void deactivateRule() {
		    	  driver.findElement(By.xpath("//button/span[contains(text(),'Deactivate Rule')]")).click();
		    	  driver.findElement(By.xpath("//div/button[contains(text(),'Ok')]")).click();
		    	  
		      }
		      
		      public void clickOnBulkUpload()  {
		    	  driver.findElement(bulkUploadButtonLocator).click();
		    	  
		      }
		      
		      public void browseTheBulkUploadFile(String filePath) throws InterruptedException {
		    	  
		    	  //click on browse button
		    	  WebElement browseFile = driver.findElement(By.xpath("//input[@type='file']"));
		    //	  File file = new File(".\testFiles//Rules_Template.xlsm")
		    	  browseFile.sendKeys(filePath);
		    	  
		    	// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		    	 
		    	 
		    	  
		      }
		      
		      public String toasterMessageForBulkUpload() {
		    	  
		    	  String bulkUploadMessage = driver.findElement(savedNotificationLocatorForBulk).getText();
		    	  
		    	  return bulkUploadMessage;
		    	  
		      }
		      
		    public String invalidDateBulkUploadMessage() {
		    	
		          String invalidDate = driver.findElement(By.xpath("//div[contains(text(),'One or more rules have an invalid URL. Please verify and try again.')]")).getText();
		    	
		          return invalidDate;
		    }
		    
		    public String missingMandatoryFieldMessage() {
		    	String mandatory = driver.findElement(By.xpath("//div[contains(text(),' Mandatory fields are missing for one or more rules. Please verify and try again. ')]")).getText();
		    	
		    	return mandatory;
		    }
		    
		    public String invalidFileFormat() {
		    	String invalidFile = driver.findElement(By.xpath("//div[contains(text(),' Incorrect file format uploaded. Please verify and try again ')]")).getText();
		    	
		    	return invalidFile;
		    }
		    
		    public List<String> invalidData() {
		    	List<WebElement> elements = driver.findElements(By.xpath("//table/tbody/tr/td[1]"));
		    //	JavascriptExecutor executor = (JavascriptExecutor)driver;
		   // 	executor.executeScript("arguments[0].scrollIntoView(true)",elements );
		    	List<String> elementsText = new ArrayList<String>();
		    	for(int i=0;i<elements.size();i++) {
		    		String text = elements.get(i).getText();
		    		elementsText.add(text);
		    	}
		    	return elementsText;
		    }
		    
		   public void sortRulesByColumnNumber(int column, String typeOfSortText) {
			   
			   //get sort&filter icons for all and put them in a list
				  WebElement sortIcon =  driver.findElement(By.xpath("//app-manage-rules/descendant::em["+column+"]"));
				  sortIcon.click();
			   
			  if(typeOfSortText.contentEquals("Ascending") ){
			  //get sort&filter icons for all and put them in a list
			//  WebElement sortIcon =  driver.findElement(By.xpath("//app-manage-rules/descendant::em["+column+"]"));
			    
			   //apply sorting action on a column
			 // sortIcon.click();
			  WebElement typeOfSort = driver.findElement(By.xpath("//button/span[text() = '"+typeOfSortText+"']"));
			  typeOfSort.click();
			  WebElement applyButton = driver.findElement(By.xpath("//button/span[text()='Apply']"));
			  applyButton.click();	
			  
			  List<WebElement> columnCells =  driver.findElements(By.xpath("//mat-row/mat-cell["+column+"]"));
			   //stream2 them and store in a collector
			  List<String> afterSortCellValues =  columnCells.stream().map(a->a.getText()).collect(Collectors.toList());
			
			   
			   //stream1 them and sort them and store in a collector (before sort action) - Ascending order
			 List<String> ascendingOrderValues =  afterSortCellValues.stream().sorted().collect(Collectors.toList());
			 
			//Asserting sort	  		 
			 Assert.assertTrue(afterSortCellValues.equals(ascendingOrderValues));	  
				  
			  
			  }
			  else {
				  
				  //get sort&filter icons for all and put them in a list
				//  WebElement sortIcon =  driver.findElement(By.xpath("//app-manage-rules/descendant::em["+column+"]"));
				    
				   //apply sorting action on a column
				//  sortIcon.click();
				  WebElement typeOfSort = driver.findElement(By.xpath("//button/span[text() = '"+typeOfSortText+"']"));
				  typeOfSort.click();
				  WebElement applyButton = driver.findElement(By.xpath("//button/span[text()='Apply']"));
				  applyButton.click();	
				  
				  List<WebElement> columnCells =  driver.findElements(By.xpath("//mat-row/mat-cell["+column+"]"));
				   //stream2 them and store in a collector
				  List<String> afterSortCellValues =  columnCells.stream().map(b->b.getText()).collect(Collectors.toList());
				  			 
				 List<String> descendingOrderValues = afterSortCellValues.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
				
					  
					  Assert.assertTrue(afterSortCellValues.equals(descendingOrderValues))	;			   
			   
		   }
		   }
		    
		      
		   }


		    	  
		    	  
		    	  
		    	  
		      
		      
		      
		      
		    	  
		      
		  	
		        
				
						
						
	
	
	
	
	
	
	


