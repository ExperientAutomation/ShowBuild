package RegOnly_Show;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class E_DemographicAnswers extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	Login login = new Login();
	WinUtils util = new WinUtils();
	WebDriverWait wait;
	String mainWindow = null;
	String secondWindow = null;
	static int count = -1;

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In DemographicAnswers mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "DemographicAnswers")) {
			APP_LOGS.debug("Skipping Test Case" + "DemographicAnswers" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "DemographicAnswers" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "DemographicAnswers");
	}

	@Test()
	public void A_Demographics() throws Exception {
		System.out.println("In DemographicAnswers mtd 2 ...........................");
		APP_LOGS.debug(" Executing Demographic Answers");

		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[contains(text(),'Configuration')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Demographics')]")).click();

	}

	@Test()
	public void B_Config_DemographicsAnswers() throws Exception {
		System.out.println("In DemographicAnswers mtd 3 ...........................");
		mainWindow = driver.getWindowHandle();
		
		for (int i = 1; i<=5; i += 2){
			
			driver.findElement(By.xpath("//*[@id='GridViewFields_ButtonPicks_"+i+"']")).click();
			util.switchIfWindowsAre(driver, 2);
			secondWindow = driver.getWindowHandle();
			driver.findElement(By.xpath("//*[@id='ButtonAdd']")).click();
			util.switchIfWindowsAre(driver, 3);
			
			if (i==1) this.CreateDemographicsAnswersPriJob();
			if (i==3) this.CreateDemographicsAnswersSecJob();
			if (i==5) this.CreateDemographicsAnswersStage();
			
			driver.close();
			driver.switchTo().window(secondWindow);
			driver.close();
			driver.switchTo().window(mainWindow);
		}

	}

	public void CreateDemographicsAnswersPriJob() throws Exception {

		for (int r = 2; r <= 8; r++) {
			driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxName']"))
					.sendKeys(suiteBxls.getCellData("DemographicAnswers", 1, r));
			driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxDescription']"))
					.sendKeys(suiteBxls.getCellData("DemographicAnswers", 2, r));
			Thread.sleep(500);

			if (suiteBxls.getCellData("DemographicAnswers", 1, r).contentEquals("G")) {
				driver.findElement(By.xpath("//*[@id='CheckBoxIsOther']")).click();
				Select sel = new Select(driver.findElement(By.xpath("//*[@id='DropDownOtherField']")));
				sel.selectByVisibleText("PriJobOther");
			}

			driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
			Thread.sleep(500);

			if (r < 8) {
				driver.findElement(By.xpath("//*[@id='ButtonCreateNew']")).click();
				Thread.sleep(500);
			}
		}

	}
	
	public void CreateDemographicsAnswersSecJob() throws Exception {

		for (int r = 9; r <= 15; r++) {
			driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxName']"))
					.sendKeys(suiteBxls.getCellData("DemographicAnswers", 1, r));
			driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxDescription']"))
					.sendKeys(suiteBxls.getCellData("DemographicAnswers", 2, r));
			Thread.sleep(500);

			if (suiteBxls.getCellData("DemographicAnswers", 1, r).contentEquals("G")) {
				driver.findElement(By.xpath("//*[@id='CheckBoxIsOther']")).click();
				Select sel = new Select(driver.findElement(By.xpath("//*[@id='DropDownOtherField']")));
				sel.selectByVisibleText("SecJobOther");
			}

			driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
			Thread.sleep(500);

			if (r < 15) {
				driver.findElement(By.xpath("//*[@id='ButtonCreateNew']")).click();
				Thread.sleep(500);
			}
		}

	}
	
	public void CreateDemographicsAnswersStage() throws Exception {

		for (int r = 16; r <= 20; r++) {
			driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxName']"))
					.sendKeys(suiteBxls.getCellData("DemographicAnswers", 1, r));
			driver.findElement(By.xpath("//*[@id='BusinessClassBindableTextBoxDescription']"))
					.sendKeys(suiteBxls.getCellData("DemographicAnswers", 2, r));
			Thread.sleep(500);

			driver.findElement(By.xpath("//*[@id='ButtonControl__ButtonSave']")).click();
			Thread.sleep(500);

			if (r < 20) {
				driver.findElement(By.xpath("//*[@id='ButtonCreateNew']")).click();
				Thread.sleep(500);
			}
		}

	}

}
