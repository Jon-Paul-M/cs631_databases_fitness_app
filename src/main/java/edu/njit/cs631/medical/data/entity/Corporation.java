package edu.njit.cs631.medical.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CORPORATIONS")
public class Corporation {

	public Corporation() {
		super();
	}

	private Integer id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="CORPORATION_ID", nullable=false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	private String name;
	@Column(name="CORPORATION_NAME", nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String headquarters;
	@Column(name="HQ_NAME", nullable=true)
	public String getHeadquarters() {
		return headquarters;
	}
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	
	private Address hqAddress;
	@ManyToOne(optional=true)
	@JoinColumn(name="ADDRESS_ID")
	public Address getHqAddress() {
		return hqAddress;
	}
	public void setHqAddress(Address hqAddress) {
		this.hqAddress = hqAddress;
	}
	
	private BigDecimal ownershipPercentage;
	@Column(name="OWNERSHIP_PERCENTAGE", nullable=true)
	public BigDecimal getOwnershipPercentage() {
		return ownershipPercentage;
	}
	public void setOwnershipPercentage(BigDecimal ownershipPercentage) {
		this.ownershipPercentage = ownershipPercentage;
	}
}
