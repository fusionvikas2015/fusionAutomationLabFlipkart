package com.fusionlab.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.fusionlab.driver.BaseClass;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class SortProductScreen extends BaseClass {

	@AndroidFindBy(id = "com.google.android.gms:id/apply")
	private static WebElement sortBtn;

	@AndroidFindBy(id = "com.google.android.gms:id/price")
	private static WebElement priceText;

	@AndroidFindBy(id = "com.google.android.gms:id/android.view.ViewGroup")
	private static List<WebElement> sortByWebEleList;

	@AndroidFindBy(id = "com.google.android.gms:id/android.view.ViewGroup")
	private static List<WebElement> productList;

	/**
	 * This method can be sort the product.
	 * 
	 * @param sortTheProduct
	 * @return
	 * @throws Exception
	 */
	public static void sortTheProduct(int index) throws Exception {
		click(sortBtn, "Sort btn clicked");
		clickOnListByIndex(sortByWebEleList, index, "Sorting Low to High");
	}

	public static void selectTheProduct(int index) {
		clickOnListByIndex(productList, index, "Selected first product from the product list");

	}

	public static String getThePrice() {
		String price = getText(priceText, "Get The price on before checkout screen");
		return price;

	}

}
