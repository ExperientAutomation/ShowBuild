package RegOnly_Show_1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;

public class C_ConfigureRegtypeExt extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	Login login = new Login();
	static int count = -1;
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		System.out.println("In ConfigureRegtypeExt mtd 1 ...........................");
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureRegtypeExt")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigureRegtypeExt" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureRegtypeExt" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureRegtypeExt");
	}

	@Test()
	public void A_RegtypeExt() throws Exception {
		System.out.println("In ConfigureRegtypeExt mtd 2 ...........................");
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[text()='Configuration']")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Table Admin')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'RegTypeExt')]")).click();
		
	}
	

	@Test()
	public void B_Config_RegType () throws Exception {
		System.out.println("In ConfigureRegtypeExt mtd 3 ...........................");
		APP_LOGS.debug(" Executing AddSchedules_UATProd");
				
		int regid=1001;
		for (int i=2; i<6; i++){
			
		
				driver.findElement(By.xpath("//td[contains(text(),'"+regid+"')]/preceding-sibling::td//a//img")).click();
				driver.findElement(By.xpath("//td[contains(text(),'"+regid+"')]/following-sibling::td[2]/input")).sendKeys(suiteBxls.getCellData("ConfigureRegtypeExt", 1, i));
				driver.findElement(By.xpath("//td[contains(text(),'"+regid+"')]/following-sibling::td[3]/input")).sendKeys(suiteBxls.getCellData("ConfigureRegtypeExt", 2, i));
				driver.findElement(By.xpath("//td[contains(text(),'"+regid+"')]/following-sibling::td[4]/input")).sendKeys(suiteBxls.getCellData("ConfigureRegtypeExt", 3, i));
				driver.findElement(By.xpath("//td[contains(text(),'"+regid+"')]/preceding-sibling::td/a[1]/img")).click();
				
				regid++;
		}
	}
}
