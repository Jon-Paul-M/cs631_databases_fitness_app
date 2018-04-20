package edu.njit.cs631.fitness.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SPECIALITIES")
public class Specialty {

	public Specialty() {
		super();
	}

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="SPECIALTY_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	private String name;
	@Column(name="SPECIALTY_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
