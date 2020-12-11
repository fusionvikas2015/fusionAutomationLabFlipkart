package com.fusionlab.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.fusionlab.android.Mobile;
import com.fusionlab.driver.BaseClass;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomeScreen extends BaseClass {

	@AndroidFindBy(accessibility = "Open Drawer")
	private static WebElement openDrawerBtn;

	@AndroidFindBy(id = "com.flipkart.android:id/flyout_parent_title")
	private static List<WebElement> myAccount;

	@AndroidFindBy(id = "com.google.android.gms:id/cancel")
	private static WebElement noneOfTheAbove;

	@AndroidFindBy(id = "com.flipkart.android:id/tv_right_cta")
	private static WebElement useEmailLink;

	@AndroidFindBy(id = "com.flipkart.android:id/phone_input")
	private static WebElement userIdEditBox;

	@AndroidFindBy(id = "com.flipkart.android:id/button")
	private static WebElement continueBtn;

	@AndroidFindBy(id = "com.flipkart.android:id/phone_input")
	private static WebElement passwordEditBox;

	@AndroidFindBy(id = "com.flipkart.android:id/button")
	private static WebElement loginBtn;

	@AndroidFindBy(id = "com.flipkart.android:id/search_widget_textbox")
	private static WebElement searchBarEditBox;

	@AndroidFindBy(id = "com.flipkart.android:id/search_autoCompleteTextView")
	private static WebElement enterProductEditBox;

	/**
	 * doValidLoginIntoTheApp: Method to do valid login into the app
	 * 
	 * @param emailID
	 * @param password
	 * @throws Exception
	 */
	public static void doValidLoginIntoTheApp(String emailID, String password) throws Exception {
		click(openDrawerBtn, "Open Drawer");
		clickOnListByIndex(myAccount, 10, "Click on My Account");
		click(noneOfTheAbove, "Clicked on None Of The Above");
		click(useEmailLink, "Use Email to Login instead phone number");
		click(userIdEditBox, "Clicked on Userid editbox");
		type(userIdEditBox, emailID, "Enter emailID");
		click(continueBtn, "Cliked on Continue Btn");

		click(passwordEditBox, "Clicked on password editbox");
		type(passwordEditBox, password, "Entered password");
		click(loginBtn, "Cliked on Continue Btn");

	}

	/**
	 * This method can be used to search any product on home screen.
	 * 
	 * @param productCategory
	 * @throws Exception
	 */
	public static void searchProductOnHomeScreen(String productCategory) throws Exception {
		System.out.println("productCategory:" + productCategory);
		click(searchBarEditBox, "Search bar");
		Thread.sleep(1000);

		click(enterProductEditBox, "Search bar");
		type(enterProductEditBox, productCategory, productCategory);
		Mobile.pressEnterKey();
	}

}
