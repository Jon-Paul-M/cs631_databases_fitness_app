package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="MEDICAL_PROFILES")
public class MedicalProfile {

	public MedicalProfile() {
		super();
	}
	
    private Long id;
	@Id
    @Column(name="PERSON_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    private Patient patient;
	@JoinColumn(name = "PERSON_ID")
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		patient.setMedicalProfile(this);
		this.patient = patient;
	}

	private BloodType bloodType;
	@Enumerated(EnumType.STRING)
	@Column(name="BLOOD_TYPE", nullable=false)
	public BloodType getBloodType() {
		return bloodType;
	}
	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}
	
	private Integer ldlBadCholesterol;
	@Column(name="LDL_BAD_CHOLESTEROL", nullable=true)
	public Integer getLdlBadCholesterol() {
		return ldlBadCholesterol;
	}
	public void setLdlBadCholesterol(Integer ldlBadCholesterol) {
		this.ldlBadCholesterol = ldlBadCholesterol;
	}

	private Integer hdlGoodCholesterol;
	@Column(name="HDL_GOOD_CHOLESTEROL", nullable=true)
	public Integer getHdlGoodCholesterol() {
		return hdlGoodCholesterol;
	}
	public void setHdlGoodCholesterol(Integer hdlGoodCholesterol) {
		this.hdlGoodCholesterol = hdlGoodCholesterol;
	}

	private Integer triglycerides;
	@Column(name="TRIGLYCERIDES", nullable=true)
	public Integer getTriglycerides() {
		return triglycerides;
	}
	public void setTriglycerides(Integer triglycerides) {
		this.triglycerides = triglycerides;
	}
	
	private Integer bloodSugar;
	@Column(name="BLOOD_SUGAR", nullable=true)
	public Integer getBloodSugar() {
		return bloodSugar;
	}
	public void setBloodSugar(Integer bloodSugar) {
		this.bloodSugar = bloodSugar;
	}
	
	private List<Allergy> allergies;
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE })
	@JoinTable(name = "MEDICAL_PROFILES_TO_ALLERGIES", 
	  joinColumns = @JoinColumn(name = "PERSON_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "ALLERGY_ID"))
	@OrderBy("id ASC")
	public List<Allergy> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public Integer totalCholesterol() {
		return getHdlGoodCholesterol() + getLdlBadCholesterol() + (int)(.2 * getTriglycerides());
	}
	
	public String heartDiseaseRisk() {
		// TODO: finish this. where should it go?
		
		return "";
	}
	
}
