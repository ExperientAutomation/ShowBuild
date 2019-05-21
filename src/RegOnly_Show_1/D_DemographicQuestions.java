package RegOnly_Show_1;

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

public class D_DemographicQuestions extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	Login login = new Login();
	WinUtils util = new WinUtils();
	WebDriverWait wait;
	String mainWindow = null;
	static int count = -1;

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In DemographicQuestions mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "DemographicQuestions")) {
			APP_LOGS.debug("Skipping Test Case" + "DemographicQuestions" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "DemographicQuestions" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "DemographicQuestions");
	}

	@Test()
	public void A_Demographics() throws Exception {
		System.out.println("In DemographicQuestions mtd 2 ...........................");
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[contains(text(),'Configuration')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Demographics')]")).click();
		driver.findElement(By.xpath("//*[@value='Create New Demographics']")).click();
		mainWindow = driver.getWindowHandle();
		util.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();

		wait = new WebDriverWait(driver, 300);
		WebElement element = driver.findElement(By.cssSelector("#ButtonWelcomeClose"));
		wait.until(ExpectedConditions.visibilityOf(element));
		element.click();
		
	}

	@Test()
	public void B_Config_DemographicsQuestions() throws Exception {
		System.out.println("In DemographicQuestions mtd 3 ...........................");
		int r=0;
		for (int i=2; i<=8; i++){
			
		String textboxName= "//input[contains(@id,'TextBoxName_"+r+"')]";
		driver.findElement(By.xpath(textboxName)).sendKeys(suiteBxls.getCellData("DemographicQuestions", 0, i));
		Thread.sleep(500);
		
		String DropdownFieldType = "//select[contains(@id,'DropDownListFieldType_"+r+"')] ";
		Select sel = new Select(driver.findElement(By.xpath(DropdownFieldType)));
		sel.selectByVisibleText(suiteBxls.getCellData("DemographicQuestions", 1, i));
		Thread.sleep(500);
		
		String textboxSize = "//input[contains(@id,'TextBoxSize_"+r+"')]";
		driver.findElement(By.xpath(textboxSize)).clear();
		driver.findElement(By.xpath(textboxSize)).sendKeys(suiteBxls.getCellData("DemographicQuestions", 2, i));
		Thread.sleep(500);
		
		String dropdownDisplayType = "//select[contains(@id,'DropDownListDisplayType_"+r+"')]";
		sel = new Select(driver.findElement(By.xpath(dropdownDisplayType)));
		sel.selectByValue(suiteBxls.getCellData("DemographicQuestions", 3, i).trim());
		Thread.sleep(500);
		
		if (! suiteBxls.getCellData("DemographicQuestions", 1, i).contains("Free Text")){
		String dropdownColCount = "//select[contains(@id,'DropDownListColumnCount_"+r+"')]";
		sel = new Select(driver.findElement(By.xpath(dropdownColCount)));
		sel.selectByVisibleText(suiteBxls.getCellData("DemographicQuestions", 4, i));
		Thread.sleep(500);
		}
		
		String textboxDes = "//input[contains(@id,'TextBoxDescription_"+r+"')]";
		driver.findElement(By.xpath(textboxDes)).sendKeys(suiteBxls.getCellData("DemographicQuestions", 5, i));
		Thread.sleep(500);
		
		if (i<=7){
			driver.findElement(By.xpath("//input[contains(@value,'Add Another Row')]")).click();
			Thread.sleep(1000);
		}
		if (i==8){
			driver.findElement(By.xpath("//input[contains(@value,'Create the Demographics')]")).click();
			Thread.sleep(1000);
		}
		r++;
		}
		
	}

	@Test()
	public void C_CreateDemographicsQuestions() throws Exception {
		System.out.println("In DemographicQuestions mtd 4 ...........................");
		driver.findElement(By.xpath("//*[@id='ButtonConfirmCreate']")).click();
		
		while(driver.getWindowHandles().size()==2){
			
			Thread.sleep(1000);
		}
		
		driver.switchTo().window(mainWindow);		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='GridViewFields']/tbody")).isDisplayed());
		System.out.println("Demographic Questions were added successfully");
	}

}
