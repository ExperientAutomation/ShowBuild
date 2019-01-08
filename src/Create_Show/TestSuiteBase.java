package Create_Show;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import showbuild.base.TestBase;
import showbuild.util.TestUtil;

public class TestSuiteBase extends TestBase{
    // check if the suite ex has to be skiped
	@BeforeSuite
	public void checkSuiteSkip() throws Exception{
		initialize();
		APP_LOGS.debug("Checking Runmode of Suite A");
		if(!TestUtil.isSuiteRunnable(suiteXls, "A Suite")){
			APP_LOGS.debug("Skipped Suite A as the runmode was set to NO");
			throw new SkipException("Runmode of Suite A set to no. So Skipping all tests in Suite A");
		}
		
	}
	
//	TestSuiteBase()throws Exception{
//		initialize();
//		APP_LOGS.debug("Checking Runmode of Suite A");
//		if(!TestUtil.isSuiteRunnable(suiteXls, "A Suite")){
//			APP_LOGS.debug("Skipped Suite A as the runmode was set to NO");
//			throw new SkipException("Runmode of Suite A set to no. So Skipping all tests in Suite A");
//		}
//		
//		
//	}
//	
}
