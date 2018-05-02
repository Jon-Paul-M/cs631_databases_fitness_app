package edu.njit.cs631.fitness.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.manager.PayrollManager;
import edu.njit.cs631.fitness.data.projection.HourlyInstructorPayroll;
import edu.njit.cs631.fitness.service.api.PayrollService;

@Service("payrollService")
public class PayrollServiceImpl implements PayrollService {

	public PayrollServiceImpl() {
		super();
	}
	
	@Autowired
	private PayrollManager payrollManager;

	@Override
	public List<HourlyInstructorPayroll> generateHourlyPayroll() {
		return payrollManager.loadHourlyPayroll();
	}

}
