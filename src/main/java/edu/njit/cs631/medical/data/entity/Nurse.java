package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="NURSES")
public class Nurse extends Personnel {

	public Nurse() {
		super();
	}
	
	private SurgeryType surgeryType;
	private List<SurgerySkill> surgerySkills;

}
