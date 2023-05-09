package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UWNotesPage {
	public WebDriver driver;
	
	public UWNotesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath = "//span[contains(text(),'UW Notes')]")
	public WebElement UWNotesLink;
	
	@FindBy(xpath = "//div[@class='addNote-area']/textarea")
	public WebElement AddNotesTextArea;
	
	@FindBy(xpath = "//button/span[@class='adding']")
	public WebElement AddNotesButton;
	
	@FindBy(xpath = "//div[@class='notesListContainer']/descendant::span/span")
	public WebElement ViewNotesArea;
	
	@FindBy(xpath = "//a[contains(text(),'Clear')]")
	public WebElement ClearNotes;
	
	@FindBy(xpath = "//mat-expansion-panel-header/span[2]")
	public WebElement ViewNotesExpander;
	
	@FindBy(xpath = "//span[contains(text(),'Delete')]")
	public WebElement ViewNotesDelete;
	
	@FindBy(xpath = "//a[contains(text(),'Yes, Delete')]")
	public WebElement YesDelete;
	
	@FindBy(xpath = "//a[contains(text(),'Cancel')]")
	public WebElement CancelDelete;
	
	@FindBy(xpath = "//div[contains(@class,'alert')]")
	public WebElement NotesDeletedMessage;
	
	@FindBy(xpath = "//div[contains(text(),' You have no notes for this Submission as of now. Once you add them, they will appear here. ')]")
	public WebElement NoNotesMessage;
	
	
	public void clickOnUWNotes() {	
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(UWNotesLink));
	//	UWNotesLink.click();	
			JavascriptExecutor execute= (JavascriptExecutor)driver;
			execute.executeScript("arguments[0].click();", UWNotesLink);
			
	}
	
	public void enterNotesInTextArea(String text) {
	//	WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
	//	wait.until(ExpectedConditions.visibilityOf(AddNotesTextArea));
		  JavascriptExecutor js = (JavascriptExecutor)driver;
		  js.executeScript("arguments[0].value='"+text+"';", AddNotesTextArea);
		//AddNotesTextArea.sendKeys(text);
	}
	
	public void clickOnAddNotes() {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(2));
		wait.until(ExpectedConditions.visibilityOf(AddNotesButton));
		AddNotesButton.click();		
	}
	
	public boolean isViewNotesDisplayed() {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ViewNotesArea));
		return ViewNotesArea.isDisplayed();		
	}
	
	public String getViewNotesText() {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ViewNotesArea));
		return driver.findElement(By.xpath("//div[@class='notesListContainer']/descendant::span/span[2]")).getText();	
	}
	
	public void changeStages(int stateNumber, String stageName) {
		
		driver.findElement(By.xpath("//div[@class='viewNotes-container']/descendant::button/span[2]")).click();
		driver.findElement(By.xpath("//div[@class='viewNotes-container']/descendant::button/following-sibling::ul/li["+stateNumber+"]/span[text()='"+stageName+"']")).click();
		
	}
	
	public String deleteNotes() {
		ViewNotesExpander.click();
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(ViewNotesDelete));	
		ViewNotesDelete.click();
		YesDelete.click();
		WebDriverWait wait2= new WebDriverWait(driver,Duration.ofSeconds(10));
		wait2.until(ExpectedConditions.visibilityOf(NotesDeletedMessage));
		return NotesDeletedMessage.getText();	
	}
	
	public void cancelNotesDelete() {
		WebDriverWait wait2= new WebDriverWait(driver,Duration.ofSeconds(5));
		wait2.until(ExpectedConditions.visibilityOf(ViewNotesExpander));	
		ViewNotesExpander.click();
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(ViewNotesDelete));	
		ViewNotesDelete.click();
		CancelDelete.click();	
	}
	
	public void clickOnclearNotes() {
		ClearNotes.click();	
	}
	
	public void clickOnCloseIcon() {
		WebElement element = driver.findElement(By.xpath("//span[contains(@class,'closeIcon')]"));
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));	
	//	element.click();
		JavascriptExecutor execute= (JavascriptExecutor)driver;
		execute.executeScript("arguments[0].click();", element);
		
	}
	
	

}
