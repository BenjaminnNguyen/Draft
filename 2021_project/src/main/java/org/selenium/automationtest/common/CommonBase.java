package org.selenium.automationtest.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.json.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.net.HttpURLConnection;

import static org.selenium.automationtest.common.TestLogger.*;
import static org.testng.Assert.assertFalse;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.viettel.common.ObjectClientChannel;
import com.viettel.common.ExchMsg;
import com.viettel.common.ViettelService;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptorExtension;

/**
 * @author lientm
 * @date: 18-Nov-2014
 * @update: 28-Aug-2020
 */
public class CommonBase {
	public WebDriver driver;
	protected String baseUrl = "https://vinvoice.viettel.vn";
	protected int DEFAULT_TIMEOUT = 60000;
	protected int WAIT_INTERVAL = 1000;
	public int loopCount = 0;
	public final int ACTION_REPEAT = 5;
	public Actions action;
	private FTPClient ftpClient;
	private int returnCode;
	private String hostExchange = "10.58.34.5";
	private int portExchange = 8060;
	private String userExchange = "prm";
	private String passExchange = "prm";
	private String hostService = "10.58.34.5";
	private int portService = 8065;
	private String userService = "cm_owner";
	private String passService = "cm_owner";

	static ObjectClientChannel channelExchange, channelService;
	static Session session;
	SftpProgressMonitor monitor;

	/**
	 * init Driver
	 * 
	 * @param URL
	 */
	public WebDriver initDriverTest(String... URL) {
		String url1 = URL.length > 0 ? URL[0] : baseUrl;
		String proName = URL.length > 2 ? URL[2] : "";
		String broName = URL.length > 1 ? URL[1] : "firefox";
		String url2 = System.getProperty("Url");
		String browser = System.getProperty("browser");
		String plaForm = System.getProperty("platForm");
		WebDriver dr = null;

		if (browser == null) {
			browser = broName;
		}
		if ("chrome".equals(browser)) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\driver\\chromedriver.exe");
			dr = new ChromeDriver();
		} else if ("iexplorer".equals(browser)) {
			dr = new InternetExplorerDriver();
		} else if ("safari".equals(browser)) {
			dr = new SafariDriver();
		} else {
			if (proName != null && proName != "") {
				// FirefoxProfile ffprofile = new FirefoxProfile(new File(proName));
				dr = new FirefoxDriver();
			}
		}
		if (url2 != "" && url2 != null) {
			dr.get(url2);
		} else {
			dr.get(url1);
		}
		dr.manage().window().maximize();
		return dr;
	}

	/**
	 * init Driver
	 * 
	 * @param URL
	 */
