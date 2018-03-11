package edu.njit.cs631.medical.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="INPATIENTS")
public class Inpatient {

	public Inpatient() {
		super();
	}
	
	private Long id;
	@Id
    @Column(name="PATIENT_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private Patient patient;
	@JoinColumn(name = "PATIENT_ID")
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		patient.setInpatient(this);
		this.patient = patient;
	}
	
	private Date admissionDate;
	@Column(name="ADMISSION_DATE", nullable=true)
	public Date getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
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
	
	// TODO: make patient location first class entity ?
	private Integer nursingUnit;
	@Column(name="NURSING_UNIT", nullable=true)
	public Integer getNursingUnit() {
		return nursingUnit;
	}
	public void setNursingUnit(Integer nursingUnit) {
		this.nursingUnit = nursingUnit;
	}
	
	private String roomNumber;
	@Column(name="ROOM_NUMBER", nullable=true)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	private InpatientLocation.Wing wing;
	@Enumerated(EnumType.STRING)
	@Column(name="WING", nullable=true)
	public InpatientLocation.Wing getWing() {
		return wing;
	}
	public void setWing(InpatientLocation.Wing wing) {
		this.wing = wing;
	}
	
	private InpatientLocation.BedNumber bedNumber;
	@Enumerated(EnumType.STRING)
	@Column(name="BED_NUMBER", nullable=true)
	public InpatientLocation.BedNumber getBedNumber() {
		return bedNumber;
	}
	public void setBedNumber(InpatientLocation.BedNumber bedNumber) {
		this.bedNumber = bedNumber;
	}
	
}
