package Pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import AbstractComponents.PageAbstracts;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ActiveSubmissionsPage {

	public  WebDriver driver;

	public ActiveSubmissionsPage(WebDriver driver) {
		 this.driver = driver;
	        PageFactory.initElements(driver, this);
	}

	
	@FindBy(xpath = "//tr[@class='ng-star-inserted' and td[3]='Walmart Inc' and td[9]/app-status-stepper/div/ul/li[5][@class='current ng-star-inserted']]/td[2]")
	public
	WebElement GLsubmissionQuote;
	
	
	@FindBy(xpath = "//tr[@class='ng-star-inserted' and td[3]='Palos Garza Forwarding Inc' and td[9]/app-status-stepper/div/ul/li[4][@class='current ng-star-inserted']]/td[2]")
	public
	WebElement IMsubmissionRisk;
	
	@FindBy(xpath="//div[@class='ngx-spinner-overlay ng-tns-c86-2 ng-trigger ng-trigger-fadeIn ng-star-inserted']")
	WebElement Loader;
	
	@FindBy(xpath = "//tr[@class='ng-star-inserted' and td[3]='Los Angeles World Airports' and td[9]/app-status-stepper/div/ul/li[3][@class='toBeComplete ng-star-inserted']]/td[2]")
	public
	WebElement Aerosubmission;

	public void openSubmissionGL(WebElement element) {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		GLsubmission.click();
		wait.until(ExpectedConditions.invisibilityOf(Loader));
	}
	public void openSubmission(WebElement element) {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		wait.until(ExpectedConditions.invisibilityOf(Loader));
	}
	public void openSubmissionIM(WebElement element) {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		IMsubmission.click();
		wait.until(ExpectedConditions.invisibilityOf(Loader));
	}
	public void openSubmissionAero(WebElement element) {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Aerosubmission.click();
		wait.until(ExpectedConditions.invisibilityOf(Loader));
	}
	

	// Locator for Active Submission Title
	public By activeSubmissionTitleLocator = By.xpath("//h1[contains(text(),'Active Submissions')]");

	// locator of submission number created in submissions table
	public By submissionNumberCreatedLocator = By.xpath(
			"//tr[@class='ng-star-inserted' and td[3]='Los Angeles World Airports' and td[9]/app-status-stepper/div/ul/li[3][@class='toBeComplete ng-star-inserted']]/td[2]");

	// locator of submission details heading
	public By submissionDetailHeadingLocator = By.cssSelector(".submission-details-heading");

	// LOCATOR OF SUBMISSION NUMBER
	public By submissionNumberLocator = By.xpath("//div[@class='heading-wrapper']/span/span[contains(text(),'APP')]");

	// locator for proceed to next step button
	public By proceedToNextStepButtonLocator = By.xpath("//button[@id='proceedtoNextStep']");

	// locator of Data Insights
	public By dataInsightsLocator = By.xpath("//a[contains(text(),'Data Insights')]");

	// locator of Submission Data Insights heading
	public By dataInsightsHeading = By.xpath("//div[contains(text(),'Submission Data Insights')]");

	public By submissionDataButtonLocator = By.xpath("//a[contains(text(),'Submission Data')]");

	public By subbmissionDocumentsHeadingLocator = By.xpath("//p[contains(text(),'Submission Documents')]");

	public By statusOfInProgressRiskEvaluationLocator = By
			.xpath("//div/mat-step-header[4]/div[3]/div/div/span[contains(text(),'In Progress')]");

	By basicInformationText = By.xpath("//div[contains(text(),'Basic Information')]");
	@FindBy(xpath = "//tr[@class='ng-star-inserted' and td[3]='Walmart Inc' and td[9]/app-status-stepper/div/ul/li[3][@class='toBeComplete ng-star-inserted']]/td[2]")
	public
	WebElement GLsubmission;

	@FindBy(xpath = "//tr[@class='ng-star-inserted' and td[3]='Palos Garza Forwarding Inc' and td[9]/app-status-stepper/div/ul/li[3][@class='toBeComplete ng-star-inserted']]/td[2]")
	public
	WebElement IMsubmission;

	@FindBy(xpath = "//button[@id='proceedtoNextStep']")
	WebElement proceedToNextStep;

	@FindBy(xpath = "//mat-horizontal-stepper/div/mat-step-header[3]/div[3]/div/div")
	WebElement triageReview;

	@FindBy(xpath = "//mat-horizontal-stepper/div/mat-step-header[4]/div[3]/div/span[text()='Risk Evaluation']")
	WebElement riskEval;

	@FindBy(xpath = "//button[@id='proceedtoNextStep']")
	WebElement generateQuote;

	@FindBy(xpath = "//mat-card/div[2]/div/table/tbody/tr/td[2][text()='Walmart Inc']")
	public
	WebElement BasicInfoPopulated;

	@FindBy(xpath = "//mat-horizontal-stepper/div/mat-step-header[5]/div[3]/div/div")
	WebElement GeneratingQuote;

	@FindBy(xpath = "//button/span[text()='Send Quote to Broker']")
	WebElement SendQuote;

	@FindBy(xpath = "//mat-horizontal-stepper/div/mat-step-header[5]/div[3]/div/div/span[text()=' Quote Sent to Broker ']")
	WebElement QuoteSentStatus;

	@FindBy(xpath = "//mat-card/div[2]/div/table/tbody/tr/td[2]")
	public List<WebElement> BasicInfoEle;

	@FindBy(xpath = "//div[@class='min ng-star-inserted']")
	public List<WebElement> ComparativeEle;
////div[@class="ng-star-inserted"]/lib-company-information/div/div/div/div[2]	

	@FindBy(xpath = "//ul[@class='nav uil-nav-tabs']/li[2]")
	WebElement DataInsights;


	@FindBy(xpath = "//div[text()='Number of Employees ']")
	public	WebElement ComparitiveLoaded;

	/*
	 * public void openSubmission() throws InterruptedException {
	 * GLsubmission.click(); WebDriverWait wait = new WebDriverWait(driver,
	 * Duration.ofSeconds(30));
	 * wait.until(ExpectedConditions.elementToBeClickable(proceedToNextStep)); }
	 */

	public void InitialToTriageReview() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(proceedToNextStep));
		proceedToNextStep.click();
		wait.until(ExpectedConditions.visibilityOf(triageReview));

		String Status1 = "Data Extraction in Progress";

		do {
			driver.navigate().refresh();
			;
			Thread.sleep(20000);
		} while (triageReview.getText().equals(Status1));
	}

	public void TriageToRiskEval() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(proceedToNextStep));
		proceedToNextStep.click();
		wait.until(ExpectedConditions.visibilityOf(riskEval));
	}

	public void RiskEvalToQuote() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(generateQuote));
		generateQuote.click();
		wait.until(ExpectedConditions.visibilityOf(GeneratingQuote));
		String status = GeneratingQuote.getText();
		String status1 = "Generating Quote";
		do {
			driver.navigate().refresh();
			Thread.sleep(20000);
		} while (GeneratingQuote.getText().equals(status1));
		wait.until(ExpectedConditions.elementToBeClickable(SendQuote));
		SendQuote.click();
		wait.until(ExpectedConditions.visibilityOf(QuoteSentStatus));
	}

	public void validateDataBasicInfo() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(BasicInfoPopulated));
		Thread.sleep(2000);
		ArrayList<String> BasicInfo = new ArrayList<String>();
		for (int k = 0; k < BasicInfoEle.size(); k++) {
			String elementText = BasicInfoEle.get(k).getText();
			BasicInfo.add(elementText);
		}
		for (int i = 0; i < BasicInfo.size(); i++) {
			// Column column =sheet.getColumn(1);
			System.out.println(BasicInfo.get(i));
		}
		File file = new File("C:\\Users\\pulkit.parmar\\Documents\\ValidationFile.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = wb.getSheet("Basic_Information");
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		ArrayList<String> ExpectedValues = new ArrayList<String>();

		for (int i = 0; i < rowCount + 1; i++) {
			// Column column =sheet.getColumn(1);
			Row row = sheet.getRow(i);
			ExpectedValues.add(row.getCell(1).getStringCellValue());
		}
		for (int j = 0; j < ExpectedValues.size(); j++) {
			Assert.assertEquals(BasicInfo.get(j), ExpectedValues.get(j));
			;
		}
	}
	
	public void WaitFor(WebElement Element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.elementToBeClickable(Element));
	}
