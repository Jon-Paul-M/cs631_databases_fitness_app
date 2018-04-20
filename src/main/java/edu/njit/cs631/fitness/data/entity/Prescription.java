package edu.njit.cs631.fitness.data.entity;

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

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="PRESCRIPTION_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	
	private Physician prescribingPhysician;
	@ManyToOne(optional=true)
	@JoinColumn(name="PHYSICIAN_ID")
	public Physician getPrescribingPhysician() {
		return prescribingPhysician;
	}
	public void setPrescribingPhysician(Physician prescribingPhysician) {
		this.prescribingPhysician = prescribingPhysician;
	}
	
	// TODO: make dosage a first class entity
	private String dosage;
	@Column(name="DOSAGE", nullable=false)
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	
	// TODO: make frequency a first class entity
	private String frequency;
	@Column(name="FREQUENCY", nullable=false)
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	
}
