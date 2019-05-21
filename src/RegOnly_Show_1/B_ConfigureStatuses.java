package RegOnly_Show_1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Create_Show.Login;
import Setup_Housing_2.TestSuiteBase;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class B_ConfigureStatuses extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	Login login = new Login();
	String firstWindow;
	String secondWindow;
	static int count = -1;
	WinUtils utils = new WinUtils();
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigureStatuses mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureStatuses")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigureStatuses" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureStatuses" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureStatuses");
	}

	@Test()
	public void A_LoginAndFocusShow() {
		System.out.println("In ConfigureStatuses mtd 2 ...........................");
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
	}

	@Test()
	public void B_Status() {
		System.out.println("In ConfigureStatuses mtd 3 ...........................");
		driver.findElement(By.xpath("//td[contains(text(),'Configuration')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Statuses')]")).click();
	}

	@Test()
	public void C_ConfigureDateStatuses() throws Exception {
		System.out.println("In ConfigureStatuses mtd 4 ...........................");
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing ConfigureDateStatuses");
		
		
		firstWindow = driver.getWindowHandle();
		driver.findElement(By.xpath("//td[contains(text(),'Date Status Items')]/following-sibling::td//input[@value='Status']")).click();
		
		Thread.sleep(2000);
		
		utils.switchIfWindowsAre(driver, 2);
		secondWindow = driver.getWindowHandle();
	}

	@Test(dataProvider = "ConfigureStatuses")
	public void D_DateStatus(String Code, String Description, String StartDate, String StartTime, String EndDate, String EndTime) throws Exception {
		System.out.println("In ConfigureStatuses mtd 5 ...........................");
		APP_LOGS.debug(Code + " -- " + StartDate + " -- " + StartTime + " -- " + EndDate + " -- " + EndTime);
		
		driver.findElement(By.xpath("//input[@id='ButtonAdd']")).click();

		utils.switchIfWindowsAre(driver, 3);
		
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxName']")).sendKeys(Code);
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxDescription']")).sendKeys(Description);
		driver.findElement(By.xpath("//*[@id='TextBoxBeginDateOnly']")).sendKeys(StartDate);
		driver.findElement(By.xpath("//*[@id='TextBoxBeginTimeOnly']")).sendKeys(StartTime);
		driver.findElement(By.xpath("//*[@id='TextBoxEndDateOnly']")).sendKeys(EndDate);
		driver.findElement(By.xpath("//*[@id='TextBoxEndTimeOnly']")).sendKeys(EndTime);
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();
		driver.switchTo().window(secondWindow);
	}

	@Test()
	public void E_ConfigureMembershipStatuses() throws Exception {
		System.out.println("In ConfigureStatuses mtd 6 ...........................");
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing ConfigureMembershipStatuses");
		
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();
		
		driver.switchTo().window(firstWindow);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//td[contains(text(),'Membership Status Items')]/following-sibling::td//input[@value='Status']")).click();
		
		utils.switchIfWindowsAre(driver, 2);
		secondWindow = driver.getWindowHandle();
	}
	
	@Test(dataProvider = "MembershipStatuses")
	public void F_MembershipStatus(String Code, String Description) throws Exception{
		System.out.println("In ConfigureStatuses mtd 7 ...........................");
		APP_LOGS.debug(Code + " -- " + Description);
		
		driver.findElement(By.xpath("//input[@id='ButtonAdd']")).click();

		utils.switchIfWindowsAre(driver, 3);
		
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxName']")).sendKeys(Code);
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxDescription']")).sendKeys(Description);
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();
		driver.switchTo().window(secondWindow);
	}

	@Test()
	public void G_ConfigureRegistrationStatuses() throws Exception {
		System.out.println("In ConfigureStatuses mtd 8 ...........................");
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing ConfigureRegistrationStatuses");
		
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();
		
		driver.switchTo().window(firstWindow);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//td[contains(text(),'Registration Status Items')]/following-sibling::td//input[@value='Status']")).click();
	
		utils.switchIfWindowsAre(driver, 2);
		secondWindow = driver.getWindowHandle();
	}
	
	@Test(dataProvider = "RegistrationStatuses")
	public void H_RegistrationStatus(String Code1, String Description1) throws Exception{
		System.out.println("In ConfigureStatuses mtd 9 ...........................");
		APP_LOGS.debug(Code1 + " -- " + Description1);
		driver.findElement(By.xpath("//input[@id='ButtonAdd']")).click();
	
		utils.switchIfWindowsAre(driver, 3);
		
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxName']")).sendKeys(Code1);
		driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxDescription']")).sendKeys(Description1);
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();
		driver.switchTo().window(secondWindow);	
	}
	
	@Test()
	public void I_TearDown(){
		System.out.println("In ConfigureStatuses mtd 10 ...........................");
		
		driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonClose']")).click();	
		driver.switchTo().window(firstWindow);
		driver.switchTo().defaultContent();
	}
	

	@DataProvider
	public Object[][] ConfigureStatuses() {
		
		return TestUtil.getData(suiteBxls, "ConfigureStatuses");
	}

	@DataProvider
	public Object[][] MembershipStatuses() {
		
		return TestUtil.getData(suiteBxls, "MembershipStatuses");
	}

	@DataProvider
	public Object[][] RegistrationStatuses() {
		
		return TestUtil.getData(suiteBxls, "RegistrationStatuses");
	}
}
