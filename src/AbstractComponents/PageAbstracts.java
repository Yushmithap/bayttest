package AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageAbstracts {

    public WebDriver driver;	

	public PageAbstracts(WebDriver driver) {
		// TODO Auto-generated constructor stub
		  PageFactory.initElements(driver, this);
	}
	
	//locator for accenture icon
		
	
      

}
