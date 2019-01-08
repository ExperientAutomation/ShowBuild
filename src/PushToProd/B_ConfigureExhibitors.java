package PushToProd;

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

public class B_ConfigureExhibitors extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	
	WebDriverWait wait;
	String firstWindow;
	String secWindow;
	WinUtils utils = new WinUtils();
	Select sel;
	
	Login login = new Login();
	
	// Run mode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigureExhibitors mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureExhibitors")) { // Class Name and sheet name are same
			APP_LOGS.debug("Skipping Test Case" + "ConfigureExhibitors" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureExhibitors" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureExhibitors");
	}

	
	@Test(enabled = true)
	public void A_LoginAndFocus() throws Exception {
		System.out.println("In ConfigureExhibitors mtd 2 ...........................");
		Login login = new Login();
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 60);
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@class='inputShowCode']"))));
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//td[text()='Show Admin']"))));
		driver.findElement(By.xpath("//td[text()='Show Admin']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Exhibitors')]")).click();
		
		Assert.assertTrue(driver.findElement(By.id("TextBoxExhibitorName")).isDisplayed(),"Exhibitor was not loaded");
	}
	

	@Test(enabled =true)
	public void B_Add_Exhibitor () throws Exception {
		System.out.println("In ConfigureExhibitors mtd 3 ...........................");
		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		
		count = -1; // next execution Count value should reset.
		
		APP_LOGS.debug(" Executing Configure Exhibitor_UAT-Stage");
		
		driver.findElement(By.id("ButtonAdd")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableTextBoxExhibitorName"))));
		
		String EName = suiteBxls.getCellData("ConfigureExhibitors", 0, 2);
		driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableTextBoxExhibitorName")).sendKeys(EName);
		driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableTextBoxBoothName")).sendKeys(EName);
		
		String EPsw = suiteBxls.getCellData("ConfigureExhibitors", 1, 2);
		driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableTextBoxExhibitorCode")).sendKeys(EPsw);
		
		sel = new Select(driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableDropDownAttendeeType")));
		sel.selectByVisibleText("Exhibitor Freesale");
		
		driver.switchTo().alert().accept();
		
		String EAddress = suiteBxls.getCellData("ConfigureExhibitors", 2, 2);
		driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableTextBoxAddress1")).sendKeys(EAddress);
		
		String EZip = suiteBxls.getCellData("ConfigureExhibitors", 3, 2);
		driver.findElement(By.id("AccordionPaneDetail_content_BusinessClassBindableTextBoxZip")).sendKeys(EZip);
		
		String EEmail = suiteBxls.getCellData("ConfigureExhibitors", 4, 2);
		driver.findElement(By.id("AccordionPaneDetail_content_BusinessclassbindabletextboxEmail")).sendKeys(EEmail);
		
		Thread.sleep(1000);
		driver.findElement(By.id("AccordionPaneDetail_content_ButtonSave")).click();
		Thread.sleep(1000);
		
		// Booth Allotments
		
		driver.findElement(By.id("AccordionPaneBoothAllotments_header")).click();
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("AccordionPaneBoothAllotments_content_ButtonAddAllotment")));
		
		firstWindow = driver.getWindowHandle();
		driver.findElement(By.id("AccordionPaneBoothAllotments_content_ButtonAddAllotment")).click();
		
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		String EDes = suiteBxls.getCellData("ConfigureExhibitors", 5, 2);
		driver.findElement(By.id("BusinessClassBindableTextBoxDescription")).sendKeys(EDes);
		
		String EBoothLimit = suiteBxls.getCellData("ConfigureExhibitors", 6, 2);
		driver.findElement(By.id("BusinessClassBindableTextBoxLimit")).sendKeys(EBoothLimit);
		
		driver.findElement(By.xpath("//td/label[text()='E']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonAddStatus")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ListboxStatuses']/option[text()='E']")));
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		
		driver.findElement(By.id("ButtonExhibitorSearch")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ButtonSearch")));
		driver.findElement(By.id("TextBoxExhibitorName")).sendKeys(EName);
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonSearch")).click();
		Thread.sleep(2000);
		String EFound = driver.findElement(By.id("DataGridExhibitors_LinkButtonCompany_0")).getText();
		
		Assert.assertTrue(EFound.equals(EName), "Count not add the Exhibitor");
	}
	
	@Test(enabled =true)
	public void C_LoginAndFocus() throws Exception {
		System.out.println("In ConfigureExhibitors mtd 3 ...........................");
		login.testcase_Login("Prod");
//		driver = BrowserFactory.getBrowser("Chrome");
//		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//		wait = new WebDriverWait(driver, 60);
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@class='inputShowCode']"))));
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//td[text()='Show Admin']"))));
		driver.findElement(By.xpath("//td[text()='Show Admin']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Exhibitors')]")).click();
		
		Assert.assertTrue(driver.findElement(By.id("TextBoxExhibitorName")).isDisplayed(),"Exhibitor was not loaded");
	}
	
	@Test(enabled =true)
	public void D_Add_Exhibitor () throws Exception {
		System.out.println("In ConfigureExhibitors mtd 4 ...........................");
		B_Add_Exhibitor();
		
	}
	
	@AfterSuite
	public void tearDown(){
		
		driver.quit();
	}
}
