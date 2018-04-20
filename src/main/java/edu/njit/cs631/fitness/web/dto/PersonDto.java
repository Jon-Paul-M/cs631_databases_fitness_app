package edu.njit.cs631.fitness.web.dto;

import edu.njit.cs631.fitness.data.entity.Person;
import edu.njit.cs631.fitness.validation.ValidEmail;
import edu.njit.cs631.fitness.validation.ValidSSN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PersonDto {

    @NotNull
    @Size(min = 1)
    private String firstName;

    @NotNull
    @Size(min = 1)
    private String lastName;


    @ValidEmail
    private String email;

    @ValidSSN
    @NotNull
    private String ssn;

    @NotNull
    private Person.Gender gender;

    public PersonDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Person.Gender getGender() {
        return gender;
    }

    public void setGender(Person.Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[UserDto ")
                .append("firstName=").append(firstName)
                .append(", lastName=").append(lastName)
                .append(", gender=").append(gender)
                .append(", ssn=").append(ssn)
                .append(", email=").append(email)
                .append("]");
        return builder.toString();
    }
}
