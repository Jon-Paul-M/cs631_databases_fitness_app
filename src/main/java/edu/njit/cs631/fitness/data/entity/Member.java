package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="MEMBER")
public class Member extends User {

	public Member() {
		super();
	}

    @Column(name="REGISTRATION_DATE", nullable=true)
	private Timestamp registrationDate;
	public Timestamp getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBERSHIP_ID")
    private Membership membership;
    public Membership getMembership() {
        return membership;
    }
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    @Column(name="ADDRESS1", nullable=true)
    private String address1;
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name="ADDRESS2", nullable=true)
    private String address2;
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name="CITY", nullable=true)
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Column(name="COUNTY", nullable=true)
    private String county;
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name="STATE", nullable=true)
    private String state;
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Column(name="POSTAL_CODE", nullable=true)
    private String postalCode;
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String zip) {
        this.postalCode = zip;
    }

}
