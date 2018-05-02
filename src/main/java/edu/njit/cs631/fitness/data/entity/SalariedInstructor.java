package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="SALARIED_INSTRUCTOR")
public class SalariedInstructor extends User implements Instructor {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTRUCTOR_ID", referencedColumnName = "USER_ID", updatable = false)
    private Set<Clazz> clazzes = new HashSet<>();
    public Set<Clazz> getClazzes() {
        return clazzes;
    }

    @Column(name="SALARY")
    private BigDecimal salary;
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public void setWage(BigDecimal wage) {
        setSalary(wage);
    }

    @Override
    public void setHours(BigDecimal hours) {// ignore
    }

    @Override
    public BigDecimal getWage() {
        return salary;
    }

    @Override
    public BigDecimal getHours() {
        return BigDecimal.ZERO;
    }

    @Override
    public InstructorTypes getInstructorType() {
        return InstructorTypes.SALARIED;
    }
}
