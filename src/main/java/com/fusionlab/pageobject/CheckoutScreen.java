package com.fusionlab.pageobject;

import org.openqa.selenium.WebElement;

import com.fusionlab.driver.BaseClass;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class CheckoutScreen extends BaseClass {

	@AndroidFindBy(id = "BUY NOW")
	private static WebElement buyNowBtn;

	@AndroidFindBy(id = "SKIP & CONTINUE")
	private static WebElement skipAndContinueBtn;

	@AndroidFindBy(id = "CONTINUE")
	private static WebElement continueBtn;

	/**
	 * buyAddedProductToCart : Method to add the product to buy list.
	 */

	public static void buyAddedProductToCart() {
		click(buyNowBtn, "Clicked on Buy Now btn");
		click(skipAndContinueBtn, "Skip and Continue btn clicked");
		click(continueBtn, "Continue Btn clicked");
	}

}
