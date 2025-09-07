package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest {

	// BT(chrome+url) -> BC(login) --> @Test

	@BeforeClass
	public void searchSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void searchTest() {
		searchPage = accPage.doSearch("macbook");
		productPage = searchPage.selectProduct("MacBook Pro");
		String actHeader = productPage.getProductHeader();
		Assert.assertEquals(actHeader, "MacBook Pro");
	}

}