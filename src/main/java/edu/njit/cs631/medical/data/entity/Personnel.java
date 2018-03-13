package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PERSONNEL")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personnel {

	public Personnel() {
		super();
	}
	
	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="PERSONNEL_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private Person person;
	@JoinColumn(name = "PERSON_ID")
	@OneToOne(fetch = FetchType.LAZY)
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	private PersonnelType personnelType;
	@ManyToOne(optional=false)
	@JoinColumn(name="PERSONNEL_TYPE_ID")
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
