package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InstructorModel {

    @NotNull
    @Size(min=1)
    private String instructorType;

    @NotNull
    @Size(min=1)
    private String name;

    @ValidEmail
    private String email;

    @NotNull
    @Size(min=1)
    private String wage;

    public String getInstructorType() {
        return instructorType;
    }

    public void setInstructorType(String instructorType) {
        this.instructorType = instructorType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }
}
