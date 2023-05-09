package Pojo;

import java.util.List;

public class ChangesInExposureVehicleType {
	
	private String vehicleType;
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getCurrentYearPower() {
		return currentYearPower;
	}
	public void setCurrentYearPower(String currentYearPower) {
		this.currentYearPower = currentYearPower;
	}
	public String getPreviousYearPower() {
		return previousYearPower;
	}
	public void setPreviousYearPower(String previousYearPower) {
		this.previousYearPower = previousYearPower;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	private String currentYearPower;
	private String previousYearPower;
	private String change;
	

}
