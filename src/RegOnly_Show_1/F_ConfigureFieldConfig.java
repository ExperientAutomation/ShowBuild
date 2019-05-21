package RegOnly_Show_1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Create_Show.Login;
import showbuild.util.BrowserFactory;

public class F_ConfigureFieldConfig extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	Login login = new Login();
	WebDriverWait wait;
	static int count = -1;

	@Test()
	public void A_FieldConfig() throws Exception {
		System.out.println("In ConfigureFieldConfig mtd 2 ...........................");
		APP_LOGS.debug(" Executing FieldConfig");
		
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[contains(text(),'Configuration')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Lead')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Field Config')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Field Config')]")).isDisplayed(),"Failed to display Field Config");
		
	}

	@Test()
	public void B_Config_LeadConfig() throws Exception {
		System.out.println("In ConfigureFieldConfig mtd 3 ...........................");
		driver.findElement(By.xpath("//a[contains(text(),'Field Config')]")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@class='DataGridHeader']")));
		driver.findElement(By.xpath("//*[@id='get-default-list-button']")).click();
		
		WebElement okButton = driver.findElement(By.xpath("//*[@id='confirm-get-default-list']/following-sibling::div/div/button[1]"));
		wait.until(ExpectedConditions.visibilityOf(okButton));
		okButton.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='RegistrantID']")));
		
		Actions action = new Actions(driver);
		
		//Move a row up
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='City']/tbody/tr/td[1]")));
		Thread.sleep(500);
		WebElement source = driver.findElement(By.xpath("//*[@id='City']/tbody/tr/td[1]"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='Address']/tbody/tr/td[1]")));
		Thread.sleep(500);
		WebElement target = driver.findElement(By.xpath("//*[@id='Address']/tbody/tr/td[1]"));
		action.dragAndDrop(source, target).perform();
		
		//Move a row down
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='StateCode']/tbody/tr/td[1]")));
		Thread.sleep(500);
		source = driver.findElement(By.xpath("//*[@id='StateCode']/tbody/tr/td[1]"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='CountryCode']/tbody/tr/td[1]")));
		Thread.sleep(500);
		target = driver.findElement(By.xpath("//*[@id='CountryCode']/tbody/tr/td[1]"));
		action.dragAndDrop(source, target).perform();
		
		//Change the Description on a row
		driver.findElement(By.xpath("//input[@value='ZipCode']")).sendKeys("Zip");
		
		//Add a field (row)
		driver.findElement(By.xpath("//a[contains(text(),'Address2')]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='Address2']")));
		Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Address2']")).isDisplayed(), "Failed to add the Address2 row");
		
		//Remove a field (row)
		driver.findElement(By.xpath("//*[@id='Fax'] //input[contains(@value,'Delete')]")).click();
		WebElement deleteButton = driver.findElement(By.xpath("//*[@id='confirm-delete-fieldconfig']/following-sibling::div/div/button[1]"));
		wait.until(ExpectedConditions.visibilityOf(deleteButton));
		deleteButton.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Fax')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Fax')]")).isDisplayed(), "Failed to remove the Row");
		
		//Click Save
		driver.findElement(By.xpath("//*[@id='save-button']")).click();
		WebElement saveButton = driver.findElement(By.xpath("//*[@id='confirm-save-to-database-dialog']/following-sibling::div/div/button[1]"));
		wait.until(ExpectedConditions.visibilityOf(saveButton));
		saveButton.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Success')]")));
		driver.findElement(By.xpath("//*[@id='save-to-database-dialog']/following-sibling::div/div/button")).click();
		
	}

}
