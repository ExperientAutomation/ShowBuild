package PushToProd_3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Create_Show.Login;
import showbuild.util.BrowserFactory;

public class C_DPConfig extends TestSuiteBase{
	
	WebDriver driver;
	WebDriverWait wait;
	Login login = new Login();
	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());	
			
	@BeforeClass
	public void LoginAndFocusShow(){
		
		login.testcase_Login("Prod");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
			
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Show Admin']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Show Admin']")).isDisplayed(), "Could not login");		
		
	}
	
	@Test
	public void A_ConfigureDecisionPoint() throws Exception{
		
		//DP RegTypes Chart
		driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP RegTypes Chart')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP RegTypes Chart')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("cmdSave")));
		
		//driver.findElement(By.xpath("//input[@class='cssinputExcludedG']")).click();
		driver.findElement(By.id("cmdSave")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("cmdSave")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblMessage")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='lblMessage'][text()='Chart Config settings saved.']")).isDisplayed());
				
		//DP ShowItems Chart
		driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP ShowItems Chart')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP ShowItems Chart')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("cmdSave")));
		
		driver.findElement(By.xpath("//a[contains(text(),'PACKAGES')]/ancestor::td/input")).click();
		driver.findElement(By.xpath("//a[contains(text(),'ROOM1')]/ancestor::td/input")).click();
		driver.findElement(By.xpath("//a[contains(text(),'GUEST')]/ancestor::td/input")).click();
		
		driver.findElement(By.id("cmdSave")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblMessage")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='lblMessage'][text()='Chart Config settings saved.']")).isDisplayed());
		
		// DP Weeks Out Setup
		driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP Weeks Out Setup')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP Weeks Out Setup')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("cmdSave")));
		
		driver.findElement(By.id("cmdSave")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblMessage")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='lblMessage'][text()='Report settings saved.']")).isDisplayed());
		
		//DP Demographics Chart
		/*driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP Demographics Chart')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP Demographics Chart')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("cmdSave")));
		driver.findElement(By.id("cmdSave")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblMessage")));
		Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='lblMessage'][text()='Chart Config settings saved.']")).isDisplayed());*/
		
		//DP Rate Tiers
		driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP Rate Tiers')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP Rate Tiers')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ButtonSave")));
		
		driver.findElement(By.id("ButtonSave")).click();
		
		Thread.sleep(1000);
		Assert.assertFalse(driver.getCurrentUrl().toLowerCase().contains("error"), "Page shows Appology Error");
		Assert.assertFalse(driver.getTitle().toLowerCase().contains("404"), "Page shows 404 Error");
		Assert.assertFalse(driver.getTitle().toLowerCase().contains("403"), "Page shows 403 Error");
		
		//DP Global Settings
		driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP Global Settings')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP Global Settings')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSave")));
		
		driver.findElement(By.id("btnSave")).click();
		
		Assert.assertFalse(driver.getCurrentUrl().toLowerCase().contains("error"), "Page shows Appology Error");
		Assert.assertFalse(driver.getTitle().toLowerCase().contains("404"), "Page shows 404 Error");
		Assert.assertFalse(driver.getTitle().toLowerCase().contains("403"), "Page shows 403 Error");
		
		//DP Sync Setup
		driver.findElement(By.xpath("//td[text()='DP Config']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'DP Sync Setup')]")));
		
		driver.findElement(By.xpath("//div[contains(text(),'DP Sync Setup')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("cmdSave")));
		
		//Click on Today's date
		Calendar c = Calendar.getInstance();
		String mth = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);    
	 		
	    Date d = new Date(); 
	    String dateWithoutTime = d.toString().substring(0, 10);
	    
	    String[] st=dateWithoutTime.split(" ");
		String a = "";
		
		if(st[2].startsWith("0")){
			a=st[2].replace("0", "");
		} else {
			a=st[2];
		}
		
		String strDate = mth + " " + a ;
			
		driver.findElement(By.xpath("//*[@id='calDateStart']//a[@title='"+strDate+"']")).click();
		
		driver.findElement(By.id("cmdSave")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblMessage")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id='lblMessage'][contains(text(),'Sync settings saved')]")).isDisplayed());
				
	}
	
	@AfterSuite
	public void tearDown(){
		
		driver.quit();
	}
	
}
