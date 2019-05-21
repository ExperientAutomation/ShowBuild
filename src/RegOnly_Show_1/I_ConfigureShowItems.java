package RegOnly_Show_1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Create_Show.Login;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class I_ConfigureShowItems extends TestSuiteBase {
	String runmodes[] = null;
	WebDriver driver;
	WebDriverWait wait;
	String firstWindow;
	WinUtils utils = new WinUtils();
	Select sel;
	
	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {
		
		if (!TestUtil.isTestCaseRunnable(suiteBxls, "ConfigureShowItems")) {
			APP_LOGS.debug("Skipping Test Case" + "ConfigureShowItems" + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + "ConfigureShowItems" + " as runmode set to NO");// reports
		}
		runmodes = TestUtil.getDataSetRunmodes(suiteBxls, "ConfigureShowItems");
	}

	@Test()
	public void A_LoginAndFocus() throws Exception {
		
		Login login = new Login();
		login.testcase_Login("Stage");
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();

		driver.findElement(By.xpath("//td[contains(text(),'Show Admin')]")).click();
		driver.findElement(By.xpath("//div[contains(text(),'Show Items')]")).click();
		
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'SHOWITEM')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(text(),'SHOWITEM')]")).isDisplayed(), "Failed to load SHOWITEM tree");
		
	}
	
	@Test(enabled = true)
	public void B_RegistrationFees () throws Exception {
		
		APP_LOGS.debug(" Executing Configure Registration Fees");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[contains(text(),'REGISTRATION')]/ancestor::tr/td[1]/img")).click();
		
		
		int regtype=2;
		for(int j=0;j<4;j++){
			int id=1;
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@exttreebranchpath='SHOWITEM_REGISTRATION_REG']//a[3]/img")).click();
			
			firstWindow = driver.getWindowHandle();
			
			utils.switchIfWindowsAre(driver, 2);
			driver.manage().window().maximize();
		for(int i=regtype;i<regtype+6;i++)
		{
			Thread.sleep(1000);
			String data=suiteBxls.getCellData("ConfigureShowItems", 0, i).trim();
			String data2=suiteBxls.getCellData("ConfigureShowItems", 1, i).trim();
			String data3=suiteBxls.getCellData("ConfigureShowItems", 2, i).trim();
			String data4=suiteBxls.getCellData("ConfigureShowItems", 3, i).trim();
			String st="(//label[text()='"+data+"'])["+id+"]";
			driver.findElement(By.xpath(st)).click();
			if(data2.equals("M"))
			{
			String st2="(//label[text()='"+data2+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			if(data2.equals("NM"))
			{
			/*String st2="(//label[text()='"+data2+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();*/
			}
			if(data3.equals("EARLY"))
			{
			String st2="(//label[text()='"+data3+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			if(data3.equals("ADV"))
			{
			String st2="(//label[text()='"+data3+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			if(data3.equals("ONS"))
			{
			String st2="(//label[text()='"+data3+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			
			String amtC="(//input[contains(@id,'Amount')])["+id+"]";
			driver.findElement(By.xpath(amtC)).sendKeys(data4);

			id++;
		}
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		driver.switchTo().window(firstWindow);
		regtype=regtype+6;
		}
		
		int itemsAdded = driver.findElements(By.xpath("//*[@id='DataGridFees']/tbody/tr")).size()-1;
		System.out.println("Number of itemsAdded are ::::::" + itemsAdded);
		Assert.assertTrue(itemsAdded==24, "Failed to add Registration Fees");
	}
	
	@Test(enabled = true)
	public void C_CancellationFees () throws Exception {
		
		APP_LOGS.debug(" Executing Configure Cancellation Fees");
		
		
		driver.navigate().refresh();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[contains(text(),'CANCELLATION')]/ancestor::tr/td[1]/img")).click();
		
		
		int regtype=2;
		for(int j=0;j<4;j++){
			int id=1;
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@exttreebranchpath='SHOWITEM_CANCELLATION_CANCEL']//a[3]/img")).click();
			
			firstWindow = driver.getWindowHandle();
			
			utils.switchIfWindowsAre(driver, 2);
			driver.manage().window().maximize();
		for(int i=regtype;i<regtype+6;i++)
		{
			Thread.sleep(1000);
			String data=suiteBxls.getCellData("CancellationFees", 0, i).trim();
			String data2=suiteBxls.getCellData("CancellationFees", 1, i).trim();
			String data3=suiteBxls.getCellData("CancellationFees", 2, i).trim();
			String data4=suiteBxls.getCellData("CancellationFees", 3, i).trim();
			String st="(//label[text()='"+data+"'])["+id+"]";
			driver.findElement(By.xpath(st)).click();
			if(data2.equals("M"))
			{
			String st2="(//label[text()='"+data2+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			if(data2.equals("NM"))
			{
			/*String st2="(//label[text()='"+data2+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();*/
			}
			if(data3.equals("EARLY"))
			{
			String st2="(//label[text()='"+data3+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			if(data3.equals("ADV"))
			{
			String st2="(//label[text()='"+data3+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			if(data3.equals("ONS"))
			{
			String st2="(//label[text()='"+data3+"'])["+id+"]";
			driver.findElement(By.xpath(st2)).click();
			}
			
			String amtC="(//input[contains(@id,'Amount')])["+id+"]";
			driver.findElement(By.xpath(amtC)).sendKeys(data4);

			id++;
		}
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		driver.switchTo().window(firstWindow);
		regtype=regtype+6;
		}
		
		int itemsAdded = driver.findElements(By.xpath("//*[@id='DataGridFees']/tbody/tr")).size()-1;
		System.out.println("Number of itemsAdded are ::::::" + itemsAdded);
		Assert.assertTrue(itemsAdded==24, "Failed to add Cancellation Fees");
	}
	
	@Test(dataProvider = "getData", enabled=true)
	public void D_ShowItemTree(String groupCode, String groupDescription) throws Exception{
		
		APP_LOGS.debug(" Executing Configure ShowItem Tree");
		
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[contains(text(),'SHOWITEM')]")).click();
		
		firstWindow = driver.getWindowHandle();
		driver.findElement(By.id("ButtonAddGroup")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("BusinessClassBindableTextBoxCode")).sendKeys(groupCode);
		driver.findElement(By.id("BusinessClassBindableTextBoxDescription")).sendKeys(groupDescription);
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		
		String showItemTreeAdded = "//a[contains(text(),'"+groupCode+"')]";
		Assert.assertTrue(driver.findElement(By.xpath(showItemTreeAdded)).isDisplayed(),"Failed to add "+ groupCode +" in the ShowItemTree");
	}
	
	@DataProvider
	public Object[][] getData(){
		
		return TestUtil.getData(suiteBxls, "ShowItemTree");
	}
	
	@Test(dataProvider = "getData1", enabled=true)
	public void E_ShowItemSUBTree(String groupCode, String groupDescription) throws Exception{
		
		APP_LOGS.debug(" Executing Configure ShowItem SUB Tree");
		
		driver.navigate().refresh();
		driver.findElement(By.xpath("//a[contains(text(),'MONDAY')]")).click();
		
		firstWindow = driver.getWindowHandle();
		driver.findElement(By.id("ButtonAddGroup")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("BusinessClassBindableTextBoxCode")).sendKeys(groupCode);
		driver.findElement(By.id("BusinessClassBindableTextBoxDescription")).sendKeys(groupDescription);
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//td[@exttreebranchpath='SHOWITEM_MONDAY']/preceding-sibling::td/img")).click();
		
		String showItemSUBTreeAdded = "//a[contains(text(),'"+groupCode+"')]";
		Assert.assertTrue(driver.findElement(By.xpath(showItemSUBTreeAdded)).isDisplayed(),"Failed to add "+ groupCode +" in the ShowItem SUB Tree");
	}
	
	@DataProvider
	public Object[][] getData1(){
		
		return TestUtil.getData(suiteBxls, "ShowItemSUBTree");
	}
	
	@Test(dataProvider = "getData2", enabled=true)
	public void F_AddShowItems(String SITree, String SISubTree, String Date, String Code, String Description,
			String SIType,
			String IsConflictCheck,
			String IsTicketed,
			String LimitQty,
			String MaxQtyPerReg,
			String StartTime,
			String EndTime,
			String Speaker,
			String Location,
			String EarlyFee,
			String AdvanceFee,
			String OnsiteFee ) throws Exception{
		
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		if (SISubTree.equals("MON1")){
			Thread.sleep(1000);
			driver.findElement(By.xpath("//td[@exttreebranchpath='SHOWITEM_MONDAY']/preceding-sibling::td/img")).click();
			driver.findElement(By.xpath("//a[text()='MON1']")).click();
			Thread.sleep(1000);
		}
		if (SISubTree.equals("MON2")){
			Thread.sleep(1000);
			driver.findElement(By.xpath("//td[@exttreebranchpath='SHOWITEM_MONDAY']/preceding-sibling::td/img")).click();
			driver.findElement(By.xpath("//a[text()='MON2']")).click();
			Thread.sleep(1000);
		}
		
		if (SISubTree.equals("")){
		driver.findElement(By.xpath("//a[text()='"+SITree+"']")).click();
		Thread.sleep(1000);
		}
		
		firstWindow = driver.getWindowHandle();
		driver.findElement(By.id("ButtonAdd")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		if (Date!="") driver.findElement(By.id("BusinessclassbindabletextboxStartDate")).sendKeys(Date);
		if (Date!="") driver.findElement(By.id("BusinessclassbindabletextboxEndDate")).sendKeys(Date);
				
		driver.findElement(By.id("BusinessClassBindableTextBoxDescription")).sendKeys(Description);
		driver.findElement(By.id("BusinessclassbindabletextboxShowItemCode")).sendKeys(Code);
		
		sel = new Select(driver.findElement(By.id("BusinessClassBindableDropDownListShowItemTypeCode")));
		sel.selectByValue(SIType);
		
		if (IsConflictCheck.equals("N")) driver.findElement(By.id("CheckboxIsConflictChecked")).click();
		if (IsTicketed.equals("N")) driver.findElement(By.id("CheckboxIsTicketed")).click();
		
		driver.findElement(By.id("BusinessclassbindabletextboxLimitQty")).clear();
		driver.findElement(By.id("BusinessclassbindabletextboxLimitQty")).sendKeys(LimitQty);
		
		driver.findElement(By.id("BusinessclassbindabletextboxMaxQtyPerReg")).clear();
		driver.findElement(By.id("BusinessclassbindabletextboxMaxQtyPerReg")).sendKeys(MaxQtyPerReg);
		
		if (!StartTime.equals(""))	driver.findElement(By.id("BusinessclassbindabletextboxStartTime")).sendKeys(StartTime);
		if (!EndTime.equals(""))  driver.findElement(By.id("BusinessclassbindabletextboxEndTime")).sendKeys(EndTime);
		
		if(!Speaker.equals("")){
		sel = new Select(driver.findElement(By.id("ListBoxShowItemsSpeakersAvailable")));
		sel.selectByVisibleText(Speaker);
		driver.findElement(By.id("ImageButtonAddSpeaker")).click();
		}
		
		if (!Location.equals("")) driver.findElement(By.id("businessclassbindabletextboxLocation")).sendKeys(Location);
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
//		utils.getscreenshot(driver, "N:\\QA\\R&DQA\\Selenium\\Chandra Workspace\\Screenshots");
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		
		driver.switchTo().window(firstWindow);
		
		//..........ADDING FEES.........
		
		Thread.sleep(1000);
		System.out.println("Show Item Code = "+Code);
		
		if (!driver.findElement(By.xpath("//a[text()='"+Code+"']/following-sibling::a[2]")).isDisplayed() && SITree.equals("MONDAY")){
			driver.findElement(By.xpath("//a[text()='"+SITree+"']/ancestor::td/preceding-sibling::td/img")).click();
			driver.findElement(By.xpath("//a[text()='"+SISubTree+"']/ancestor::td/preceding-sibling::td/img")).click();
		}
		
		
		if (!driver.findElement(By.xpath("//a[text()='"+Code+"']/following-sibling::a[2]")).isDisplayed())
			
			driver.findElement(By.xpath("//a[text()='"+SITree+"']/ancestor::td/preceding-sibling::td/img")).click();
		
		driver.findElement(By.xpath("//a[text()='"+Code+"']/following-sibling::a[2]")).click();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		int j = 0;
		for(int i=0; i<2; i++){
		driver.findElement(By.id("sa_Registration"+j+"")).click();
		if (i==0) driver.findElement(By.id("Membership"+j+"_0")).click();
		driver.findElement(By.id("Date"+j+"_1")).click();
		driver.findElement(By.id("Amount"+j+"")).sendKeys(EarlyFee);
		j++;
		
		driver.findElement(By.id("sa_Registration"+j+"")).click();
		if (i==0) driver.findElement(By.id("Membership"+j+"_0")).click();
		driver.findElement(By.id("Date"+j+"_0")).click();
		driver.findElement(By.id("Amount"+j+"")).sendKeys(AdvanceFee);
		j++;
		
		driver.findElement(By.id("sa_Registration"+j+"")).click();
		if (i==0) driver.findElement(By.id("Membership"+j+"_0")).click();
		driver.findElement(By.id("Date"+j+"_2")).click();
		driver.findElement(By.id("Amount"+j+"")).sendKeys(OnsiteFee);
		j++;
		}
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();		
		driver.switchTo().window(firstWindow);
		
	}
	
	@DataProvider
	public Object[][] getData2(){
		return TestUtil.getData(suiteBxls, "ShowItemInputs");
	}
	
}
