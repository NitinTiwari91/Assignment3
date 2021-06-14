package com.sales.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sales.pojo.SalesPersonDetails;
import com.sales.utils.Constants;

public class TransformationOutput implements ITransformationOutput {

	@Override
	public void storeDataInOutputFile(String outputFilePath,
			List<SalesPersonDetails> salesPersonListPostTransformation) {
		// Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
  
        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet(Constants.OUT_SPREADSHEET_NAME);
        int rowNum = 0;
        //Inserting column titles
        salesPersonListPostTransformation.add(0, getColumnNames());
        for(SalesPersonDetails salesDetail: salesPersonListPostTransformation) {
        	Row row = sheet.createRow(rowNum++);
        	insertDataInColumns(salesDetail, row, rowNum);
        }
  
        try (FileOutputStream out = new FileOutputStream(new File(outputFilePath));){
            // this Writes the workbook output file path
            workbook.write(out);
            out.close();
            System.out.println(outputFilePath.split(Constants.FILE_NAME_SPLITTER)[3] + " written successfully at " + outputFilePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

	private SalesPersonDetails getColumnNames() {
		String [] columnNames = Constants.OUTPUT_COLOUMN_NAMES;
        SalesPersonDetails salesOutputColumnName = new SalesPersonDetails();
        salesOutputColumnName.setSalesPersonID(columnNames[0]);
        salesOutputColumnName.setRegion(columnNames[1]);
        salesOutputColumnName.setCountry(columnNames[2]);
        salesOutputColumnName.setCity(columnNames[3]);
        salesOutputColumnName.setSalesAmount(columnNames[4]);
        salesOutputColumnName.setGlobalRank(columnNames[6]);
        salesOutputColumnName.setRegionRank(columnNames[7]);
        salesOutputColumnName.setCountryRank(columnNames[8]);
        salesOutputColumnName.setSalesIncentive(columnNames[9]);
        salesOutputColumnName.setMonthYear(columnNames[10]);
        return salesOutputColumnName;
	}
	
	private void insertDataInColumns(SalesPersonDetails salesDetails, Row row, int rowNum) {
		int counter = 0;
		Cell cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getSalesPersonID());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getRegion());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getCountry());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getCity());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getSalesAmount());
				
		cell = row.createCell(counter++);
		if(rowNum == 1) {
			cell.setCellValue(Constants.OUTPUT_COLOUMN_NAMES[5]);
		} else {
			cell.setCellValue(salesDetails.getSalesAmtUSD() + "USD");			
		}
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getGlobalRank());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getRegionRank());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getCountryRank());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getSalesIncentive());
		
		cell = row.createCell(counter++);
		cell.setCellValue(salesDetails.getMonthYear());
	}
}
