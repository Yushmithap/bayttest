package Pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public class Assessed_Value {
	
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String tax_value;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String market_value;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String appraised_value;
	
	public String getTax_value() {
		return tax_value;
	}
	public void setTax_value(String tax_value) {
		this.tax_value = tax_value;
	}
	public String getMarket_value() {
		return market_value;
	}
	public void setMarket_value(String market_value) {
		this.market_value = market_value;
	}
	public String getAppraised_value() {
		return appraised_value;
	}
	public void setAppraised_value(String appraised_value) {
		this.appraised_value = appraised_value;
	}
	

}
