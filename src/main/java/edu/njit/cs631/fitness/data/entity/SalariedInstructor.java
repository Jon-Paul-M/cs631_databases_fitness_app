package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name="SALARIED_INSTRUCTOR")
public class SalariedInstructor extends User implements Instructor {

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
