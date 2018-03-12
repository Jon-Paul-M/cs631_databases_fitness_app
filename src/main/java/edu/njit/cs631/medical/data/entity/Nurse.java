package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "NURSES")
public class Nurse extends Personnel {	
	
	public Nurse() {
		super();
	}

	// TODO: what is a surgery type? 
	// Is a surgery type the same as a surgery specialty?
	//
	// A nurse can have at most 1 Surgery Type
	private SurgeryType surgeryType;
	@ManyToOne(optional = true)
	@JoinColumn(name = "SURGERY_TYPE_ID")
	public SurgeryType getSurgeryType() {
		return surgeryType;
	}
	public void setSurgeryType(SurgeryType surgeryType) {
		this.surgeryType = surgeryType;
	}

	// In order to assign a nurse to a surgery type, a nurse should possess
	// one or more of the skills required for the surgery type
	private List<SurgerySkill> surgerySkills;
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE })
	@JoinTable(name = "NURSES_TO_SKILLS", 
	  joinColumns = @JoinColumn(name = "PERSONNEL_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "SURGERY_SKILL_ID"))
	@OrderBy("id ASC")
	public List<SurgerySkill> getSurgerySkills() {
		return surgerySkills;
	}
	public void setSurgerySkills(List<SurgerySkill> surgerySkills) {
		this.surgerySkills = surgerySkills;
	}

	private Grade grade;
	@Enumerated(EnumType.STRING)
	@Column(name="GRADE", nullable=true)
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	private Integer yearsOfExperience;
	@Column(name="YEARS_OF_EXPERIENCE", nullable=true)
	public Integer getYearsOfExperience() {
		return yearsOfExperience;
	}
	public void setYearsOfExperience(Integer yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}


	// TODO: what is a nurse grade actually?
	public enum Grade {
		GOOD,
		BETTER,
		BEST
	}

}
