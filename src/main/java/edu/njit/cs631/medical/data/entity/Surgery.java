package edu.njit.cs631.medical.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SURGERIES")
public class Surgery {

	public Surgery() {
		super();
	}
	
	private Long id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="SURGERY_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private Surgeon surgeon;
	@ManyToOne
	@JoinColumn(name = "SURGEON_ID")
	public Surgeon getSurgeon() {
		return surgeon;
	}
	public void setSurgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
	}

	private Patient patient;
	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	private Nurse nurse;
	@ManyToOne
	@JoinColumn(name = "NURSE_ID")
	public Nurse getNurse() {
		return nurse;
	}
	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
	
	private SurgeryType surgeryType;
	@ManyToOne
	@JoinColumn(name = "SURGERY_TYPE_ID")
	public SurgeryType getSurgeryType() {
		return surgeryType;
	}
	public void setSurgeryType(SurgeryType surgeryType) {
		this.surgeryType = surgeryType;
	}

	private Date schedule;
	@Column(name="SCHEDULE", nullable=true)
	public Date getSchedule() {
		return schedule;
	}
	public void setSchedule(Date schedule) {
		this.schedule = schedule;
	}
	
}
