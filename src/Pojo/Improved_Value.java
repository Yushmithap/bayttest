package Pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;


public class Improved_Value {
	
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String assessedValueImp;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String marketValueImp;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String appraised_imp_value;
	
	public String getAssessedValueImp() {
		return assessedValueImp;
	}
	public void setAssessedValueImp(String assessedValueImp) {
		this.assessedValueImp = assessedValueImp;
	}
	public String getMarketValueImp() {
		return marketValueImp;
	}
	public void setMarketValueImp(String marketValueImp) {
		this.marketValueImp = marketValueImp;
	}
	public String getAppraised_imp_value() {
		return appraised_imp_value;
	}
	public void setAppraised_imp_value(String appraised_imp_value) {
		this.appraised_imp_value = appraised_imp_value;
	}
	

}
