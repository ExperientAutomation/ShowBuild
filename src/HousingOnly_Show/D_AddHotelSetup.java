package HousingOnly_Show;

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
import Setup_Housing_2.TestSuiteBase;
import showbuild.util.BrowserFactory;
import showbuild.util.TestUtil;
import showbuild.util.WinUtils;

public class D_AddHotelSetup extends TestSuiteBase {

	String runmodes[] = null;
	WebDriver driver;
	static int count = -1;
	WinUtils utils = new WinUtils();
	WebDriverWait wait;
	Select sel;
	String firstWindow;

	// Runmode of test case in a suite
	@BeforeClass
	public void checkTestSkip() {

		if (!TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName())) {
			APP_LOGS.debug("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// logs
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
	}

	@Test
	public void A_LoginAndFocusShow() {
		Login login = new Login();
		
		login.testcase_Login("Stage");
//		login.testcase_Login("Prod");
		
		driver = BrowserFactory.getBrowser("Chrome");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@class='inputShowCode']")).sendKeys(CONFIG.getProperty("showcode"));
		driver.findElement(By.xpath("//input[contains(@id,'ButtonStaffSearch')]")).click();
		wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='Show Admin']")));
		Assert.assertTrue(driver.findElement(By.xpath("//td[text()='Show Admin']")).isDisplayed(), "Could not login");
	}

	@Test(dataProvider = "getTestData")
	public void B_AddConfigureHotel(String hotelName, String cat1, String cat2, String cat1r1, String cat1r2, String cat1r3, String cat1r4,
									String cat2r1, String cat2r2, String cat2r3, String cat2r4) throws Exception {

		driver.findElement(By.xpath("//td[text()='Housing']")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("imgHotelSetting")));

		driver.findElement(By.xpath("//div[contains(text(),'Hotel')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ButtonAddHotels")));

		driver.findElement(By.id("ButtonAddHotels")).click();
		firstWindow = driver.getWindowHandle();
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();

		driver.findElement(By.id("TextBoxFacilityName")).sendKeys(hotelName);

		Thread.sleep(1000);
		driver.findElement(By.id("ButtonSearch")).click();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='DataGridHotels']//td/a[contains(text(),'"+hotelName+"')]")));

		driver.findElement(By.id("DataGridHotels_ckbSelectHotel_0")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonAddSelected")).click();

		driver.findElement(By.id("ButtonControl__ButtonClose")).click();
		driver.switchTo().window(firstWindow);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'"+hotelName+"')]")));
		driver.findElement(By.xpath("//a[contains(text(),'"+hotelName+"')]")).click();

		// Details

		wait.until(ExpectedConditions.elementToBeClickable(By.id("a-details")));
		driver.findElement(By.id("a-details")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-details")));

		sel = new Select(driver.findElement(By.id("BusinessclassbindabledropdownlistCompRatio")));
		sel.selectByValue("1_40");

		driver.findElement(By.xpath("//label[text()='Exhibitor Hotels']")).click();
		driver.findElement(By.xpath("//label[text()='Shuttle Available']")).click();

		sel = new Select(driver.findElement(By.id("DropDownListTracking")));
		sel.selectByValue("None");

		Thread.sleep(1000);
		driver.findElement(By.id("ButtonSave")).click();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();

		// Contact

		wait.until(ExpectedConditions.elementToBeClickable(By.id("a-contacts")));
		driver.findElement(By.id("a-contacts")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-contact")));
		driver.findElement(By.xpath("//input[@value='Add New Contact']")).click();

		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();

		driver.findElement(By.id("checkboxPrimary")).click();

		sel = new Select(driver.findElement(By.xpath("//select[contains(@id,'DropDownListSalutation')]")));
		sel.selectByValue("Mr");

		driver.findElement(By.xpath("//input[contains(@id,'TextBoxFirstName')]")).sendKeys("Mahesh");
		driver.findElement(By.xpath("//input[contains(@id,'TextBoxLastName')]")).sendKeys("Mishra");

		driver.findElement(By.xpath("//input[contains(@id,'TextBoxEmail')]")).sendKeys("mah@test.com");
		driver.findElement(By.xpath("//*[contains(@id,'TextBoxComments')]")).sendKeys("Testing");

		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();

		driver.switchTo().window(firstWindow);
		driver.switchTo().defaultContent();

		// Categories
		driver.findElement(By.id("a-categories")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-category")));
		driver.findElement(By.id("btnAddRoomCategory")).click();

		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();

		sel = new Select(driver.findElement(By.id("DropDownListCategoryLookup")));
		sel.selectByValue(cat1);
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);

		sel = new Select(driver.findElement(By.id("DropDownListCategoryLookup")));
		sel.selectByValue(cat2);
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);

		driver.findElement(By.id("ButtonControl__ButtonClose")).click();

		driver.switchTo().window(firstWindow);
		driver.switchTo().defaultContent();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+cat1+"')]")));
		driver.findElement(By.xpath("//a[contains(text(),'"+cat1+"')]")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a-groups")));
		driver.findElement(By.id("a-groups")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-groups")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddNewGroup")));
		driver.findElement(By.id("btnAddNewGroup")).click();

		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();

		driver.findElement(By.id("txtGroupName")).sendKeys("Attendee");
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();

		driver.switchTo().window(firstWindow);
		driver.switchTo().frame(driver.findElement(By.id("frm-groups")));

		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddNewGroup")));
		driver.findElement(By.id("btnAddNewGroup")).click();

		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();

		driver.findElement(By.id("txtGroupName")).sendKeys("Exhibitor");
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("ButtonControl__ButtonClose")).click();

		driver.switchTo().window(firstWindow);
		driver.switchTo().frame(driver.findElement(By.id("frm-groups")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ListBoxAvaliable")));
		sel = new Select(driver.findElement(By.id("ListBoxAvaliable")));
		sel.selectByVisibleText("Attendee");
		sel.selectByVisibleText("Exhibitor");
		driver.findElement(By.id("ButtonAddSubBlock")).click();

		driver.switchTo().defaultContent();
		driver.findElement(By.id("a-beddingtypes")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-beddingtypes")));

		for (int i = 0; i < 5; i++) {

			driver.findElement(By.id("cblBeddingTypes_"+i+"")).click();
		}
		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);

		driver.switchTo().defaultContent();

		driver.findElement(By.xpath("//a[contains(text(),'"+cat2+"')]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("a-groups")));
		driver.findElement(By.id("a-groups")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-groups")));

		driver.findElement(By.xpath("//*[@id='ListBoxAvaliable']/option[text()='Attendee']")).click();
		driver.findElement(By.id("ButtonAddSubBlock")).click();
		Thread.sleep(2000);

		driver.findElement(By.xpath("//*[@id='ListBoxAvaliable']/option[text()='Exhibitor']")).click();
		driver.findElement(By.id("ButtonAddSubBlock")).click();
		Thread.sleep(2000);

		driver.switchTo().defaultContent();
		driver.findElement(By.id("a-beddingtypes")).click();

		driver.switchTo().frame(driver.findElement(By.id("frm-beddingtypes")));

		for (int i = 0; i < 5; i++) {

			driver.findElement(By.id("cblBeddingTypes_"+i+"")).click();
		}
		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);

		driver.switchTo().defaultContent();

		// Add Attendee Types for the Catagory lavel for cat1

		driver.findElement(By.xpath("//a[contains(text(),'"+cat1+"')]/following-sibling::ul//a[contains(text(),'Attendee')]")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a-attendeetypes")));
		driver.findElement(By.id("a-attendeetypes")).click();
		Thread.sleep(1000);

		driver.switchTo().frame(driver.findElement(By.id("frm-attendeetypes")));
		driver.findElement(By.xpath("//label[text()='Attendee']")).click();
		driver.findElement(By.xpath("//label[text()='Exhibitor Block']")).click();
		driver.findElement(By.xpath("//label[text()='Exhibitor Freesale']")).click();

		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();

		driver.findElement(By.xpath("//a[contains(text(),'"+cat1+"')]/following-sibling::ul//a[contains(text(),'Exhibitor')]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a-attendeetypes")));
		driver.findElement(By.id("a-attendeetypes")).click();
		Thread.sleep(1000);

		driver.switchTo().frame(driver.findElement(By.id("frm-attendeetypes")));
		driver.findElement(By.xpath("//label[text()='Attendee']")).click();
		driver.findElement(By.xpath("//label[text()='Attendee Block']")).click();

		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();

		// Add Attendee Types for the Catagory lavel for cat2

		driver.findElement(By.xpath("//a[contains(text(),'"+cat2+"')]/following-sibling::ul//a[contains(text(),'Attendee')]")).click();
		Thread.sleep(1000);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a-attendeetypes")));
		driver.findElement(By.id("a-attendeetypes")).click();
		Thread.sleep(1000);

		driver.switchTo().frame(driver.findElement(By.id("frm-attendeetypes")));
		driver.findElement(By.xpath("//label[text()='Attendee']")).click();
		driver.findElement(By.xpath("//label[text()='Exhibitor Block']")).click();
		driver.findElement(By.xpath("//label[text()='Exhibitor Freesale']")).click();
		
		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();

		driver.findElement(By.xpath("//a[contains(text(),'"+cat2+"')]/following-sibling::ul//a[contains(text(),'Exhibitor')]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a-attendeetypes")));
		driver.findElement(By.id("a-attendeetypes")).click();
		Thread.sleep(1000);

		driver.switchTo().frame(driver.findElement(By.id("frm-attendeetypes")));
		driver.findElement(By.xpath("//label[text()='Attendee']")).click();
		driver.findElement(By.xpath("//label[text()='Attendee Block']")).click();

		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);
		driver.switchTo().defaultContent();
		
		// Define Rates 
		
		driver.findElement(By.xpath("//a[text()='"+hotelName+"']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a-definerates")));
		driver.findElement(By.id("a-definerates")).click();
		
		driver.switchTo().frame(driver.findElement(By.id("frm-definerates")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("ButtonAddNewRatePlan")));
		
		//Add Rates for cat1
		driver.findElement(By.id("ButtonAddNewRatePlan")).click();
		
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("BindableTextBoxRatePlanName")).sendKeys(""+cat1+" Rates");
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRateSetName_0")).sendKeys(""+cat1+" Rates");
		
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt_0")).sendKeys(cat1r1);
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt2_0")).sendKeys(cat1r2);
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt3_0")).sendKeys(cat1r3);
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt4_0")).sendKeys(cat1r4);
		Thread.sleep(1000);
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		
		driver.switchTo().window(firstWindow);
		driver.switchTo().frame(driver.findElement(By.id("frm-definerates")));
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='DataGridItem']/td[text()='"+cat1+" Rates']")).isDisplayed(),"Could not add cat1 Rates");
		
		// Add Rates for cat2
		driver.findElement(By.id("ButtonAddNewRatePlan")).click();
		
		utils.switchIfWindowsAre(driver, 2);
		driver.manage().window().maximize();
		
		driver.findElement(By.id("BindableTextBoxRatePlanName")).sendKeys(""+cat2+" Rates");
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRateSetName_0")).sendKeys(""+cat2+" Rates");
		
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt_0")).sendKeys(cat2r1);
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt2_0")).sendKeys(cat2r2);
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt3_0")).sendKeys(cat2r3);
		driver.findElement(By.id("DataListRateSets_BindableTextBoxRatePlanFeeAmt4_0")).sendKeys(cat2r4);
		Thread.sleep(1000);
		
		driver.findElement(By.id("ButtonControl__ButtonSave")).click();
		Thread.sleep(1000);
		
		driver.switchTo().window(firstWindow);
		driver.switchTo().frame(driver.findElement(By.id("frm-definerates")));
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='DataGridItem']/td[text()='"+cat2+" Rates']")).isDisplayed(),"Could not add "+cat2+" Rates");
		
		// Assign Rates
		
		driver.switchTo().defaultContent();
		driver.findElement(By.id("a-assignrates")).click();
		
		driver.switchTo().frame(driver.findElement(By.id("frm-assignrates")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SearchdatagridAssignRates_BindableDropDownRatePlan_0")));
		
		sel = new Select(driver.findElement(By.id("SearchdatagridAssignRates_BindableDropDownRatePlan_0")));
		sel.selectByVisibleText(""+cat1+" Rates");
		Thread.sleep(1000);
		driver.findElement(By.id("SearchdatagridAssignRates_CategoryImagebutton_0")).click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.switchTo().window(firstWindow);
		driver.switchTo().frame(driver.findElement(By.id("frm-assignrates")));
				
		sel = new Select(driver.findElement(By.id("SearchdatagridAssignRates_BindableDropDownRatePlan_4")));
		sel.selectByVisibleText(""+cat2+" Rates");
		Thread.sleep(1000);
		driver.findElement(By.id("SearchdatagridAssignRates_CategoryImagebutton_4")).click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		Thread.sleep(1000);
		driver.switchTo().window(firstWindow);
		driver.switchTo().frame(driver.findElement(By.id("frm-assignrates")));
				
		driver.findElement(By.id("btnSave")).click();
		Thread.sleep(1000);
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='DataGridItem']/td[text()='"+cat1+" Rates']")).isDisplayed(),"Could not add "+cat1+" Rates");
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='DataGridItem']/td[text()='"+cat2+" Rates']")).isDisplayed(),"Could not add "+cat2+" Rates");
		
		driver.switchTo().defaultContent();	
		
	}
	
	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, this.getClass().getSimpleName());
	}
	
}
