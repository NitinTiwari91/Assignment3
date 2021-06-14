package com.sales.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sales.pojo.CurrencyRate;
import com.sales.pojo.LoadedData;
import com.sales.pojo.Location;
import com.sales.pojo.SalesIncentive;
import com.sales.pojo.SalesPersonDetails;
import com.sales.utils.Constants;
import com.sales.utils.SalesUtils;

public class TransformationLogic implements ITransformationLogic {

	@Override
	public LoadedData loadAllInputData(String sourceFileName, String sourceSheetName,
			String locationSheetName, String currencySheetName, String incentiveSheetName) {
		LoadedData loadedData = new LoadedData();
		ITransformationInput transformationInputData = new TransformationInput();
		try(FileInputStream file = new FileInputStream(new File(sourceFileName));){
			// Create Workbook instance holding reference to .xlsx file
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	        
	        // Get desired sheet from the workbook
	        loadedData.setSalesDetailsSet(transformationInputData.getSalesData(workbook.getSheet(sourceSheetName)));
	        loadedData.setLocationDataMap(transformationInputData.getLocationData(workbook.getSheet(locationSheetName)));
	        loadedData.setCurrencyMap(transformationInputData.getCurrencyData(workbook.getSheet(currencySheetName)));
	        loadedData.setSalesIncentiveMap(transformationInputData.getSalesIncentiveData(workbook.getSheet(incentiveSheetName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadedData;
	}

	@Override
	public List<SalesPersonDetails> performTransformation(LoadedData loadedData) {
		HashSet<SalesPersonDetails> salesDetailsSet= loadedData.getSalesDetailsSet();
		HashMap<String, CurrencyRate> currencyMap = loadedData.getCurrencyMap();
		HashMap<String, Location> locationDataMap = loadedData.getLocationDataMap();
		HashMap<String, SalesIncentive> salesIncentiveMap = loadedData.getSalesIncentiveMap();
		setUSDAmountInSalesDetails(salesDetailsSet, currencyMap);
		salesDetailsSet = sortSalesDetails(salesDetailsSet);
		setGlobalRank(salesDetailsSet);
		Map<String, List<SalesPersonDetails>> countryToSalesPersonMap = groupDataCountryWise(salesDetailsSet);
		setCountryRank(countryToSalesPersonMap, locationDataMap);
		List<SalesPersonDetails> salesPersonList = new ArrayList<>();
		for(List<SalesPersonDetails> l : countryToSalesPersonMap.values()) {
			salesPersonList.addAll(l);
		}
		Map<String, List<SalesPersonDetails>> regionToSalesPersonMap = groupDataRegionWise(salesPersonList);
		setLocalRank(regionToSalesPersonMap);
		salesPersonList = new LinkedList<>();
		for(List<SalesPersonDetails> l : regionToSalesPersonMap.values()) {
			salesPersonList.addAll(l);
		}
		salesPersonList = setIncentive(salesPersonList, salesIncentiveMap, currencyMap);
		
		return salesPersonList;
	}
	
	private void setUSDAmountInSalesDetails(HashSet<SalesPersonDetails> salesDetailsSet, HashMap<String, CurrencyRate> currencyMap) {
		Iterator<SalesPersonDetails> itr = salesDetailsSet.iterator();
		while (itr.hasNext()) {
			SalesPersonDetails salesPersonDetails = itr.next();
			String salesAmt = salesPersonDetails.getSalesAmount();
			int subStringIndex = salesAmt.length() - 3;
			int amtBaseCurrency = Integer.parseInt(salesAmt.substring(0,subStringIndex));
			String baseCurrency = salesAmt.substring(subStringIndex);
			double exchangeRate = currencyMap.get(baseCurrency).getExchangeRate();
			salesPersonDetails.setSalesAmtUSD(SalesUtils.round(amtBaseCurrency / exchangeRate, Constants.ROUND_TO_DECIMAL_PLACES));
			salesPersonDetails.setBaseCurrencyValue(amtBaseCurrency);
			salesPersonDetails.setBaseCurrency(baseCurrency);
		}
	}
	
	private HashSet<SalesPersonDetails> sortSalesDetails(HashSet<SalesPersonDetails> salesDetailsSet){
		return salesDetailsSet.stream()
                .sorted((s1,s2) -> s2.getSalesAmtUSD().compareTo(s1.getSalesAmtUSD()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	private void setGlobalRank(HashSet<SalesPersonDetails> salesDetailsSet) {
		AtomicInteger counter = new AtomicInteger(0);
		salesDetailsSet.forEach(s -> s.setGlobalRank("G" + counter.addAndGet(1)));
	}
	
	private Map<String, List<SalesPersonDetails>> groupDataCountryWise(HashSet<SalesPersonDetails> salesDetailsSet) {
		Map<String, List<SalesPersonDetails>> countryToSalesPersonMap= salesDetailsSet.stream().collect(Collectors.groupingBy(SalesPersonDetails::getCountry, 
				Collectors.mapping(d -> d, Collectors.collectingAndThen(Collectors.toList(), ArrayList::new))));
		return countryToSalesPersonMap;
	}
	
	private Map<String, List<SalesPersonDetails>> setCountryRank(Map<String, List<SalesPersonDetails>> countryToSalesPersonMap,
			HashMap<String, Location> locationDataMap){
		for(String country : countryToSalesPersonMap.keySet()) {
			List<SalesPersonDetails> lis = countryToSalesPersonMap.get(country);
			AtomicInteger counter = new AtomicInteger(0);
			lis.forEach(s -> s.setCountryRank(locationDataMap.get(s.getCity()).getCountryAbb() + counter.addAndGet(1)));
		}
		return countryToSalesPersonMap;
	}
	
	private Map<String, List<SalesPersonDetails>> groupDataRegionWise(List<SalesPersonDetails> salesPersonList) {
		Map<String, List<SalesPersonDetails>> regionToSalesPersonMap= salesPersonList.stream().collect(Collectors.groupingBy(SalesPersonDetails::getRegion, 
				Collectors.mapping(d -> d, Collectors.collectingAndThen(Collectors.toList(), ArrayList::new))));
		return regionToSalesPersonMap;
	}
	
	private Map<String, List<SalesPersonDetails>> setLocalRank(Map<String, List<SalesPersonDetails>> regionToSalesPersonMap) {
		for(String country : regionToSalesPersonMap.keySet()) {
			List<SalesPersonDetails> lis = regionToSalesPersonMap.get(country);
			AtomicInteger counter = new AtomicInteger(0);
			lis.forEach(s -> s.setRegionRank(s.getRegion() + counter.addAndGet(1)));
		}
		return regionToSalesPersonMap;
	}
	
	private List<SalesPersonDetails> setIncentive(List<SalesPersonDetails> salesPersonList, 
			HashMap<String, SalesIncentive> salesIncentiveMap, HashMap<String, CurrencyRate> currencyMap) { 
		salesPersonList.stream().forEach(s-> s.setSalesIncentive(calculateIncentive(s, salesIncentiveMap, currencyMap)));
		return salesPersonList;
	}
	
	private String calculateIncentive(SalesPersonDetails salesPersonDetail, HashMap<String, SalesIncentive> salesIncentiveMap,
			HashMap<String, CurrencyRate> currencyMap) {
		SalesIncentive salesIncentiveBean = salesIncentiveMap.get(salesPersonDetail.getSalesPersonLevel());
		double incentive = (salesIncentiveBean.getCalculationPercentage()*salesPersonDetail.getSalesAmtUSD())/100;
		incentive = (incentive < salesIncentiveBean.getMaxIncentiveInUSD())?incentive:salesIncentiveBean.getMaxIncentiveInUSD();
		incentive =currencyMap.get(salesPersonDetail.getBaseCurrency()).getExchangeRate()*incentive;
		return SalesUtils.round(incentive, Constants.ROUND_TO_DECIMAL_PLACES) + salesPersonDetail.getBaseCurrency();
	}
}
