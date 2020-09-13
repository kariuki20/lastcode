package seleniumConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CRMTestNG {

	WebDriver driver;
	String browser = null;
	String url = null;
	
	@BeforeTest
	public void readConfig() {
		
		Properties prop = new Properties();
		//BufferReader  //FileReader  //InputSteam  //Scanner
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
			System.out.println("Browser used: " + browser);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@BeforeMethod
	public void init() {
				
		if(browser.equalsIgnoreCase("Chrome")) {			
			// Setting the Property
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
			// Creating an object for chrome driver
			driver = new ChromeDriver();
			
		}else if(browser.equalsIgnoreCase("Firefox")){
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}
		
		
		// Navigating to the website
		driver.get(url);
		// Maximizing the window
		driver.manage().window().maximize();
		// Use Implicitly wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	
	//@Test(priority = 1)
	public void loginTest() throws InterruptedException {
		
		//Element Liberary
		By USER_NAME_LOCATOR = By.id("username");
		By PASSWORD_LOCATOR = By.id("password");
		By SIGNIN_BUTTON_LOCATOR = By.name("login");
		By DASHBOARD_BUTTON = By.xpath("//span[contains(text(), 'Dashboard')]");
		
		// Login Data
		String userName = "demo@techfios.com";
		String password = "abc123";

		// Identifying the web element and pass user name
		driver.findElement(USER_NAME_LOCATOR).sendKeys(userName);
		// Identifying the web element and pass password
		driver.findElement(PASSWORD_LOCATOR).sendKeys(password);
		// Identifying the web element and click on the sign in button
		driver.findElement(SIGNIN_BUTTON_LOCATOR).click();
		
		waitForElement(driver, 5, DASHBOARD_BUTTON);

		// stop the driver
		Thread.sleep(2000);
		String dashboardTitle = driver.findElement(DASHBOARD_BUTTON).getText();
		Assert.assertEquals(dashboardTitle, "Dashboard", "Wrong Page!!!");

	}
	
	@Test(priority=2)
	public void addCustomerTest() throws InterruptedException {

		// Element Library
		By USER_NAME_FIELD = By.id("username");
		By PASSWORD_FIELD = By.id("password");
		By SIGNIN_BUTTON = By.name("login");
		By MENU_BUTTON = By.xpath("//i[@class='fa fa-dedent']");
		By DASHBOARD_BUTTON = By.xpath("//span[contains(text(), 'Dashboard')]");
		By CUSTOMERS_BUTTON = By.xpath("//span[contains(text(), 'Customers')]");
		By ADD_CUSTOMER_BUTTON = By.xpath("//a[contains(text(), 'Add Customer')]");
		By ADD_CONTACT_LOCATOR = By.xpath("//h5[contains(text(),'Add Contact')]");
		By FULL_NAME_FIELD = By.xpath("//input[@id='account']");
		By COMPANY_NAME_FIELD = By.xpath("//input[@id='company']");
		By EMAIL_FIELD = By.xpath("//input[@id='email']");
		By PHONE_FIELD = By.xpath("//input[@id='phone']");
		By ADDRESS_FIELD = By.xpath("//input[@id='address']");
		By CITY_FIELD = By.xpath("//input[@id='city']");
		By STATE_REGION_FIELD = By.xpath("//input[@id='state']");
		By ZIP_FIELD = By.xpath("//input[@id='zip']");
		By SUBMIT_BUTTON = By.xpath("//button[@class='btn btn-primary']");
		By LIST_CONTACTS_BUTTON = By.xpath("//a[contains(text(),'List Contacts')]");
		

		// Login Data
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		//Test Data
		String fullName= "Test April";
		String companyName = "Techfios";
		String emailAddress = "test@gmail.com";
		String phoneNumber = "2316564564";
		
		//Perform Login In
		driver.findElement(USER_NAME_FIELD).sendKeys(loginId);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON).click();
		
		driver.findElement(MENU_BUTTON).click();
		waitForElement(driver, 3, DASHBOARD_BUTTON);
		
		String dashboardTitle = driver.findElement(DASHBOARD_BUTTON).getText();
		Assert.assertEquals(dashboardTitle, "Dashboard", "Wrong Page!!!");
		
		driver.findElement(CUSTOMERS_BUTTON).click();
		waitForElement(driver, 2, ADD_CUSTOMER_BUTTON);
		driver.findElement(ADD_CUSTOMER_BUTTON).click();
		
		Random rnd = new Random();
		int random = rnd.nextInt(999);
		
		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + random);
		driver.findElement(EMAIL_FIELD).sendKeys(random + emailAddress);
		
		Select sel = new Select(driver.findElement(By.xpath("//select[@id='country']")));
		sel.selectByVisibleText("Pakistan");
	}

	
	//@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	
	public void waitForElement(WebDriver driver, int timeInSeconds, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
				
	}

}