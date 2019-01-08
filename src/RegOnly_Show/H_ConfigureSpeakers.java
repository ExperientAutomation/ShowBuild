package RegOnly_Show;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import Setup_Housing.TestSuiteBase;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class H_ConfigureSpeakers extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	String firstWindow;
	String secondWindow;
	static int count = -1;
	WinUtils utils = new WinUtils();
	WebDriverWait wait;
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigureSpeakers mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureSpeakers")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigureSpeakers" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureSpeakers" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureSpeakers");
	}

	@Test()
	public void A_LoginAndFocusShow() throws Exception {
		System.out.println("In ConfigureSpeakers mtd 2 ...........................");
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode for test set data set to no " + count);
		}
		APP_LOGS.debug(" Executing Add Speakers");
		
		Login login = new Login();
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		
		wait= new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'Show Admin')]")));
		WebElement showAdmin=driver.findElement(By.xpath("//td[contains(text(),'Show Admin')]"));
		
		Assert.assertTrue(showAdmin.isDisplayed(), "Failed to focus Showcode");
		
		driver.findElement(By.xpath("//td[contains(text(),'Show Admin')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Speakers')]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("DataGridSpeakers")));
		
		Assert.assertTrue(driver.findElement(By.id("DataGridSpeakers")).isDisplayed(), "Failed to load Speakers Page");
		
		
	}

	@Test(dataProvider = "ConfigureSpeakers")
	public void B_AddSpeakers(String isActive, String FirstName, String Middleinitital, String LastName, 
			String Prefix,
			String Suffix,
			String CertificationTitle,
			String BusinessTitle,
			String Company,
			String Address1,
			String Address2,
			String City,
			String State,
			String ZipCode,
			String Country,
			String Phone,
			String Email) throws Exception {
		System.out.println("In ConfigureSpeakers mtd 3 ...........................");
		firstWindow = driver.getWindowHandle();
		driver.findElement(By.id("ButtonAdd")).click();
		
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		Thread.sleep(1000);
		if (isActive.equalsIgnoreCase("Yes")) driver.findElement(By.id("checkboxIsActive")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("BusinessClassBindableTextBoxFirstName")));
		driver.findElement(By.id("BusinessClassBindableTextBoxFirstName")).sendKeys(FirstName);
		driver.findElement(By.id("BusinessClassBindableTextBoxMiddleName")).sendKeys(Middleinitital);
		driver.findElement(By.id("BusinessClassBindableTextBoxLastName")).sendKeys(LastName);
		driver.findElement(By.id("BusinessClassBindableTextBoxPrefix")).sendKeys(Prefix);
		driver.findElement(By.id("BusinessClassBindableTextBoxSuffix")).sendKeys(Suffix);
		driver.findElement(By.id("BusinessClassBindableTextBoxCertificationTitle")).sendKeys(CertificationTitle);
		driver.findElement(By.id("BusinessClassBindableTextBoxBusinessTitle")).sendKeys(BusinessTitle);
		driver.findElement(By.id("BusinessClassBindableTextBoxCompany")).sendKeys(Company);
		driver.findElement(By.id("BusinessClassBindableTextBoxAddress1")).sendKeys(Address1);
		driver.findElement(By.id("BusinessClassBindableTextBoxAddress2")).sendKeys(Address2);
		
		if(!Country.isEmpty()){
			Select select = new Select(driver.findElement(By.id("BusinessClassBindableDropDownListCountry")));
			select.selectByValue(Country);		
			}
		
		driver.findElement(By.id("BusinessClassBindableTextBoxZip")).sendKeys(ZipCode);
		driver.findElement(By.id("BusinessClassBindableTextBoxZip")).sendKeys(Keys.TAB);
		
		driver.findElement(By.id("BusinessClassBindableTextBoxCity")).click();
		driver.findElement(By.id("BusinessClassBindableTextBoxCity")).clear();
		driver.findElement(By.id("BusinessClassBindableTextBoxCity")).sendKeys(City);
		
		
		if(!State.isEmpty()){
		Select sel = new Select(driver.findElement(By.id("BusinessClassBindableDropDownListState")));
		sel.selectByValue(State);
		}
		
		driver.findElement(By.id("BusinessClassBindableTextBoxPhone")).sendKeys(Phone);
		driver.findElement(By.id("BusinessClassBindableTextBoxEmail")).sendKeys(Email);
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ButtonAdd")));
	}

	@DataProvider
	public Object[][] ConfigureSpeakers() {
		return TestUtil.getData(suiteBxls, "ConfigureSpeakers");
	}

}
