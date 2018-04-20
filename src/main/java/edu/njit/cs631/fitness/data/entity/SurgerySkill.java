package edu.njit.cs631.fitness.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="SURGERY_SKILLS")
public class SurgerySkill {

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="SURGERY_SKILL_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private String name;
	@Column(name="SKILL_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String skillCode;
	@Column(name="SKILL_CODE", nullable=false)
	public String getSkillCode() {
		return skillCode;
	}
	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}

	private List<Nurse> nurses;
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "surgerySkills")
	public List<Nurse> getNurses() {
		return nurses;
	}
	public void setNurses(List<Nurse> nurses) {
		this.nurses = nurses;
	}
	
	private List<SurgeryType> surgeryTypes;
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "surgerySkills")
	public List<SurgeryType> getSurgeryTypes() {
		return surgeryTypes;
	}
	public void setSurgeryTypes(List<SurgeryType> surgeryTypes) {
		this.surgeryTypes = surgeryTypes;
	}

}
