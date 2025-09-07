package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest {

	@DataProvider
	public Object[][] getNegativeLoginData() {
		return new Object[][] { { "testtttttt@gmail.com", "test@123" }, { "march2024@open.com", "test@123" }, { "", "" }

		};
	}

	@Test(dataProvider = "getNegativeLoginData")
	public void negativeLogintest(String InvalidUN, String InvalidPwd) {

		Assert.assertTrue(loginPage.doLoginWithInvalidCreds(InvalidUN, InvalidPwd));
	}

}
