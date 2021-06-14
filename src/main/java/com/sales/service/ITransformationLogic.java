package com.sales.service;

import java.util.List;

import com.sales.pojo.LoadedData;
import com.sales.pojo.SalesPersonDetails;

public interface ITransformationLogic {

	LoadedData loadAllInputData(String sourceFileName, String sourceSheetName,
			String locationSheetName, String currencySheetName, String incentiveSheetName);
	
	List<SalesPersonDetails> performTransformation(LoadedData loadedData);
	
}
