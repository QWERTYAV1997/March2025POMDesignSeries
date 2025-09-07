package com.qa.opencart.tests;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;


@Epic("EP-01: Design the Open Cart App Page")
@Feature("F-002: design open cart Account Registration feature")
@Story("US-02: develop register core features: title, url, user is able to register")
@Epic("Design of Register page test")

public class AccountsPageTests extends BaseTest {

	// Run : BeforeTest than BeforeClass that @Test 

	WebDriver driver;

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));	
	}

	//--------------------------------------------------------------------------------------------

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.LogoutLinkIsExist());
	}
	
	//--------------------------------------------------------------------------------------------

	@Test
	public void accPageHeaders() {
		List<String> actualheaderlist = accPage.getAccPageheaders();
		Assert.assertEquals(actualheaderlist.size(), AppConstants.ACC_PAGE_HEADERS_COUNT);
		Assert.assertEquals(actualheaderlist, AppConstants.expectedAccPageHeadersList);
	}

}
