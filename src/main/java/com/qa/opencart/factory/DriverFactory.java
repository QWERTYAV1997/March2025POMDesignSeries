package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlightEle;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	public OptionsManager optionsmanager;

	/**
	 * This method is init the driver on the basis of browser
	 * 
	 * @param prop2
	 * @return it returns the driver
	 */
	public WebDriver initDriver(Properties prop) {
		
		String browsername = prop.getProperty("browser");
		
		//System.out.println("browser name: " + browsername);
		log.info("browser name : " + browsername);

		//ChainTestListener.log("Running browser : " + browsername);

		highlightEle = prop.getProperty("highlight");
		optionsmanager = new OptionsManager(prop);

		switch (browsername.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsmanager.getChromeOptions()));
			break;

		case "edge":
			tlDriver.set(new EdgeDriver(optionsmanager.getEdgeOptions()));
			break;

		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsmanager.getFirefoxOptions()));
			break;

		case "safari":
			tlDriver.set(new SafariDriver());
			break;

		default:
			// System.out.println(AppError.INVALID_BROWSER_MSG +":"+browsername);
			log.error(AppError.INVALID_BROWSER_MESG + ":" + browsername);
			FrameworkException fe = new FrameworkException(AppError.INVALID_BROWSER_MESG + " : " + browsername);
			log.error("Exception occurred while initializing driver: ", fe);
			throw new FrameworkException("===INVALID BROWSER===");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	/**
	 * This is used to get the local copy of the driver .......
	 * 
	 */
	//RUN>>IT
	//mvn clean install -Denv="dev" -Dsurefire.suiteXmlFiles=src/tests/resources/testRunners/testng_sanity.xml
	//mvn clean install -Denv="dev" -Dsurefire.suiteXmlFiles=src/tests/resources/testRunners/testng_regression.xml
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method initial prop with properties file .......
	 * 
	 */
	// mvn clean install -Denv="qa"
	// mvn clean install

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		log.info("Env. name=====>>>" + envName);
		try {
			if (envName == null) {
				log.warn("no env. is passed , hence runnig the testcases on qa env.");
				ip = new FileInputStream("./src/tests/resources/config/config.qa.properties");
			}

			else {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/tests/resources/config/config.qa.properties");
					break;

				case "stage":
					ip = new FileInputStream("./src/tests/resources/config/config.stage.properties");
					break;

				case "dev":
					ip = new FileInputStream("./src/tests/resources/config/config.dev.properties");
					break;

				case "uat":
					ip = new FileInputStream("./src/tests/resources/config/config.uat.properties");
					break;

				case "prod":
					ip = new FileInputStream("./src/tests/resources/config/config.properties");
					break;

				default:
					log.error("Env. value is wrong pls pass the right environment...");
					throw new FrameworkException("===INVALID ENV===");
				}
			}
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * takescreenshot
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}
