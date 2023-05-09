package testData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

public class RulesManagementData {
	public WebDriver driver;

	public RulesManagementData() {
		
	}
	
	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ArrayList<String> rulesData(String sheetName) throws FileNotFoundException, IOException {
		File file = new File("./testFiles\\ValidationData.xlsx");
		
    	  FileInputStream inputStream = new FileInputStream(file);
    	  XSSFWorkbook wb = new XSSFWorkbook(inputStream);
    	 XSSFSheet sheetRule = wb.getSheet(sheetName);	 
    	  
    	  //find no of rows in excel file
    	  int rowCount = sheetRule.getLastRowNum() - sheetRule.getFirstRowNum();   
    	  
    	  //to store rules engine data
    	  ArrayList<String> ruleValues = new ArrayList<String>();	  
    	  
    	  //create a loop over all excel rows to read it
    	  for(int i=0; i < rowCount + 1 ; i++) {
    		  XSSFRow rowRule  = sheetRule.getRow(i);
    		  if(rowRule != null) {
    			  Cell cellRule = rowRule.getCell(1);
    			  if(cellRule.getCellType()==CellType.STRING) {
    				  String cellRuleValue = cellRule.getStringCellValue();
    				  ruleValues.add(cellRuleValue);
    			  }
    			  else if (cellRule.getCellType()==CellType.NUMERIC) {
    				  String cellRuleValue = String.valueOf(cellRule.getNumericCellValue());
    				  ruleValues.add(cellRuleValue);
    			  }
    		
    			
    		  }  
    	  }
		return ruleValues;
	}
	 
}