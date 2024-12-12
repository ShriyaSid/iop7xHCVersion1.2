package testBase;



import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;
import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;
import org.apache.commons.lang3.RandomStringUtils;


/**
 * @author Shriya Siddarth
   @version 1.2
 */
public class CreateCitizenAccount extends TestBase implements ITestListener{

	

   
	
	@SuppressWarnings("deprecation")
	@Test(priority=1)
	public void create7XAccount() throws IOException, MailosaurException, InterruptedException
	{
		logger.info("***** Starting the first step of health check - Creating OHID Account ****");
		String emailId = getRandomEmail();
		
		
		//open URL
		logger.info("***** Launching Homepage and clicking on CreateOHID Account Button  ****");
		
		
		System.out.println("Page title is: " +driver.getTitle());
		
		//Clicks Create OHID Account Button 
		driver.findElement(By.xpath("//a[contains(text(),'Create OHID Account')]")).click();
		Thread.sleep(3000);
		
		
		//Prints the page title
		System.out.println("Page title is: " +driver.getTitle());
		System.out.println("Email ID is " +emailId);
		
		//Entering the mailosaur generated email id in the textboxes email address & confirm email address
		
		driver.findElement(By.xpath("//*[@id='user-email']")).sendKeys(emailId);
		
		driver.findElement(By.xpath("//*[@id='user-confirm-email']")).sendKeys(emailId);
		
		Thread.sleep(3000);
		
		
		driver.findElement(By.xpath("//*[@id='send-pin-email']")).click();
		
		Thread.sleep(8000);
		
		MailosaurClient mailosaur = new MailosaurClient(apiKey);

	    MessageSearchParams params = new MessageSearchParams();
	    params.withServer(serverId);

	    SearchCriteria criteria = new SearchCriteria();
	    criteria.withSentTo(emailId);
	    criteria.withSentFrom(from);
	    

	    Message message = mailosaur.messages().get(params, criteria);
	    System.out.println(message.subject());
	   
	    
	    System.out.println(message.html().body());
	    Pattern pattern = Pattern.compile(".*([0-9]{6}).*");
	    Matcher matcher = pattern.matcher(message.html().body());
	    matcher.find();
	    String otp=matcher.group(1);
	   System.out.println(otp);
		
		
	   // Enter OTP
		
		driver.findElement(By.xpath("//*[@id='user-entered-pin']")).sendKeys(otp);
		OhioIdOTPTest.captureName(driver,"OTPVerification");
		logger.info("***** OTP Verification - Fetching the OTP from mailosaur account  ****");
	    
		// Click Verify
		
		driver.findElement(By.xpath("//*[@id='verify-pin-email']")).click();
		logger.info("***** OTP is verified ****");
		
		Thread.sleep(3000);
		
		// Click Next
		driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
		
	    
	    // goes to next page to enter username
	   driver.getTitle();
	    
	    // Enters personal info
	   driver.findElement(By.xpath("//*[@id='user-firstname']")).sendKeys(p.getProperty("fName"));
	   driver.findElement(By.xpath("//*[@id='user-lastname']")).sendKeys(p.getProperty("lName"));
	   driver.findElement(By.xpath("//*[@id='user-dob']")).sendKeys(p.getProperty("dob"));
	   OhioIdOTPTest.captureName(driver,"PersonalInfo");
	   logger.info("***** Entering personal info  ****");
	   Thread.sleep(3000);
	   JavascriptExecutor js1 = (JavascriptExecutor) driver;
	   js1.executeScript("window.scrollBy(0,350)", "");
	   driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
	    Thread.sleep(5000);
	    
	    //Enters username
	    
	    driver.findElement(By.xpath("//*[@id='username']")).sendKeys(p.getProperty("userName"));
	    System.out.println("Username entered:" +p.getProperty("userName"));
	    
	    logger.info("***** Entering username  ****");
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
	    String password=randomAlphaNumberic();
	    System.out.println("Password entered:" +password);
	    logger.info("***** Entering Password  ****");
	    
	    //Enters Password
	    JavascriptExecutor js2 = (JavascriptExecutor) driver;
		   js2.executeScript("window.scrollBy(0,350)", "");
		   Thread.sleep(3000);
	    driver.findElement(By.xpath("//*[@id='user-password']")).sendKeys(password);
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//*[@id='user-confirm-password']")).sendKeys(password);
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
	    Thread.sleep(3000);
	    
	    //SMS OTP 
	    
	  /*  OhioIdOTPTest.captureName(driver,"Account Recovery");
		   driver.findElement(By.xpath("//*[@id='user-mobile-number']")).sendKeys("9303560106");
		   driver.findElement(By.xpath("//*[@id='send-pin-mobile']")).click();
		   
		   Thread.sleep(7000);
		   
	     MailosaurClient mailosaur1 = new MailosaurClient(apiSMSKey);
// Only look for messages after our test began

// Send an SMS message to your test number...

MessageSearchParams params1 = new MessageSearchParams();
params1.withServer(serversmsId);

SearchCriteria criteria1 = new SearchCriteria();
criteria1.withSentTo("9303560106");
criteria1.withSentFrom(smsFrom);// YOUR_TEST_NUMBER

Message message1 = mailosaur1.messages().get(params1, criteria1);

System.out.println(message1.text().body()); // "Your order number is 51223"
Pattern pattern1 = Pattern.compile(".*([0-9]{6}).*");
	    Matcher matcher1 = pattern1.matcher(message1.text().body());
	    matcher1.find();
	    String otp1=matcher1.group(1);
	   System.out.println(otp1);
	   OhioIdOTPTest.captureName(driver,"SMS OTP");
driver.findElement(By.xpath("//*[@id='user-entered-mobile-pin']")).sendKeys(otp1);
Thread.sleep(2000);
driver.findElement(By.xpath("//*[@id='verify-pin-mobile']")).click();
Thread.sleep(2000);

driver.findElement(By.xpath("//*[@id='create-account-next']")).click();*/


	   
	      
	     
	   //Skip Account Recovery
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0,350)", "");
	    Thread.sleep(2000);
	   
