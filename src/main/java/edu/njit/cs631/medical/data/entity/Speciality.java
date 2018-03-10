package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SPECIALITIES")
public class Speciality {

	public Speciality() {
		super();
	}

	private Long id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="SPECIALITY_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private String name;
	@Column(name="SPECIALITY_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
