package Pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public class Year {
	
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String tax_year;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String market_year;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String appraised_year;
	
	public String getTax_year() {
		return tax_year;
	}
	public void setTax_year(String tax_year) {
		this.tax_year = tax_year;
	}
	public String getMarket_year() {
		return market_year;
	}
	public void setMarket_year(String market_year) {
		this.market_year = market_year;
	}
	public String getAppraised_year() {
		return appraised_year;
	}
	public void setAppraised_year(String appraised_year) {
		this.appraised_year = appraised_year;
	}
	

}
