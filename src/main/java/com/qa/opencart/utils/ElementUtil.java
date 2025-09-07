package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.LoginPage;

import io.qameta.allure.Step;
import com.qa.opencart.exception.ElementException;
import com.qa.opencart.utils.JavaScriptUtil;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	private static final Logger log = LogManager.getLogger(ElementUtil.class);

	// 1.
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	// 2.
	public void doSendKeys(By locator, String value) {
        log.info("entering the value : "+value+"into locator : "+ locator);
		if (value == null) {
			log.error("value : "+value+"is null....");
			throw new ElementException("===Value can not be null====");
		}
		WebElement ele = getElement(locator);
		ele.clear();
		getElement(locator).sendKeys(value);

	}

	// 3.
	public void doMultipleSendKeys(By locator, CharSequence... value) {

		if (value == null) {
			throw new ElementException("===Value can not be null====");
		}

		getElement(locator).sendKeys(value);

	}

	// 4.
	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	// 5.
	@Step("checking the element is displayed on the page..")
	public boolean isElementDisplayed(By locator) {

		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed on page :" + locator);
			return false;
		}
	}
	
	
	public boolean isElementDisplayed(WebElement element) {

		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed on page :" + element);
			return false;
		}
	}

	// 6.
	public boolean isElementEnabled(By locator) {

		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed on page :" + locator);
			return false;
		}
	}

	// 7.
	public int getElementscount(By locator) {
		return getElements(locator).size();
	}

	// 8.
	public List<WebElement> getElements(By locator) {
		return  driver.findElements(locator);
	}

	// 9.
	public List<String> getElementsTextList(By locator) {

		List<WebElement> eleList = getElements(locator);
		// Top-casting
		List<String> eletextlist = new ArrayList<String>();// pc=0 , vc=10

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eletextlist.add(text);
			}
		}
		return eletextlist;
	}

	// 10.
	public void doClick(By locator) {
		log.info("clicking on element using : "+locator);
		getElement(locator).click();
	}

	// 11.
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		log.info("element is found using locator"+locator);
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;

	}

	// This method for one only
	// 12.
	public boolean isElementExist(By locator) {
		if (getElementscount(locator) == 1) {
			System.out.println("the element : " + locator + " is present on the page one time ");
			return true;
		} else {
			System.out.println("the element : " + locator + " is not present on the page  ");
			return false;
		}

	}

	// Overloaded for multiple elements count
	// 13.
	public boolean isElementExist(By locator, int ExpectedEleCount) {
		if (getElementscount(locator) == ExpectedEleCount) {
			System.out.println("the element : " + locator + " is present on the page " + ExpectedEleCount + " time ");
			return true;
		} else {
			System.out.println(
					"the element : " + locator + " is not present on the page  " + ExpectedEleCount + " time ");
			return false;
		}

	}

	// 14.
	public boolean isElementcheck(By locator) {

		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			throw new ElementException("====ELEMENT NOT FOUND====");
		}
	}

	// 15.
	public void clickElement(By locator, String eleText) {

		List<WebElement> elelists = getElements(locator);
		System.out.println("total number of elements  : " + elelists.size());

		for (WebElement e : elelists) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(eleText)) {
				e.click();
				break;
			}

		}

	}

	// 16.
	public void doSearch(By searchlocator, String searchKey, By suggLocator, String suggValue)
			throws InterruptedException {

		doSendKeys(searchlocator, searchKey);
		Thread.sleep(4000);

		List<WebElement> sugglist = getElements(suggLocator);
		System.out.println("total number of suggestions : " + sugglist.size());

		for (WebElement e : sugglist) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggValue)) {// getting org.openqa.selenium.ElementClickInterceptedException:
				e.click();
				break;
			}
		}
	}

//******************************Select Drop Down menu sections**************************//

	// 17. -----------------Select Utils-------------------------------
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

//18.
	public void doSelectByVisibletext(By locator, String eletext) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(eletext);
	}

//19.
	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

//20.   -------------------------Dropdown utils-------------------------
	public int getDropDownOptionsCount(By Locator) {
		Select select = new Select(getElement(Locator));
		return select.getOptions().size();

	}

//21.
	public List<String> getDropDownValueLists(By Locator) {
		Select select = new Select(getElement(Locator));
		List<WebElement> optionsLists = select.getOptions();
		System.out.println("total no. of options" + optionsLists.size());

		List<String> optionsValueLists = new ArrayList<String>();
		for (WebElement e : optionsLists) {
			String text = e.getText();
			optionsValueLists.add(text);
		}
		return optionsValueLists;

	}

