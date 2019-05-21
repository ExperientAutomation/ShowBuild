package PushToProd_3;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.WinUtils;

public class A_PushToProd extends TestSuiteBase {

	WebDriver driver;
	WebDriverWait wait;
	Login login = new Login();
	Select sel;
	WinUtils util = new WinUtils();

	@Test()
	public void A_LoginAndFocusShow() {
		
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Show Admin']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Show Admin']")).isDisplayed(), "Could not login");
	}
	
//	Show Transfer Utility
	@Test()
	public void AA_PushEverythingElse() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[1];
		String methodName=e.getMethodName();

		driver.findElement(By.xpath("//td[text()='Configuration']")).click();
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Show Transfer Utility')]")));

		driver.findElement(By.xpath("//div[contains(text(),'Show Transfer Utility')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnExecuteTransfer")));

		// check the below checkboxes
		driver.findElement(By.id("chkStatuses")).click();
		driver.findElement(By.id("chkDemographics")).click();
		driver.findElement(By.id("chkCustomCodes")).click();
		driver.findElement(By.id("chkShowItems")).click();
		driver.findElement(By.id("chkGLAccounts")).click();
		driver.findElement(By.id("chkCredentials")).click();
		driver.findElement(By.id("chkRegConfigValidators")).click();
		driver.findElement(By.id("chkCssFiles")).click();
		driver.findElement(By.id("chkShowSetupWizard")).click();

		// check all the checkboxes from the Extended Tables
		List<WebElement> chkboxes = driver.findElements(By.xpath("//*[@id='cblExtTables']//tr//input"));
		Iterator<WebElement> itr = chkboxes.iterator();

		while (itr.hasNext()) {

			WebElement we = itr.next();
			we.click();
		}

		driver.findElement(By.id("btnExecuteTransfer")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ExecutionMessageSummary']")));

		if (driver
				.findElements(
						By.xpath("//*[@class='ExecutionMessageSummary']/li[contains(text(),'Transfers Successful!')]"))
				.size() > 0) {

			System.out.println("Pushed Everything Else successfully");

		} else {

			util.emailscreenshot(driver, methodName);
		}

		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ExecutionMessageSummary']/li[contains(text(),'Transfers Successful!')]")).isDisplayed(), "Could not PushEverythingElse");

	}
	
	//Housing Transfer
	@Test(enabled =true)
	public void B_HousingTransfer() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[1];
		String methodName=e.getMethodName();
		
		driver.findElement(By.xpath("//td[text()='Configuration']")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Housing Transfer')]")));
		driver.findElement(By.xpath("//div[contains(text(),'Housing Transfer')]")).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnExecuteTransfer")));
		driver.findElement(By.id("btnExecuteTransfer")).click();

		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		int error = 0;
		if (driver.findElements(By.xpath("//*[@id='lblStatus'][text()='Housing push complete!']")).size() > 0) {

			System.out.println("Pushed WebFiles successfully");

		} else if (driver.findElements(By.xpath("//li[contains(text(),'it has already been pushed')]")).size()>0) {
			System.out.println("This show's record indicates it has already been pushed!");
		}
		
		else {
			util.emailscreenshot(driver, methodName );
			error++;
		}	
		
		Assert.assertFalse(error>0,"Housing was not Pushed");

	}

//	Report Config. Transfer
	@Test()
	public void C_PushConfirmationLettersToProduction() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[1];
		String methodName=e.getMethodName();
		
		driver.findElement(By.xpath("//td[text()='Configuration']")).click();
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Report Config Transfer')]")));

		driver.findElement(By.xpath("//div[contains(text(),'Report Config Transfer')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ButtonTransfer")));

		sel = new Select(driver.findElement(By.id("ListBoxExistingReportDefinitionItems")));
		sel.selectByVisibleText("HTML Confirmation - Attendee");
		sel.selectByVisibleText("HTML Confirmation - Exhibitor");
		sel.selectByVisibleText("HTML Friend Invite");

		driver.findElement(By.id("ImageButtonAdd")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ListBoxSelectedReportDefinitionItems']")));
		
		driver.findElement(By.id("ButtonTransfer")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='LabelSuccess']")));
		
		if (driver.findElements(By.xpath("//*[@id='LabelSuccess'][text()='HTML Production Configuration Items were successfully transferred!']")).size() > 0) {

			System.out.println("Pushed WebFiles successfully");

		} else {

			util.emailscreenshot(driver, methodName);
		}		
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='LabelSuccess'][text()='HTML Production Configuration Items were successfully transferred!']")).isDisplayed(), "Could not transfer");

	}

//	Web File Management transfer	
	@Test()
	public void D_PushWebFiles() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[1];
		String methodName=e.getMethodName();

		driver.findElement(By.xpath("//td[text()='Configuration']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Web File Management')]")));

		driver.findElement(By.xpath("//div[contains(text(),'Web File Management')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnExecuteTransfer")));

		driver.findElement(By.id("btnExecuteTransfer")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='ExecutionMessageUser']")));
		
		if (driver.findElements(By.xpath("//li[@class='ExecutionMessageUser'][contains(text(),'SUCCESS: Transferred files to server')]")).size() > 0) {

			System.out.println("Pushed WebFiles successfully");

		} else {

			util.emailscreenshot(driver, methodName);
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='ExecutionMessageUser'][contains(text(),'SUCCESS: Transferred files to server')]")).isDisplayed(),
				"Could not Push Web Files");

	}

//	
}
