package edu.njit.cs631.fitness.data.manager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.njit.cs631.fitness.data.projection.InstructorPayroll;


@Service("payrollManager")
public class PayrollManager {

	private static final String GENERATE_PAYROLL_PROCEDURE_NAME = "GENERATE_PAYROLL";
	
    @PersistenceContext
    protected EntityManager entityManager;

	public PayrollManager() {
		super();
	}

	@Transactional
	public List<InstructorPayroll> loadHourlyPayroll(LocalDateTime beginning, LocalDateTime ending, BigDecimal federalRate, BigDecimal stateRate, BigDecimal otherRate) {
		Timestamp beginningTimestamp = Timestamp.valueOf(beginning);
		Timestamp endingTimestamp = Timestamp.valueOf(ending);
		@SuppressWarnings("unchecked")
		List<InstructorPayroll> payrolls = entityManager
				.createStoredProcedureQuery(GENERATE_PAYROLL_PROCEDURE_NAME, InstructorPayroll.class)
				.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR)
				.registerStoredProcedureParameter(2, Timestamp.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Timestamp.class, ParameterMode.IN)
				.registerStoredProcedureParameter(4, BigDecimal.class, ParameterMode.IN)
				.registerStoredProcedureParameter(5, BigDecimal.class, ParameterMode.IN)
				.registerStoredProcedureParameter(6, BigDecimal.class, ParameterMode.IN)
				.setParameter(2, beginningTimestamp)
				.setParameter(3, endingTimestamp)
				.setParameter(4, federalRate)
				.setParameter(5, stateRate)
				.setParameter(6, otherRate)
				.getResultList();
		return payrolls;
	}
	
}
