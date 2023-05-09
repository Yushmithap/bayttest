package Testcases;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

import APIresources.APIUtils;
import Pages.HomePage;
import Pages.LoginPage;
import Pages.RulesManagementPage;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testData.RulesManagementData;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v104.network.Network;
import org.openqa.selenium.devtools.v105.cachestorage.CacheStorage;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class RulesManagement extends APIUtils{

	//RulesManagementData data = new RulesManagementData();
	public WebDriver driver;
	


	@BeforeClass
	public void testSetUp() throws InterruptedException, Throwable {
		
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
	   	  
	  // 	  driver.navigate().refresh();
	   	  
	 //  	  Thread.sleep(10000);

		
	}



	@Test(description = "Validate user able to create business rule for Referral", enabled = true, priority = 1)
	public void tc1_creationOfBusinessRuleForReferral() throws InterruptedException, IOException {
		

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);

		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);

		Assert.assertEquals(rulesManagement.getRulesManagementText(), "Rules Management");
		Thread.sleep(10000);
		rulesManagement.clickOnCreateBusinessRuleButton();
		Thread.sleep(3000);
		Assert.assertEquals(rulesManagement.getCreateBusinessRuleText(), "Create Business Rule");
		RulesManagementData data = new RulesManagementData();

		ArrayList<String> ruleValues = data.rulesData("Rules_Referral_ADD");

		rulesManagement.createBusinessRuleReferral(ruleValues.get(0), ruleValues.get(1), ruleValues.get(2),
				ruleValues.get(3), ruleValues.get(4), ruleValues.get(5), ruleValues.get(6), ruleValues.get(7),
				ruleValues.get(8), ruleValues.get(9), ruleValues.get(10), ruleValues.get(11), ruleValues.get(12),
				ruleValues.get(13), ruleValues.get(14));
		Thread.sleep(3000);
		Assert.assertEquals(rulesManagement.savedNotificationText(), "Business rule created sucessfully!");

	}

	@Test(description = "Validate user able to edit business rule for Referral", enabled = true, priority = 2)
	public void tc2_editOfBusinessRuleForReferral() throws InterruptedException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);

		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);


		RulesManagementData rulesData1 = new RulesManagementData();
		ArrayList<String> ruleValuesAdd = rulesData1.rulesData("Rules_Referral_ADD");

		Thread.sleep(3000);
		rulesManagement.typeInSearch(ruleValuesAdd.get(9));
		Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesAdd.get(9));
		rulesManagement.clickOnActionsIcon();
		rulesManagement.clickOnEditRuleButton();
		Thread.sleep(2000);

		RulesManagementData rulesData2 = new RulesManagementData();
		ArrayList<String> ruleValues = rulesData2.rulesData("Rules_Referral_EDIT");

		rulesManagement.editBusinessRuleReferral(ruleValues.get(0), ruleValues.get(1), ruleValues.get(2),
				ruleValues.get(3), ruleValues.get(4), ruleValues.get(5), ruleValues.get(6), ruleValues.get(7));

		Assert.assertEquals(rulesManagement.editedNotificationText(), "Business rule edited sucessfully!");

	}

	@Test(description = "Validate user able to delete business rule for Referral", enabled = true, priority = 3)
	public void tc3_deleteOfBusinessRuleForReferral() throws InterruptedException, FileNotFoundException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);
		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);


		RulesManagementData rulesData1 = new RulesManagementData();
		ArrayList<String> ruleValuesEdit = rulesData1.rulesData("Rules_Referral_EDIT");

		Thread.sleep(3000);
		rulesManagement.typeInSearch(ruleValuesEdit.get(2));
		Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesEdit.get(2));

		String status = rulesManagement.fetchStatus();

		System.out.println(status);

		// String[] notActiveStatus = {"Inactive", "Created"};

		if (status.contentEquals("Active")) {
			rulesManagement.clickOnActionsIcon();
			Thread.sleep(1000);
			rulesManagement.deactivateRule();
			Thread.sleep(2000);
			Assert.assertEquals(rulesManagement.statusUpdateNotificationText(),
					"Business rule status got updated sucessfully!");
			Thread.sleep(3000);
			Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesEdit.get(2));
			Thread.sleep(1000);
			rulesManagement.clickOnActionsIcon();
			rulesManagement.deleteBusinessRule();
			Assert.assertEquals(rulesManagement.deletedNotificationText(), "Rule deleted successfully");
		}

		else {
			rulesManagement.clickOnActionsIcon();
			rulesManagement.deleteBusinessRule();
			Assert.assertEquals(rulesManagement.deletedNotificationText(), "Rule deleted successfully");
		}
	}

	@Test(description = "Validate user able to create business rule for Knowledge Link", enabled = true, priority = 4)
	public void tc4_creationOfBusinessRuleForKnowledgelink() throws InterruptedException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);

		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);
