package testBase;



import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.annotations.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.Files;
import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSearchParams;
import com.mailosaur.models.SearchCriteria;

public class ForgotPassword extends TestBase implements ITestListener{
	
	
	//Forgot Password
	
			@SuppressWarnings("deprecation")
			@Test()
			public void checkForgotPassword() throws InterruptedException, IOException, MailosaurException
			{
			
				
			//OhioIdOTPTest.captureName(driver,"Forgot username or password");
			logger.info("***** Clicking on Forgot your OHID or Password link ****");
			driver.findElement(By.linkText("Forgot your OHID or password?")).click();
	        Thread.sleep(3000);
			
			driver.getTitle();
			logger.info("***** chatbot opens  ****");
			OhioIdOTPTest.captureName(driver,"Account Recovery - Choose your option");
			Thread.sleep(3000);
			
			//Choose your option -Forgot Password
			OhioIdOTPTest.captureName(driver,"Forgot Password");
			logger.info("***** Choosing Forgot Password option  ****");
			driver.findElement(By.xpath("//*[@id='chabot-primary-options']/div[2]/button[2]")).click();
			Thread.sleep(3000);
			System.out.println(p.getProperty("userName"));
			driver.findElement(By.xpath("//input[@id='custom-input-username']")).sendKeys(p.getProperty("userName"));
			Thread.sleep(2000);
			
			
			
			   driver.findElement(By.xpath("//*[@id='password-username-first-attempt']/button")).click();
			    
			  // JavascriptExecutor jse = (JavascriptExecutor)driver;
				//jse.executeScript("document.body.style.zoom='60%'");
			
			Thread.sleep(3000);
			//driver.findElement(By.xpath("//*[@id='odx-main-content']/article/div[1]/div")).click();
			//JavascriptExecutor js5 = (JavascriptExecutor) driver;
			  // js5.executeScript("window.scrollBy(0,350)", "");
			//SEND PIN BUTTON
			
			WebElement pin=driver.findElement(By.xpath("//button[@class='send-my-pin']"));
			pin.click();
			
			
			
			
			Thread.sleep(10000);
			
			MailosaurClient mailosaur2 = new MailosaurClient(apiKey);
			// Only look for messages after our test began

			// Send an SMS message to your test number...
			//Date testStart = new Date();
			
			
			MessageSearchParams params2 = new MessageSearchParams();
			params2.withServer(serverId).withTimeout(30000);

			SearchCriteria criteria2 = new SearchCriteria();
			criteria2.withSentTo(emailId); //login email id
			criteria2.withSentFrom(from);

			Message message2 = mailosaur2.messages().get(params2, criteria2);

			System.out.println(message2.html().body()); // 
			Pattern pattern2 = Pattern.compile(".*([0-9]{6}).*");
				    Matcher matcher2 = pattern2.matcher(message2.html().body());
				    matcher2.find();
				    String otp2=matcher2.group(1);
				   System.out.println(otp2);
				  // OhioIdOTPTest.captureName(driver,"Email OTP-Forgot password");
			driver.findElement(By.xpath("//*[@id='password-email-pin-first-try']")).sendKeys(otp2);
			System.out.println(otp2);
			OhioIdOTPTest.captureName(driver,"Forgot password -OTP Verification");
			
			driver.findElement(By.xpath("//*[@id='password-email-pin-first-attempt']/div[4]/button[1]")).click();
			
			//Enter New Password
			logger.info("***** Entering a New Password ****");
			String changePassword =randomAlphaNumberic();
			driver.findElement(By.xpath("//*[@id='new-password']")).sendKeys(changePassword);
			
			
			//Confirm New Password
			logger.info("***** Confirming new password ****");
			driver.findElement(By.xpath("//*[@id='confirm-password']")).sendKeys(changePassword);
			System.out.println("Your New password is "+changePassword );
			OhioIdOTPTest.captureName(driver,"NewPassword Submission");
			
			driver.findElement(By.xpath("//*[@id='reset-password-input']/button")).click();
			
			Thread.sleep(4000);
			OhioIdOTPTest.captureName(driver,"Password successfully changed");
			logger.info("***** Successfully Changed Password  ****");
			driver.quit();
		}

}
