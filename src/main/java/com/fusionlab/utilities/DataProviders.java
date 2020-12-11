package com.fusionlab.utilities;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

/*
 * This Class will drive data from DataUtil class to Test Class
 * Step:
 * 1. Test Class call DataProvide Class with Data Provide name like  "credentialDP" or "searchProductDP".
 * 2. This Class will get Method Name of Calling Test Class using reflection and Pass it to Variable testcase.
 * 3. Then This Class will pass testcase name to DataUtil Class.
 * 4. This Class will get Data from DataUtil Class and Pass it to Test Class.
 * 
 */

public class DataProviders {

	/**
	 * Data provider for getDataCredential. It will provide username & password from
	 * the excel sheet.
	 * 
	 * @param m
	 * @return
	 */
	@DataProvider(name = "credentialDP", parallel = false)
	public static Object[][] getDataCredential(Method m) {
		ExcelReader excel = new ExcelReader(DPConstants.DataProviderExcelSheet);
		return DataUtil.getCredentialData(excel);
	}

	/**
	 * Data provider for getDataSearchProduct. It will provide Category & Product
	 * from the excel sheet.
	 * 
	 * @param m
	 * @return
	 */
	@DataProvider(name = "searchProductDP", parallel = false)
	public static Object[][] getDataSearchProduct(Method m) {
		ExcelReader excel = new ExcelReader(DPConstants.DataProviderExcelSheet);
		return DataUtil.getProductData(excel);
	}

}
