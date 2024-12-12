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
public class OhioIdOTPTest implements ITestListener{

	//Available in the API tab of server
	String apiKey = "2vbErWh8mZUCNSPh2Vo3IxVLAT9YyB0O";
	String apiSMSKey ="H4AfqEuyU4Hv8ooW2epQpETK02go06bF";
    String serverId = "khorpcz4";
    String serversmsId ="m3zluwmk";
    String serverDomain = "khorpcz4.mailosaur.net";
    String from = "DONOTREPLY-EnterpriseIdentity@ohio.gov";
    String smsFrom = "47534";
    static WebDriver driver;
    WebDriverWait wait;
    String FName ="Verona";
    String LName ="Liam";
    String emailId;
    String password;
    String userName;
    
   /* @SuppressWarnings("deprecation")
	@BeforeClass
	
	public void initialization() {
		WebDriver driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid"); 
		driver.manage().window().maximize();
	}
    
    @AfterClass
	public void tearDown()
	{
		driver.quit();
	}*/
    
	public String getRandomUserName()
	{
		String generatedName=RandomStringUtils.randomAlphabetic(1)+RandomStringUtils.randomNumeric(2);
		return FName+generatedName;
	}

    //generate random email ids
    
    public String getRandomEmail()
    {
    	 // create instance of Random class
        Random rand = new Random();
   
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
    	return FName +rand_int1 + "@" +serverDomain;
    	
    	
    }
    
