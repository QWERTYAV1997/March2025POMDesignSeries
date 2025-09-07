package com.qa.opencart.tests;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class ProductInfoTest extends BaseTest {

	// BT(Chrome+url) >> BC(login) >> @Test

	@Description("Product page Info test....")
	@Owner("Naveen Automation Labs")
	@Severity(SeverityLevel.NORMAL)
	@BeforeClass
	public void prodInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProducts() {
		return new Object[][] { { "macbook", "MacBook Pro" }, { "samsung", "Samsung SyncMaster 941BW" },
			 };
	}

	
	@DataProvider
	public Object[][] getProductTestdata() {
		return ExcelUtil.getTestData("product");
	}

	@Test(dataProvider = "getProductTestdata")
	public void productHeaderTest(String searchKey, String productname) {
		searchPage = accPage.doSearch(searchKey);
		productPage = searchPage.selectProduct(productname);
		String actHeader = productPage.getProductHeader();
		Assert.assertEquals(actHeader, productname);
	}

	
	@DataProvider
	public Object[][] getProductsImages() {
		return new Object[][] { { "macbook", "MacBook Pro",4}, { "samsung", "Samsung SyncMaster 941BW",1}
		};
	}

	@Test(dataProvider = "getProductsImages")
	public void productImagesCountTest(String searchKey, String productname, int imagesCount) {
		searchPage = accPage.doSearch(searchKey);
		productPage = searchPage.selectProduct(productname);
		int actImagesCount = productPage.getProductImages();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	@Test
	public void productInfoTest() {
		searchPage = accPage.doSearch("macbook");
		productPage = searchPage.selectProduct("MacBook Pro");
		Map<String , String> productMap = productPage.getProductData();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(productMap.get("productName"),"MacBook Pro");
		softAssert.assertEquals(productMap.get("Brand"),"Apple");
		softAssert.assertEquals(productMap.get("Product Code"),"Product 18");
		softAssert.assertEquals(productMap.get("Reward Points"),"800");
		softAssert.assertEquals(productMap.get("Availability"),"Out Of Stock");
		
		
	}
	
	//AAA Pattern : Arrange Act Assert 
	//Only One hard assertion in the test case 
	//but we can have multiple soft assertion 
	
	
	

}
