package PushToProd_3;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import showbuild.base.TestBase;
import showbuild.util.TestUtil;

public class TestSuiteBase extends TestBase{

	// check if the suite ex has to be skiped
		@BeforeSuite
		public void checkSuiteSkip() throws Exception{
			initialize();
			APP_LOGS.debug("Checking Runmode of Suite C");
			if(!TestUtil.isSuiteRunnable(suiteXls, "C Suite")){
				APP_LOGS.debug("Skipped Suite C as the runmode was set to NO");
				throw new SkipException("Runmode of Suite C set to no. So Skipping all tests in Suite C");
			}
			
		}
}
