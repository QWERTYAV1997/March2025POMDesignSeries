package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class CommonsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// public constructor
	public CommonsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private final By logo = By.cssSelector("img.img-responsive");
	private final By search = By.name("search");
	private final By footerslinks = By.cssSelector("footer li a");

	public boolean isLogoExists() {
		return eleUtil.isElementDisplayed(logo);
	}

	public boolean isSearchExists() {
		return eleUtil.isElementDisplayed(search);
	}

	public List<String> footerExists() {
		List<WebElement> footerList = eleUtil.WaitForElementsVisible(footerslinks, AppConstants.DEFAULT_MEDIUM_WAIT);
		List<String> footerListValue = new ArrayList<String>();
		for (WebElement e : footerList) {
			String text = e.getText();
			footerListValue.add(text);
		}
		return footerListValue;
	}

}
