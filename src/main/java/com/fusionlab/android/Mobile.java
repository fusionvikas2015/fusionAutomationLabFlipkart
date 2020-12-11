package com.fusionlab.android;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

import java.time.Duration;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.fusionlab.driver.BaseClass;
import com.fusionlab.utilities.Utility;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

/**
 * This class contains the common android method to perform actions on the
 * device.
 */
public class Mobile extends BaseClass {

	public static void setContextToWebview() throws Exception {
		Set<String> availableContexts = driver.getContextHandles();
		System.out.println("availableContexts:" + availableContexts);

		availableContexts.stream().filter(new Predicate<String>() {
			public boolean test(String context) {
				return context.toLowerCase().contains("webview");
			}
		}).forEach(new Consumer<String>() {
			public void accept(String newcontext) {
				driver.context(newcontext);
			}
		});

		driver.context("WEBVIEW_1");
		System.out.println("switched context");
		WebElement userId = driver.findElement(By.id("ap_email_login"));
		WebElement loginButton = driver.findElement(By.id("continue"));

		userId.sendKeys("TestUser");
		loginButton.click();

		driver.findElementById("ap_email_login").click();
		driver.findElementById("ap_email_login").sendKeys("9066811976");
		Thread.sleep(2000);
		driver.findElementById("continue");
	}

	public static void changeDriverContextToWeb() throws Exception {
		Set<String> allContext = driver.getContextHandles();
		System.out.println("allContext:" + allContext);

		for (String context : allContext) {
			if (context.contains("WEBVIEW")) {
				System.out.println("Switching to WEBVIEW...");
				driver.context(context);
			} else if (context.contains("NATIVE_APP")) {
				System.out.println("Switching to NATIVE_APP...");
				driver.context(context);
			}
		}

		driver.context("WEBVIEW_com.amazon.mShop.splashscreen.StartupActivity");
		driver.findElementById("ap_email_login").click();
		driver.findElementById("ap_email_login").sendKeys("9066811976");
		Thread.sleep(2000);
		driver.findElementById("continue");

	}

	/**
	 * This method can be used to scroll to the text
	 * 
	 * @param text
	 */
	public static void scrollToText(String text) {
		MobileElement el = (MobileElement) ((FindsByAndroidUIAutomator<MobileElement>) driver)
				.findElementByAndroidUIAutomator(
						"new UiScrollable(" + "new UiSelector().scrollable(true)).scrollIntoView("
								+ "new UiSelector().text(\"" + text + "\"));");

	}

	public static void scrollToTextMatch(String text) {
		driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""
						+ text + "\").instance(0))"));

	}

	public static void pressEnterKey() throws Exception {
		Utility.exec_adb_command(androidDeviceID, "shell input keyevent KEYCODE_ENTER");
	}

	/**
	 * This method can be used to press hard key back button.
	 * 
	 * @throws Exception
	 */
	public static void pressBackButton() throws Exception {
		Utility.exec_adb_command(androidDeviceID, "shell input keyevent KEYCODE_BACK");
	}

	/**
	 * This method can be used to press hard key home button
	 * 
	 * @throws Exception
	 */
	public static void pressHomeButton() throws Exception {
		Utility.exec_adb_command(androidDeviceID, "shell input keyevent KEYCODE_HOME");
	}

	/**
	 * This method can be used to drag the one web element to the another web
	 * element.
	 * 
	 * @param source
	 * @param destination
	 * @throws InterruptedException
	 */
	public static void dragItem(WebElement source, WebElement destination) throws InterruptedException {
		System.out.println("Dragging " + source.getText() + " To " + destination.getText());
		TouchAction t = new TouchAction(driver);
		t.longPress(longPressOptions().withElement(element(source))).moveTo(element(destination)).release().perform();
		Thread.sleep(1000);
	}

	/**
	 * This method can be used to swipe using co-ordinates.
	 * 
	 * @param X1
	 * @param Y1
	 * @param X2
	 * @param Y2
	 * @throws Exception
	 */
	public static void swipeByPoints(int X1, int Y1, int X2, int Y2) throws Exception {
		System.out.println("X1: " + X1 + " || Y1: " + Y1);
		System.out.println("X2: " + X2 + " || Y2: " + Y2);
		TouchAction swipe = new TouchAction(driver).press(PointOption.point(X1, Y1))
				.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))).moveTo(PointOption.point(X2, Y2))
				.release().perform();
		Thread.sleep(2000);
	}

	/**
	 * This method can be used to scroll by co-ordinates.
	 * 
	 * @param X1
	 * @param Y1
	 * @param X2
	 * @param Y2
	 * @param DurationInMilliSeconds
	 * @throws Exception
	 */
	public static void scrollByCoordinates(int X1, int Y1, int X2, int Y2, int DurationInMilliSeconds)
			throws Exception {
		System.out.println("X1: " + X1 + " || Y1: " + Y1);
		System.out.println("X2: " + X2 + " || Y2: " + Y2);
		TouchAction swipe = new TouchAction(driver).press(PointOption.point(X1, Y1))
				.waitAction(new WaitOptions().withDuration(Duration.ofMillis(DurationInMilliSeconds)))
				.moveTo(PointOption.point(X2, Y2)).release().perform();
		Thread.sleep(2000);
	}

	/**
	 * This method can be used to get the screen size
	 * 
	 * @return
	 */
	public static Dimension screenSize() {
		Dimension size = driver.manage().window().getSize();
		System.out.println("Screen Size : " + size);
		return size;
	}

	/**
	 * This method can be used to get the screen height.
	 * 
	 * @return
	 */
	public static int screenHeight() {
		int Height = driver.manage().window().getSize().getHeight();
		System.out.println("Screen Height: " + Height);
		return Height;
	}

	/**
	 * This method can be used to get the device screen width.
	 * 
	 * @return
	 */
	public static int screenWidth() {
		int Width = driver.manage().window().getSize().getWidth();
		System.out.println("Screen Width: " + Width);
		return Width;
	}

}