	    driver.findElement(By.xpath("//a[@id='recovery-option-checkbox']")).click();
	    
	    Thread.sleep(5000);
	    
	    //Terms & conditions 
	    
	    driver.findElement(By.xpath("//*[@id='tc-agreement-checkbox']")).click();
	   
	    logger.info("***** Accepting Terms and Conditions ****");
	    JavascriptExecutor js4 = (JavascriptExecutor) driver;
	    js4.executeScript("window.scrollBy(0,350)", "");
	    Thread.sleep(3000);
	    
	   // OhioIdOTPTest.captureName(driver,"Captcha Question");
	    logger.info("***** Captcha is verified ****");
	    
	    
	    //Handling Captcha Q&A
	    
	    
	    WebElement ele = driver.findElement(By.xpath("//*[@id=\"terms-and-conditions\"]/div[4]/label"));
	    Thread.sleep(4000);
	    String captchaque= ele.getText();
	    Thread.sleep(3000);
	    
	    System.out.println(captchaque);
	    String que1= "What is the 2nd color in the list pink, house and purple?";
	    String que2= "What is forty six thousand and fifty eight as a number?";
	    String que3= "Which word from the list \"carload, exact, assail, portfolio\" contains the letter \"p\"?";
	    String que4= "Bee, chin, ankle, leg and dog: how many body parts in the list?";
	    String que5= "What is the 2nd digit in 217903?";
	    String que6= "Which of brain, toe, knee, ankle or arm is part of the head?";
	    String que7= "Red, pants and pink: the 1st color is?";
	    if(captchaque.equalsIgnoreCase(que1))
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("purple");
	    }
	    //que 2
	    else if(captchaque.equalsIgnoreCase(que2))
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("46058");
	    }
	    //que 3
	    else if(captchaque.equalsIgnoreCase(que3))
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("portfolio");
	    }
	    //que 4
	    else if(captchaque.equalsIgnoreCase(que4))
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("3");
	    }
	    //que 5
	    else if(captchaque.equalsIgnoreCase(que5))
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("1");	
	    }
	    //que 6
	    else if(captchaque.equalsIgnoreCase(que6))
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("brain");	
	    }
	    //que 7
	    else if(captchaque.equalsIgnoreCase(que7))
	    {
	    driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("red");	
	    }
	    else
	    {
	    	driver.findElement(By.xpath("//input[@id='captchaAnswer']")).sendKeys("9");
	    }
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//button[@id='verify-captcha']")).click();
	    Thread.sleep(4000);
	    driver.findElement(By.xpath("//button[@id='account-creation-submit']")).click();
    	
    	OhioIdOTPTest.captureName(driver,"Create Account Button Enabled");
    	logger.info("***** Create Account button is enabled  ****");
	    
	    
		
	    	
		Thread.sleep(15000);
		if(driver.findElement(By.xpath("//h2[contains(text(),'Check your Email')]")).isDisplayed()==true)
		{
			System.out.println("Account created, Try to login");
			OhioIdOTPTest.captureName(driver,"Status of account creation");
			logger.info("***** Account created, Please try to login now  ****");
		}
		else 
		{
			System.out.println("Account not created, Try again");
			logger.info("***** Account not created , Please try again****");
			OhioIdOTPTest.captureName(driver,"Status of account creation");
		}
		
		Thread.sleep(10000);
		
		
		//Login
		driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='loginUserID']")).sendKeys(p.getProperty("userName"));
		driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(password);
		
		//OhioIdOTPTest.captureName(driver,"Login");
		logger.info("***** Logged in to the account  ****");
		driver.findElement(By.xpath("//*[@id='loginSubmit']")).click();	
		
		Thread.sleep(1000);
		
		driver.getTitle();
		Thread.sleep(2000);
		//OhioIdOTPTest.captureName(driver,"Verify UserLoginPage");
		logger.info("***** Account Verified  ****");
		
		
		
		
		//APP store navigation
		
		driver.findElement(By.xpath("//*[@id='main-nav-container']/ul/li[6]/a")).click();
		
		driver.getTitle();
		Thread.sleep(2000);
		OhioIdOTPTest.captureName(driver,"App store");
		logger.info("***** Navigated to App store  ****");
		
		Thread.sleep(3000);
		
		//Logout
		
		driver.findElement(By.xpath("//*[@id='aw-main-wrapper']/section/div[3]")).click();
		WebElement target = driver.findElement(By.xpath("//span[text()='Log Out']"));
		WebElement logout = driver.findElement(By.xpath("//a[text()='Log Out']"));
		Actions s = new Actions(driver);
		s.moveToElement(target).click().perform();
		//OhioIdOTPTest.captureName(driver,"Log Out");
		logger.info("***** Logging out  ****");
		s.moveToElement(logout).click().perform();
		
		
		
		Thread.sleep(8000);
		
		
		
		driver.quit();
	}
		
	}
	
	
	
