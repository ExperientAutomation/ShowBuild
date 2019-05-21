package RegOnly_Show_1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class J_ConfigureTracks extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	WebDriverWait wait;
	String firstWindow;
	WinUtils utils = new WinUtils();
	Select sel;
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigureTracks mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureTracks")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigureTracks" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureTracks" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureTracks");
	}

	
	@Test()
	public void A_LoginAndFocus() throws Exception {
		System.out.println("In ConfigureTracks mtd 2 ...........................");
		Login login = new Login();
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[text()='Show Admin']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Tracks')]")).click();
		Assert.assertTrue(driver.findElement(By.id("ButtonAddNew")).isDisplayed(),"Tracks page was not loaded");
	}
	

	@Test(dataProvider="ConfigureTracks")
	public void B_Add_Tracks (String Description, String Code, String SelShowItems) throws Exception {
		System.out.println("In ConfigureTracks mtd 3 ...........................");
		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing AddSchedules_UATProd");
				
		driver.findElement(By.id("ButtonAddNew")).click();
		firstWindow = driver.getWindowHandle();
		
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("BusinessClassBindableTextBoxDescription")).sendKeys(Description);
		driver.findElement(By.id("BusinessClassBindableTextBoxCode")).sendKeys(Code);
		
		if (Code.trim().contains("ESP")){
			sel = new Select(driver.findElement(By.id("ListBoxShowItemsAvailable")));
			sel.selectByVisibleText("ESP1");
			sel.selectByVisibleText("ESP2");
			sel.selectByVisibleText("ESP3");
			sel.selectByVisibleText("ESP4");
		}
		
		if (Code.trim().contains("CAD")){
			sel = new Select(driver.findElement(By.id("ListBoxShowItemsAvailable")));
			sel.selectByVisibleText("A1");
			sel.selectByVisibleText("A2");
			sel.selectByVisibleText("A3");
			sel.selectByVisibleText("A4");
			sel.selectByVisibleText("A5");
			sel.selectByVisibleText("B");
			sel.selectByVisibleText("C");
			sel.selectByVisibleText("D");
		}
		
		if (Code.trim().contains("DWW")){
			sel = new Select(driver.findElement(By.id("ListBoxShowItemsAvailable")));
			sel.selectByVisibleText("DWW");
		}
		
		driver.findElement(By.id("ImageButtonAddShowItem")).click();
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		driver.switchTo().window(firstWindow);
	}
	
	@DataProvider
	public Object[][] ConfigureTracks(){
		return TestUtil.getData(suiteBxls, "ConfigureTracks");
	}

}
