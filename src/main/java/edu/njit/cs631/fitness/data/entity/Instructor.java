package edu.njit.cs631.fitness.data.entity;

import java.math.BigDecimal;

public interface Instructor {
    Integer getId();
    String getName();
    void setWage(BigDecimal wage);
    void setHours(BigDecimal hours);

    BigDecimal getWage();
    BigDecimal getHours();


    InstructorTypes getInstructorType();
}
