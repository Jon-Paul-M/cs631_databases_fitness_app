package edu.njit.cs631.fitness.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CONSULTATIONS")
public class Consultation {

	public Consultation() {
		super();
	}

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="CONSULTATION_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private Person person;
	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	private Physician physician;
	@ManyToOne
	@JoinColumn(name = "PERSONNEL_ID")
	public Physician getPhysician() {
		return physician;
	}
	public void setPhysician(Physician physician) {
		this.physician = physician;
	}
	
	private Date schedule;
	@Column(name="SCHEDULE", nullable=true)
	public Date getSchedule() {
		return schedule;
	}
	public void setSchedule(Date schedule) {
		this.schedule = schedule;
	}

}