    //generate random alphanumeric password
    public String randomAlphaNumberic()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		String generatednumber=RandomStringUtils.randomNumeric(3);
		return ("Univer"+generatedstring+"@%*"+generatednumber);
	}
  
    //generate screenshot names
        public static void captureName(WebDriver driver,String name) throws IOException
    {
    	File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Files.copy(f, new File("C:\\Users\\rbalasubramanians\\Documents\\IOP_HC_Screenshots\\HC"+name+System.currentTimeMillis()+".jpg"));
    }
   
   
   
	
	@SuppressWarnings("deprecation")
	@Test(priority=1)
	public void create7XAccount() throws IOException, MailosaurException, InterruptedException
	{
		
		String emailId = getRandomEmail();
		
		
		//open URL
		WebDriver driver = new FirefoxDriver();
		driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		OhioIdOTPTest.captureName(driver,"Homepage");
		
		
		//Filename as timestamp
		//Calendar cal = Calendar.getInstance();
		//Date time = cal.getTime();
		
		//String timestamp = time.toString().replace(":", "").replace(" ", "");
		
		
		
		//take screenshot
		
		//File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Files.copy(f, new File("C:\\Users\\rbalasubramanians\\Documents\\IOP_HC_Screenshots\\HC"+timestamp+".jpg"));
		
		
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
		OhioIdOTPTest.captureName(driver,"EmailVerification");
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
	    
		// Click Verify
		
		driver.findElement(By.xpath("//*[@id='verify-pin-email']")).click();
		
		Thread.sleep(3000);
		
		// Click Next
		driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
		
	    
	    // goes to next page to enter username
	   driver.getTitle();
	    
	    // Enters personal info
	   driver.findElement(By.xpath("//*[@id='user-firstname']")).sendKeys(FName);
	   driver.findElement(By.xpath("//*[@id='user-lastname']")).sendKeys(LName);
	   driver.findElement(By.xpath("//*[@id='user-dob']")).sendKeys("08031996");
	   OhioIdOTPTest.captureName(driver,"PersonalInfo");
	   Thread.sleep(3000);
	   JavascriptExecutor js1 = (JavascriptExecutor) driver;
	   js1.executeScript("window.scrollBy(0,350)", "");
	   driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
	    Thread.sleep(5000);
	    
	    //Enters username
	    String userName =getRandomUserName();
	    driver.findElement(By.xpath("//*[@id='username']")).sendKeys(userName);
	    System.out.println("Username entered:" +userName);
	    OhioIdOTPTest.captureName(driver,"Pick a username");
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[@id='create-account-next']")).click();
	    String password=randomAlphaNumberic();
	    System.out.println("Password entered:" +password);
	    
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
	    OhioIdOTPTest.captureName(driver,"Skip Account Recovery");
	    driver.findElement(By.xpath("//a[@id='recovery-option-checkbox']")).click();
	    
	    Thread.sleep(5000);
	    
	    //Terms & conditions 
	    
	    driver.findElement(By.xpath("//*[@id='tc-agreement-checkbox']")).click();
	    OhioIdOTPTest.captureName(driver,"Terms and Conditions");
	    JavascriptExecutor js4 = (JavascriptExecutor) driver;
	    js4.executeScript("window.scrollBy(0,350)", "");
	    Thread.sleep(3000);
	    //wait until captcha is answered
	  //Thread.sleep(8000);
	  
	 // WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(120));
	  
	   //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='account-creation-submit']")));
	   
	    OhioIdOTPTest.captureName(driver,"Captcha Question");
	    
	    
	    	//driver.findElement(By.xpath("//button[@id='account-creation-submit']")).click();
	    	
	    	//OhioIdOTPTest.captureName(driver,"Create Account Button Enabled");
	    	
	    	
	    
	   
	    
	    //Handling Captcha Q&A
	    //que 1
	    
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
	    
	    
	    
	    
	    
	    
	    
	    
	    
		
		// Last name textbox: //*[@id='user-lastname']
		//DOB textbox : //*[@id='user-dob']   mm/dd/yyyy
		// next button : //*[@id='create-account-next']
		
		//username textbox : //*[@id='username']
		// create a regex for username  must be 6 digits atleast
		
		// next button : //*[@id='create-account-next']
		
		//create password:
		
	//Password textbox: 	//*[@id='user-password']
		//Confirm password textbox: //*[@id='user-confirm-password']
		// next button : //*[@id='create-account-next']
		
		//skip ph no so click next again
	    // skip link : //a[@id='recovery-option-checkbox']
		// next button : //*[@id='create-account-next']
		//I agree checkbox = //*[@id='tc-agreement-checkbox'] // click 
		//enter captcha answer
		//click verify
		//click create account
		
	    	
		Thread.sleep(15000);
		if(driver.findElement(By.xpath("//h2[contains(text(),'Created')]")).isDisplayed())
		{
			System.out.println("Account not created, Try again");
			OhioIdOTPTest.captureName(driver,"Status of account creation");
		}
		else 
		{
			System.out.println("Account created, Try to Login again");
			OhioIdOTPTest.captureName(driver,"Status of account creation");
		}
		
		Thread.sleep(10000);
		driver.quit();
	}
		
		/*//Login HC
		@Test(priority=2,dependsOnMethods= {"create7XAccount"})
		public void checkLogin() throws InterruptedException,IOException{
			//WebDriver driver = new FirefoxDriver();
			//driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
			//driver.manage().window().maximize();
			//driver.manage().deleteAllCookies();
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//a[text()='Log in to OH|ID.']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//*[@id='loginUserID']")).sendKeys(userName);
		driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(password);
		
		OhioIdOTPTest.captureName(driver,"Login");
		driver.findElement(By.xpath("//*[@id='loginSubmit']")).click();	
		
		Thread.sleep(1000);
		
		driver.getTitle();
		Thread.sleep(2000);
		OhioIdOTPTest.captureName(driver,"Verify UserLoginPage");
		}
		
		//Navigate to Appstore
		@Test(priority=3,dependsOnMethods= {"checkLogin"})
		public void navigateToAppStore() throws InterruptedException, IOException {
			
		driver.findElement(By.xpath("//*[@id='main-nav-container']/ul/li[6]/a")).click();
		
		driver.getTitle();
		Thread.sleep(2000);
		OhioIdOTPTest.captureName(driver,"App store");
		
		Thread.sleep(3000);
		}
		
		//Logout
		@Test(priority=4,dependsOnMethods= {"navigateToAppStore"})
		public void checkAccountLogOut() throws IOException, InterruptedException
		{
		driver.findElement(By.xpath("//*[@id='aw-main-wrapper']/section/div[3]")).click();
		WebElement target = driver.findElement(By.xpath("//span[text()='Log Out']"));
		WebElement logout = driver.findElement(By.xpath("//a[text()='Log Out']"));
		Actions s = new Actions(driver);
		s.moveToElement(target).click().perform();
		OhioIdOTPTest.captureName(driver,"Log Out");
		s.moveToElement(logout).click().perform();
		
		
		
		Thread.sleep(8000);
		}
		
		//Forgot OHID Username
		@Test(priority=5,dependsOnMethods= {"checkAccountLogOut"})
		public void checkForgotUsernameOHID() throws InterruptedException, IOException {
		driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
		
		Thread.sleep(4000);
		OhioIdOTPTest.captureName(driver,"Forgot username or password");
		driver.findElement(By.linkText("Forgot your OHID or password?")).click();
		
		Thread.sleep(3000);
		
		driver.getTitle();
		OhioIdOTPTest.captureName(driver,"Account Recovery - Choose your option");
		Thread.sleep(3000);
		//Choose your option
		OhioIdOTPTest.captureName(driver,"Account Recovery - Forgot OHIO ID");
		driver.findElement(By.xpath("//*[@id='chabot-primary-options']/div[2]/button[1]")).click();
		Thread.sleep(3000);
		//enter your mail id 
		driver.findElement(By.xpath("//input[@id='custom-input-email']")).sendKeys(emailId);
		Thread.sleep(2000);
		OhioIdOTPTest.captureName(driver,"Enter your email id");
		driver.findElement(By.xpath("//*[@id='custom-input-email-container']//button[@type='submit']")).click();
		OhioIdOTPTest.captureName(driver,"Can we help with anything else");
		Thread.sleep(8000);
		}
		
		//Forgot Password
		
		@Test(priority=6, dependsOnMethods= {"checkForgotUsernameOHID"})
		public void checkForgotPassword() throws InterruptedException, IOException, MailosaurException
		{
		
		driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
		driver.manage().window().maximize();
		Thread.sleep(4000);
		OhioIdOTPTest.captureName(driver,"Forgot username or password");
		driver.findElement(By.linkText("Forgot your OHID or password?")).click();
        Thread.sleep(3000);
		
		driver.getTitle();
		OhioIdOTPTest.captureName(driver,"Account Recovery - Choose your option");
		Thread.sleep(3000);
		
		//Choose your option -Forgot Password
		OhioIdOTPTest.captureName(driver,"Forgot Password");
		driver.findElement(By.xpath("//*[@id='chabot-primary-options']/div[2]/button[2]")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@id='custom-input-username']")).sendKeys("NyraKen33");
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
		params2.withServer(serverId);

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
			   OhioIdOTPTest.captureName(driver,"Email OTP-Forgot password");
		driver.findElement(By.xpath("//*[@id='password-email-pin-first-try']")).sendKeys(otp2);
		System.out.println(otp2);
		OhioIdOTPTest.captureName(driver,"Forgot password -OTP Verification");
		
		driver.findElement(By.xpath("//*[@id='password-email-pin-first-attempt']/div[4]/button[1]")).click();
		
		//Enter New Password
		String changePassword =randomAlphaNumberic();
		driver.findElement(By.xpath("//*[@id='new-password']")).sendKeys(changePassword);
		
		
		//Confirm New Password
		driver.findElement(By.xpath("//*[@id='confirm-password']")).sendKeys(changePassword);
		System.out.println("Your New password is "+changePassword );
		OhioIdOTPTest.captureName(driver,"NewPassword Submission");
		driver.findElement(By.xpath("//*[@id='reset-password-input']/button")).click();
		
		Thread.sleep(4000);
		OhioIdOTPTest.captureName(driver,"Password successfully changed");
		driver.quit();
	}*/

	
	
	}
	
	
	
