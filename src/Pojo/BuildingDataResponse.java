package Pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public class BuildingDataResponse {
	
	public String pbKey;
	public Owners[] owners;
	public String ownerType;
	public Year year;
	public Assessed_Value assessed_value;
	public Land_Value land_value;
	public Improved_Value improved_value;
	@JsonSetter(nulls=Nulls.AS_EMPTY)
	public String replacement_cost;
	
	public String getPbKey() {
		return pbKey;
	}
	public void setPbKey(String pbKey) {
		this.pbKey = pbKey;
	}
	public Owners[] getOwners() {
		return owners;
	}
	public void setOwners(Owners[] owners) {
		this.owners = owners;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Assessed_Value getAssessed_value() {
		return assessed_value;
	}
	public void setAssessed_value(Assessed_Value assessed_value) {
		this.assessed_value = assessed_value;
	}
	public Land_Value getLand_value() {
		return land_value;
	}
	public void setLand_value(Land_Value land_value) {
		this.land_value = land_value;
	}
	public Improved_Value getImproved_value() {
		return improved_value;
	}
	public void setImproved_value(Improved_Value improved_value) {
		this.improved_value = improved_value;
	}
	public String getReplacement_cost() {
		return replacement_cost;
	}
	public void setReplacement_cost(String replacement_cost) {
		this.replacement_cost = replacement_cost;
	}
	
	

	
}
