package com.sales.service;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.sales.pojo.CurrencyRate;
import com.sales.pojo.Location;
import com.sales.pojo.SalesIncentive;
import com.sales.pojo.SalesPersonDetails;

public interface ITransformationInput {
	public HashSet<SalesPersonDetails> getSalesData(XSSFSheet sourceSheet);
	/* City Name - Object Map*/
	public HashMap<String, Location> getLocationData(XSSFSheet locationSheet);
	/* USD - CorrespondingCurrencyObject Map*/
	public HashMap<String, CurrencyRate> getCurrencyData(XSSFSheet currencySheet);
	/* Sales Person Level Regionwise - Object Map*/
	public HashMap<String, SalesIncentive> getSalesIncentiveData(XSSFSheet incentiveSheet);
}
