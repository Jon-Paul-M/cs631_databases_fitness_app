package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = HourlyInstructor.TABLE_NAME)
public class HourlyInstructor extends User implements Instructor {

	public static final String TABLE_NAME = "HOURLY_INSTRUCTOR";
	
    @Column(name="HOURS", nullable=false)
    private BigDecimal hours;
    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }
    public BigDecimal getHours() {
        return this.hours;
    }

    @Column(name="HOURLY_WAGE", nullable=false)
    private BigDecimal wage;
    public void setHourlyWage(BigDecimal wage) {
        this.wage = wage;
    }
    public BigDecimal getHourlyWage() {
        return this.wage;
    }
    
	@Override
	public String toString() {
		return "HourlyInstructor [getId()=" + getId() + "]";
	}
    
    
}
