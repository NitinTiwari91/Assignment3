package com.sales.service;

import java.util.List;

import com.sales.pojo.SalesPersonDetails;

public interface ITransformationOutput {
	void storeDataInOutputFile(String outputFilePath, List<SalesPersonDetails> salesPersonListPostTransformation);
}
