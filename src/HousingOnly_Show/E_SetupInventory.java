package HousingOnly_Show;

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
import Setup_Housing_2.TestSuiteBase;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class E_SetupInventory extends TestSuiteBase {

	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	WinUtils utils = new WinUtils();
	WebDriverWait wait;
	Select sel;
	String firstWindow;

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {

		if (!TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName())) {
			APP_LOGS.debug("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
	}

	@Test
	public void A_LoginAndFocusShow() {
		Login login = new Login();
		
//		login.testcase_Login("Prod");
		login.testcase_Login("Stage");
		
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Show Admin']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Show Admin']")).isDisplayed(), "Could not login");
	}

	@Test
	public void B_inventory_Setup() {

		driver.findElement(By.xpath("//td[text()='Housing']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("imgHotelSetting")));

		driver.findElement(By.xpath("//div[contains(text(),'Inventory Management')]")).click();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("SelectHotelsDataGrid")));

		int noOfHotels = suiteCxls.getRowCount("AddHotelSetup") - 1;

		for (int i = 1; i <= noOfHotels; i++) {

			driver.findElement(By.xpath("(//*[@id='SelectHotelsDataGrid']//input[@value='Load Inventory'])["+i+"]")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("SaveChanges")));
			int noOfDays = driver.findElements(By.xpath("//td[text()='Actual Hotel Totals']/following-sibling::td")).size();

			for (int x = 9; x <= 11; x++) {

				for (int y = 2; y <= noOfDays; y++) {
					
					driver.findElement(By.xpath("//*[@id='PlaceHolder']/table/tbody/tr[" + x + "]/td["+y+"]/table/tbody/tr[1]/td/input")).clear();
					driver.findElement(By.xpath("//*[@id='PlaceHolder']/table/tbody/tr[" + x + "]/td["+y+"]/table/tbody/tr[1]/td/input")).sendKeys("50");
				}
			}

			for (int x = 15; x <= 17; x++) {

				for (int y = 2; y <= noOfDays; y++) {

					driver.findElement(By.xpath("//*[@id='PlaceHolder']/table/tbody/tr[" + x + "]/td["+y+"]/table/tbody/tr[1]/td/input")).clear();
					driver.findElement(By.xpath("//*[@id='PlaceHolder']/table/tbody/tr[" + x + "]/td["+y+"]/table/tbody/tr[1]/td/input")).sendKeys("50");
				}
			}
			
			driver.findElement(By.id("SaveChanges")).click();
			driver.findElement(By.xpath("//div[contains(text(),'Inventory Management')]")).click();
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("SelectHotelsDataGrid")));
		}

	}
	
}
