package com.sales.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.sales.pojo.CurrencyRate;
import com.sales.pojo.Location;
import com.sales.pojo.SalesIncentive;
import com.sales.pojo.SalesPersonDetails;
import com.sales.utils.Constants;

public class TransformationInput implements ITransformationInput {
	
	@Override
	public HashSet<SalesPersonDetails> getSalesData(XSSFSheet sourceSheet) {
		HashSet<SalesPersonDetails> salesDetailsSet = new HashSet<>();
		Iterator<Row> rowIterator = sourceSheet.iterator();
        A: while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            SalesPersonDetails salesData = new SalesPersonDetails();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                // Check the cell type and format accordingly
                String cellValue = "";
                switch (cell.getCellType()) {
                case NUMERIC:
                	cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                	cellValue = cell.getStringCellValue();
                    break;
				default:
					break;
                }
                // Skipping the title row
                if(Constants.COLOUMN_NAMES_FOR_MATCHING.contains(cellValue)) {
                	continue A;
                }
                switch (cell.getColumnIndex()) {
				case 0:
					salesData.setSalesPersonID(cellValue);
					break;
				case 1:
					salesData.setRegion(cellValue);
					break;
				case 2:
					salesData.setCountry(cellValue);
					break;
				case 3:
					salesData.setCity(cellValue);
					break;
				case 4:
					salesData.setSalesPersonLevel(cellValue);
					break;
				case 5:
					salesData.setSalesAmount(cellValue);
					break;
				case 6:
					salesData.setMonthYear(cellValue);
					break;

				default:
					break;
				}
            }
            salesDetailsSet.add(salesData);
        }
		return salesDetailsSet;
	}

	@Override
	public HashMap<String, Location> getLocationData(XSSFSheet locationSheet) {
		HashMap<String, Location> locationDataMap = new HashMap<>();
		Iterator<Row> rowIterator = locationSheet.iterator();
        A: while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            Location locationData = new Location();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                // Check the cell type and format accordingly
                String cellValue = "";
                switch (cell.getCellType()) {
                case NUMERIC:
                	cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                	cellValue = cell.getStringCellValue();
                    break;
				default:
					break;
                }
             // Skipping the title row
                if(Constants.COLOUMN_NAMES_FOR_MATCHING.contains(cellValue)) {
                	continue A;
                }
                switch (cell.getColumnIndex()) {
				case 0:
					locationData.setCityName(cellValue);
					break;
				case 1:
					locationData.setCityAbb(cellValue);
					break;
				case 2:
					locationData.setCountryName(cellValue);
					break;
				case 3:
					locationData.setCountryAbb(cellValue);
					break;
				case 4:
					locationData.setRegion(cellValue);
					break;

				default:
					break;
				}
            }
            locationDataMap.put(locationData.getCityName(), locationData);
        }
		return locationDataMap;
	}

	@Override
	public HashMap<String, CurrencyRate> getCurrencyData(XSSFSheet currencySheet) {
		HashMap<String, CurrencyRate> currencyMap = new HashMap<>();
		Iterator<Row> rowIterator = currencySheet.iterator();
        A: while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            CurrencyRate currencyData = new CurrencyRate();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                // Check the cell type and format accordingly
                String cellValue = "";
                switch (cell.getCellType()) {
                case NUMERIC:
                	cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                	cellValue = cell.getStringCellValue();
                    break;
				default:
					break;
                }
                // Skipping the title row
                if(Constants.COLOUMN_NAMES_FOR_MATCHING.contains(cellValue)) {
                	continue A;
                }
                switch (cell.getColumnIndex()) {
				case 0:
					currencyData.setCurrencyPair(cellValue);
					currencyData.setToCountryCurrency(cellValue.split(Constants.CURRENCY_RATE_SPLITTER)[1]);
					break;
				case 1:
					currencyData.setExchangeRate(Double.parseDouble(cellValue));
					break;

				default:
					break;
				}
            }
            currencyMap.put(currencyData.getToCountryCurrency(), currencyData);
        }
		return currencyMap;
	}

	@Override
	public HashMap<String, SalesIncentive> getSalesIncentiveData(XSSFSheet incentiveSheet) {
		HashMap<String, SalesIncentive> salesIncentiveMap = new HashMap<>();
		Iterator<Row> rowIterator = incentiveSheet.iterator();
        A: while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            SalesIncentive incentiveData = new SalesIncentive();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                // Check the cell type and format accordingly
                String cellValue = "";
                switch (cell.getCellType()) {
                case NUMERIC:
                	cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                	cellValue = cell.getStringCellValue();
                    break;
				default:
					break;
                }
                // Skipping the title row
                if(Constants.COLOUMN_NAMES_FOR_MATCHING.contains(cellValue)) {
                	continue A;
                }
                switch (cell.getColumnIndex()) {
				case 0:
					incentiveData.setRegion(cellValue);
					break;
				case 1:
					incentiveData.setSalesPersonLevel(cellValue);
					break;
				case 2:
					incentiveData.setIncentiveFormula(cellValue);;
					String onlyNumbers = cellValue.replaceAll(Constants.REGEX_FOR_NUMBERS, "");
					String [] salesFormulaDetails = onlyNumbers.split(Constants.PERCENTAGE_SPLITTER);
					incentiveData.setCalculationPercentage(Integer.parseInt(salesFormulaDetails[0]));
					incentiveData.setMaxIncentiveInUSD(Integer.parseInt(salesFormulaDetails[1]));
					break;

				default:
					break;
				}
            }
            salesIncentiveMap.put(incentiveData.getSalesPersonLevel(), incentiveData);
        }
		return salesIncentiveMap;
	}

}
