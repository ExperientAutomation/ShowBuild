package Setup_Housing_2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class D_AddEventFacilities extends TestSuiteBase{

	String runmodes[] = null;
	WebDriver driver;
	Login login = new Login();
	static int count = -1;
	WinUtils utils = new WinUtils();
	WebDriverWait wait;
	Select sel;
	String firstWindow;

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {

		if (!TestUtil.isTestCaseRunnable(suiteCxls, "AddEventFacilities")) {
			APP_LOGS.debug("Skipping Test Case" + "AddEventFacilities" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "AddEventFacilities" + " as runmode set to NO");// reports
		}
	}

	@Test(enabled = true)
	public void A_LoginAndFocusShow() {
		

		login.testcase_Login("Stage");
//		login.testcase_Login("Prod");
		
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Show Admin']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Show Admin']")).isDisplayed(), "Could not login");
	}
	@Test(enabled = true)
	public void B_Add_Event_Facilities() throws Exception{

		driver.findElement(By.xpath("//div[contains(text(),'Event Facilities')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("LookUpButtonFacility")));		
		
		driver.findElement(By.id("LookUpButtonFacility")).click();
		firstWindow = driver.getWindowHandle();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		String hotelName = suiteCxls.getCellData("AddHotelSetup", 0, 2);
		driver.findElement(By.id("TextBoxFacilityName")).sendKeys(hotelName);
		driver.findElement(By.id("ButtonSearch")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='DataGridFacilities']//a[text()='"+hotelName+"']")));
		
		driver.findElement(By.xpath("(//*[@id='DataGridFacilities']//a[text()='"+hotelName+"'])[1]")).click();
		driver.switchTo().window(firstWindow);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ButtonAdd")));
		
		driver.findElement(By.id("ButtonAdd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='DataGridFacility']//td[text()='"+hotelName+"']")));
		
		driver.findElement(By.id("LookUpButtonFacility")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("TextBoxFacilityName")).sendKeys("Anaheim Convention Center");
		driver.findElement(By.id("ButtonSearch")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='DataGridFacilities']//a[text()='Anaheim Convention Center']")));
		
		driver.findElement(By.xpath("(//*[@id='DataGridFacilities']//a[text()='Anaheim Convention Center'])[1]")).click();
		driver.switchTo().window(firstWindow);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ButtonAdd")));
		
		driver.findElement(By.id("ButtonAdd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='DataGridFacility']//td[text()='Anaheim Convention Center']")));
		
		driver.findElement(By.xpath("(//td[text()='Anaheim Convention Center']/ancestor::tr/td/input)[1]")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("(//td[text()='Anaheim Convention Center']/ancestor::tr/td/input[@value='Yes'])[1]")).isDisplayed(),"Could not set Primary Facility");

	}
	
	@Test()
	public void C_LoginToProdAndFocusShow() {
		
		login.testcase_Login("Prod");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Show Admin']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Show Admin']")).isDisplayed(), "Could not login");
	}
	
	@Test()
	public void D_AddEventFacilitiesToProd() throws Exception{
		
		B_Add_Event_Facilities();
		
	}
	
	@AfterSuite
	public void tearDown(){
		
		driver.quit();
	}
	
}
