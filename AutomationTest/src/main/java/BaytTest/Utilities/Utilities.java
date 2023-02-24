package BaytTest.Utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {
 

public static void takeScreenShot(WebDriver webdriver, String filePath) throws IOException {
		TakesScreenshot shot = ((TakesScreenshot)webdriver);
		File scrfile = shot.getScreenshotAs(OutputType.FILE);
		File desfile = new File(filePath);
		FileUtils.copyFile(scrfile,desfile);
		
	}

	
	
}
