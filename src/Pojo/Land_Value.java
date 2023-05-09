package Pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public class Land_Value {
	
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String tax_land_value;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String market_land_value;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String appraised_land_value;
	
	public String getTax_land_value() {
		return tax_land_value;
	}
	public void setTax_land_value(String tax_land_value) {
		this.tax_land_value = tax_land_value;
	}
	public String getMarket_land_value() {
		return market_land_value;
	}
	public void setMarket_land_value(String market_land_value) {
		this.market_land_value = market_land_value;
	}
	public String getAppraised_land_value() {
		return appraised_land_value;
	}
	public void setAppraised_land_value(String appraised_land_value) {
		this.appraised_land_value = appraised_land_value;
	}
	
	

}
