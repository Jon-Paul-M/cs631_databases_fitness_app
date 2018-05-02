package edu.njit.cs631.fitness.service.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import edu.njit.cs631.fitness.data.projection.InstructorPayroll;

public interface PayrollService {

	List<InstructorPayroll> generateHourlyPayroll(LocalDateTime beginning, LocalDateTime ending, BigDecimal federalRate,
			BigDecimal stateRate, BigDecimal otherRate);
}
