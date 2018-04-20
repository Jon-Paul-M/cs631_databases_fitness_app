package edu.njit.cs631.fitness.data.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="INTERACTIONS")
public class Interaction {

	public enum Severity {
		S, // Severe 
		M, // Moderate 
		L, // Little 
		N  // None
	}
	public Interaction() {
		super();
	}

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="INTERACTION_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private Severity severity;
	@Enumerated(EnumType.STRING)
	@Column(name="SEVERITY", nullable=false)
	public Severity getSeverity() {
		return severity;
	}
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
	private Set<Medication> medications;
	@ManyToMany(cascade = { 
			CascadeType.PERSIST, 
			CascadeType.MERGE },
			fetch=FetchType.LAZY)
	@JoinTable(name = "INTERACTIONS_TO_MEDICATIONS", 
	  joinColumns = @JoinColumn(name = "INTERACTION_ID"), 
	  inverseJoinColumns = @JoinColumn(name = "MEDICATION_ID"))
	public Set<Medication> getMedications() {
		return medications;
	}
	public void setMedications(Set<Medication> medications) {
		this.medications = medications;
	}
	
}
