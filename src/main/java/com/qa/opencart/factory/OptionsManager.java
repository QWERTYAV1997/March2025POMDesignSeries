package com.qa.opencart.factory;

import java.util.Properties;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private EdgeOptions eo;
	private FirefoxOptions fo;
	private static final Logger log = LogManager.getLogger(OptionsManager.class);

	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			log.info("Running test in headless mode");
			co.addArguments("--Headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("Incognito"))) {
			co.addArguments("--Incognito");
		}
		return co;

	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			eo.addArguments("--Headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("Incognito"))) {
			eo.addArguments("--Inprivate");
		}
		return eo;

	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			fo.addArguments("--Headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("Incognito"))) {
			fo.addArguments("--Incognito");
		}
		return fo;

	}

}
