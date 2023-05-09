package cucumberOptions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/features2", glue="StepDefinition2",  monochrome=true, tags="@InlandMarineWidgets",
plugin= {"html: target/cucumber.html"})

public class TestNGTestRunner extends AbstractTestNGCucumberTests{
	 
	
	 
	
    
	

}
