package RegOnly_Show_1;

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

public class G_HTML_Production_Config extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	WebDriverWait wait;
	Select select;
	static int count = -1;


	@Test()
	public void A_HTMLProdConfig() throws Exception {
		
		System.out.println("In HTML_Production_Config mtd 1 ...........................");
		APP_LOGS.debug(" Executing FieldConfig");

		Login login = new Login();
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[contains(text(),'Configuration')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'HTML Production Config')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("DropDownListReport")));
		Assert.assertTrue(driver.findElement(By.id("DropDownListReport")).isDisplayed(),"Failed to display HTML_Production_Config Page");		
	}

	@Test()
	public void B_ConfigureAttendeeConfirmation() throws Exception {
		
		System.out.println("In HTML_Production_Config mtd 2 ...........................");
		select = new Select(driver.findElement(By.id("DropDownListReport")));
		select.selectByVisibleText("HTML Confirmation - Attendee");
				
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'Introduction Text')]/ancestor::tr/td[2]/a[2]/img")));
		WebElement arrow = driver.findElement(By.xpath("//span[contains(text(),'Introduction Text')]/ancestor::tr/td[2]/a[2]/img"));
		
		arrow.click();

		driver.switchTo().frame(driver.findElement(By.id("DataListTextRegions_TextBox1_12_ifr")));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body[@data-id='DataListTextRegions_TextBox1_12']")));
		
		WebElement paragraph = driver.findElement(By.xpath("//body[@data-id='DataListTextRegions_TextBox1_12']"));

		paragraph.clear();
		paragraph.click();
		paragraph.sendKeys("Thank you for registering");
		
		driver.switchTo().defaultContent();
		driver.findElement(By.id("ButtonSave")).click();;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("LabelStatus")));
		String successMsg = driver.findElement(By.id("LabelStatus")).getText();
		Assert.assertTrue(successMsg.contains("Your configuration has been saved successfully"), "Failed to Save Attendee Configuration");	

	}
	
	@Test()
	public void C_ConfigureExhibitorConfirmation() throws Exception {
		System.out.println("In HTML_Production_Config mtd 3 ...........................");
		select = new Select(driver.findElement(By.id("DropDownListReport")));
		select.selectByVisibleText("HTML Confirmation - Exhibitor");
				
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		WebElement arrow = driver.findElement(By.xpath("//span[contains(text(),'Introduction Text')]/ancestor::tr/td[2]/a[2]/img"));
		
		arrow.click();

		driver.switchTo().frame(driver.findElement(By.id("DataListTextRegions_TextBox1_12_ifr")));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body[@data-id='DataListTextRegions_TextBox1_12']")));
		
		WebElement paragraph = driver.findElement(By.xpath("//body[@data-id='DataListTextRegions_TextBox1_12']"));

		paragraph.clear();
		paragraph.click();
		paragraph.sendKeys("Thank you for registering");
		
		driver.switchTo().defaultContent();
		driver.findElement(By.id("ButtonSave")).click();;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("LabelStatus")));
		String successMsg = driver.findElement(By.id("LabelStatus")).getText();
		Assert.assertTrue(successMsg.contains("Your configuration has been saved successfully"), "Failed to Save Exhibitor Configuration");
		
			}
	
	@Test(enabled = false)
	public void D_ConfigureInviteFriend() throws Exception {
		System.out.println("In HTML_Production_Config mtd 4 ...........................");
		select = new Select(driver.findElement(By.xpath("//*[@id='DropDownListReport']")));
		select.selectByVisibleText("HTML Friend Invite");
				
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		WebElement arrow = driver.findElement(By.xpath("//span[contains(text(),'Introduction Text')]/ancestor::tr/td[2]/a[2]/img"));
		
		arrow.click();

		driver.switchTo().frame(driver.findElement(By.id("DataListTextRegions_TextBox1_7_ifr")));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//body[@data-id='DataListTextRegions_TextBox1_7']")));
		
		WebElement paragraph = driver.findElement(By.xpath("//body[@data-id='DataListTextRegions_TextBox1_7']"));

		paragraph.clear();
		paragraph.click();
		paragraph.sendKeys("Take a look");
		
		driver.switchTo().defaultContent();
		driver.findElement(By.id("ButtonSave")).click();;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("LabelStatus")));
		String successMsg = driver.findElement(By.id("LabelStatus")).getText();
		Assert.assertTrue(successMsg.contains("Your configuration has been saved successfully"), "Failed to Save InviteFriend Configuration");
		
			}
}
