package com.fusionlab.pageobject;

import org.openqa.selenium.WebElement;

import com.fusionlab.driver.BaseClass;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class PaymentGatewayScreen extends BaseClass {

	@AndroidFindBy(id = "Price")
	private static WebElement priceWebEle;

	/**
	 * Method to get the price on payement screen
	 * 
	 * @return
	 */
	public static String getThePriceOnPaymentScreen() {
		String totalPrice = getText(priceWebEle, "Get the price");
		return totalPrice;
	}

}
