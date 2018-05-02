package edu.njit.cs631.fitness.data.entity;

import edu.njit.cs631.fitness.data.entity.security.User;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = HourlyInstructor.TABLE_NAME)
public class HourlyInstructor extends User implements Instructor {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "HOURLY_INSTRUCTOR";

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTRUCTOR_ID", referencedColumnName = "USER_ID", updatable = false)
    private Set<Clazz> clazzes = new HashSet<>();
    public Set<Clazz> getClazzes() {
        return clazzes;
    }

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
    @Override
    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }
    @Override
    public BigDecimal getWage() {
        return wage;
    }

    @Override
    public InstructorTypes getInstructorType() {
        return InstructorTypes.HOURLY;
    }

    @Transient
    public void setHourlyWage(BigDecimal wage) {
        setWage(wage);
    }
    @Transient
    public BigDecimal getHourlyWage() {
        return getWage();
    }

	@Override
	public String toString() {
		return "HourlyInstructor [getId()=" + getId() + "]";
	}

}
