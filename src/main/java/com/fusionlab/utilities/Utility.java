package com.fusionlab.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;

import com.fusionlab.common.Constants;
import com.fusionlab.driver.BaseClass;
import com.fusionlab.pageobject.CheckoutScreen;
import com.fusionlab.pageobject.HomeScreen;
import com.fusionlab.pageobject.ProductSearchScreen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * This class can be used to perform common operation on both android and iOS.
 * 
 * @author Vikas Kumar
 *
 */

public class Utility extends BaseClass {

	/**
	 * This method is used to initialize all the screen for page factory model.
	 * 
	 * @param driver
	 */

	public static void InitialisePages(AppiumDriver<MobileElement> driver) {
		try {
			PageFactory.initElements(new AppiumFieldDecorator(driver), new HomeScreen());
			PageFactory.initElements(new AppiumFieldDecorator(driver), new ProductSearchScreen());
			PageFactory.initElements(new AppiumFieldDecorator(driver), new CheckoutScreen());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method can be used to clear the older android logcat.
	 * 
	 * @throws Exception
	 */
	public static void clearLogcat() throws Exception {
		exec_adb_command(androidDeviceID, Constants.CLEAR_LOGCAT);
		Thread.sleep(1000);
	}

	/**
	 * This method can be used to start the android logcat capture.
	 * 
	 * @param logFilePath
	 * @throws Exception
	 */
	public static void startLogcat(String logFilePath) throws Exception {
		String logcatCmd = "logcat com.astrogaming.scorpius -d > " + logFilePath;
		exec_adb_command(androidDeviceID, logcatCmd);
		Thread.sleep(1000);
	}

	/**
	 * This method can be used to capture iOS logs
	 * 
	 * @param logFilePath
	 * @param udid
	 * @throws Exception
	 */
	public static void startIOSLogsCapture(String logFilePath, String udid) throws Exception {

		String iosConsoleLogCmd = "idevicesyslog -u" + " " + udid + " " + "| grep com.astrogaming.scorpius > "
				+ logFilePath;
		System.out.println("iosConsoleLogCmd : " + iosConsoleLogCmd);
		Thread.sleep(1000);
	}

	/**
	 * This method can be used to validate the current screen intent.
	 * 
	 * @param activityName
	 * @return
	 * @throws Exception
	 */
	public static boolean verifyScreenByActivityName(String activityName) throws Exception {
		String currentActivityName = exec_adb_command(androidDeviceID, Constants.SCREEN_DUMPSYS);
		if (currentActivityName.contains(activityName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method can be used to get the screenshots for failed test cases.
	 * 
	 * @param driver
	 * @param screenshotName
	 * @return
	 * @throws IOException
	 */
	public static String getScreenshot(AppiumDriver<MobileElement> driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	/**
	 * This method can be used to execute all the android adb commands.
	 * 
	 * @param serialId
	 * @param Command
	 * @return
	 */
	public static String exec_adb_command(String serialId, String Command) {
		String line = null;
		String data = null;
		String CMD = "adb -s" + " " + serialId + " " + Command;
		// System.out.println("COMMAND: " + CMD);

		ProcessBuilder processBuilder = new ProcessBuilder();
		if (Utility.isWindows()) {
			processBuilder.command("cmd.exe", "/c", CMD);
		} else {
			String ANDROID_HOME = Utility.parseGlobalProp("MAC_ANDROID_HOME");
			processBuilder.command("bash", "-c", ANDROID_HOME + "//" + CMD);
		}
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine()) != null) {
				data = line;
				// System.out.println("==OUTPUT : " + line);
			}
			int exitCode = process.waitFor();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * This method can be used to execute iOS commands.
	 * 
	 * @param Command
	 * @return
	 * @throws Exception
	 */
	public static StringBuilder exec_ios_command(String Command) throws Exception {
		StringBuilder result = null;
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", Command);

		try {

			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				// System.out.println("Success!");
				// System.out.println(output);
				result = output;
			} else {
				// abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method is used to parse the global.properties.
	 * 
	 * @param propName
	 * @return
	 */
	public static String parseGlobalProp(String propName) {
		FileReader reader = null;
		try {
			reader = new FileReader(System.getProperty("user.dir") + "//global.properties");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(reader);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String ITEM = null;
		try {
			String value = System.getProperty(propName);
			if (value != (null)) {
				ITEM = value;
				// System.out.println(propName + " from console :" + ITEM);
			} else {
				ITEM = prop.getProperty(propName);
				// System.out.println(propName + " from global properties: " + ITEM);
			}
		} catch (Exception e) {
			System.out.println("Exception -" + e);
		}
		return ITEM;
	}

	/**
	 * This method is used to get the unique jobid for the execution.
	 * 
	 * @return
	 */
	public static String getCustomJobID() {
		String myJobIdIs = null;
		String JOB = parseGlobalProp("JOBID");
		if (JOB.contains("_")) {
			myJobIdIs = JOB;
		} else {
			myJobIdIs = JOB + "_" + timeStamp;
		}
		System.out.println("Job-Id Created : " + myJobIdIs);
		return myJobIdIs;
	}

	/**
	 * This method is used to get tghe execution mode ie. IOS/ANDROID
	 * 
	 * @return
	 */
	public static boolean isAndroidPlatform() {
		boolean isAndroid;
		String platform = parseGlobalProp("EXECUTION_MODE");
		if (platform.contains("ANDROID")) {
			isAndroid = true;
		} else {
			isAndroid = false;
		}
		return isAndroid;
	}

	/**
	 * This method can be used to check weather the OS is windows or not.
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("win") >= 0);
	}

	/**
	 * This method can be used to check weather the OS is mac or not.
	 * 
	 * @return
	 */
	public static boolean isMac() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("mac") >= 0);
	}

	/**
	 * This method can be used to check weather the OS is Linux or not.
	 * 
	 * @return
	 */
	public static boolean isUnix() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	/**
	 * This method can be used to check weather the OS is Solaris or not.
	 * 
	 * @return
	 */
	public static boolean isSolaris() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("sunos") >= 0);
	}

	public static String createDir(String Path) {
		// System.out.println("Creating dir : " + Path);
		File files = new File(Path);
		if (!files.exists()) {
			if (files.mkdirs()) {
				// System.out.println("Multiple directories are created!");
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}
		return Path;
	}

	/**
	 * This method can be used to capture the screen shots.
	 * 
	 * @param screenshotName
	 * @return
	 * @throws Exception
	 */
	public static String captureScreenshot(String screenshotName) throws Exception {
		Thread.sleep(1000);
		String dir_nm = language + "_" + locale;
		String screenshotPath = createDir(System.getProperty("user.dir") + "//Screenshots//" + myJobID + "//" + dir_nm);
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = screenshotPath + "//" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		System.out.println("==> Screenshot Captured : " + screenshotName);
		return destination;
	}

}