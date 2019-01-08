package RegOnly_Show;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class A_ConfigureSchedules extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	Login login = new Login();
	WebDriverWait wait;
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigureSchedules mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureSchedules")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigureSchedules" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureSchedules" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureSchedules");
	}
	
	@Test()
	public void A_Schedules_LoginAndFocusProd(){
		System.out.println("In ConfigureSchedules mtd 2 ...........................");
		login.testcase_Login("Prod");
		driver = BrowserFactory.getBrowser("Chrome");

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		wait= new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'Show Admin')]")));
		WebElement showAdmin=driver.findElement(By.xpath("//td[contains(text(),'Show Admin')]"));
		
		Assert.assertTrue(showAdmin.isDisplayed(), "Failed to focus Showcode");
		
	}

	@Test()
	public void B_Schedules() throws Exception {
		System.out.println("In ConfigureSchedules mtd 3 ...........................");
		driver.findElement(By.xpath("//td[contains(@igtag,'ShowAdmin')]")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[contains(text(),'Schedules')]")).click();

		while (WinUtils.isElementPresent(driver, By.xpath("//input[@value='Delete']"))) {
			driver.findElement(By.xpath("//input[@value='Delete']")).click();
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Thread.sleep(100);
		}
		System.out.println("Deleted all the default schedules entries");

	}

	@Test(dataProvider = "ConfigureSchedules")
	public void C_AddSchedules(String Schedule, String StartDate, String StartTime, String EndDate,
			String EndTime) throws Exception {
		System.out.println("In ConfigureSchedules mtd 4 ...........................");
		// test the runmode of current dataset
		setAddSchedules(Schedule, StartDate, StartTime, EndDate,EndTime);
		
	}
	
	public void setAddSchedules(String Schedule, String StartDate, String StartTime, String EndDate,String EndTime)throws Exception{
		
		APP_LOGS.debug(" Executing AddSchedules");
		APP_LOGS.debug(Schedule + " -- " + StartDate + " -- " + StartTime + " -- " + EndDate + " -- " + EndTime);

		String firstWindow = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@id='ButtonAddNew']")).click();
		Thread.sleep(100);

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}

		Select dropdownStype = new Select(driver.findElement(By.xpath("//*[@id='BusinessClassBindableDropDownListScheduleType']")));
		dropdownStype.selectByVisibleText(Schedule);
		driver.findElement(By.xpath("//*[@id='TextBoxBeginDateOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxBeginDateOnly']")).sendKeys(StartDate);
		driver.findElement(By.xpath("//*[@id='TextBoxBeginTimeOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxBeginTimeOnly']")).sendKeys(StartTime);
		driver.findElement(By.xpath("//*[@id='TextBoxEndDateOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxEndDateOnly']")).sendKeys(EndDate);
		driver.findElement(By.xpath("//*[@id='TextBoxEndTimeOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxEndTimeOnly']")).sendKeys(EndTime);
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();
		driver.switchTo().window(firstWindow);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Test()
	public void D_AddOnsiteRegistration() throws Exception {
		System.out.println("In ConfigureSchedules mtd 5 ...........................");
		String firstWindow = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@id='ButtonAddNew']")).click();
		Thread.sleep(100);

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm a");
		Calendar calender = Calendar.getInstance();
		Date date = calender.getTime();
		String currectDate = dateFormat.format(date);
		String currentTime = timeFormat.format(date);

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}

		Select dropdownStype = new Select(
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableDropDownListScheduleType']")));
		dropdownStype.selectByVisibleText("Onsite Registration Start/End Dates");
		driver.findElement(By.xpath("//*[@id='TextBoxBeginDateOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxBeginDateOnly']")).sendKeys(currectDate);
		driver.findElement(By.xpath("//*[@id='TextBoxBeginTimeOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxBeginTimeOnly']")).sendKeys(currentTime);
		driver.findElement(By.xpath("//*[@id='TextBoxEndDateOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxEndDateOnly']")).sendKeys("12/23/2018");
		driver.findElement(By.xpath("//*[@id='TextBoxEndTimeOnly']")).clear();
		driver.findElement(By.xpath("//*[@id='TextBoxEndTimeOnly']")).sendKeys("12:00 AM");
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();

		driver.switchTo().window(firstWindow);
		
	}
	
	@Test(enabled = true)
	public void E_LoginAndFocusStage(){
		System.out.println("In ConfigureSchedules mtd 6 ...........................");
		login.testcase_Login("Stage");
		
		String keyURL = CONFIG.getProperty("environment")+"Stage";
		driver.get(CONFIG.getProperty(keyURL));
		
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		wait= new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'Show Admin')]")));
		WebElement showAdmin=driver.findElement(By.xpath("//td[contains(text(),'Show Admin')]"));
		
		Assert.assertTrue(showAdmin.isDisplayed(), "Failed to focus Showcode");
		
	}
	
	@Test(enabled = true)
	public void F_SchedulesStage() throws Exception{
		System.out.println("In ConfigureSchedules mtd 7 ...........................");
		B_Schedules();
	}
	
	@Test(dataProvider = "ConfigureSchedules")
	public void G_AddSchedulesStage(String Schedule, String StartDate, String StartTime, String EndDate, String EndTime) throws Exception{
		System.out.println("In ConfigureSchedules mtd 8 ...........................");
		setAddSchedules(Schedule, StartDate, StartTime, EndDate, EndTime);
	}
	
	@Test()
	public void H_AddOnsiteRegistrationStage() throws Exception {
		System.out.println("In ConfigureSchedules mtd 9 ...........................");
		D_AddOnsiteRegistration();
	}

	@DataProvider
	public Object[][] ConfigureSchedules() {
		
		return TestUtil.getData(suiteBxls, "ConfigureSchedules");
	}

}
