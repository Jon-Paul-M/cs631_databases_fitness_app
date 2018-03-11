package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PERSONNEL")
public class Personnel extends Person {

	public Personnel() {
		super();
	}

	private PersonnelType personnelType;
	@ManyToOne(optional=false)
	@JoinColumn(name="personnel_type_id")
	public PersonnelType getPersonnelType() {
		return personnelType;
	}
	public void setPersonnelType(PersonnelType personnelType) {
		this.personnelType = personnelType;
	}
	
	private int annualSalary;
	@Column(name="ANNUAL_SALARY", nullable=true)
	public int getAnnualSalary() {
		return annualSalary;
	}
	public void setAnnualSalary(int annualSalary) {
		this.annualSalary = annualSalary;
	}
	
	private String employmentNumber;
	@Column(name="EMPLOYMENT_NUMBER", nullable=false)
	public String getEmploymentNumber() {
		return employmentNumber;
	}
	public void setEmploymentNumber(String employmentNumber) {
		this.employmentNumber = employmentNumber;
	}
	
	
}
