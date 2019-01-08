package Create_Show;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class Create_ShowCode extends TestSuiteBase {
	String runmodes[] = null;
	static int count = -1;
	WebDriverWait wait;
	WebDriver driver;
	WinUtils utils = new WinUtils();
	//static Logger logger = Logger.getLogger(Create_ShowCode.class);

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {

		//logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<I AM LOGGING>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<I AM LOGGING DEBUG>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (!TestUtil.isTestCaseRunnable(suiteAxls, this.getClass().getSimpleName())) {
			APP_LOGS.debug("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteAxls, this.getClass().getSimpleName());

	}

	@Test()
	public void A_testcase_CreateShowCode() throws Exception {
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing testcase_CreateShowCode");

		// Login to Show Manager
		Login login = new Login();
		login.testcase_Login("Prod");
		driver = BrowserFactory.getBrowser("Chrome");

		// Get the showcode from Config property file
		String oShowCode = CONFIG.getProperty("existingshowcode");
		driver.findElement(By.xpath("//*[@id='TextBoxShowCode']")).sendKeys(oShowCode);
		driver.findElement(By.xpath("//*[@id='ButtonSearch']")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		List<WebElement> DataGridItem = driver.findElements(By.xpath("//*[@class='DataGridItem']"));

		if (DataGridItem.size() != 0) {

			System.out.println("Existing Show Code appeared as expected");

			// Get the new showcode from the Config Property file
			String nShowCode = CONFIG.getProperty("showcode");

			driver.findElement(By.id("TextBoxShowCode")).clear();
			driver.findElement(By.id("TextBoxShowCode")).sendKeys(nShowCode);
			driver.findElement(By.id("ButtonSearch")).click();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			List<WebElement> DataGridItem1 = driver.findElements(By.xpath("//*[@class='DataGridItem']"));

			if (DataGridItem1.size() != 0) {

				System.out.println("New Show Code already exists");

			} else {

				String firstWindow = driver.getWindowHandle();
				wait = new WebDriverWait(driver, 120);
				// wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ButtonAddNew")));
				driver.findElement(By.id("ButtonAddNew")).click();

				utils.switchIfWindowsAre(driver, 2);
				String secondWindow = driver.getWindowHandle();

				String nShowName = CONFIG.getProperty("newshowname");
				driver.findElement(By.id("BusinessClassBindableTextBoxName")).sendKeys(nShowName);
				driver.findElement(By.id("BusinessClassBindableTextBoxCode")).sendKeys(CONFIG.getProperty("showcode"));
				driver.findElement(By.id("LookUpButton1")).click();

				utils.switchIfWindowsAre(driver, 3);

//				driver.findElement(By.id("TextBoxShowMasterName")).sendKeys("Julie's Show Master");
				driver.findElement(By.id("ButtonSearch")).click();
				driver.findElement(By.partialLinkText("(Unknown)")).click();
				driver.switchTo().window(secondWindow);
				driver.findElement(By.id("ButtonControl__ButtonSave")).click();
				driver.switchTo().window(firstWindow);

			}

		} else {

			System.out.println("Existing Show Code did not show up");
		}

	}

	@Test()
	public void B_testcase_CreateShow() throws Exception {

		// In ShowManager complete the following information on the [Show] ->
		// Show Admin -> Detail tab:

		Select dropdownType = new Select(driver.findElement(By.id("BusinessClassBindableDropDownListCreationType")));
		dropdownType.selectByValue("CREATE");

		Select dropdownVersion = new Select(
				driver.findElement(By.id("BusinessClassBindableDropDownListCreationVersion")));
		dropdownVersion.selectByValue(CONFIG.getProperty("version"));

		driver.findElement(By.id("BusinessClassBindableTextBoxAcronym")).sendKeys("Automation");
		driver.findElement(By.id("BusinessClassBindableTextBoxAbbreviation")).sendKeys("Automation");

		driver.findElement(By.xpath("//label[text()='Essential']")).click();
		driver.findElement(By.xpath("//label[text()='Housing']")).click();
		driver.findElement(By.xpath("//label[text()='Onsite']")).click();
		driver.findElement(By.xpath("//label[text()='Registration']")).click();
		driver.findElement(By.xpath("//label[text()='Self-Reg']")).click();
		driver.findElement(By.xpath("//label[text()='Traffic Boost']")).click();

		driver.findElement(By.id("ButtonSave")).click();
		Thread.sleep(1000);
		
		Select sourcectl = new Select(driver.findElement(By.id("ddlCreateShowSourceControl")));
		sourcectl.selectByValue("GIT");

		driver.findElement(By.xpath("//a[contains(text(),'here')]")).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(2000);
		driver.quit();

	}

}
