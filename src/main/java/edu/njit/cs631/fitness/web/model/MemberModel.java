package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemberModel {

    private Integer id;

    @NotNull
    @Size(min = 1)
    private String name;

    @ValidEmail
    private String email;

    @NotNull
    private Integer membership;

    @NotNull
    @Size(min=1)
    private String address1;

    private String address2;

    @NotNull
    @Size(min=1)
    private String city;

    @NotNull
    @Size(min=1)
    private String county;


    @NotNull
    @Size(min=1)
    private String state;

    @NotNull
    @Size(min=1)
    private String postalCode;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[MemberModel ")
                .append("name=").append(name)
                .append("membership=").append(membership)
                .append(", email=").append(email)
                .append("]");
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembership(Integer membership) {
        this.membership = membership;
    }

    public Integer getMembership() {
        return this.membership;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void copyFromMember(Member member) {
        setId(member.getId());

        // member details
        setName(member.getName());
        setEmail(member.getEmail());
        setMembership(member.getMembership().getId());

        // member address
        setAddress1(member.getAddress1());
        setAddress2(member.getAddress2());
        setCity(member.getCity());
        setCounty(member.getCounty());
        setState(member.getState());
        setPostalCode(member.getPostalCode());
    }
}
