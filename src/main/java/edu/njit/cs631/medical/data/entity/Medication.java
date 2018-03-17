package edu.njit.cs631.medical.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="MEDICATIONS")
public class Medication {

	public Medication() {
		super();
	}

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="MEDICATION_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private String code;
	@Column(name="MEDICATION_CODE", nullable=false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	private String name;
	@Column(name="MEDICATION_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private List<Prescription> prescriptions;
	@OneToMany(mappedBy="medication", fetch=FetchType.LAZY)
	public List<Prescription> getPrescriptions() {
		return prescriptions;
	}
	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	private MedicationInventory medicationInventory;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "medication", fetch = FetchType.LAZY)
	public MedicationInventory getMedicationInventory() {
		return medicationInventory;
	}
	public void setMedicationInventory(MedicationInventory medicationInventory) {
		this.medicationInventory = medicationInventory;
	}
}
