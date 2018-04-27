package edu.njit.cs631.fitness.data.entity;

import java.math.BigDecimal;

public interface Instructor {
    Integer getId();
    String getName();
    void setRate(BigDecimal rate);
    void setHours(BigDecimal hours);
}
