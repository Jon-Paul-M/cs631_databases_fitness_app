package edu.njit.cs631.fitness.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="PATIENTS")
public class Patient {

	public Patient() {
		super();
	}
	
	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="PATIENT_ID", nullable=false)
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

	private String patientNumber;
	@Column(name="PATIENT_NUMBER", nullable=false)
	public String getPatientNumber() {
		return patientNumber;
	}
	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	private List<Illness> illnesses;
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE })
	@JoinTable(name = "PATIENTS_TO_ILLNESSES", 
	  joinColumns = @JoinColumn(name = "PATIENT_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "ILLNESS_ID"))
	@OrderBy("id ASC")
	public List<Illness> getIllnesses() {
		return illnesses;
	}
	public void setIllnesses(List<Illness> illnesses) {
		this.illnesses = illnesses;
	}

	private Physician primaryCarePhysician;
	@ManyToOne(optional=true)
	@JoinColumn(name="PRIMARY_PHYSICIAN_ID")
	public Physician getPrimaryCarePhysician() {
		return primaryCarePhysician;
	}
	public void setPrimaryCarePhysician(Physician primaryCarePhysician) {
		this.primaryCarePhysician = primaryCarePhysician;
	}
	
	private List<Prescription> prescriptions;
	@OneToMany(mappedBy="patient")
	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}
	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	private MedicalProfile medicalProfile;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
	public MedicalProfile getMedicalProfile() {
		return medicalProfile;
	}
	public void setMedicalProfile(MedicalProfile medicalProfile) {
		this.medicalProfile = medicalProfile;
	}
	
	private Inpatient inpatient;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
	public Inpatient getInpatient() {
		return inpatient;
	}
	public void setInpatient(Inpatient inpatient) {
		this.inpatient = inpatient;
	}
}
