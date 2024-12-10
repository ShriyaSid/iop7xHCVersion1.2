package testBase;



import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.io.Files;

import org.apache.logging.log4j.LogManager;  //Log4j
import org.apache.logging.log4j.Logger;  //Log4j

public class TestBase {
	
	public static WebDriver driver;
	public Logger logger;  //Log4j
	
	String apiKey = "2vbErWh8mZUCNSPh2Vo3IxVLAT9YyB0O";
	String apiSMSKey ="H4AfqEuyU4Hv8ooW2epQpETK02go06bF";
    String serverId = "xiflbzm6";
    String serversmsId ="m3zluwmk";
    static String serverDomain = "xiflbzm6.mailosaur.net";
    String from = "DONOTREPLY-EnterpriseIdentity@ohio.gov";
    String smsFrom = "47534";
    
    WebDriverWait wait;
    static String FName ="Nancy";
    String LName ="Drew";
   public static final String emailId = getRandomEmail();
    String password = randomAlphaNumberic();
   public static final String userName = "Nand045";
   public static int rand_int1;
	
	@SuppressWarnings("deprecation")
	@BeforeClass
	public void setup() throws InterruptedException
	{
		logger=LogManager.getLogger(this.getClass());  //lOG4J2
		driver=new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://ohid.ohio.gov/wps/portal/gov/ohid/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}

	public static String getRandomUserName()
	{
		final String generatedName=RandomStringUtils.randomAlphabetic(1)+RandomStringUtils.randomNumeric(2);
		return FName+generatedName;
	}

    //generate random email ids
    
    public static String getRandomEmail()
    {
    	 // create instance of Random class
        //Random rand = new Random();
   
        // Generate random integers in range 0 to 999
        //rand_int1 = rand.nextInt(1000);
    	return FName +"77" + "@" +serverDomain;
    	
    	
    }
    
    //generate random alphanumeric password
    public String randomAlphaNumberic()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(6);
		String generatednumber=RandomStringUtils.randomNumeric(2);
		return ("IVySDUK"+generatedstring+"@%"+generatednumber);
	}
  
    //generate screenshot names
        public static void captureName(WebDriver driver,String name) throws IOException
    {
    	File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		Files.copy(f, new File("C:\\Users\\rbalasubramanians\\Documents\\IOP_HC_Screenshots\\HC"+name+System.currentTimeMillis()+".jpg"));
    }
   
   
   
}
