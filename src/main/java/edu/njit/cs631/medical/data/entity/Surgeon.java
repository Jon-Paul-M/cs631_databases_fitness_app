package edu.njit.cs631.medical.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SURGEONS")
public class Surgeon extends Personnel {

	public Surgeon() {
		super();
	}

	private Speciality speciality;
	@ManyToOne(optional=true)
	@JoinColumn(name="SPECIALITY_ID")
	public Speciality getSpeciality() {
		return speciality;
	}
	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
}
