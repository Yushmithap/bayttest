package Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/Cucumber", glue="StepDefinitions", monochrome=true, tags="@BulkUpload",
plugin= {"html: target/cucumber.html"})

public class TestNGTestRunner extends AbstractTestNGCucumberTests{
	
	

	

}
