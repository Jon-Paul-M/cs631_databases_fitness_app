package edu.njit.cs631.medical.data.entity;

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
	//AnnualSalary
	//EmployeeNo
	//ContractId
	//SpecialtyId

	@ManyToOne(optional=false)
	@JoinColumn(name="personnel_type_id")
	public PersonnelType getPersonnelType() {
		return personnelType;
	}
	public void setPersonnelType(PersonnelType personnelType) {
		this.personnelType = personnelType;
	}
}
