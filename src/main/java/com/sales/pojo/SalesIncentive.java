package com.sales.pojo;

import lombok.Data;

@Data
public class SalesIncentive {

	private String region;
	private String salesPersonLevel;
	private String incentiveFormula;
	private int maxIncentiveInUSD;
	private int calculationPercentage;
}
