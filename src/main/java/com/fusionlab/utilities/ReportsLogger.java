package com.fusionlab.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
//import com.relevantcodes.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.fusionlab.android.Mobile;
import com.fusionlab.driver.BaseClass;
import com.fusionlab.utilities.Utility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * 
 * @author Vikas Kumar. This class is used to generate the extent HTML report
 *
 */
public class ReportsLogger extends Thread {

	protected static ExtentReports extent;
	public static ExtentTest mytest;
	private static ThreadLocal parentTest = new ThreadLocal();
	protected static ThreadLocal test = new ThreadLocal();

	public static String timeStamp = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());
	protected static String myJobID = Utility.getCustomJobID();
	private static String myReport = "report" + "//" + myJobID + "_Report.html";

	@BeforeSuite
	public void beforeSuite() {
		extent = ExtentManager.createInstance(myReport);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(myReport);
		extent.attachReporter(htmlReporter);
	}

	@BeforeClass
	public synchronized void beforeClass() {
		ExtentTest parent = extent.createTest(getClass().getName());
		parentTest.set(parent);
	}

	@BeforeMethod
	public synchronized void beforeMethod(Method method) throws Exception {
		String method_name = method.getName();
		ExtentTest child = ((ExtentTest) parentTest.get()).createNode(method_name);
		test.set(child);
		System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(">>>>> Automation Started : " + method_name + " <<<<<");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("Testcase Setup...");
		System.out.println("JobId: " + myJobID);
		Utility.clearLogcat();
	}

	/**
	 * Add screenshot to the Failed TCs. Collect Logcat for the Failed TCs.
	 * 
	 * @param result
	 * @param method
	 * @throws Exception
	 */
	@AfterMethod
	public synchronized void afterMethod(ITestResult result, Method method) throws Exception {
		AppiumDriver<MobileElement> driver = BaseClass.driver;

		// Add Screenshot attachment to the failed TCs.
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Test Case got failed as :\n" + result.getThrowable());
			System.out.println("==> TC FAILED : " + method.getName() + "\n");
			((ExtentTest) test.get())
					.fail(MarkupHelper.createLabel(result.getName() + " Test Case Failed", ExtentColor.RED));
			((ExtentTest) test.get()).fail(result.getThrowable());

			String screenshotPath = getScreenshot(driver, result.getName());
			((ExtentTest) test.get()).fail("Click on image for full view",
					MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

			// Android Logcat Capture - for failed test cases only.
			String TC_NAME = method.getName();
			String rootLogDir2 = "logs" + "//" + myJobID + "//";
			String methodLevelLogcatDir = rootLogDir2 + TC_NAME;
			String methodLevelLogcatFileName = methodLevelLogcatDir + "//" + TC_NAME + "_Logcat.txt";
			Utility.createDir(methodLevelLogcatDir);
			Utility.startLogcat(methodLevelLogcatFileName);

		} else if (result.getStatus() == ITestResult.SKIP) {
			System.out.println("==> TC SKIPPED : " + method.getName() + "\n");
			((ExtentTest) test.get()).skip(result.getThrowable());

		} else {
			System.out.println("==> TC PASSED : " + method.getName() + "\n");
			((ExtentTest) test.get()).pass("Test passed");
		}

		extent.flush();
		Thread.sleep(2000);

		// Kil the driver
//		if (driver != null) {
//			driver.quit();
//		}

	}

	/**
	 * Capture the screenshots for attaching the extent report.
	 * 
	 * @param driver
	 * @param screenshotName
	 * @return
	 * @throws IOException
	 */
	private String getScreenshot(AppiumDriver<MobileElement> driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static class ExtentManager {
		private static ExtentReports extent;

		public ExtentReports getInstance() {
			if (extent == null)
				createInstance(myReport);
			return extent;
		}

		/**
		 * Extent Report Configuration
		 * 
		 * @param fileName
		 * @return
		 */
		public static ExtentReports createInstance(String fileName) {
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setTimeStampFormat("mm/dd/yyyy hh:mm:ss a");
			htmlReporter.config().setDocumentTitle("Astro Automation Report");
			htmlReporter.config().setDocumentTitle(fileName);
			htmlReporter.config().setEncoding("utf-8");
			htmlReporter.config().setReportName(fileName);
			htmlReporter.config().setCSS("css-string");
			htmlReporter.config().setJS("js-string");
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			return extent;
		}
	}
}