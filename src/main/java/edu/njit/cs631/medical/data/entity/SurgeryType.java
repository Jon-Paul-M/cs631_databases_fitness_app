package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	
}
