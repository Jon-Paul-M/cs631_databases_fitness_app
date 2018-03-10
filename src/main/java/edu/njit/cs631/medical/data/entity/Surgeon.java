package edu.njit.cs631.medical.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SURGEONS")
public class Surgeon extends Personnel {

	public Surgeon() {
		super();
	}

}
