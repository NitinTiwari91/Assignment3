package com.sales.service;

import com.sales.pojo.CurrencyRate;
import com.sales.pojo.Location;
import com.sales.pojo.SalesIncentive;
import com.sales.pojo.SalesPersonDetails;
import com.sales.service.TransformationInput;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class TransformationInputTest {

  @Test
  public void getSalesData() {
    TransformationInput transformationInput = new TransformationInput();
    XSSFSheet xssfSheet = createXSSFsheetInputData("Source Data");
    HashSet<SalesPersonDetails> salesPersonDetailsHashSet = transformationInput.getSalesData(xssfSheet);
    Assert.assertNotNull(salesPersonDetailsHashSet);
    Assert.assertEquals(6, salesPersonDetailsHashSet.size());
  }

  @Test
  public void getLocationData() {
    TransformationInput transformationInput = new TransformationInput();
    XSSFSheet xssfSheet = createXSSFsheetInputData("Location Data");
    HashMap<String, Location> locationHashMap = transformationInput.getLocationData(xssfSheet);
    Assert.assertNotNull(locationHashMap);
    Assert.assertEquals(5, locationHashMap.size());
  }

  @Test
  public void getCurrencyData() {
    TransformationInput transformationInput = new TransformationInput();
    XSSFSheet xssfSheet = createXSSFsheetInputData("Currency Rate Data");
    HashMap<String, CurrencyRate> currencyData = transformationInput.getCurrencyData(xssfSheet);
    Assert.assertNotNull(currencyData);
    Assert.assertEquals(3, currencyData.size());
  }

  @Test
  public void getSalesIncentiveData() {
    TransformationInput transformationInput = new TransformationInput();
    XSSFSheet xssfSheet = createXSSFsheetInputData("Sales Incentive Data");
    HashMap<String, SalesIncentive> salesIncentiveData = transformationInput.getSalesIncentiveData(xssfSheet);
    Assert.assertNotNull(salesIncentiveData);
    Assert.assertEquals(4, salesIncentiveData.size());
  }

  private XSSFSheet createXSSFsheetInputData(String name) {
    try(FileInputStream file = new FileInputStream(new File("src/main/resources/InputData.xlsx"));){
      // Create Workbook instance holding reference to .xlsx file
      XSSFWorkbook workbook = new XSSFWorkbook(file);
      // Get desired sheet from the workbook
      return workbook.getSheet(name);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}