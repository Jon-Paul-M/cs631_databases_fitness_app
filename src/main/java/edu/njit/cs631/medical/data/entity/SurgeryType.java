package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="SURGERY_TYPES")
public class SurgeryType {

	private Long id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="SURGERY_TYPE_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	// "A surgery type requires at least one but often many 
	// surgery skills are utilized in numerous surgery types."
	private List<SurgerySkill> surgerySkills;
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE })
	@JoinTable(name = "SURGERY_TYPES_TO_SKILLS", 
	  joinColumns = @JoinColumn(name = "SURGERY_TYPE_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "SURGERY_SKILL_ID"))
	@OrderBy("id ASC")
	public List<SurgerySkill> getSurgerySkills() {
		return surgerySkills;
	}
	public void setSurgerySkills(List<SurgerySkill> surgerySkills) {
		this.surgerySkills = surgerySkills;
	}
	
	private String name;
	@Column(name="TYPE_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	private String surgeryCode;
	@Column(name="SURGERY_CODE", nullable=false)
	public String getSurgeryCode() {
		return surgeryCode;
	}
	public void setSurgeryCode(String surgeryCode) {
		this.surgeryCode = surgeryCode;
	}
}
