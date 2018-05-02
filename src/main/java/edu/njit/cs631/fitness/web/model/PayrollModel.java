package edu.njit.cs631.fitness.web.model;

public class PayrollModel {

	public PayrollModel() {
		super();
	}

	private String startMM;
	private String startDD;
	private String startYYYY;
	private String endMM;
	private String endDD;
	private String endYYYY;
	private String federalRate;
	private String stateRate;
	private String otherRate;

	
	public String getStartMM() {
		return startMM;
	}
	public void setStartMM(String startMM) {
		this.startMM = startMM;
	}
	public String getStartDD() {
		return startDD;
	}
	public void setStartDD(String startDD) {
		this.startDD = startDD;
	}
	public String getStartYYYY() {
		return startYYYY;
	}
	public void setStartYYYY(String startYYYY) {
		this.startYYYY = startYYYY;
	}

	public String getEndMM() {
		return endMM;
	}
	public void setEndMM(String endMM) {
		this.endMM = endMM;
	}
	public String getEndDD() {
		return endDD;
	}
	public void setEndDD(String endDD) {
		this.endDD = endDD;
	}
	public String getEndYYYY() {
		return endYYYY;
	}
	public void setEndYYYY(String endYYYY) {
		this.endYYYY = endYYYY;
	}
	public String getFederalRate() {
		return federalRate;
	}
	public void setFederalRate(String federalRate) {
		this.federalRate = federalRate;
	}
	public String getStateRate() {
		return stateRate;
	}
	public void setStateRate(String stateRate) {
		this.stateRate = stateRate;
	}
	public String getOtherRate() {
		return otherRate;
	}
	public void setOtherRate(String otherRate) {
		this.otherRate = otherRate;
	}

}
