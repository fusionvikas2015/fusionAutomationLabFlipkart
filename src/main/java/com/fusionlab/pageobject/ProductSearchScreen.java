package com.fusionlab.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.fusionlab.android.Mobile;
import com.fusionlab.driver.BaseClass;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductSearchScreen extends BaseClass {

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private static WebElement SearchBar;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/filter_text")
	private static WebElement filter;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/brand_text")
	private static WebElement brand;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private static List<WebElement> checkBox;

	@AndroidFindBy(id = "com.google.android.gms:id/apply")
	private static WebElement applyBtn;

	/**
	 * This method can be apply filter.
	 * 
	 * @param searchForProduct
	 * @return
	 * @throws Exception
	 */
	public static void applyFilter(String Brand) throws Exception {
		click(filter, "Clicked on Filter");
		click(brand, "Clicked on Brand");
		Mobile.scrollToText(Brand);
		clickOnListByIndex(checkBox, 10, "Check index 10");
		click(applyBtn, "Filter applied");
	}

}
