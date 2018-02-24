package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="PATIENTS")
public class Patient extends Person {

	public Patient() {
		super();
	}
	
	private List<Illness> illnesses;
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE })
	@JoinTable(name = "PATIENTS_TO_ILLNESSES", 
	  joinColumns = @JoinColumn(name = "PERSON_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "ILLNESS_ID"))
	@OrderBy("id ASC")
	public List<Illness> getIllnesses() {
		return illnesses;
	}
	public void setIllnesses(List<Illness> illnesses) {
		this.illnesses = illnesses;
	}

}
