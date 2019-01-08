package showbuild.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class WinUtils {

	public static boolean isElementPresent(WebDriver driver, By by) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	public void switchIfWindowsAre(WebDriver driver, int num) throws Exception {
		int count = 0;
		while (count < 10) {
			if (driver.getWindowHandles().size() == num) {
				for (String handle : driver.getWindowHandles()) {
					driver.switchTo().window(handle);
					Thread.sleep(100);
				}
				break;
			} else {
				Thread.sleep(1000);
				count++;
			}
		}
	}

	public void getscreenshot(WebDriver driver, String path) throws Exception {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in d drive with name
		// "screenshot.png"
		FileUtils.copyFile(scrFile, new File(path + "\\screenshot.png"));
	}

	public void emailscreenshot(WebDriver driver, String methodName) {
		
		emailScheduler sendReport = new  emailScheduler();
	
		// Take screenshot and store as a file format
		File src1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile
			// //method
			FileUtils.copyFile(src1, new File(
					"N:/QA/R&DQA/Selenium/Scheduler Login/Screenshots/" + System.currentTimeMillis() + ".png"));
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		File screenshot = latestFile.lastFileModified("N:/QA/R&DQA/Selenium/Scheduler Login/Screenshots/");
		sendReport.sendEmail(screenshot.getAbsolutePath(), methodName);
	}

}