/*	
	public void RuleTEST() throws IOException {
		File file = new File("C:\\Users\\pulkit.parmar\\Documents\\ValidationFile.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheetRule = wb.getSheet("Rules_Engine");
		XSSFRow rowRule= sheetRule.getRow(0);
		Cell cellRule = rowRule.getCell(1);
		System.out.println(cellRule);
	}
*/	
	public void NavigateToDataInsights() throws InterruptedException {
		DataInsights.click();
		Thread.sleep(10000);
		/*WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.elementToBeClickable(ComparitiveLoaded));*/
	}
	
	public void VehicleDetailsAPI() throws InterruptedException {
		
		RestAssured.baseURI = "https://api.uw-dev.cognitiveinsurance.accenture.com/widget/vehicle";
		RequestSpecification httpRequest = RestAssured.given().header("Authorization", "Bearer eyJraWQiOiJvNWRwcWFpZWduRVJZM05TVjJZWU9cL1pmbnJpWTQ1MHJ4TDJtVTJQU1BrZz0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiQUpZdThlcU91RDFSMGFDR1FhSzd3ZyIsInN1YiI6ImRkYTMyNWQwLTQ1N2UtNDlhZi04YWUxLTI1YmNiNjA2NGMyOSIsImNvZ25pdG86Z3JvdXBzIjpbInVzLWVhc3QtMl9NN2VndHFqSlRfQXp1cmUtQUQtUHJvZHVjdGlvbiJdLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0yX003ZWd0cWpKVCIsImNvZ25pdG86dXNlcm5hbWUiOiJhenVyZS1hZC1wcm9kdWN0aW9uX2Vwc3BvMWxpaWVmZGJ4Nm43N3QzdC1vZ3Jub2Zrb3ZocDh6cXdtLTlycmsiLCJnaXZlbl9uYW1lIjoiUHVsa2l0Iiwibm9uY2UiOiI4cG03ek83ckgzcVMxakN0bDZZUUdsN2h6Ty1wRjVTRW1kWkxXQm1MaFd6OHphbE5pYVZaNnJ6TUFLZnRqR296bERGZFUxdGp5ZEhzWTZKbFZkcDluVzBZWmRiXzVLQlFhZktOb2I0MlA4TXFfNy1BRVYtOGxlNV9obVczeWg5TjQ0UG9LbHA1Mzk0U3NGTXZxWHZYVnlMWWJaUVdSU3lPVy1kYWdFSkRybkEiLCJvcmlnaW5fanRpIjoiNDVjNDkyZmUtMmZhYy00ZDc2LWE3MGMtNzA5Y2FkOGEyZTBhIiwiYXVkIjoiMzI2bmFxamgyZWE3NmY0c3FtMmIya3RmZXMiLCJpZGVudGl0aWVzIjpbeyJ1c2VySWQiOiJFcFNwbzFsaWlFZkRiWDZuNzd0M1QtT0dybm9GS092aFA4enFXTS05cnJrIiwicHJvdmlkZXJOYW1lIjoiQXp1cmUtQUQtUHJvZHVjdGlvbiIsInByb3ZpZGVyVHlwZSI6IlNBTUwiLCJpc3N1ZXIiOiJodHRwczpcL1wvc3RzLndpbmRvd3MubmV0XC9lMDc5M2QzOS0wOTM5LTQ5NmQtYjEyOS0xOThlZGQ5MTZmZWJcLyIsInByaW1hcnkiOiJ0cnVlIiwiZGF0ZUNyZWF0ZWQiOiIxNjU5MzMxNDcyNjY2In1dLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTY3Mjc0NTE0MiwiZXhwIjoxNjcyNzQ4NzQyLCJjdXN0b206cm9sZSI6Im1hbmFnZXIiLCJpYXQiOjE2NzI3NDUxNDIsImZhbWlseV9uYW1lIjoiUGFybWFyIiwianRpIjoiYTFkNWUxNWYtNGEyZi00OTI5LTkzMzgtNjI4YTgxYjg1MmI4IiwiZW1haWwiOiJwdWxraXQucGFybWFyQGFjY2VudHVyZS5jb20ifQ.BHaKmusScuotyWH1QoMZJU_5kuN8H0lHjUwnCFe4GWnNzqAS9six7tPNgy_VqBhqRWkW-sV8rXu9Vtcz4IbPMZrQ7mp5chgMVAu-q7We2WheWTHXqk4VBfLF4vt2x22xXJiLY7fJeepXSQvuzL4lVEn9OKMVrRmEnx0_FYvtNczLsziBOn7fD64IvVGPlV0-faia9x1TOBYerSloa5Gk-QKFMYDIFM5w5r1C13FLIIamYRo25He-luqQuojSgookleYKfvlk-uohFAWDeyFCxLKNLiIv9JC5K1egUCVoB_0KOVuP47sKUxTykTfxOiEtzggCjdR7lpuDzNFfaL0BuA");
		Response response = httpRequest.get("/APP202212140004");
		JsonPath jsonPathEvaluator = response.jsonPath();
        ArrayList <String> VIN = jsonPathEvaluator.get("vin");
        ArrayList <String> MAKE_MODEL = jsonPathEvaluator.get("make_model");
        ArrayList <String> TYPE = jsonPathEvaluator.get("type");
        ArrayList <String> PHYSICAL_DAMAGE = jsonPathEvaluator.get("physical_damage");
        ArrayList <String> COLLISION = jsonPathEvaluator.get("collision");
        ArrayList <String> SEAT_CAPACITY = jsonPathEvaluator.get("seat_capacity");
        ArrayList <String> OPERATION_RADIUS = jsonPathEvaluator.get("operating_radius");
        
        List<WebElement> Vehicle_Vin = driver.findElements(By.xpath("//tr/td[@class='table-data vin']"));
        List<WebElement> Year_Make_Model = driver.findElements(By.xpath("//tr/td[@class='table-data model']"));
        List<WebElement> Type = driver.findElements(By.xpath("//tr/td[@class='table-data type']"));
        List<WebElement> Damage = driver.findElements(By.xpath("//tr/td[4]"));
        List<WebElement> Collision = driver.findElements(By.xpath("//tr/td[5]"));
        List<WebElement> Seat = driver.findElements(By.xpath("//tr/td[@class='table-data seat']"));
        List<WebElement> Radius = driver.findElements(By.xpath("//tr/td[7]"));
        System.out.println(VIN.size());
        System.out.println(Vehicle_Vin.size());
        for(int i=0 ; i < VIN.size() ; i++) {
        	System.out.println(VIN.get(i));
        	
        }
        for(int j = 0; j < VIN.size(); j++) {
        	Assert.assertEquals(Vehicle_Vin.get(j).getText(),  VIN.get(j));
        	Assert.assertEquals(Year_Make_Model.get(j).getText(),  MAKE_MODEL.get(j));
        	Assert.assertEquals(Type.get(j).getText(),  TYPE.get(j));
        }
}
	

	public void validateData(List<WebElement> Element, String sheet) throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		Thread.sleep(4000);
		ArrayList<String> ActualValues = new ArrayList<String>();
		for (int k = 0; k < Element.size(); k++) {
			String elementText = Element.get(k).getText();
			ActualValues.add(elementText);
		}
	//	File file = new File("C:\\Users\\pulkit.parmar\\Documents\\ValidationFile.xlsx");
		File file = new File("TestFiles/ValidationFile.xlsx");
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet2 = wb.getSheet(sheet);
		XSSFSheet sheetRule = wb.getSheet("Rules_Engine");
		XSSFRow rowRule= sheet2.getRow(0);
		Cell cellRule = rowRule.getCell(1);
		System.out.println(cellRule);
		int rowCount = sheet2.getLastRowNum() - sheet2.getFirstRowNum();
		ArrayList<String> ExpectedValues = new ArrayList<String>();
		for (int i = 0; i < sheet2.getLastRowNum() + 1; i++) {
			XSSFRow row = sheet2.getRow(i);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				DataFormatter dataFormatter = new DataFormatter();
				String formattedCellStr = dataFormatter.formatCellValue(cell1);
				ExpectedValues.add(formattedCellStr);
			}
		}
		for (int j = 0; j < ActualValues.size(); j++) {
			Assert.assertEquals(ActualValues.get(j), ExpectedValues.get(j));
			;
		}
	}


