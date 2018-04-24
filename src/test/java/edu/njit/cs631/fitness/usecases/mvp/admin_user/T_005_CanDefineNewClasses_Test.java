package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.HourlyInstructor;
import edu.njit.cs631.fitness.data.repository.ExerciseCrudRepository;
import edu.njit.cs631.fitness.data.repository.HourlyInstructorCrudRepository;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Assert;


@Transactional
@Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class T_005_CanDefineNewClasses_Test extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior
    
	@Autowired
	private ExerciseCrudRepository exerciseCrudRepository;
	
	@Autowired
	private  HourlyInstructorCrudRepository hourlyInstructorCrudRepository;
	
	@Test 
    public void findExercises_ExercisesExist_RepoReturnsExercises() {
    	int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Exercise.TABLE_NAME);
    	Collection<Exercise> exercises = (Collection<Exercise>) exerciseCrudRepository.findAll();
    	Assert.assertNotNull("exercises should never be null", exercises);
    	Assert.assertNotEquals("exercises should not be empty", 0, exercises.size());
    	Assert.assertEquals("number of rows should should equal number of entities", rowCount, exercises.size());
	}

	@Test 
    public void findHourlyInstructor_HourlyInstructorExist_RepoReturnsHourlyInstructor() {
    	int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, HourlyInstructor.TABLE_NAME);
    	Collection<HourlyInstructor> instructors = (Collection<HourlyInstructor>) hourlyInstructorCrudRepository.findAll();
    	Assert.assertNotNull("instructors should never be null", instructors);
    	Assert.assertNotEquals("instructors should not be empty", 0, instructors.size());
    	Assert.assertEquals("number of rows should should equal number of entities", rowCount, instructors.size());
	}
	
	
}
