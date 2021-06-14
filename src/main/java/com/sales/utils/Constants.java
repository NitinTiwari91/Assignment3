package com.sales.utils;

public class Constants {

	public static final String REGEX_FOR_NUMBERS = "[^0-9?%]";
	
	public static final String CURRENCY_RATE_SPLITTER = "USD";
	public static final String PERCENTAGE_SPLITTER = "%";
	public static final String FILE_NAME_SPLITTER = "\\/";
	
	//Specify the decimal place to round double
	public static final int ROUND_TO_DECIMAL_PLACES = 2;
	//We require first column of each table and skip that row
	public static final String COLOUMN_NAMES_FOR_MATCHING = "Salesperson ID | City Name | Currency Pair | Region";

	public static final String OUT_SPREADSHEET_NAME = "Sales Output";
	public static final String[] OUTPUT_COLOUMN_NAMES = {"Salesperson ID", "Region", "Country", "City", 
			"Sales Amt-Base Currency", "Sales Amt-USD", "Global Rank", "Region Rank", "Country Rank", "Sales Incentive", "Month"};
}
