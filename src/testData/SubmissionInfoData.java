package testData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

public class SubmissionInfoData {
	
	public WebDriver driver;

	public SubmissionInfoData() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> basicInfoColumnHeaders(String sheetName) throws FileNotFoundException, IOException {
		File file = new File(System.getProperty("user.dir")+"\\testFiles\\BasicInfo.xlsx");
    	  FileInputStream inputStream = new FileInputStream(file);
    	  XSSFWorkbook wb = new XSSFWorkbook(inputStream);
    	 XSSFSheet sheetInfo = wb.getSheet(sheetName);	 
    	  
    	  //find no of rows in excel file
    	  int rowCount = sheetInfo.getLastRowNum() - sheetInfo.getFirstRowNum();   
    	  
    	  //to store rules engine data
    	  List<String> columnHeaders = new ArrayList<String>();	  
    	  
    	  //create a loop over all excel rows to read it
    	  for(int i=0; i < rowCount + 1 ; i++) {
    		  XSSFRow rowInfo  = sheetInfo.getRow(i);
    		  if(rowInfo != null) {
    			  Cell cellInfo = rowInfo.getCell(0);
    			  if(cellInfo.getCellType()==CellType.STRING) {
    				  String cellInfoValue = cellInfo.getStringCellValue();
    				  columnHeaders.add(cellInfoValue);
    			  }
    			  else if (cellInfo.getCellType()==CellType.NUMERIC) {
    				  String cellInfoValue = String.valueOf(cellInfo.getNumericCellValue());
    				  columnHeaders.add(cellInfoValue);
    			  }
    		
    			
    		  }  
    	  }
		return columnHeaders;
	}
	
	
	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> basicInfoColumnValues(String sheetName) throws FileNotFoundException, IOException {
		File file = new File(System.getProperty("user.dir")+"\\testFiles\\BasicInfo.xlsx");
    	  FileInputStream inputStream = new FileInputStream(file);
    	  XSSFWorkbook wb = new XSSFWorkbook(inputStream);
    	 XSSFSheet sheetInfo = wb.getSheet(sheetName);	 
    	  
    	  //find no of rows in excel file
    	  int rowCount = sheetInfo.getLastRowNum() - sheetInfo.getFirstRowNum();   
    	  
    	  //to store rules engine data
    	  List<String> columnValues = new ArrayList<String>();	  
    	  
    	  //create a loop over all excel rows to read it
    	  for(int i=0; i < rowCount + 1 ; i++) {
    		  XSSFRow rowInfo  = sheetInfo.getRow(i);
    		  if(rowInfo != null) {
    			  Cell cellInfo = rowInfo.getCell(1);
    			  if(cellInfo.getCellType()==CellType.STRING) {
    				  String cellInfoValue = cellInfo.getStringCellValue();
    				  columnValues.add(cellInfoValue);
    			  }
    			  else if (cellInfo.getCellType()==CellType.NUMERIC) {
    				  String cellInfoValue = String.valueOf(cellInfo.getNumericCellValue());
    				  columnValues.add(cellInfoValue);
    			  }
    		
    			
    		  }  
    	  }
		return columnValues;
	}
	 

}
