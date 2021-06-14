package com.sales.pojo;

import java.util.HashMap;
import java.util.HashSet;

import lombok.Data;

@Data
public class LoadedData {

	private HashSet<SalesPersonDetails> salesDetailsSet;
	private HashMap<String, Location> locationDataMap;
	private HashMap<String, CurrencyRate> currencyMap;
	private HashMap<String, SalesIncentive> salesIncentiveMap;
}
