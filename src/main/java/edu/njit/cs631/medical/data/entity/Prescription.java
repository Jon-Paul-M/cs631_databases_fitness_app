package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PRESCRIPTIONS")
public class Prescription {

	public Prescription() {
		super();
	}

	private Long id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="PRESCRIPTION_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	private Patient patient;
	@JoinColumn(name="PATIENT_ID")
	@ManyToOne(optional=true)
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	private Medication medication;
	@JoinColumn(name="MEDICATION_ID")
	@ManyToOne(optional=true)
	public Medication getMedication() {
		return medication;
	}
	public void setMedication(Medication medication) {
		this.medication = medication;
	}
	
	
}
