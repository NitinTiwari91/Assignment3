package com.sales.service;

import com.sales.pojo.LoadedData;
import com.sales.pojo.SalesPersonDetails;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TransformationLogicTest {

  @Test
  public void testLoadAllInputData() {
    TransformationLogic transformationLogic = new TransformationLogic();
    LoadedData loadedData = transformationLogic.loadAllInputData("src/main/resources/InputData.xlsx",
        "Source Data", "Location Data",
        "Currency Rate Data", "Sales Incentive Data");
    Assert.assertNotNull(loadedData);
    Assert.assertNotNull(loadedData.getSalesDetailsSet());
    Assert.assertNotNull(loadedData.getLocationDataMap());
    Assert.assertNotNull(loadedData.getSalesIncentiveMap());
    Assert.assertNotNull(loadedData.getCurrencyMap());
    Assert.assertEquals(6, loadedData.getSalesDetailsSet().size());
    Assert.assertEquals(5,loadedData.getLocationDataMap().size());
    Assert.assertEquals(4,loadedData.getSalesIncentiveMap().size());
    Assert.assertEquals(3,loadedData.getCurrencyMap().size());
  }

  @Test
  public void testPerformTransformation() {
    TransformationLogic transformationLogic = new TransformationLogic();
    LoadedData loadedData = transformationLogic.loadAllInputData("src/main/resources/InputData.xlsx",
        "Source Data", "Location Data",
        "Currency Rate Data", "Sales Incentive Data");
    List<SalesPersonDetails> result = transformationLogic.performTransformation(loadedData);
    Assert.assertNotNull(result);
    Assert.assertEquals(6, result.size());
  }
}