/*
 * do { driver.navigate().refresh(); Thread.sleep(20000);
 * }while(status.equals(Status1)); } }
 */

	// gets Active submission title
	public String getActiveSubmissionTitle() {
		String submissionTitle = driver.findElement(activeSubmissionTitleLocator).getText();
		return submissionTitle;
	}

	// click on submission number created
	public void selectSubmissionCreated() {

		driver.findElement(submissionNumberCreatedLocator).click();
	}
	
	// click on submission number created
		public void selectInlandMarineSubmissionCreated() {

			driver.findElement(submissionNumberCreatedLocator).click();
		}
	
	public void selectSubmissionDataExtracted() {
		
		WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='Los Angeles World Airports' and td[9]/app-status-stepper/div/ul/li[4][@class='current ng-star-inserted']]/td[2]"));
		
		element.click();
		
	}

	// get text of submission detail heading
	public String getSubmissionDetailsHeading() {

		String submissionNumberHeading = driver.findElement(submissionDetailHeadingLocator).getText();

		return submissionNumberHeading;

	}

	public String getSubmissionNumber() {

		String submissionNumber = driver.findElement(submissionNumberLocator).getText();
		return submissionNumber;
	}

	public void clickOnProceedToNextStep() {
		WebElement element = driver.findElement(proceedToNextStepButtonLocator);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	public void clickOnDataInsights() {

		driver.findElement(dataInsightsLocator).click();

	}

	public String getDataInsightsHeading() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOfElementLocated(dataInsightsHeading));
		String dataInsights = driver.findElement(dataInsightsHeading).getText();
		return dataInsights;
	}

	public void clickOnSubmissionData() {

		driver.findElement(submissionDataButtonLocator).click();

	}

	public String getSubmissionDocumentsHeading() {

		String submissionDocumentText = driver.findElement(subbmissionDocumentsHeadingLocator).getText();

		return submissionDocumentText;

	}

	public boolean isGenerateQuoteButtonEnabled() {
		Boolean isVisibleTrue = driver.findElement(By.xpath("//span[contains(text(),'Generate Quote')]")).isDisplayed();
		return isVisibleTrue;

	}

	public void clickOnGenerateQuote() {
		driver.findElement(By.xpath("//span[contains(text(),'Generate Quote')]")).click();
	}

	public Boolean riskEvaluationDispalyed() {

		Boolean isRiskDisplayed = driver.findElement(By.xpath("//div/mat-step-header[@role='tab'][4]/div"))
				.isDisplayed();
		return isRiskDisplayed;

	}

	public String getStateOfSubmissionAs(String status, int state) {
		WebElement stateName =driver.findElement(By.xpath(
				"//div/mat-step-header[@role='tab'][" + state + "]/div[3]/div/span[contains(text(),'" + status + "')]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(stateName));
		String statusText =stateName.getText();
		return statusText;
	}

	public String getStatusOfStateOfSubmissionAs(int state) {

		WebElement statusOfState = driver
				.findElement(By.xpath("//div/mat-step-header[@role='tab'][" + state + "]/div[3]/div/div"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(statusOfState));
		String statusText = statusOfState.getText();
		return statusText;
	}

	public void processTheInitialReviewState() throws InterruptedException {

//		do {
			clickOnProceedToNextStep();
//			driver.navigate().refresh();
    	//	Thread.sleep(5000);
//		} while (getStatusOfStateOfSubmissionAs(2).contains("In Progress"));

	}

	public void processTheTriageReviewState() throws InterruptedException {

		do {
			driver.navigate().refresh();
			Thread.sleep(5000);
		} while (getStatusOfStateOfSubmissionAs(3).contains("Data Extraction"));

	}
	
	public void processTheQuoteGenerationState() throws InterruptedException {

		do {
			driver.navigate().refresh();
			Thread.sleep(5000);
		} while (getStatusOfStateOfSubmissionAs(5).contains("Generating Quote"));

	}

	public String getStatusOfRiskEvaluationOfSubmissionAs(int state, String status) {

		WebElement statusOfState = driver.findElement(By
				.xpath("//div/mat-step-header[" + state + "]/div[3]/div/div/span[contains(text(),'" + status + "')]"));
		// WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div/mat-step-header["+state+"]/div[3]/div/div/span[contains(text(),'"+status+"')]")));
		String statusText = statusOfState.getText();
		return statusText;
	}

	public List<String> getBasicInfoColumnsHeaders() {

		List<WebElement> columnHeaderWebElements = driver
				.findElements(By.xpath("//table/tbody/tr/td[@class='information']"));

		List<String> columnHeaderText = columnHeaderWebElements.stream().map(s -> s.getText())
				.collect(Collectors.toList());
		
		columnHeaderText.remove(1);

		return columnHeaderText;

	}

	public List<String> getBasicInfoColumnValues() {

		List<WebElement> columnValuesWebElements = driver
				.findElements(By.xpath("//table/tbody/tr/td[@class='information']/following-sibling::td"));

		List<String> columnValuesText = columnValuesWebElements.stream().map(s -> s.getText())
				.collect(Collectors.toList());
		
		columnValuesText.remove(1);

		return columnValuesText;

	}
	
	public String getAccountNumberFromBasicInfo() {
		
		List<WebElement> columnValuesWebElements = driver
				.findElements(By.xpath("//table/tbody/tr/td[@class='information']/following-sibling::td"));
		
		String accountNumber = columnValuesWebElements.stream().map(s -> s.getText()).filter(a->a.contains("ACC")).findAny().get();
		
		return accountNumber;
		
	}

	public String getBasicInfoText() {

		WebElement basicInfo = driver.findElement(basicInformationText);
		String basicInfoText = basicInfo.getText();
		return basicInfoText;

	}
	
	public void selectSubmissionCommercialProperty() {
		
		WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='Mcdonalds Corporation' and td[9]/app-status-stepper/div/ul/li[3][@class='toBeComplete ng-star-inserted']]/td[2]"));
		
		element.click();
		
	}
	
    public void selectSubmissionDataExtractedForCP() {
		
		WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='Mcdonalds Corporation' and td[9]/app-status-stepper/div/ul/li[4][@class='current ng-star-inserted']]/td[2]"));
		
		element.click();
		
	}
    
   
    
 public void selectSubmissionInState4(String accountName) {
		
		WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName.trim()+"' and td[9]/app-status-stepper/div/ul/li[4][@class='current ng-star-inserted']]/td[2]"));
		
		element.click();
		
	}
 
 public void selectSubmissionInState7(String accountName) {
	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName.trim()+"' and td[9]/app-status-stepper/div/ul/li[7][@class='current ng-star-inserted']]/td[2]")));
		
		WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName.trim()+"' and td[9]/app-status-stepper/div/ul/li[7][@class='current ng-star-inserted']]/td[2]"));
		
		
		element.click();
		
	}
 
 public String getSubmissionNumberInState4(String accountName) {
		
     WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName+"' and td[9]/app-status-stepper/div/ul/li[4][@class='current ng-star-inserted']]/td[2]"));
		
		String applicationNumber = element.getText();
		return applicationNumber;
		
 		
 	}
 
 public String getSubmissionNumberInState7(String accountName) {
	 
		
   //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@class='ng-star-inserted' and td[3]='\"+accountName+\"' and td[9]/app-status-stepper/div/ul/li[7][@class='current ng-star-inserted']]/td[2]")));
     
		  WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName+"' and td[9]/app-status-stepper/div/ul/li[7][@class='current ng-star-inserted']]/td[2]"));
		String applicationNumber = element.getText();
		return applicationNumber;
		
 		
 	}
 public void selectSubmissionState2(String accountName) {
		
		WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName+"' and td[9]/app-status-stepper/div/ul/li[2][@class='current ng-star-inserted']]/td[2]"));
		
		element.click();
		
	}
 
 public String getSubmissionNumberInState2(String accountName) {
		
     WebElement element =  driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName+"' and td[9]/app-status-stepper/div/ul/li[2][@class='current ng-star-inserted']]/td[2]"));
		
		String applicationNumber = element.getText();
		return applicationNumber;
		
 		
 	}
 
 
 public void selectActiveSubmissionCreated(String accountName) {
	 WebElement element = driver.findElement(By.xpath("//tr[@class='ng-star-inserted' and td[3]='"+accountName+"' and td[9]/app-status-stepper/div/ul/li[2][@class='current ng-star-inserted']]/td[2]"));
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));	
	 element.click();
	 
 }
 
 public void clickOnActiveSubmissionTab() {
	 WebElement element = driver.findElement(By.xpath("//app-home/descendant::li[2]/a"));
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));	
		JavascriptExecutor execute = (JavascriptExecutor)driver;
		execute.executeScript("arguments[0].click();",element);
	// element.click();	 
 }
 
 public void expandActiveSubmissionAndClickOnApplication(String accountName, int state) throws InterruptedException {
	    List<WebElement> element = driver.findElements(By.xpath("//table/descendant::tr/td[contains(text(),'"+accountName+"')]/following-sibling::td[5]/span"));
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));	
		
		for(int i=0; i<element.size();i++) {
			element.get(i).click();	
			Thread.sleep(5000);
	//		WebElement element2 = driver.findElement(By.xpath("//app-status-stepper/div/ul/li["+state+"][contains(@class,'toBeComplete')]"));
		//	if(element2.isDisplayed()==true) {
			try {
				WebElement element3 = driver.findElement(By.xpath("//td/app-status-stepper/div/ul/li["+state+"][contains(@class,'toBeComplete')]/ancestor::tr[1]/td[1]"));	
				element3.click();
				break;
			}
		//	}
		//	else if(element2.isDisplayed()==false){
			catch(Exception e) {
				   List<WebElement> element2 = driver.findElements(By.xpath("//table/descendant::tr/td[contains(text(),'"+accountName+"')]/following-sibling::td[5]/span"));
					WebDriverWait wait2= new WebDriverWait(driver,Duration.ofSeconds(5));
					wait2.until(ExpectedConditions.visibilityOfAllElements(element2));	
				element2.get(i).click();	
			}
		}
 }

 
 
 

}
