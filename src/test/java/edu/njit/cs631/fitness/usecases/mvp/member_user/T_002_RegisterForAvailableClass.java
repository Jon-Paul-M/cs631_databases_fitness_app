package edu.njit.cs631.fitness.usecases.mvp.member_user;

import edu.njit.cs631.fitness.data.entity.*;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.ExerciseRepository;
import edu.njit.cs631.fitness.data.repository.MemberRepository;
import edu.njit.cs631.fitness.data.repository.RoomRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import org.junit.Assert;
import org.junit.Test;

import edu.njit.cs631.fitness.testutils.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class T_002_RegisterForAvailableClass extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior

    @Autowired
    private ClazzAdministrationService clazzAdministrationService;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void member_canRegisterForNewClass() {
        // DELETE ME LATER

        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, Clazz.TABLE_NAME);
        Exercise exercise = ((List<Exercise>) exerciseRepository.findAll()).get(0);
        Instructor instructor = userService.listAllInstructors().get(0);
        Room room = ((List<Room>) roomRepository.findAll()).get(0);
        LocalDateTime start = LocalDateTime.now().plusHours(3);
        Member member = memberRepository.findAll().get(0);
        Integer duration = 55;
        Clazz clazz = clazzAdministrationService.createClass(exercise.getId(), instructor.getId(), room.getId(), start, duration);
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


    }
}
