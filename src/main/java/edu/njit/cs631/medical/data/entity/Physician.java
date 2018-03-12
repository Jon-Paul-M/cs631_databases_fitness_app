package edu.njit.cs631.medical.data.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PHYSICIANS")
public class Physician extends Personnel {

	public Physician() {
		super();
	}
	
	private BigDecimal ownershipPercentage;
	@Column(name="OWNERSHIP_PERCENTAGE", nullable=true)
	public BigDecimal getOwnershipPercentage() {
		return ownershipPercentage;
	}
	public void setOwnershipPercentage(BigDecimal ownershipPercentage) {
		this.ownershipPercentage = ownershipPercentage;
	}

	private List<Patient> primaryCarePatients;
	@OneToMany(mappedBy="primaryCarePhysician", fetch=FetchType.LAZY)
	public List<Patient> getPrimaryCarePatients() {
		return primaryCarePatients;
	}
	public void setPrimaryCarePatients(List<Patient> primaryCarePatients) {
		this.primaryCarePatients = primaryCarePatients;
	}
	
	private List<Prescription> prescriptions;
	@OneToMany(mappedBy="prescribingPhysician", fetch=FetchType.LAZY)
	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}
	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	private Specialty specialty;
	@ManyToOne(optional=true)
	@JoinColumn(name="SPECIALTY_ID")
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
}
