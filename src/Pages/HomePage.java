package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class HomePage  {
	
	WebDriver driver;


	public HomePage(WebDriver driver) {
		this.driver  = driver;	
	}
	
	
	//home page title locator
		By head = By.xpath("//span[contains(text(),'Welcome')]");
		
		public String getHeading() {	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(head));
			String heading = driver.findElement(head).getText();
			return heading;
		}
	
	

}