//22.
	public void selectDropdownvalue(By locator, String value) {

		List<WebElement> selectele = getElements(locator);

		System.out.println(selectele.size());

		for (WebElement e : selectele) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				break;

			}

		}

	}

	// 23.
	public String getFirstSelecteddropdownoption(By locator) {
		Select select = new Select(getElement(locator));
		return select.getFirstSelectedOption().getText();
	}

	// 24. ---ACTION UTILS-------------
	public void menuSubMenuHandlinglevel2(By parentMenu, By childmenu) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenu)).perform();
		Thread.sleep(2000);
		doClick(childmenu);
	}

	// 25.
	public void menuSubmenuLevel4handling(By menuLevel1, By menuLevel2, By menuLevel3, By menuLevel4, String actionType)
			throws InterruptedException {

		Actions act = new Actions(driver);
		if (actionType.equalsIgnoreCase("Click")) {
			doClick(menuLevel1);
		} else if (actionType.equalsIgnoreCase("mousehover")) {
			act.moveToElement(getElement(menuLevel1)).perform();
		}

		driver.findElement(menuLevel1).click();
		doClick(menuLevel1);
		Thread.sleep(4000);
		act.moveToElement(getElement(menuLevel2)).perform();
		Thread.sleep(4000);
		act.moveToElement(getElement(menuLevel3)).perform();
		Thread.sleep(4000);
		doClick(menuLevel4);
	}

	// 26.
	public void doActionsSendkeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	// 27.
	public void doActionsclick(By locator) {
		act.click(getElement(locator)).perform();
	}

	// 28 .
	public void doSendkeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char val[] = value.toCharArray();
		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}

	}

	// 29.
	public WebElement waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
	}

	// An expectation for checking that an element is present on the DOM of a page.
	// This does not necessarily mean that the element is visible.
	// 30.
	@Step("waiting for element :{0} visible within the timeout: {1}")	
	public WebElement waitForElementVisible(By locator, int timeout) {
		log.info("waiting for element using By locator : " + locator + "within timeout : " + timeout);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsUtil.flash(element);
		}
		return element;
	}

	// visibility : An expectation for checking that an element is present on the
	// DOM of a page and visible.Visibility means that the element is not only
	// displayed
	// but also has a height and width that isgreater than 0.

	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(int timeout) {
		return waitForAlert(timeout).getText();
	}

	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public void sendKeysAlert(int timeout, String value) {
		waitForAlert(timeout).sendKeys(value);
	}

	public String waitForTitleContains(String fractionTitlevalue, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitlevalue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("expected title value" + fractionTitlevalue + "is not present");
		}
		return driver.getTitle();
	}

	@Step("waiting for page title with expected value : {0}")
	public String waitForTitleIs(String expectedTitlevalue, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleIs(expectedTitlevalue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("expected title value " + expectedTitlevalue + " is not present");
		}
		return driver.getTitle();
	}

	@Step("waiting for page title with expected value : {0}")
	public String waitForURLIs(String expectedURLvalue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlToBe(expectedURLvalue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("expected URL value " + expectedURLvalue + " is not present");
		}
		return driver.getCurrentUrl();
	}

	public String waitURLContains(String fractionURLvalue, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURLvalue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("expected title value" + fractionURLvalue + "is not present");
		}
		return driver.getTitle();

	}

	public boolean waitforWindows(int expectedWindowsCount, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedWindowsCount));
		} catch (TimeoutException e) {
			System.out.println("expected number of winodows are not correct");
			return false;
		}

	}

	public boolean waitForFrame(By frameLocator, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	public boolean waitForFrame(int frameIndex, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	public boolean waitForFrame(String frameNameOrID, int timeout) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
			return true;
		} catch (TimeoutException e) {
			System.out.println("frame is not present on the page");
			return false;
		}

	}

	public List<WebElement> WaitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

		// presenceOfAllElementsLocatedBy - An expectation for checking if the given
		// text is present in the specified element.
	}

	public List<WebElement> WaitForElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

		/**
		 * An expectation for checking that all elements present on the web page that
		 * match the locator are visible. Visibility means that the elements are not
		 * only displayed but also have a height and width that is greater than 0.
		 */
	}

	public void clickElementWhenItsReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator));

		/**
		 * An expectation for checking an element is visible and enabled such that you
		 * can click it.
		 */

	}

	// ********************FLUENT WAIT UTILS*********************************

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingtime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				// wait is written with fluent wait features
				.pollingEvery(Duration.ofSeconds(pollingtime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("==Element not visible on page==");

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementPresenceFluentWait(By locator, int timeout, int pollingtime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				// wait is written with fluent wait features
				.pollingEvery(Duration.ofSeconds(pollingtime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("==Element not present on page==");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForFramewithFluentWait(By frameLocator, int timeout, int pollingtime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				// wait is written with fluent wait features
				.pollingEvery(Duration.ofSeconds(pollingtime)).ignoring(NoSuchFrameException.class)
				.withMessage("==Frame not visible on page==");

		wait.until(ExpectedConditions.presenceOfElementLocated(frameLocator));
	}

	public Alert waitForAlertFluentWait(int timeout, int pollingtime) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				// wait is written with fluent wait features
				.pollingEvery(Duration.ofSeconds(pollingtime)).ignoring(NoAlertPresentException.class)
				.withMessage("==Alert not visible on page==");

		return wait.until(ExpectedConditions.alertIsPresent());
	}

}
