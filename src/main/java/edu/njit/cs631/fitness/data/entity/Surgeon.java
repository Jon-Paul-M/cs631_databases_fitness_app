package edu.njit.cs631.fitness.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SURGEONS")
public class Surgeon extends Personnel {

	public Surgeon() {
		super();
	}

	private Specialty specialty;
	@ManyToOne(optional=true)
	@JoinColumn(name="SPECIALTY_ID")
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	
	// TODO: the requirements don't specify what type of data goes in contract type
	private String contractType;
	@Column(name="CONTRACT_TYPE", nullable=true)
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	
	private Integer contractLength;
	@Column(name="CONTRACT_LENGTH", nullable=true)
	public Integer getContractLength() {
		return contractLength;
	}
	public void setContractLength(Integer contractLength) {
		this.contractLength = contractLength;
	}
}
