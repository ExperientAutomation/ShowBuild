package Setup_Housing;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class A_EventAdminDetails extends TestSuiteBase {

	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	WinUtils utils = new WinUtils();
	WebDriverWait wait;
	Select sel;

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {

		if (!TestUtil.isTestCaseRunnable(suiteCxls, "EventAdminDetails")) {
			APP_LOGS.debug("Skipping Test Case" + "EventAdminDetails" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "EventAdminDetails" + " as runmode set to NO");// reports
		}
	}

	@Test
	public void A_LoginAndFocusShow() {
		Login login = new Login();
		
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

	@Test
	public void B_AddEventAdminDetails() throws Exception {
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//td[text()='Housing']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("imgHotelSetting")));

		// Contract

		driver.findElement(By.id("imgHotelSetting")).click();
		Thread.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("frmhotelsetting")));
		Thread.sleep(2000);
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[@title='Add new row']/div/span")));
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//td[@title='Add new row']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@title='Add new row']")));

		driver.findElement(By.xpath("//td[@title='Add new row']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jqg1_StartDate")));

		String startDate = suiteBxls.getCellData("ConfigureSchedules", 1, 2);
		// taking Inventory Dates
		
		driver.findElement(By.id("jqg1_StartDate")).sendKeys(startDate);
		Thread.sleep(10000);

		String endDate = suiteBxls.getCellData("ConfigureSchedules", 3, 2);
		driver.findElement(By.id("jqg1_EndDate")).sendKeys(endDate);

		Thread.sleep(10000);
		driver.findElement(By.xpath("//td[@title='Save row']/div/span")).click();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button/span[text()='Close']")).click();
//		driver.findElement(By.xpath("//button/span[text()='Close']")).click(); //RD 2 matching found for Close button. //button/span[Contains(text()='Close')]"
		

		// Taxes & Fees

		driver.findElement(By.id("imgTaxAuthority")).click();
		Thread.sleep(1000);

		driver.switchTo().frame(driver.findElement(By.id("frmtaxauthority")));
		Thread.sleep(1000);
//		driver.findElement(By.xpath("(//td[@title='Add new row']/div/span)[1]")).click();
		
		driver.findElement(By.xpath("(//td[@title='Add new row']/div)[1]")).click();

		String taxDistCode = suiteCxls.getCellData("EventAdminDetails", 6, 2);
		driver.findElement(By.id("jqg1_TaxAuthorityTypeCode")).sendKeys(taxDistCode);

		String taxDistName = suiteCxls.getCellData("EventAdminDetails", 7, 2);
		driver.findElement(By.id("jqg1_TaxAuthorityTypeName")).sendKeys(taxDistName);

		String taxDistDesc = suiteCxls.getCellData("EventAdminDetails", 8, 2);
		driver.findElement(By.id("jqg1_TaxAuthorityTypeDescr")).sendKeys(taxDistDesc);

		driver.findElement(By.xpath("(//td[@title='Save row']/div/span)[1]")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("(//td[@title='Add new row']/div/span)[2]")).click();
		Thread.sleep(2000);
		String taxStartDate = suiteCxls.getCellData("EventAdminDetails", 9, 2);
		driver.findElement(By.name("StartDate")).sendKeys(taxStartDate);
		Thread.sleep(2000);

		driver.findElement(By.name("CityTaxAmt")).clear();
		String CityTax = suiteCxls.getCellData("EventAdminDetails", 10, 2);
		driver.findElement(By.name("CityTaxAmt")).sendKeys(CityTax);

		driver.findElement(By.name("OccupancyFeeAmt")).clear();
		String OccupancyTax = suiteCxls.getCellData("EventAdminDetails", 11, 2);
		driver.findElement(By.name("OccupancyFeeAmt")).sendKeys(OccupancyTax);

		driver.findElement(By.xpath("(//td[@title='Save row']/div/span)[2]")).click();

		sel = new Select(driver.findElement(By.id("ddlTaxAuthority")));
		sel.selectByIndex(1);

		Thread.sleep(1000);

		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);

		driver.switchTo().defaultContent();
		
//		driver.findElement(By.xpath("//button/span[text()='Close']")).click();
		
//		for(WebElement we: driver.findElements(By.xpath("//button/span[text()='Close']"))) we.click();
		
		List<WebElement> list =  driver.findElements(By.xpath("//button/span[text()='Close']"));
		
		/*Iterator<WebElement> it = list.iterator();
		while (it.hasNext()){
			it.next().click();
		}*/
		
		
		try {
			list.get(0).click();
		} catch (Exception e) {
			list.get(1).click();
		}
		
		
		/*if(list.size()==1)	list.get(0).click();
		else				list.get(1).click();
		
*/
		// RoomList Self-Serve

		driver.findElement(By.id("ckRMLSelfServeEnabled")).click();

		String autoCloseDay = suiteCxls.getCellData("EventAdminDetails", 0, 2);
		driver.findElement(By.id("txtRMLSelfServeAutoCloseDay")).sendKeys(autoCloseDay);

		String pickupFirstDay = suiteCxls.getCellData("EventAdminDetails", 1, 2);
		driver.findElement(By.id("txtRMLSelfServePickupFirstDay")).sendKeys(pickupFirstDay);

		String pickupLastDay = suiteCxls.getCellData("EventAdminDetails", 2, 2);
		driver.findElement(By.id("txtRMLSelfServePickupLastDay")).sendKeys(pickupLastDay);

		sel = new Select(driver.findElement(By.id("ddlRMLSelfServeDayOfWeek")));
		sel.selectByVisibleText("NONE");

		// Collection

		sel = new Select(driver.findElement(By.id("BusinessclassbindabledropdownlistCommission")));
		sel.selectByValue("Percentage");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtCommission")));

		String collectionPer = suiteCxls.getCellData("EventAdminDetails", 3, 2);
		driver.findElement(By.id("txtCommission")).sendKeys(collectionPer);

		// Email

		String emailExh = suiteCxls.getCellData("EventAdminDetails", 4, 2);
		driver.findElement(By.id("TextBoxEmailExhibitor")).sendKeys(emailExh);

		String emailGrp = suiteCxls.getCellData("EventAdminDetails", 5, 2);
		driver.findElement(By.id("TextBoxEmailGroup")).sendKeys(emailGrp);

		String resortFee = suiteCxls.getCellData("EventAdminDetails", 12, 2);
		driver.findElement(By.id("txtResortfee")).sendKeys(resortFee);
		driver.findElement(By.id("RadioButtonResortFeePerStay")).click();

		// Block/SubBlock

		sel = new Select(driver.findElement(By.id("BusinessclassbindabledropdownlistDeposit")));
		sel.selectByIndex(2); 
//		.selectByVisibleText("CC Guarantee");

		driver.findElement(By.id("ckbWaitlisting")).click();
		driver.findElement(By.id("ckInvenShare")).click();

		driver.findElement(By.xpath("//span[@id='rbgBookingStatus']/label[text()='Open']")).click();
		driver.findElement(By.xpath("//span[@id='RadioButtonListConfirmation']/label[text()='Yes']")).click();
		driver.findElement(By.xpath("//span[@id='RadioButtonListwaitlistConfirmation']/label[text()='No']")).click();

		driver.findElement(By.id("ButtonSave")).click();

	}
}
