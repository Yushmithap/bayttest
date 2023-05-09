package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public ExtentReporterNG() {
		// TODO Auto-generated constructor stub
	}

	
	public static ExtentReports getReportObject() {
		
		String path = System.getProperty("user.dir")+"/ExtentReports/report.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("UW Automation Results");
		reporter.config().setDocumentTitle("UW Test Results");
		
		ExtentReports extentReport = new ExtentReports();
		extentReport.attachReporter(reporter);
		extentReport.setSystemInfo("Tester", "Yushmitha P");
		
		return extentReport;
		
		
		
		
		
	}

}
