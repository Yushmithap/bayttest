package APIresources;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Pages.DataInsightsWidgetPage;
import Pojo.Assessed_Value;
import Pojo.BuildingDataResponse;
import Pojo.Improved_Value;
import Pojo.Land_Value;
import Pojo.Owners;
import Pojo.Year;
import StepDefinitions.BuildingDataWidget;

public class APIResponseValidation {
	public WebDriver driver;
	

	
	public void buildingDataResponseValidation() {
		DataInsightsWidgetPage data = new DataInsightsWidgetPage(driver);
    	List<String> buildingDataUIValues = data.getBuildingDataTableValues();
    	System.out.println(buildingDataUIValues);
    	BuildingDataWidget widget = new BuildingDataWidget();
    BuildingDataResponse buildingDataResponse =  widget.buildingDataReq.when().post("precisely/building-data").as(BuildingDataResponse.class);
    	
 //pbKey
		
		Assert.assertEquals(data.getpbKey(), buildingDataResponse.getPbKey());  				

//ownerName
		Owners[] owner = buildingDataResponse.getOwners();	
		Assert.assertEquals(data.getOwnerName(), owner[0].getOwnerName());  				

//year	
		  Year year = buildingDataResponse.getYear(); 
		  Assert.assertEquals(buildingDataUIValues.get(1), year.getTax_year());  		
		  Assert.assertEquals(buildingDataUIValues.get(2), year.getMarket_year());
		  Assert.assertEquals(buildingDataUIValues.get(3), year.getAppraised_year());
		  
//assedssed value		  
		  Assessed_Value assessed_Value = buildingDataResponse.getAssessed_value();
		  Assert.assertEquals(buildingDataUIValues.get(5),assessed_Value.getTax_value());
		  Assert.assertEquals(buildingDataUIValues.get(6),assessed_Value.getMarket_value()); 
		  Assert.assertEquals(buildingDataUIValues.get(7),assessed_Value.getAppraised_value());

//land value
		  Land_Value land_value = buildingDataResponse.getLand_value();
		  Assert.assertEquals(buildingDataUIValues.get(9),
		  land_value.getTax_land_value());
		  Assert.assertEquals(buildingDataUIValues.get(10),land_value.
		  getMarket_land_value());
		  Assert.assertEquals(buildingDataUIValues.get(11),
		  land_value.getAppraised_land_value());
	
 //improved value
		  Improved_Value improved_value =
		  buildingDataResponse.getImproved_value();
		  Assert.assertEquals(buildingDataUIValues.get(13),
		  improved_value.getAssessedValueImp());
		  Assert.assertEquals(buildingDataUIValues.get(14),improved_value.
		  getMarketValueImp());
		  Assert.assertEquals(buildingDataUIValues.get(15),
		  improved_value.getAppraised_imp_value());
		  
//Replacement cost		  
		  Assert.assertEquals(data.getReplacementCost(),
		  buildingDataResponse.getReplacement_cost());
		
	}

}
