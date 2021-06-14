package com.sales;

import java.util.List;

import com.sales.pojo.LoadedData;
import com.sales.pojo.SalesPersonDetails;
import com.sales.service.ITransformationLogic;
import com.sales.service.ITransformationOutput;
import com.sales.service.TransformationLogic;
import com.sales.service.TransformationOutput;

public class Spark {

	public static void main(String[] args) {
		ITransformationLogic targetData = new TransformationLogic();
		LoadedData loadedData = targetData.loadAllInputData("src/main/resources/InputData.xlsx",
				"Source Data", "Location Data",
				"Currency Rate Data", "Sales Incentive Data");
		
		List<SalesPersonDetails> salesPersonListPostTransformation = targetData.performTransformation(loadedData);
		
		ITransformationOutput outputData = new TransformationOutput();
		outputData.storeDataInOutputFile("src/main/resources/SalesOutput.xlsx",
				salesPersonListPostTransformation);
	}
}
