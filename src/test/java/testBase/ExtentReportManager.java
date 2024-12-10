package testBase;



import java.io.File;
import java.awt.Desktop;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.OhioIdOTPTest;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;
	
	public void onStart(ITestContext testContext) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
		repName = "HCTest-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// specify location of the report

		sparkReporter.config().setDocumentTitle("Citizen Account Healthcheck Automation Report"); // Title of report
		sparkReporter.config().setReportName("7X HC Report"); // name of the report
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getMethod().getMethodName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed");
		
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
		
	public void onFinish(ITestContext Context) {
		
			extent.flush();
			/*String pathOfExtentReport = ".\\reports\\"+repName;
			File extentReport = new File(pathOfExtentReport);
			
			try {
				Desktop.getDesktop().browse(extentReport.toURI());
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
}


