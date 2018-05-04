package edu.njit.cs631.fitness.usecases.mvp.member_user;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Exercise;
import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.Room;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.testutils.BaseTest;

public class T_002_RegisterForAvailableClass_Test extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior
    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private ClazzAdministrationService clazzAdministrationService;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void member_canRegisterForNewClass() {

        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Clazz.TABLE_NAME);
        Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
        Instructor instructor = userService.listAllInstructors().get(0);
        Room room = ((List<Room>) roomRepository.findAll()).get(0);
        LocalDateTime start = LocalDateTime.now().plusHours(3);
        Member member = memberRepository.findAll().get(0);
        Integer duration = 8 * 60;
        Clazz clazz = clazzAdministrationService.createClass(exercise.getId(), instructor.getId(), room.getId(), start, duration);
        clazzRepository.flush();
        Assert.assertNotNull("clazz should not be null", clazz);
        Assert.assertNotNull("clazz.getId() should not be null", clazz.getId());
        Assert.assertEquals("there should be one row in the table " + Clazz.TABLE_NAME, rowCount + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, Clazz.TABLE_NAME));

        Assert.assertTrue("clazz has no members", clazz.getMembers().size() == 0);
        Assert.assertTrue("clazz has no members", !clazz.hasUserRegistered(member));

        User user = userRepository.findByEmail(member.getEmail());

        Assert.assertEquals("Member and user are one and the same id", user.getId(), member.getId());

        System.out.println("Member: " + member.getId());
        System.out.println("Clazz: " + clazz.getId());

        clazzAdministrationService.registerUserForClass(member.getId(), clazz.getId());

        Assert.assertEquals("clazz has 1 member", 1, clazz.getMembers().size());
        Assert.assertTrue("clazz has the member provided to service", clazz.hasUserRegistered(member));

        clazzAdministrationService.deregisterUserForClass(member.getId(), clazz.getId());

        Assert.assertEquals("clazz has 0 members", 0, clazz.getMembers().size());
        Assert.assertFalse("clazz does not have the member provided to service", clazz.hasUserRegistered(member));

    }
}
