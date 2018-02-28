package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PHYSICIAN")
public class Physician extends Person {

	public Physician() {
		super();
	}

	List<Patient> primaryCarePatients;
	@OneToMany(mappedBy="primaryCarePhysician", fetch=FetchType.LAZY)
	public List<Patient> getPrimaryCarePatients() {
		return primaryCarePatients;
	}
	public void setPrimaryCarePatients(List<Patient> primaryCarePatients) {
		this.primaryCarePatients = primaryCarePatients;
	}
}
