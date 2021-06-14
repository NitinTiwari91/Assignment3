package com.sales.pojo;

import lombok.Data;

@Data
public class CurrencyRate {

	private String currencyPair;
	private double exchangeRate;
	private String toCountryCurrency;
}
