package edu.njit.cs631.fitness.web.model;

public class MonthModel {

	final private Integer index;
	final private String name;
	
	public MonthModel(Integer index, String name) {
		super();
		this.index = index;
		this.name = name;
	}
	
	public Integer getIndex() {
		return index;
	}
	public String getName() {
		return name;
	}

}
