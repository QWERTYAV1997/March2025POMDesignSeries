package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EP-01: Design the Open Cart App Login Page")
@Feature("F-001: design open cart login feature")
@Story("US-01: develop login core features: title, url, user is able to login")
@Epic("Design of login page test")

public class LoginPageTest extends BaseTest {

	@Description("login page title test....")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		ChainTestListener.log("login Page title : " + actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Description("forgot password link exist test....")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		ChainTestListener.log("login Page url : " + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Description("forgot password link exist test....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isForgotwdlinkExists() {
		Assert.assertTrue(loginPage.FgtPwrdLinkIsExist());
	}

	@Description("login page header test....")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void isLoginPageheaderExists() {
		Assert.assertTrue(loginPage.IsHeaderExist());
	}

	@Description("user is able to login to app with the correct creds....")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.LogoutLinkIsExist());
	}

	// Sequence of Running test cases in TestNg is in Alphabetical order
	// Add More Test cases for the Login Page

}
