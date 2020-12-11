package com.fusionlab.apptest;

import java.util.Hashtable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.Test;

import com.fusionlab.driver.BaseClass;
import com.fusionlab.pageobject.CheckoutScreen;
import com.fusionlab.pageobject.HomeScreen;
import com.fusionlab.pageobject.PaymentGatewayScreen;
import com.fusionlab.pageobject.ProductSearchScreen;
import com.fusionlab.pageobject.SortProductScreen;
import com.fusionlab.utilities.DataProviders;

import junit.framework.Assert;

public class ApplicationTest extends BaseClass {

	public static Logger logger = LogManager.getLogger(ApplicationTest.class);

	@Test(dataProviderClass = DataProviders.class, dataProvider = "credentialDP")
	public void TC01_Verify_Valid_Login_Into_The_App(Hashtable<String, String> data) throws Exception {
		logger.info("TC01_Verify_Valid_Login_Into_The_App");
//		HomeScreen.doValidLoginIntoTheApp(data.get("username"), data.get("password"));
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "searchProductDP")
	public void TC02_VerifyDetailsOnProductSearchScreenVsCheckoutScreen(Hashtable<String, String> data)
			throws Exception {
		logger.info("TC02_VerifyDetailsOnProductSearchScreenVsCheckoutScreen");
//		driver.rotate(ScreenOrientation.PORTRAIT);
//
//		logger.info("seraching the product by category");
//		HomeScreen.searchProductOnHomeScreen(data.get("Category"));
//
//		logger.info("Applying Filter with Brand.");
//		ProductSearchScreen.applyFilter(data.get("Product"));
//
//		logger.info("Sorting the product from low to high");
//		SortProductScreen.sortTheProduct(2);
//
//		logger.info("Selecting the first product from the product list");
//		SortProductScreen.selectTheProduct(0);
//		String beforeAddingToCartPrice = SortProductScreen.getThePrice();
//
//		logger.info("Checkout the added product");
//		CheckoutScreen.buyAddedProductToCart();
//
//		logger.info("Validating the price on payment screen");
//		String afterAddingToCartPrice = PaymentGatewayScreen.getThePriceOnPaymentScreen();
//
//		Assert.assertEquals(beforeAddingToCartPrice, afterAddingToCartPrice);

	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "searchProductDP")
	public void TC02_TestToShowScreenshotAttachmentToFailedTC(Hashtable<String, String> data) throws Exception {
		logger.info("TC02_VerifyDetailsOnProductSearchScreenVsCheckoutScreen");

		driver.rotate(ScreenOrientation.PORTRAIT);
		logger.info("TC02_VerifyDetailsOnProductSearchScreenVsCheckoutScreen");
		driver.rotate(ScreenOrientation.PORTRAIT);

		logger.info("seraching the product by category");
		HomeScreen.searchProductOnHomeScreen(data.get("Category"));

		logger.info("Applying Filter with Brand.");
		ProductSearchScreen.applyFilter(data.get("Product"));

		logger.info("Entering wrong index intentionally to capture the sreenshot");
		SortProductScreen.sortTheProduct(10);

		logger.info("Selecting the first product from the product list");
		SortProductScreen.selectTheProduct(0);
		String beforeAddingToCartPrice = SortProductScreen.getThePrice();

		logger.info("Checkout the added product");
		CheckoutScreen.buyAddedProductToCart();

		logger.info("Validating the price on payment screen");
		String afterAddingToCartPrice = PaymentGatewayScreen.getThePriceOnPaymentScreen();

		Assert.assertEquals(beforeAddingToCartPrice, afterAddingToCartPrice);

	}

}
