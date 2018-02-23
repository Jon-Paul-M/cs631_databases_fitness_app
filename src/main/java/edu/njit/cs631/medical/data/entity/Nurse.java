package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

	// A nurse can have at most 1 Surgery Type
	private SurgeryType surgeryType;

	// In order to assign a nurse to a surgery type, a nurse should possess
	// one or more of the skills required for the surgery type
	private List<SurgerySkill> surgerySkills;

	@ManyToOne(optional = true)
	@JoinColumn(name = "SURGERY_TYPE_ID")
	public SurgeryType getSurgeryType() {
		return surgeryType;
	}

	public void setSurgeryType(SurgeryType surgeryType) {
		this.surgeryType = surgeryType;
	}

	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE })
	@JoinTable(name = "NURSES_TO_SKILLS", 
	  joinColumns = @JoinColumn(name = "PERSON_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "SURGERY_SKILL_ID"))
	@OrderBy("id ASC")
	public List<SurgerySkill> getSurgerySkills() {
		return surgerySkills;
	}

	public void setSurgerySkills(List<SurgerySkill> surgerySkills) {
		this.surgerySkills = surgerySkills;
	}

}
