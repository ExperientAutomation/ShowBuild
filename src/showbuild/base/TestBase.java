package showbuild.base;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import showbuild.util.Xls_Reader;

public class TestBase {
	public static Logger APP_LOGS=null;
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static Properties CREDENTAIL = null;
	public static Xls_Reader suiteXls=null;
	public static Xls_Reader suiteAxls=null;
	public static Xls_Reader suiteBxls=null;
	public static Xls_Reader suiteCxls=null;
	public static Xls_Reader suiteDxls=null;
	public static boolean isInitalized=false;
	
	
	// initializing the Tests
	public void initialize() throws Exception{
	
		// logs
		try{
		if(!isInitalized){
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		
		//config
		CONFIG = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"//src//showbuild//config//config.properties");
		CONFIG.load(ip);
		
		// If we have one more config file then below script is used. (not using this script )
//		OR = new Properties();
//		ip = new FileInputStream(System.getProperty("user.dir")+"//src//showbuild//config//OR.properties");
//		OR.load(ip);
//		
		//credentails
		CREDENTAIL = new Properties();
		FileInputStream creip = new FileInputStream("N:\\QA\\R&DQA\\Selenium\\GlobalCredentials\\LoginCredentials.properties");
		CREDENTAIL.load(creip);

		APP_LOGS.debug("Loading XLS Files");

		// xls file
		suiteAxls = new Xls_Reader(System.getProperty("user.dir")+"//src//showbuild//xls//A suite.xlsx");
		suiteBxls = new Xls_Reader(System.getProperty("user.dir")+"//src//showbuild//xls//B suite.xlsx");
		suiteCxls = new Xls_Reader(System.getProperty("user.dir")+"//src//showbuild//xls//C suite.xlsx");
		suiteDxls = new Xls_Reader(System.getProperty("user.dir")+"//src//showbuild//xls//D suite.xlsx");
		suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//showbuild//xls//Suite.xlsx");

		isInitalized=true;
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
}