;

		rulesManagement.clickOnCreateBusinessRuleButton();
		Thread.sleep(3000);
		Assert.assertEquals(rulesManagement.getCreateBusinessRuleText(), "Create Business Rule");

		// test data
		RulesManagementData data = new RulesManagementData();
		ArrayList<String> ruleValues = data.rulesData("Rules_Know_ADD");

		// get values and create the business rule
		rulesManagement.createBusinessRuleKnowledgeLink(ruleValues.get(0), ruleValues.get(1), ruleValues.get(2),
				ruleValues.get(3), ruleValues.get(4), ruleValues.get(5), ruleValues.get(6), ruleValues.get(7),
				ruleValues.get(8), ruleValues.get(9), ruleValues.get(10), ruleValues.get(11), ruleValues.get(12), ruleValues.get(13),
				ruleValues.get(14), ruleValues.get(15), ruleValues.get(16));
		Thread.sleep(3000);
		Assert.assertEquals(rulesManagement.savedNotificationText(), "Business rule created sucessfully!");

	}

	@Test(description = "Validate user able to edit business rule for Knowledge Link", enabled = true, priority = 5)
	public void tc5_editOfBusinessRuleForKnowledgeLink() throws InterruptedException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);

		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);



		RulesManagementData rulesData1 = new RulesManagementData();
		ArrayList<String> ruleValuesAdd = rulesData1.rulesData("Rules_Know_ADD");

		Thread.sleep(3000);
		rulesManagement.typeInSearch(ruleValuesAdd.get(9));
		Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesAdd.get(9));
		rulesManagement.clickOnActionsIcon();
		rulesManagement.clickOnEditRuleButton();
		Thread.sleep(2000);

		RulesManagementData rulesData2 = new RulesManagementData();
		ArrayList<String> ruleValues = rulesData2.rulesData("Rules_Know_EDIT");

		rulesManagement.editBusinessRuleKnowledgeLink(ruleValues.get(0), ruleValues.get(1), ruleValues.get(2),
				ruleValues.get(3), ruleValues.get(4), ruleValues.get(5), ruleValues.get(6), ruleValues.get(7),
				ruleValues.get(8), ruleValues.get(9), ruleValues.get(10));

		Assert.assertEquals(rulesManagement.editedNotificationText(), "Business rule edited sucessfully!");

	}

	@Test(description = "Validate user able to delete business rule for Knowledge Link", enabled = true, priority = 6)
	public void tc6_deleteOfBusinessRuleForKnowledgeLink()
			throws InterruptedException, FileNotFoundException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);
		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);


		RulesManagementData rulesData1 = new RulesManagementData();
		ArrayList<String> ruleValuesEdit = rulesData1.rulesData("Rules_Know_EDIT");

		Thread.sleep(3000);
		rulesManagement.typeInSearch(ruleValuesEdit.get(2));
		Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesEdit.get(2));

		String status = rulesManagement.fetchStatus();

		System.out.println(status);

		// String[] notActiveStatus = {"Inactive", "Created"};

		if (status.contentEquals("Active")) {
			rulesManagement.clickOnActionsIcon();
			Thread.sleep(1000);
			rulesManagement.deactivateRule();
			Thread.sleep(2000);
			Assert.assertEquals(rulesManagement.statusUpdateNotificationText(),
					"Business rule status got updated sucessfully!");
			Thread.sleep(3000);
			Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesEdit.get(2));
			Thread.sleep(1000);
			rulesManagement.clickOnActionsIcon();
			rulesManagement.deleteBusinessRule();
			Assert.assertEquals(rulesManagement.deletedNotificationText(), "Rule deleted successfully");
		}

		else {
			rulesManagement.clickOnActionsIcon();
			rulesManagement.deleteBusinessRule();
			Assert.assertEquals(rulesManagement.deletedNotificationText(), "Rule deleted successfully");
		}
	}

	@Test(description = "Validate user able to create business rule for Risk Appetite", enabled = true, priority = 7)
	public void tc7_creationOfBusinessRuleForRiskAppetite() throws InterruptedException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);

		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);

		rulesManagement.clickOnCreateBusinessRuleButton();
		Thread.sleep(3000);
		Assert.assertEquals(rulesManagement.getCreateBusinessRuleText(), "Create Business Rule");

		// test data
		RulesManagementData data = new RulesManagementData();
		ArrayList<String> ruleValues = data.rulesData("Rules_Risk_Add");

		// get values and create the business rule
		rulesManagement.createBusinessRuleRiskAppetite(ruleValues.get(0), ruleValues.get(1), ruleValues.get(2),
				ruleValues.get(3), ruleValues.get(4), ruleValues.get(5), ruleValues.get(6), ruleValues.get(7),
				ruleValues.get(8), ruleValues.get(9), ruleValues.get(10), ruleValues.get(11), ruleValues.get(12),
				ruleValues.get(13), ruleValues.get(14));
		Thread.sleep(3000);
		Assert.assertEquals(rulesManagement.savedNotificationText(), "Business rule created sucessfully!");

	}

	@Test(description = "Validate user able to edit business rule for Risk Appetite", enabled = true, priority = 8)
	public void tc8_editOfBusinessRuleForRiskAppetite() throws InterruptedException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);

		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);


		RulesManagementData rulesData1 = new RulesManagementData();
		ArrayList<String> ruleValuesAdd = rulesData1.rulesData("Rules_Risk_Add");

		Thread.sleep(3000);
		rulesManagement.typeInSearch(ruleValuesAdd.get(9));
		Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesAdd.get(9));
		rulesManagement.clickOnActionsIcon();
		rulesManagement.clickOnEditRuleButton();
		Thread.sleep(2000);

		RulesManagementData rulesData2 = new RulesManagementData();
		ArrayList<String> ruleValues = rulesData2.rulesData("Rules_Risk_Edit");

		rulesManagement.editBusinessRuleRiskAppetite(ruleValues.get(0), ruleValues.get(1), ruleValues.get(2),
				ruleValues.get(3), ruleValues.get(4), ruleValues.get(5), ruleValues.get(6), ruleValues.get(7));

		Assert.assertEquals(rulesManagement.editedNotificationText(), "Business rule edited sucessfully!");

	}

	@Test(description = "Validate user able to delete business rule Risk Appetite", enabled = true, priority = 9)
	public void tc9_deleteOfBusinessRuleRiskAppetite() throws InterruptedException, FileNotFoundException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);
		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);


		RulesManagementData rulesData1 = new RulesManagementData();
		ArrayList<String> ruleValuesEdit = rulesData1.rulesData("Rules_Risk_Edit");

		Thread.sleep(3000);
		rulesManagement.typeInSearch(ruleValuesEdit.get(2));
		Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesEdit.get(2));

		String status = rulesManagement.fetchStatus();

		System.out.println(status);

		// String[] notActiveStatus = {"Inactive", "Created"};

		if (status.contentEquals("Active")) {
			rulesManagement.clickOnActionsIcon();
			Thread.sleep(1000);
			rulesManagement.deactivateRule();
			Thread.sleep(2000);
			Assert.assertEquals(rulesManagement.statusUpdateNotificationText(),
					"Business rule status got updated sucessfully!");
			Thread.sleep(3000);
			Assert.assertEquals(rulesManagement.getMatCellRuleName(), ruleValuesEdit.get(2));
			Thread.sleep(1000);
			rulesManagement.clickOnActionsIcon();
			rulesManagement.deleteBusinessRule();
			Assert.assertEquals(rulesManagement.deletedNotificationText(), "Rule deleted successfully");
		}

		else {
			rulesManagement.clickOnActionsIcon();
			rulesManagement.deleteBusinessRule();
			Assert.assertEquals(rulesManagement.deletedNotificationText(), "Rule deleted successfully");
		}
	}

	@Test(description = "Validate user able to bulk upload businesss rules", enabled = true, priority = 10)
	public void tc10_bulkUploadBusinessRules() throws InterruptedException, FileNotFoundException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);
		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(3000);

		rulesManagement.clickOnBulkUpload();
		File file = new File("./testFiles\\Rules_Template.xlsm");
		rulesManagement.browseTheBulkUploadFile(file.getAbsolutePath());
		
		Thread.sleep(2000);

		Assert.assertEquals(rulesManagement.toasterMessageForBulkUpload(), "Business rules created sucessfully!");

	}

	@Test(description = "Validate user able to bulk upload businesss rules for invalid data", enabled = true, priority = 11)
	public void tc11_bulkUploadBusinessRulesOnInvalidData()
			throws InterruptedException, FileNotFoundException, IOException {

		RulesManagementPage rulesManagement = new RulesManagementPage(driver);
		rulesManagement.mouseOverAccentureIcon();
		Thread.sleep(2000);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(rulesManagement.rulesManagementIcon));

		rulesManagement.mouseOverRulesManagementAndClick();
		Thread.sleep(4000);

		rulesManagement.clickOnBulkUpload();
		Thread.sleep(2000);
		
		File file1 = new File("./testFiles\\Rules_Template_invalid_date.xlsm");
		rulesManagement.browseTheBulkUploadFile(file1.getAbsolutePath());
	   Assert.assertTrue(rulesManagement.invalidData().contains("Incorrect Effective Start Date value"));

		File file2 = new File("./testFiles\\Rules_Template_invalid_mandatory.xlsm");
		rulesManagement.browseTheBulkUploadFile(file2.getAbsolutePath());
		Thread.sleep(3000);
		System.out.println(rulesManagement.invalidData());
       Assert.assertTrue(rulesManagement.invalidData().contains("Rule Type cannot be empty"));
       Assert.assertTrue(rulesManagement.invalidData().contains("Time Zone cannot be empty"));
       
		
		File file3 = new File("./testFiles\\Rules_Template_blank.xlsm");
		rulesManagement.browseTheBulkUploadFile(file3.getAbsolutePath());
		Assert.assertEquals(rulesManagement.missingMandatoryFieldMessage(),
				"Mandatory fields are missing for one or more rules. Please verify and try again.");

		File file4 = new File("./testFiles\\Documents_2.docx");
		rulesManagement.browseTheBulkUploadFile(file4.getAbsolutePath());
		Assert.assertEquals(rulesManagement.invalidFileFormat(),
				"Incorrect file format uploaded. Please verify and try again");

	}

	
	@AfterClass
	public void quitBrowser() {
		 driver.quit();
	}
	


}
