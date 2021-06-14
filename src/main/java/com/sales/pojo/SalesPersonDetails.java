package com.sales.pojo;

import lombok.Data;

@Data
public class SalesPersonDetails {

	private String salesPersonID;
	private String region;
	private String country;
	private String city;
	private String salesPersonLevel;
	private String salesAmount;
	private String monthYear;
	
	private double baseCurrencyValue;
	private String baseCurrency;
	private Double salesAmtUSD;
	private String globalRank;
	private String regionRank;
	private String countryRank;
	private String salesIncentive;
}
