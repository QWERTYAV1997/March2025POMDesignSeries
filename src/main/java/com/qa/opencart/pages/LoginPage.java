package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// private By locator : page objects
	private final By emailID = By.id("input-email");
	private final By pwrd = By.id("input-password");
	private By loginbutton = By.xpath("//input[@class='btn btn-primary']");
	private final By forgotpwdLink = By.linkText("Forgotten Password");
	private final By header = By.tagName("h2");
	private final By registerLink = By.linkText("Register");
	
	private final By loginerrorMag = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	// public constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// public page methods / Actions
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
		log.info("Login page title" + title);
		return title;
	}

	public String getLoginPageURL() {
		String URL = eleUtil.waitForURLIs(AppConstants.LOGIN_PAGE_FRACTION_URL, AppConstants.DEFAULT_SHORT_WAIT);
		log.info("Login page URL:" + URL);
		return URL;
	}

	public boolean FgtPwrdLinkIsExist() {
		return eleUtil.isElementDisplayed(forgotpwdLink);
	}

	public boolean IsHeaderExist() {
		return eleUtil.isElementDisplayed(header);
	}

	@Step("POSITIVE : Login with correct username")
	public AccountsPage doLogin(String AppUsername, String AppPwd) {
		log.info("Application creds :" + AppUsername + " and Password is :" + "************");
		eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(AppUsername);
		eleUtil.doSendKeys(pwrd, AppPwd);
		eleUtil.doClick(loginbutton);
		return new AccountsPage(driver); // By page chaining Model concept
	}

	@Step("NEGATIVE : Login with In-correct username")
	public boolean doLoginWithInvalidCreds(String InvalidUN, String InvalidPwd) {
		log.info("Application creds :" + InvalidUN + " and Password is :" + InvalidPwd);
		WebElement emailEle = eleUtil.waitForElementVisible(emailID, AppConstants.DEFAULT_MEDIUM_WAIT);
		emailEle.clear();
		emailEle.sendKeys(InvalidUN);
		eleUtil.doSendKeys(pwrd, InvalidPwd);
		eleUtil.doClick(loginbutton);
		String errorMsg = eleUtil.doElementGetText(loginerrorMag);

		log.error("Invalid creds error message" + errorMsg);

		if (errorMsg.contains(AppConstants.LOGIN_NO_CREDS_MSG)) {
			return true;
		} else if (errorMsg.contains(AppConstants.LOGIN_INVALID_CREDS_MSG)) {
			return true;
		}
		return false;
	}

	public RegistrationPage navigateToRegisterPage() {
		log.info("trying to navigate to registration page....");
		eleUtil.waitForElementVisible(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegistrationPage(driver);
	}
}
