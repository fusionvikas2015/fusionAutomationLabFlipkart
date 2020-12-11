/**
 * @author Vikas Kumar
 * 
 * Description: Driver main class. This class is used to initialize the driver. In this class all the desired capabilities are being defined.
 * 
 * Desired Capabilities are keys and values encoded in a JSON object, sent by Appium clients to the server when a new automation session
 * is requested. They tell the Appium drivers all kinds of important things about how you want your test to work. Each Appium client builds
 * capabilities in a way specific to the client's language, but at the end of the day, they are sent over to Appium as JSON objects. 
 * 
*/
package com.fusionlab.driver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.fusionlab.common.Constants;
import com.fusionlab.utilities.ReportsLogger;
import com.fusionlab.utilities.Utility;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class BaseClass extends ReportsLogger {

	public static AppiumDriverLocalService service;

	/**
	 * Logger instance
	 */
	public static Logger logger = LogManager.getLogger(BaseClass.class);

	/**
	 * driver instance
	 */
	public static AndroidDriver<MobileElement> driver;

	/**
	 * Android serial id
	 */
	public static String androidDeviceID = Utility.parseGlobalProp("ANDROID_SERIALID");

	/**
	 * Appium Port number
	 */
	public static String PORT = Utility.parseGlobalProp("PORT");
	/**
	 * Used to set the device language
	 */
	public static String language = Utility.parseGlobalProp("LANGUAGE");
	/**
	 * Select the device region
	 */
	public static String locale = Utility.parseGlobalProp("LOCALE");

	/**
	 * This metod is used to start the appium server on free appium port ie. 4723
	 * 
	 * @return service
	 */
	public static AppiumDriverLocalService startServer() {
		boolean flag = checkIfServerIsRunnning(4723);
		if (!flag) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;

	}

	/**
	 * This method is used to check whether the appiym port 4723 is free or not.
	 * 
	 * @param port
	 * @return port
	 */
	public static boolean checkIfServerIsRunnning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
			logger.debug("Server is already Running and Port is in Use");
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	/**
	 * START the appium server at the suite level. It can be changed to class or
	 * method level too depending on the requirements.
	 * 
	 * @throws Exception
	 */
	public static void startAppiumServer() throws Exception {
		logger.debug("Starting the appium server at suite level");
		System.out.println("\n\tStarting the appium server at suite level");
		startServer();
	}

	/**
	 * STOP the appium server at the suite level. It can be changed to class or
	 * method level too depending on the requirements.
	 * 
	 * @throws Exception
	 */
	public void stopAppiumServer() throws Exception {
		logger.debug("\nStopping the appium server at suite level");
		System.out.println("\n\t Stopping the appium server at suite level");
		service.stop();
	}

	/**
	 * Creating the instance of driver with the help of desired capabilities.
	 * 
	 * @return
	 * @throws Exception
	 */
	@BeforeSuite
	public static AndroidDriver<MobileElement> createEnvironment() throws Exception {

		startAppiumServer();

		String version = Utility.parseGlobalProp("VERSION");
		logger.info(" Automation Framework Version:" + version);

		DesiredCapabilities caps = new DesiredCapabilities();
		logger.info("Please Wait ! Initiating Android Appium Driver...");
		logger.info("androidDeviceID: " + androidDeviceID);
		String platformVersion = Utility.exec_adb_command(androidDeviceID, Constants.ANDROID_PLATFORM_VERSION_CMD);
		logger.info("Platform: " + platformVersion);
		String DeviceName = Utility.exec_adb_command(androidDeviceID, Constants.ANDROID_DEVICE_NAME_CMD);
		logger.info("DeviceName: " + DeviceName);

		caps.setCapability("udid", androidDeviceID);
		caps.setCapability("platformVersion", platformVersion);
		caps.setCapability("deviceName", DeviceName);
		caps.setCapability("platformName", "Android");

		caps.setCapability("appPackage", Constants.ANDROID_APP_PACKAGE);
		caps.setCapability("appActivity", Constants.ANDROID_APP_ACTIVITY);

		caps.setCapability(MobileCapabilityType.AUTO_WEBVIEW, false);

		caps.setCapability(MobileCapabilityType.LANGUAGE, language);
		caps.setCapability(MobileCapabilityType.LOCALE, locale);

		caps.setCapability("noReset", "true");
		caps.setCapability("fullReset ", "false");

		File appPath = new File(System.getProperty("user.dir") + Utility.parseGlobalProp("APP_PATH"));
		logger.info("App Path:" + appPath);

		caps.setCapability("app", appPath.getAbsolutePath());

		try {
			logger.info("Please wait a while!! Driver is being initialised.");
			driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:" + PORT + "/wd/hub"), caps);
			driver.manage().timeouts().implicitlyWait(15000, TimeUnit.MILLISECONDS);
		} catch (MalformedURLException e) {
			System.out.println("Error while initiaising the driver : " + e.getMessage());
		}

		/**
		 * Page Initialization Page factory implementation
		 * 
		 */
		logger.info("iOS Driver Initiated Successfully.Now initiating all the screens objects.");
		Utility.InitialisePages(driver);
		System.out.println("All Pages initaited with driver.");
		return driver;
	}

	/**
	 * Kill/destroy the driver instance on test case execution completion.
	 * 
	 * @throws Exception
	 */
	@AfterSuite
	public void teardown() throws Exception {
		logger.info("\n\n\t\t\t\t============>>>> " + " Testcase Teardown " + " <<<<============");

		// Android Logcat Capture - Suite level
		String rootLogDir = "logs" + "//" + myJobID + "//";
		String mainLogcat = rootLogDir + myJobID + "_Logcat.txt";
		Utility.createDir(rootLogDir);
		Utility.startLogcat(mainLogcat);

		Thread.sleep(1000);
		if (driver != null) {
			driver.quit();
		}

		stopAppiumServer();
	}

	/**
	 * Click event - using the webElement
	 * 
	 * @param element
	 * @param elementName
	 */
	public static void click(WebElement element, String elementName) {
		element.click();
		logger.info(elementName + " Clicked");
	}

	public static void clickOnListByIndex(List<WebElement> element, int index, String elementName) {
		element.get(index).click();
		logger.info(elementName + " Clicked");
	}

	/**
	 * Get text using the webElement.
	 * 
	 * @param element
	 * @param elementName
	 * @return
	 */
	public static String getText(WebElement element, String elementName) {
		String txt = element.getText();
		logger.info(elementName + " gettext");
		return txt;
	}

	/**
	 * Send key event - using the WebElement.
	 * 
	 * @param element
	 * @param value
	 * @param elementName
	 */
	public static void type(WebElement element, String value, String elementName) {
		System.out.println("SendKey :" + value);
		element.sendKeys(value);
		logger.info("Typing  : " + value);
	}

}
