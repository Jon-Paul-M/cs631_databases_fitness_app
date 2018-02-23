package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PERSONNEL_TYPE")
public class PersonnelType {

	public PersonnelType() {
		super();
	}
	
    private Long id;
    private String name;
    
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="PERSONNEL_TYPE_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="TYPE_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
