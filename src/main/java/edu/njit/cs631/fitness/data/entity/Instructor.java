package edu.njit.cs631.fitness.data.entity;

import java.math.BigDecimal;
import java.util.Set;

public interface Instructor {
    Integer getId();
    String getName();
    void setWage(BigDecimal wage);
    void setHours(BigDecimal hours);
    Set<Clazz> getClazzes();

    BigDecimal getWage();
    BigDecimal getHours();


    InstructorTypes getInstructorType();
}
