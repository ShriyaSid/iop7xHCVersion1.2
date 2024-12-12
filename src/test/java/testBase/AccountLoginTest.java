package testBase;



import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestListener;
import org.testng.annotations.Test;

import com.google.common.io.Files;


/**
 * @author Shriya Siddarth
   @version 1.2
 */
public class AccountLoginTest extends CreateCitizenAccount implements ITestListener{
	
	
	    
	 	

	   
	    
	   //generate screenshot names
	    
	    public static void captureName(WebDriver driver,String name) throws IOException
	    {
	    	File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			Files.copy(f, new File("C:\\Users\\rbalasubramanians\\Documents\\IOP_HC_Screenshots\\HC"+name+System.currentTimeMillis()+".jpg"));
	    }
	   
	   
	  //Login HC
	  		@SuppressWarnings("deprecation")
			@Test()
	  		public void checkLogin() throws InterruptedException,IOException{
	  			WebDriver driver = new FirefoxDriver();
	  			driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
	  			driver.manage().window().maximize();
	  			driver.manage().deleteAllCookies();
	  			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  		
	  		//System.out.println(super.userName);
	  		driver.findElement(By.xpath("//*[@id='loginUserID']")).sendKeys();
	  		driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(super.password);
	  		
	  		OhioIdOTPTest.captureName(driver,"Login");
	  		driver.findElement(By.xpath("//*[@id='loginSubmit']")).click();	
	  		
	  		Thread.sleep(1000);
	  		
	  		driver.getTitle();
	  		Thread.sleep(2000);
	  		OhioIdOTPTest.captureName(driver,"Verify UserLoginPage");
	  	//Navigate to Appstore
			driver.findElement(By.xpath("//*[@id='main-nav-container']/ul/li[6]/a")).click();
			
			driver.getTitle();
			Thread.sleep(2000);
			OhioIdOTPTest.captureName(driver,"App store");
			
			Thread.sleep(3000);
			
			//Logout
			
			driver.findElement(By.xpath("//*[@id='aw-main-wrapper']/section/div[3]")).click();
			WebElement target = driver.findElement(By.xpath("//span[text()='Log Out']"));
			WebElement logout = driver.findElement(By.xpath("//a[text()='Log Out']"));
			Actions s = new Actions(driver);
			s.moveToElement(target).click().perform();
			OhioIdOTPTest.captureName(driver,"Log Out");
			s.moveToElement(logout).click().perform();
			
			
			
			Thread.sleep(8000);
			
	  		driver.quit();
	  		}
	  		

}