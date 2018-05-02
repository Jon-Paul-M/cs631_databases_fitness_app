package edu.njit.cs631.fitness.data.projection;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

// An unfortunate "feature" of hibernate/jpa:
// If a stored procedure/func returns a result set,
// the objects must be put into entities not DTOs
@Entity
@Immutable
@Table(name="INSTRUCTOR_PAYROLL")
public class InstructorPayroll {

	public static final BigDecimal MINUTES_PER_HOUR = new BigDecimal(60);
	

	public InstructorPayroll() {
		super();
	}
	
	@Id
	@Column(name="ID")
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="NAME")
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="WAGE")
	private BigDecimal wage;
	public BigDecimal getWage() {
		return wage;
	}
	public void setWage(BigDecimal wage) {
		this.wage = wage;
	}
	
	@Column(name="HOURS")
	private BigDecimal hours;
	public BigDecimal getHours() {
		return hours;
	}
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	@Column(name="GROSS")
	private BigDecimal gross;
	public BigDecimal getGross() {
		return gross;
	}
	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}
	
	@Column(name="FED_TAX")
	private BigDecimal federalTax;
	public BigDecimal getFederalTax() {
		return federalTax;
	}
	public void setFederalTax(BigDecimal federalTax) {
		this.federalTax = federalTax;
	}
	
	@Column(name="STATE_TAX")
	private BigDecimal stateTax;
	public BigDecimal getStateTax() {
		return stateTax;
	}
	public void setStateTax(BigDecimal stateTax) {
		this.stateTax = stateTax;
	}

	@Column(name="OTHER_TAX")
	private BigDecimal otherTax;
	public BigDecimal getOtherTax() {
		return otherTax;
	}
	public void setOtherTax(BigDecimal otherTax) {
		this.otherTax = otherTax;
	}
	
	@Column(name="INSTRUCTOR_TYPE")
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InstructorPayroll [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", wage=");
		builder.append(wage);
		builder.append(", hours=");
		builder.append(hours);
		builder.append(", gross=");
		builder.append(gross);
		builder.append(", federalTax=");
		builder.append(federalTax);
		builder.append(", stateTax=");
		builder.append(stateTax);
		builder.append(", otherTax=");
		builder.append(otherTax);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
