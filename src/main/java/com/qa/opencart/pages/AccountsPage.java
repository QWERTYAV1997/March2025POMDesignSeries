package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// Account creds : march2024@open.com/Selenium@12345

	private final By headers = By.cssSelector("div#content h2");
	private final By logoutlink = By.linkText("Logout");
	private final By search = By.name("search");
	private final By searchicon = By.cssSelector("div#search button");

	public List<String> getAccPageheaders() {

		List<WebElement> headerList = eleUtil.WaitForElementsPresence(headers, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("total number of headers are as : " + headerList.size());
		List<String> headersValList = new ArrayList<String>();
		for (WebElement e : headerList) {
			String text = e.getText();
			headersValList.add(text);
		}
		return headersValList;
	}

	public boolean LogoutLinkIsExist() {
		WebElement logoutEle = eleUtil.waitForElementVisible(logoutlink, AppConstants.DEFAULT_MEDIUM_WAIT);
		return eleUtil.isElementDisplayed(logoutEle);
	}

	public SearchResultPage doSearch(String searchkey) {
		System.out.println("search key --->"+searchkey);
		WebElement searchele = eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_SHORT_WAIT);
		searchele.clear();
		searchele.sendKeys(searchkey);
		eleUtil.doClick(searchicon);	
        return new SearchResultPage(driver);
	}

}
