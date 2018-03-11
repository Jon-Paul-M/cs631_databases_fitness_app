package edu.njit.cs631.medical.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="MEDICATION_INVENTORIES")
public class MedicationInventory {

	public MedicationInventory() {
		super();
	}

    private Long id;
	@Id
    @Column(name="MEDICATION_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	private Medication medication;
	@JoinColumn(name = "MEDICATION_ID")
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	public Medication getMedication() {
		return medication;
	}
	public void setMedication(Medication medication) {
		this.medication = medication;
	}
	
	private Integer quantityOnHand;
	@Column(name="QUANTITY_ON_HAND", nullable=true)
	public Integer getQuantityOnHand() {
		return quantityOnHand;
	}
	public void setQuantityOnHand(Integer quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}
	
	private Integer quantifyOnOrder;
	@Column(name="QUANTIFY_ON_ORDER", nullable=true)
	public Integer getQuantifyOnOrder() {
		return quantifyOnOrder;
	}
	public void setQuantifyOnOrder(Integer quantifyOnOrder) {
		this.quantifyOnOrder = quantifyOnOrder;
	}
	
	private BigDecimal unitCost;
	@Column(name="UNIT_COST", nullable=true)
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	
	private Long usageYearToDate;
	@Column(name="USAGE_YEAR_TO_DATE", nullable=true)
	public Long getUsageYearToDate() {
		return usageYearToDate;
	}
	public void setUsageYearToDate(Long usageYearToDate) {
		this.usageYearToDate = usageYearToDate;
	}

}