//	public WebDriver initDriverTest(String... URL) {
//		String url = System.getProperty("Url") != null ? System.getProperty("Url"): URL[0];
//		String browser = System.getProperty("browser") != null ? System.getProperty("browser"): URL[1];
//		WebDriver dr = null;
//		
//		if (browser.equalsIgnoreCase("chrome")){
//			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "driver" + File.separator + "chromedriver.exe");
//			dr = new ChromeDriver();
//		    dr.get("chrome://settings/clearBrowserData");
//		    dr.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
//		    pause(2000);
//		}else if (browser.equalsIgnoreCase("sfive")){
//			ChromeOptions options = new ChromeOptions();
//			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "driver" + File.separator + "chromedriver.exe");
//			options.setBinary("C:\\Program Files (x86)\\Viettel\\SFive\\Application\\sfive.exe");
//			options.addArguments("enable-vsa-modify-header");
//			options.addArguments("user-data-dir=" + System.getProperty("user.dir") + File.separator + "profile" + File.separator + "sfive_profile");
//			dr = new ChromeDriver(options);
//		    dr.get("chrome://settings/clearBrowserData");
//		    dr.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
//		    pause(2000);
//		}else {		
//			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + File.separator + "driver" + File.separator + "geckodriver.exe");
//			FirefoxProfile fxProfile = new FirefoxProfile();
//			fxProfile.setPreference("browser.download.folderList", 2);
//			fxProfile.setPreference(
//					"browser.download.manager.showWhenStarting", false);
//			fxProfile.setPreference("browser.download.dir", System.getProperty("user.dir") + File.separator + "downloads");
//			fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
//							"text/csv,application/vnd.android.package-archive,application/apk,application/java-archive,application/apk,application/java-archive,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,image/png,application/pdf,image/jpeg,application/excel");
//	        FirefoxOptions firefoxOptions = new FirefoxOptions();
//	        firefoxOptions.setProfile(fxProfile);
//			dr = new FirefoxDriver(firefoxOptions);
//		}
//		dr.get(url);
//		dr.manage().window().maximize();
//		return dr;
//	}

	/**
	 * 
	 * @param driver
	 */
	public void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout khi cho trang web hoan thanh load");
		}
	}

	/**
	 * Open page
	 * 
	 * @param pageUrl
	 * @param driver
	 */
	public void openPage(String pageUrl, WebDriver... DR) {
		WebDriver dr = DR.length > 0 ? DR[0] : driver;
		String urlSys = System.getProperty("Url");
		info("Url sys" + urlSys);
		if (pageUrl != null && !pageUrl.contains("http://")) {
			if (urlSys != null && urlSys != "") {
				dr.get(urlSys + "/" + pageUrl);
				info("vao nhanh sys " + urlSys + "/" + pageUrl);
			} else {
				dr.get(baseUrl + "/" + pageUrl);
				info("vao nhanh base " + baseUrl + "/" + pageUrl);
			}
		} else {
			dr.get(pageUrl);
		}
//		waitForPageLoaded(driver);
		pause(1000);
	}

	/**
	 * Open page at not loaded status, as clear cache
	 * 
	 * @param pageUrl
	 * @param driver
	 */
	public void openPageNotLoad(String pageUrl, WebDriver driver) {
		if (pageUrl != null) {
			driver.get(pageUrl);
			pause(2000);
		}
	}

	/**
	 * pause driver in timeInMillis
	 * 
	 * @param timeInMillis
	 */
	public void pause(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param locator
	 * @return
	 */
	public WebElement getElement(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebElement elem = null;
		try {
			elem = driver.findElement(by);
		} catch (NoSuchElementException e) {
			checkCycling(e, 10);
			pause(WAIT_INTERVAL);
			getElement(locator);
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 10);
			pause(WAIT_INTERVAL);
			getElement(locator);
		}
		return elem;
	}

	/**
	 * get a display element in web page
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getDisplayedElement(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebElement e = null;
		try {
			if (by != null)
				e = driver.findElement(by);
			if (e != null) {
				if (isDisplay(by))
					return e;
			}
		} catch (NoSuchElementException ex) {
			checkCycling(ex, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		} finally {
			loopCount = 0;
		}
		return null;
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> getListElement(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		List<WebElement> elementOptions;
		try {
			elementOptions = driver.findElements(by);
			return elementOptions;
		} catch (NoSuchElementException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			getListElement(locator);
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			getListElement(locator);
		} finally {
			loopCount = 0;
		}
		return null;
	}

	/**
	 * lay cac gia tri thuoc tinh cua 1 mang doi tuong element
	 * 
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public String[] getAttOfListElement(Object locator, String attribute) {
		String[] att = new String[20];
		List<WebElement> list;
		list = getListElement(locator);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				att[i] = list.get(i).getAttribute(attribute);
			}
		}
		return att;
	}

	/**
	 * checking an element is displayed in web page
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isDisplay(Object locator) {
		boolean bool = false;
		WebElement e = getElement(locator);
		try {
			if (e != null)
				bool = e.isDisplayed();
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			isDisplay(locator);
		} finally {
			loopCount = 0;
		}
		return bool;
	}

	/**
	 * check repeat times
	 * 
	 * @param e
	 * @param loopCountAllowed
	 */
	public void checkCycling(Exception e, int loopCountAllowed) {
		info("Co exception xay ra: " + e.getClass().getName());
		if (loopCount > loopCountAllowed) {
			driver.manage().deleteAllCookies();
			Assert.fail("Qua thoi gian nhung khong thay hoac thay doi tuong " + e.getMessage());
		}
		info("Lap lai lan thu " + loopCount);
		loopCount++;
	}

	/**
	 * get an element that present in Web Page
	 * 
	 * @param locator
	 * @param opParams
	 * @return
	 */
	public WebElement getElementPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1] : 1;
		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;
		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2) {
				elem = getElement(locator);
			} else {
				elem = getDisplayedElement(locator);
			}
			if (null != elem)
				return elem;
			pause(WAIT_INTERVAL);
		}
		if (isAssert == 1) {
			String date = getDateTime("yyyyMMddHHmmss");
			info("date");
			captureScreen(driver, "Loi_" + date + ".jpg");
			assert false : ("Qua thoi gian " + timeout + "ma khong tim thay doi tuong " + locator);
			quitDriver(driver);
		}
		return null;
	}

	/**
	 * input data to element
	 * 
	 * @param locator
	 * @param value
	 * @param validate
	 */
	public void type(Object locator, String value, boolean validate, boolean... clear) {
		boolean clean = clear.length > 0 ? clear[0] : true;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			for (int loop = 1;; loop++) {
				if (loop >= ACTION_REPEAT) {
					Assert.fail("Qua thoi gian khi input du lieu: " + value + " vao doi tuong " + locator);
				}
				WebElement element = getElementPresent(locator, 10000, 0);
				if (element != null) {
					wait.until(ExpectedConditions.visibilityOf(element));
					if (clean)
						element.clear();
//				element.click();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator))) {
						break;
					}
				}
				info("Lap lai tac dong input text lan thu " + loop);
				pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} catch (NoSuchElementException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} finally {
			loopCount = 0;
		}
	}

	public void typeElementIfDisplay(Object obj, String value, int... opParams) {
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		if (getElementPresentNoAssert(obj, timeout) != null) {
			type(obj, value, true, true);
		}
	}

	/**
	 * get value of element in web page
	 * 
	 * @param locator
	 * @return
	 */
	public String getValue(Object locator, Object... opParams) {
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		try {
			return getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay).getAttribute("value");
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * click on an element
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void click(Object locator, Object... opParams) {
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		Actions actions = new Actions(driver);
		try {
			WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
			if (element.isEnabled()) {
				actions.click(element).perform();
			} else {
				info("Element is not enabled");
//				click(locator, notDisplay);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} catch (NoSuchElementException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 * @return
	 */
	public WebElement waitForElementNotPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1] : 1;
		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;

		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2) {
				elem = getElement(locator);
			} else {
				elem = getDisplayedElement(locator);
			}
			if (elem == null) {
				return null;
			}
			pause(WAIT_INTERVAL);
		}
		if (isAssert == 1) {
			assert false : ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
		}
		return elem;
	}

	public void checkElementNotPresent(Object locator, int... opParams) {
		WebElement ele;
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			try {
				ele = driver.findElement(by);
				if (tick == timeout / WAIT_INTERVAL && ele != null) {
					Assert.fail("Qua thoi gian " + timeout + " nhung doi tuong " + locator + " van dang xuat hien");
				}
			} catch (NoSuchElementException e) {
				pause(WAIT_INTERVAL);
			} catch (StaleElementReferenceException e) {
				pause(WAIT_INTERVAL);
			}
		}
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void check(Object locator, int... opParams) {
		int notDisplayE = opParams.length > 0 ? opParams[0] : 0;
		Actions actions = new Actions(driver);
		try {
			WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplayE);
			boolean a = element.getAttribute("class").contains("ui-state-active");
			if (!element.isSelected() && !a) {
				actions.click(element).perform();
			} else {
				info("Element " + locator + " is already checked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			check(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void uncheck(Object locator, int... opParams) {
		int notDisplayE = opParams.length > 0 ? opParams[0] : 0;
		Actions actions = new Actions(driver);
		try {
			WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplayE);

			if (element.isSelected()) {
				actions.click(element).perform();
			} else {
				info("Element " + locator + " is already unchecked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			uncheck(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * @param locator
	 */
	public void doubleClick(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = getElementPresent(locator);
			actions.doubleClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			doubleClick(locator);
		} finally {
			loopCount = 0;
		}
	}

	public void contextClick(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = getElementPresent(locator);
			actions.contextClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			contextClick(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * get text of element
	 * 
	 * @param locator
	 * @return
	 */
	public String getText(Object locator) {
		WebElement element = null;
		try {
			element = getElementPresent(locator);
			return element.getText();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getText(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * 
	 * @param locator
	 * @param safeToSERE
	 * @param opParams
	 */
	public void mouseOver(Object locator, boolean safeToSERE, Object... opParams) {
		WebElement element;
		Actions actions = new Actions(driver);
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		try {
			if (safeToSERE) {
				for (int i = 1; i < ACTION_REPEAT; i++) {
					info("Thuc hien mouserover repeat lan thu " + i);
					element = getElementPresent(locator, 2000, 0, notDisplay);
					info("Doi tuong " + element);
					if (element == null) {
						pause(WAIT_INTERVAL);
					} else {
						info("Thuc hien action");
						actions.moveToElement(element).build().perform();
						break;
					}
				}
			} else {
				element = getElementPresent(locator);
				actions.moveToElement(element).build().perform();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			mouseOver(locator, safeToSERE);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void mouseOverAndClick(Object locator, Object... opParams) {
		WebElement element;
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		Actions actions = new Actions(driver);
		try {
			element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
			actions.moveToElement(element).click(element).build().perform();
		} catch (StaleElementReferenceException e) {
			mouseOverAndClick(locator, opParams);
		}
	}

	/**
	 * quit driver if driver existed
	 * 
	 * @param dr
	 */
	public void quitDriver(WebDriver dr) {
		if (dr.toString().contains("null")) {
			System.out.print("All Browser windows are closed ");
		} else {
			driver.manage().deleteAllCookies();
			dr.quit();
		}
	}

	/**
	 * switch to a frame
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void switchToFrame(Object locator, Object... opParams) {
		try {
			WebElement e = getElementPresentNoAssert(locator);
			if (e != null) {
				driver.switchTo().frame(e);
			} else {
				info("frame does not exists");
			}

		} catch (Exception e) {
			info("Can not switch frame");
		}
	}

	/**
	 * back to main frame
	 */
	public void switchToParentFrame() {
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			switchToParentFrame();
		}
	}

	/**
	 * accept unexpected alert
	 */
	public void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();

		} catch (NoAlertPresentException ex) {
			info("No Alert present");
			;
		}
	}

	/**
	 * get datetime
	 * 
	 * @param format
	 */
	public String getDateTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		String dateTime = dateFormat.format(cal.getTime());
		info("time at moment is " + dateTime);
		return dateTime;
	}

	public String subtractDate(String format, String delta) {
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		int del = Integer.parseInt(delta);
		c.add(Calendar.DATE, -del);
		return formatDateToString(c.getTime(), format);
	}

	public String addDate(String format, String delta) {
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		int del = Integer.parseInt(delta);
		c.add(Calendar.DATE, del);
		return formatDateToString(c.getTime(), format);
	}

	public String addDate(String date, String format, String delta) {
		Date currentDate = formatDate(format, date);
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		int del = Integer.parseInt(delta);
		c.add(Calendar.DATE, del);
		return formatDateToString(c.getTime(), format);
	}

	public String formatDateToString(Date date, String format) {
		DateFormat f = new SimpleDateFormat(format);
		String dat = "";
		try {
			dat = f.format(date);
		} catch (Exception e) {
			info("Exception: " + e);
			info("Can not convert date");
		}
		info("dat " + dat);
		return dat;
	}

	/**
	 * @param format
	 * @param date
	 * @return
	 */
	public Date formatDate(String format, String date) {
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date dat = null;
		try {
			dat = f.parse(date);
		} catch (Exception e) {
			info("Exception: " + e);
			info("Can not convert date");
		}
		return dat;
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public String formatDateToString(String date, String format) {
		DateFormat f = new SimpleDateFormat(format);
		String dat = "";
		try {
			dat = f.format(date);
		} catch (Exception e) {
			info("Exception: " + e);
			info("Can not convert date");
		}
		info("dat" + dat);
		return dat;
	}

	/**
	 * @param date
	 * @param format
	 * @return
	 */
	public String formatDateToString(String date, String originalFormat, String intendedFormat) {
		DateFormat oriFormat = new SimpleDateFormat(originalFormat);
		DateFormat intendFormat = new SimpleDateFormat(intendedFormat);
		String dat = "";
		try {
			dat = intendFormat.format(oriFormat.parse(date));
		} catch (Exception e) {
			info("Exception: " + e);
			info("Can not convert date");
		}
		info("dat" + dat);
		return dat;
	}

	/**
	 * @param locator
	 * @param att
	 * @return
	 */
	public String getAttribute(Object locator, String att, int... opParams) {
		try {
			return getElementPresent(locator, opParams).getAttribute(att);
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * 
	 * @param object
	 */
	public void clickTab(By object) {
		if (object != null) {
			WebElement e = getElementPresent(object);
			e.sendKeys(Keys.TAB);
		}
	}

	/**
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void scrollToElement(Object locator, Object... opParams) {
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	/**
	 * compare 2 string
	 * 
	 * @param s1
	 * @param s2
	 */
	public void verifyCompare(String s1, String s2) {
		if (s1 != "" && s1 != null && s2 != null && s2 != "") {
			Assert.assertFalse(!s1.equalsIgnoreCase(s2), "So sanh khong bang nhau: " + s1 + " va " + s2);
		} else if ((s1 == "" || s1 == null) && (s2 == "" || s2 == null)) {
			info("2 truong du lieu can so sanh deu null");
		} else {
			Assert.fail("Du lieu so sanh co 1 truong bi null");
		}
	}

	/**
	 * check field is null = ""
	 * 
	 * @param s
	 */
	public void verifyNull(String s) {
		if (!s.equalsIgnoreCase("")) {
			Assert.fail("Du lieu khong null");
		}
	}

	public void verifyContains(String s1, String s2) {
		if (s1 != null && s2 != null) {
			Assert.assertFalse(!s2.contains(s1), "Chuỗi " + s1 + " không nằm trong chuỗi " + s2);
		}
	}

	/**
	 * 
	 * @param xpath
	 * @param option
	 */
	public void selectOptionFromCombobox(String xpath, String option) {
		if (option != null) {
			String locator = xpath.replaceAll("&option", option);
			click(locator);
			waitForElementNotPresent(locator, 10000, 0);
		}
	}

	public void waitOptionAfterSelectFromCombox(String xpath, String data) {
		getElementPresent(By.xpath(xpath.replaceAll("&option", data)), DEFAULT_TIMEOUT, 2);
	}

	/**
	 * 
	 * @param tbSearch
	 * @param textSearch
	 * @param xpath
	 */
	public void selectOptionFromComBoxSearch(Object tbSearch, String textSearch, String xpath) {
		type(tbSearch, textSearch, true, false);
		pause(500);
		click(By.xpath(xpath.replaceAll("&option", textSearch)));
		pause(1000);
	}

	/**
	 * @param driver
	 */
	public void captureScreen(WebDriver driver, String fileName) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String dir = System.getProperty("user.dir");
			FileUtils.copyFile(scrFile, new File(dir + "\\capture_screen\\" + fileName));
		} catch (Exception e) {
			info("Khong capture duoc man hinh");
		}
	}

	/**
	 * parse to object from a xpath contains option
	 * 
	 * @param xpathOption
	 * @param option
	 * @return
	 */
	public String parseStringToObject(String xpathOption, String option) {
		String xpath = xpathOption.replaceAll("&option", option);
		return xpath;
	}

	/**
	 * select option from combobox with FW 1.0
	 * 
	 * @param selectObject
	 * @param index
	 */
	public void selectOptionByIndex(Object selectObject, int index) {
		WebElement ele = getElementPresent(selectObject);
		Select select = new Select(ele);
		select.selectByIndex(index);
		pause(1000);
	}

	public String trimCharactor(String input, String trim) {
		info("Xau can xu ly trim: " + input);
		if (input != "" && input != null && trim != "") {
			if (trim == ".") {
				return input.replaceAll("\\.", "");
			} else {
				return input.replaceAll(trim, "");
			}
		} else
			return "";
	}

	/**
	 * get absolute path of file
	 * 
	 * @param relativeFilePath
	 * @return
	 */
	public String getAbsoluteFilePath(String relativeFilePath) {
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + relativeFilePath;
		return absolutePath;
	}

	/**
	 * 
	 * @param file
	 * @param filePath
	 */
	public void uploadFile(Object file, String filePath) {
		WebElement e = getElement(file);
		String plaForm = System.getProperty("platForm") != null ? System.getProperty("platForm") : "Window";
		if (plaForm.equalsIgnoreCase("Window")) {
			e.sendKeys(getAbsoluteFilePath("\\file_to_upload\\" + filePath));
		} else {
			e.sendKeys(getAbsoluteFilePath("/file_to_upload/" + filePath));
		}
	}

	/**
	 * 
	 * @param urlText
	 * @param data
	 * @param column
	 * @return
	 */
	public String[] callWS(String urlText, String data, String... column) {
		String output = "";
		String[] da = new String[100];
		String urlT = "";
		if (urlText != "" && !urlText.contains("http")) {
			urlT = System.getProperty("serviceLink") + urlText;
		} else {
			urlT = urlText;
		}
		try {
			URL url = new URL(urlT);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.setRequestProperty("Accept", "text/xml");
			info("connection: " + connection);
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			osw.write(data);
			osw.flush();
			osw.close();
			InputStream in = connection.getInputStream();
			output = read(in);
			info("output cua WS: " + output);
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(output));
			Document doc = builder.parse(src);
			if (column.length > 0) {
				for (int i = 0; i < column.length; i++) {
					if (doc.getElementsByTagName(column[i]).item(0).getTextContent() != null) {
						da[i] = doc.getElementsByTagName(column[i]).item(0).getTextContent();
						info("Gia tri trong tag " + column[i] + "la: " + da[i]);
					}
				}
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			assertFalse(true, "Co loi khi thuc hien WS");

		}
		return da;
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}

	public WebElement getElementPresentNoAssert(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			try {
				elem = driver.findElement(by);
				if (null != elem)
					return elem;
				pause(WAIT_INTERVAL);
				info("Lap lai lan thu " + tick + 1);
			} catch (NoSuchElementException ex) {
				if (tick == timeout / WAIT_INTERVAL) {
					return null;
				}
			} catch (WebDriverException e) {
				if (tick == timeout / WAIT_INTERVAL) {
					return null;
				}
			} catch (IllegalStateException e) {
				if (tick == timeout / WAIT_INTERVAL) {
					return null;
				}
			}
		}
		return elem;
	}

	/**
	 * tra ve so lan xuat hien cua 1 xau trong chuoi
	 * 
	 * @param value
	 * @param array
	 * @return
	 */
	public String checkValueInArray(String value, String[] array) {
		int soLan = 0;
		if (value != null && value != "" && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (value.equalsIgnoreCase(array[i])) {
					soLan++;
					info("Chuoi \"" + value + "\" xuat hien trong mang lan thu: " + soLan);
				}
			}
			return String.valueOf(soLan);
		} else {
			info("Chuoi can kiem tra dang null hoac mang khong co phan tu");
			return "";
		}
	}

	/**
	 * 
	 * @param string
	 * @param split
	 * @return
	 */
	public String[] convertStringToArray(String string, String split) {
		String[] a = new String[100];
		if (string != null && string != "") {
			a = string.split(split);
		} else {
			info("Xau ky tu can chuyen sang mang ");
		}
		return a;
	}

	public String subString(String s, int start, int end) {
		return s.substring(start, end);
	}

	public void verifyNotNull(String s) {
		if (s == null || s == "") {
			Assert.fail("Du lieu  null");
		}
	}

	public void clickElementIfDisplay(Object obj, int... opParams) {
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		if (getElementPresentNoAssert(obj, timeout) != null) {
			click(obj);
		}
	}

	public void switchNewTab(int... index) {
		int tab = index.length > 0 ? index[0] : 1;
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(tab));
	}

	public void closeCurrentTab() {
		driver.close();
	}

	/**
	 * Close all tab is not main tab
	 */
	public void closeAllTab() {
		String originalHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}
	}

	public void verifyCompareNotEqual(String s1, String s2) {
		if (s1 != "" && s1 != null && s2 != null && s2 != "") {
			Assert.assertTrue(!s1.equalsIgnoreCase(s2),
					"Pass neu 2 truong so sanh khong bang nhau: " + s1 + " va " + s2);
		} else if ((s1 == "" || s1 == null) && (s2 == "" || s2 == null)) {
			info("2 truong du lieu can so sanh deu null");
		} else {
			Assert.fail("Du lieu so sanh co 1 truong bi null");
		}
	}

	public void setProxy(String ip, String port) {
		System.setProperty("https.proxyHost", ip);
		System.setProperty("https.proxyPort", port);
	}

	public String checkCondition(String conditionType, String param1, String param2) {
		Boolean check = true;
		if (conditionType.equalsIgnoreCase("contains")) {
			check = param1.contains(param2) ? true : false;
		} else if (conditionType.equalsIgnoreCase("notcontains")) {
			check = !param1.contains(param2) ? true : false;
		} else if (conditionType.equalsIgnoreCase("<")) {
			check = Integer.parseInt(param1) < Integer.parseInt(param2) ? true : false;
		} else if (conditionType.equalsIgnoreCase(">")) {
			check = Integer.parseInt(param1) > Integer.parseInt(param2) ? true : false;
		} else if (conditionType.equalsIgnoreCase("=")) {
			check = Integer.parseInt(param1) == Integer.parseInt(param2) ? true : false;
		}
		info("Ket qua kiem tra dieu kien " + conditionType + " la: " + check);
		return Boolean.toString(check);
	}

	/**
	 * Connects to a remote FTP server
	 */
	public void connectFTPServer(String hostname, String username, String password) {
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(hostname, 21);
			returnCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(returnCode)) {
				info("Could not connect ftp server");
			}
			boolean loggedIn = ftpClient.login(username, password);
			if (!loggedIn) {
				info("Could not login to ftp server");
			}
			info("Connected and logged in ftp server");
		} catch (IOException e) {
			info("Could not connect ftp server");
		}
	}

	/**
	 * Logs out and disconnects from the server
	 */
	public void logoutFTPServer() {
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			info("Can not logout ftp");
		}
	}

	/**
	 * 
	 * @param filePath
	 */
	public void checkFileExistOnFTP(String filePath) {
		try {
			info(filePath);
			FTPFile[] remoteFiles = ftpClient.listFiles(filePath);
			if (remoteFiles.length == 0) {
				assertFalse(true, "File not exist on FTP server");
			} else {
				info("Check file in ftp is OK");
			}
		} catch (IOException e) {
			info("Has error when check file in FTP");
		}
	}

	public void checkFileExist(String fileName) {
		String file = "";
		if (!fileName.contains("/") || !fileName.contains("\\")) {
			file = System.getProperty("user.dir") + File.separator + "downloads" + File.separator + fileName;
		} else {
			file = fileName;
		}
		File f = new File(file);
		if (f.exists() && !f.isDirectory()) {
			info(fileName + " có tồn tại");
			f.delete();
		} else {
			Assert.fail(fileName + " không tồn tại");
		}
	}

	public void enter(Object locator) {
		if (locator != null) {
			WebElement e = getElementPresent(locator);
			e.sendKeys(Keys.ENTER);
		}
	}

	/* Evaluates the given expression and returns the result in String format. */
	public String evaluate(String expression) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String result = null;
		try {
			result = engine.eval(expression).toString();
			if (result.endsWith(".0")) {
				info("Ket qua cua bieu thuc " + expression + " la: " + result + " Return phan nguyen");
				for (int i = 0; i < result.length(); i++) {
					if (result.charAt(i) == '.') {
						return result.substring(0, i);
					}
				}
			}
			info("Ket qua cua bieu thuc " + expression + " la: " + result);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return result;
	}

	// Tách chuỗi
	public String[] shouldMatchRegex(String pattern, String message) {
		String[] result = new String[10];
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(message);
		if (matcher.find()) {
			for (int i = 0; i < matcher.groupCount() + 1; i++) {
				result[i] = matcher.group(i);
			}
		} else {
			Assert.fail("String, patten does not match");
		}
		return result;
	}

	public void verifyNotContains(String s1, String s2) {
		if (s1 != null && s2 != null && s2.contains(s1)) {
			info("Fail do chuoi " + s2 + "  van chua chuoi " + s1);
			Assert.assertFalse(s2.contains(s1));
		}
	}

	/**
	 * func de ma hoa 1 chuoi
	 * 
	 * @param dataEncrypt
	 * @return
	 */
	public String encrypt(String dataEncrypt) {
		StandardPBEStringEncryptorExtension encryptor = new StandardPBEStringEncryptorExtension();
		return encryptor.encrypt(dataEncrypt);
	}

	/**
	 * func de giai ma 1 chuoi
	 * 
	 * @param dataDecrypt
	 * @return
	 */
	public String decrypt(String dataDecrypt) {
		StandardPBEStringEncryptorExtension encryptor = new StandardPBEStringEncryptorExtension();
		return encryptor.decrypt(dataDecrypt);
	}

	/**
	 * doc noi dung file xml
	 * 
	 * @param fileName
	 * @param xpath
	 * @param tag
	 * @return
	 */
	public String[] readXMLFile(String fileName, String xpath, String... tag) {
		String[] va = new String[100];
		try {
			File inputFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = xpath;
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			Node nNode = nodeList.item(0);
			info("Current Element : " + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				if (tag.length > 0) {
					for (int i = 0; i < tag.length; i++) {
						if (eElement.getElementsByTagName(tag[i]).item(0).getTextContent() != null) {
							va[i] = eElement.getElementsByTagName(tag[i]).item(0).getTextContent();
							info("Gia tri trong tag " + tag[i] + " la: " + va[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return va;
	}

	public void waitForElementDisappear(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		int i = 0;
		while (i < 60) {
			try {
				if (driver.findElement(by).isDisplayed()) {
					pause(1000);
					i++;
				}
			} catch (NoSuchElementException ex) {
				break;
			} catch (StaleElementReferenceException ex) {
				break;
			}
		}
		if (i == 60) {
			Assert.fail("Qua thoi gian doi tuong van display");
		}
	}

	public void acceptAlert(String object, String data) {
		try {
			info("Clicking on Webelement " + object);
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			info("Not able to click --- " + e.getMessage());
		}

	}

	public void GetTitle(String object, String data) {
		try {
			info("Get Title");
			String title = driver.getTitle();
			title.toLowerCase().contains(data);
		} catch (Exception e) {
			info("Not get title" + e.getMessage());
		}
	}

	public void selectValueFromDropdown(Object locator, String value) {
		Select dropDown = new Select(getElement(locator));
		dropDown.selectByVisibleText(value);
	}
}
