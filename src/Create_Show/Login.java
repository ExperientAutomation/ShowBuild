package Create_Show;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import showbuild.util.BrowserFactory;

public class Login extends TestSuiteBase {
	
	public static WebDriver driver;
	WebDriverWait wait;	

	public void testcase_Login(String subenv) {
		
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().window().maximize();
		
		String environment = CONFIG.getProperty("environment");
		String keyURL = environment+subenv;
		System.out.println("URL= "+CONFIG.getProperty(keyURL));
		driver.get(CONFIG.getProperty(keyURL));
		
		
//		if(driver.findElements(By.id("Login1_LoginButton")).size()>0){
		
		if(driver.findElements(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).size()>0){
		
		driver.findElement(By.xpath("//input[contains(@id,'Username') or contains(@id,'UserName')]")).sendKeys(CREDENTAIL.getProperty("USER_NAME"), Keys.TAB, CREDENTAIL.getProperty("PASSWORD"), Keys.ENTER);	
		
		/*driver.findElement(By.id("Login1_UserName")).sendKeys(CONFIG.getProperty("Username"));
		driver.findElement(By.id("Login1_Password")).sendKeys(CONFIG.getProperty("Password"));
		driver.findElement(By.id("Login1_LoginButton")).click();*/
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Logout')]")));
		WebElement logout= driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		Assert.assertTrue(logout.isDisplayed());
		System.out.println("Logged in successfully");

	}
}
