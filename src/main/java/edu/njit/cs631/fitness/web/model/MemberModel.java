package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemberModel {

    @NotNull
    @Size(min = 1)
    private String name;

    @ValidEmail
    private String email;

    @NotNull
    private Integer membership;


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
}
