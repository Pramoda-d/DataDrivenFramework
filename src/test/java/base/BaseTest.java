package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utilities.ExcelReader;
import utilities.MonitoringMail;
import utilities.TestUtil;

public class BaseTest {

	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static WebDriverWait wait;
	public static MonitoringMail mail = new MonitoringMail();
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebElement element;
	public static ExtentReports extent = getExtentInstance();
	public static ExtentTest test;
	public static ExtentHtmlReporter htmlrep;

	@BeforeSuite
	public void setup() throws IOException {

		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");
		log.info("log4j properties file is loaded!!!");
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis); 
		log.info("OR properties file loaded!!!");
		fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
		config.load(fis);
		log.info("Config properties file loaded!!!");

		if (config.getProperty("browser").equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.info("chrome driver launched!!!");
		} else if (config.getProperty("browser").equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
			log.info("FirefoxDriver Launched!!!");
		}

		driver.get(config.getProperty("testsiteurl"));
		log.info("testsite URL" + config.getProperty("testsiteurl") + "launched!!!");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);

		wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("explicit.wait")));
	}
	
	public static ExtentReports getExtentInstance() {
		if (extent==null) {
			extent = new ExtentReports();
			htmlrep = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\extent.html"));
			extent.attachReporter(htmlrep);
			
			
			// htmlrep.config().setChartVisibilityOnOpen(true);
		        htmlrep.config().setDocumentTitle("Extent Report Demo");
		        htmlrep.config().setReportName("Test Report");
		    //    htmlrep.config().setTestViewChartLocation(ChartLocation.TOP);
		        htmlrep.config().setTheme(Theme.STANDARD);
		        htmlrep.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		}
		return extent;
	}
	public static void type(String key, String value) {
		try {
		if (key.endsWith("_xpath")) {
		driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);
		}
		else if(key.endsWith("_id")) {
			driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);
		}
		else if (key.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(key))).sendKeys(value);
		}
		
		}
        catch(Throwable t) {
			
			//capture screenshot
					}
		
		log.info("Typed in --"+key);
		test.log(Status.INFO,"typed in:"+ key);
	}

	public static void click(String key) {
		try {
			if (key.endsWith("_xpath")) {
				driver.findElement(By.xpath(OR.getProperty(key))).click();

			} else if (key.endsWith("_id")) {
				driver.findElement(By.id(OR.getProperty(key))).click();
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key))).click();
			}
		
		} catch (Throwable t) {

			// capture screenshot

		}
		log.info("clicked -- " + key);
		test.log(Status.INFO,"clicking on:"+ key);
	}
	public static void select(String key, String value) {
		try {
			if (key.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(OR.getProperty(key)));

			} else if (key.endsWith("_id")) {
				 element = driver.findElement(By.id(OR.getProperty(key)));
			} else if (key.endsWith("_CSS")) {
				 element = driver.findElement(By.cssSelector(OR.getProperty(key)));
			}
		Select select = new Select(element);
		select.selectByVisibleText(value);
		} catch (Throwable t) {

			// capture screenshot

		}
		log.info("selecting the element"+ value+ "in dropdown -- " + key);
		test.log(Status.INFO, "selecting the element"+ value+ "in dropdown -- " + key);
	}
	public static void checkRunMode(Hashtable<String,String> data) {
		if(!data.get("runmode").equals("Y") ) {
			throw new SkipException("Not running the test as the runmode is NO");
		}
	}
	public static boolean isElementPresent(String key) {
		
		try {
			if (key.endsWith("_xpath")) {
				driver.findElement(By.xpath(OR.getProperty(key)));
			}
			else if (key.endsWith("_id")) {
				driver.findElement(By.id(OR.getProperty(key)));
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key)));
			}
			return true;
		}
		catch(Throwable t) {
			//capture screenshot
			return false;
		}
		
		}
	
	public static void verifyEquals(String expected,String actual) throws IOException {
		
		try {
			Assert.assertEquals(actual, expected);
		}
		catch(Throwable t) {
			TestUtil.captureScreenshot();
			 Reporter.log("<br>"+"verification failure"+ t.getMessage()+ "<br>");
			  Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			  Reporter.log("<br>");
			  test.log(Status.FAIL, "Verification failure:"+ t.getMessage());
			  test.fail("failed screen:"+ test.addScreenCaptureFromPath(TestUtil.screenshotName));
		
			}
		
	}

	@AfterSuite
	public void tearDown() {
	
		extent.flush();
		driver.quit();
		log.info("Test Execution completed!!!");
	}
}
