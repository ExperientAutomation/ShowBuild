package RegOnly_Show_1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

public class K_ConfigurePackages extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	WebDriverWait wait;
	String firstWindow;
	String secWindow;
	WinUtils utils = new WinUtils();
	Select sel;
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigurePackages mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigurePackages")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigurePackages" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigurePackages" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigurePackages");
	}
	
	@Test()
	public void A_LoginAndFocus() throws Exception {
		System.out.println("In ConfigurePackages mtd 2 ...........................");
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
		driver.findElement(By.xpath("//div[contains(text(),'Show Items')]")).click();
		driver.findElement(By.xpath("//a[text()='PACKAGES']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='PACKAGES']")).isDisplayed(),"Package was not loaded");
	}
	

	@Test()
	public void B_Add_Packages () throws Exception {
		System.out.println("In ConfigurePackages mtd 3 ...........................");
		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing ConfigurePackages_UAT-Stage");
		
		driver.findElement(By.id("ButtonAdd")).click();
		firstWindow = driver.getWindowHandle();
		
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		String description = suiteBxls.getCellData("ConfigurePackages", 0, 2);
		driver.findElement(By.id("BusinessClassBindableTextBoxDescription")).sendKeys(description);
		
		String packageCode = suiteBxls.getCellData("ConfigurePackages", 1, 2);
		driver.findElement(By.id("BusinessclassbindabletextboxShowItemCode")).sendKeys(packageCode);
		
		sel = new Select(driver.findElement(By.id("BusinessClassBindableDropDownListShowItemTypeCode")));
		sel.selectByValue("PKG");
		
		String limitQty = suiteBxls.getCellData("ConfigurePackages", 2, 2);
		driver.findElement(By.id("BusinessclassbindabletextboxLimitQty")).clear();
		driver.findElement(By.id("BusinessclassbindabletextboxLimitQty")).sendKeys(limitQty);
		
		driver.findElement(By.id("CheckboxIsTicketed")).click();
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		
		driver.findElement(By.id("ButtonEdit")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//td[contains(text(),'Qualifiers')]")).click();
		Thread.sleep(1000);
		driver.switchTo().frame(driver.findElement(By.id("UltraWebTabMain_frame2")));
       
		sel = new Select(driver.findElement(By.id("DropDownListTracks")));
		sel.selectByValue("1000");
		
		String qQty = suiteBxls.getCellData("ConfigurePackages", 5, 2);
		driver.findElement(By.id("TextBoxMinQuantity")).clear();
		driver.findElement(By.id("TextBoxMinQuantity")).sendKeys(qQty);
		
		driver.findElement(By.id("ButtonAddTrack")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@value='Remove']"))));		
		driver.findElement(By.id("CheckBoxAutoProcessPackage")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button/span[text()='Ok']"))));
		driver.findElement(By.xpath("//button/span[text()='Ok']")).click();
		
		driver.findElement(By.id("ButtonSave")).click();
		Thread.sleep(1000);
		
		driver.switchTo().defaultContent();
		
		driver.findElement(By.xpath("//td[contains(text(),'Discount Items')]")).click();
		Thread.sleep(2000);
		driver.switchTo().frame(driver.findElement(By.id("UltraWebTabMain_frame5")));
		
		secWindow = driver.getWindowHandle();
		driver.findElement(By.id("ButtonAddTrack")).click();
		
		utils.switchIfWindowsAre(driver, 3);
		driver.manage().window().maximize();
		
		String discountType = suiteBxls.getCellData("ConfigurePackages", 6, 2);
		sel = new Select(driver.findElement(By.id("DropDownListDiscountType")));
		sel.selectByVisibleText(discountType.trim());
		
		String discountAmt = suiteBxls.getCellData("ConfigurePackages", 7, 2);
		driver.findElement(By.id("TextBoxDiscountAmount")).sendKeys(discountAmt.trim());
		
		String track = suiteBxls.getCellData("ConfigurePackages", 8, 2);
		sel = new Select(driver.findElement(By.id("DropDownListTracks")));
		sel.selectByVisibleText(track.trim());
		
		
		String dateStatus = suiteBxls.getCellData("ConfigurePackages", 9, 2);
		sel = new Select(driver.findElement(By.id("DropDownListDateStatus")));
		sel.selectByVisibleText(dateStatus.trim());
		
		String reqQty = suiteBxls.getCellData("ConfigurePackages", 10, 2);
		driver.findElement(By.id("TextBoxMinQuantity")).sendKeys(reqQty);
		
		String discountLimit = suiteBxls.getCellData("ConfigurePackages", 11, 2);
		driver.findElement(By.id("TextBoxMaxQuantity")).sendKeys(discountLimit);
		
		driver.findElement(By.id("CheckBoxDiscountAll")).click();
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(secWindow);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		
		// ADD FEES
		
		driver.findElement(By.xpath("//td/a[text()='ESPPACK']/ancestor::td/a[3]/img")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("sa_Registration0")).click();
		driver.findElement(By.id("Membership0_0")).click();
		driver.findElement(By.id("sa_Date0")).click();
		driver.findElement(By.id("Amount0")).sendKeys("0");
		
		driver.findElement(By.id("sa_Registration1")).click();
		driver.findElement(By.id("sa_Date1")).click();
		driver.findElement(By.id("Amount1")).sendKeys("0");
		
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		Thread.sleep(1000);
		
	}
}
