package edu.njit.cs631.fitness.service.api;

import java.util.List;

import edu.njit.cs631.fitness.data.projection.HourlyInstructorPayroll;

public interface PayrollService {
	public List<HourlyInstructorPayroll>  loadHourlyPayroll();
}
