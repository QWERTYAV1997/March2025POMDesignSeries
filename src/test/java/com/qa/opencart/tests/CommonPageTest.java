package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class CommonPageTest extends BaseTest {

	@Test
	public void checkCommonElementsOnLoginPageTest() {

		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		SoftAssert softAssert = new SoftAssert();
		Assert.assertTrue(commonspage.isLogoExists());
		Assert.assertTrue(commonspage.isSearchExists());
		List<String> footersList = commonspage.footerExists();
		Assert.assertEquals(footersList.size(), AppConstants.DEFAULT_FOOTERLINKS_COUNT);
		softAssert.assertAll();
	}
	@Test
	public void checkCommonElementsOnAccountsPageTest() {

		SoftAssert softAssert = new SoftAssert();
		Assert.assertTrue(commonspage.isLogoExists());
		Assert.assertTrue(commonspage.isSearchExists());
		List<String> footersList = commonspage.footerExists();
		Assert.assertEquals(footersList.size(), AppConstants.DEFAULT_FOOTERLINKS_COUNT);
		softAssert.assertAll();
	}

}
