package com.fusionlab.utilities;

import java.util.Hashtable;

import org.testng.annotations.DataProvider;

/**
 * This class is mainly responsible for Data manipulation from Excel Sheet.
 * 
 * This class Receive testcase name from DataProvide class and return data in
 * the form of Key and Value Pair.
 */

public class DataUtil {

	/**
	 * Data Provider to provide the username and password from the excel sheet to
	 * the testcases.
	 * 
	 * @param excel
	 * @return
	 */
	@DataProvider
	public static Object[][] getCredentialData(ExcelReader excel) {

		String SHEETNAME = DPConstants.LoginCredentialSheet;

		int rows = excel.getRowCount(SHEETNAME);
		System.out.println("Total rows are : " + rows);

		int dataStartRowNum = 2;
		int testRows = 0;
		while (!excel.getCellData(SHEETNAME, 0, dataStartRowNum + testRows).equals("")) {
			testRows++;
		}
		System.out.println("Total rows of data are : " + testRows);

		int colStartColNum = 1;
		int testCols = 0;
		while (!excel.getCellData(SHEETNAME, testCols, colStartColNum).equals("")) {
			testCols++;
		}
		System.out.println("Total cols of data are : " + testCols);

		Object[][] data = new Object[testRows][1];
		int i = 0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < testCols; cNum++) {
				String testData = excel.getCellData(SHEETNAME, cNum, rNum);
				String colName = excel.getCellData(SHEETNAME, cNum, colStartColNum);
				table.put(colName, testData);
			}
			data[i][0] = table;
			i++;
		}
		return data;
	}

	/**
	 * Data Provider to provide the product's Category and Product name from the
	 * excel sheet to the Testcases.
	 * 
	 * @param excel
	 * @return
	 */
	@DataProvider
	public static Object[][] getProductData(ExcelReader excel) {

		String SHEETNAME = DPConstants.ProductSearchSheet;

		int rows = excel.getRowCount(SHEETNAME);
		System.out.println("Total rows are : " + rows);

		int dataStartRowNum = 2;
		int testRows = 0;
		while (!excel.getCellData(SHEETNAME, 0, dataStartRowNum + testRows).equals("")) {
			testRows++;
		}
		System.out.println("Total rows of data are : " + testRows);

		int colStartColNum = 1;
		int testCols = 0;
		while (!excel.getCellData(SHEETNAME, testCols, colStartColNum).equals("")) {
			testCols++;
		}
		System.out.println("Total cols of data are : " + testCols);

		Object[][] data = new Object[testRows][1];
		int i = 0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < testCols; cNum++) {
				String testData = excel.getCellData(SHEETNAME, cNum, rNum);
				String colName = excel.getCellData(SHEETNAME, cNum, colStartColNum);
				table.put(colName, testData);
			}
			data[i][0] = table;
			i++;
		}
		return data;
	}

}
