package AbstractComponents;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

public class BaseTest {
	
	WebDriver driver;

	
	@AfterTest
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"/screenshots/" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"/screenshots/" + testCaseName + ".png";
	
	}

}
