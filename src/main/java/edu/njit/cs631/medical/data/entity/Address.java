package edu.njit.cs631.medical.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESSES")
public class Address {

	public Address() {
		super();
	}
	
	private Long id;
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name="ADDRESS_ID", nullable=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	private String address1;
	@Column(name="ADDRESS1", nullable=true)
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	private String address2;
	@Column(name="ADDRESS2", nullable=true)
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	private String city;
	@Column(name="CITY", nullable=true)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	private String county;
	@Column(name="COUNTY", nullable=true)
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	
	private String state;
	@Column(name="STATE", nullable=true)
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	private String postalCode;
	@Column(name="POSTAL_CODE", nullable=true)
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String zip) {
		this.postalCode = zip;
	}

}
