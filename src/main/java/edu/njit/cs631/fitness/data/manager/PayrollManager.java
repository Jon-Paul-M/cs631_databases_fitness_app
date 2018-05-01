package edu.njit.cs631.fitness.data.manager;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.projection.HourlyInstructorPayroll;

@Service("payrollManager")
public class PayrollManager {

	
    @PersistenceContext
    protected EntityManager entityManager;

	public PayrollManager() {
		super();
	}

	public List<HourlyInstructorPayroll> loadHourlyPayroll() {
		Timestamp beginning = Timestamp.valueOf(LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC));
		Timestamp ending = Timestamp.valueOf(LocalDateTime.now());
		List<HourlyInstructorPayroll> payrolls = entityManager.createQuery(
				HourlyInstructorPayroll.GET_HOURLY_INSTRUCTOR_PAYROLL_JPQL, 
				HourlyInstructorPayroll.class)
				.setParameter("beginning", beginning)
				.setParameter("ending", ending)
				.getResultList();
		return payrolls;
	}
}
