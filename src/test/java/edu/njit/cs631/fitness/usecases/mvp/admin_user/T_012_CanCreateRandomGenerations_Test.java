package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Clazz;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.data.entity.security.User;
import edu.njit.cs631.fitness.data.repository.ClazzRepository;
import edu.njit.cs631.fitness.service.api.ClazzAdministrationService;
import edu.njit.cs631.fitness.service.api.ClazzService;
import edu.njit.cs631.fitness.service.api.UserService;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_012_CanCreateRandomGenerations_Test extends BaseTest {

    @Autowired
    private ClazzAdministrationService clazzAdministrationService;

    @Autowired
    private ClazzService clazzService;


    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminRandomGenerationRunsOnServiceCalls() throws Exception {
        List<Member> members = memberRepository.findAll();

        Assert.assertEquals("Only one member", 1, members.size());
        userService.generateManyMembers();
        members = memberRepository.findAll();

        Assert.assertEquals("Many members", 21, members.size());

        List<Clazz> clazzes = clazzService.listFutureActiveClasses();

        Assert.assertEquals("No future clazzes", 0, clazzes.size());

        clazzAdministrationService.generateRandomClasses();

        clazzes = clazzService.listFutureActiveClasses();

        Assert.assertTrue("Future clazzes", clazzes.size() > 0);

        Set<Integer> users_registered = new HashSet<>();

        for( Clazz clazz : clazzes) {
            users_registered.addAll(clazz.getMembers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList())
            );
        }

        Assert.assertEquals("No registered members.", 0, users_registered.size());

        clazzAdministrationService.generateRandomRegistrations();

        users_registered.clear();

        for( Clazz clazz : clazzes) {
            users_registered.addAll(clazz.getMembers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList())
            );
        }

        Assert.assertTrue("Many registered members.", 1 < users_registered.size());

    }

    // TODO: shouldn't put multiple tests in the same method
    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminRandomGenerationRunsOnControllerCalls() throws Exception {
        List<Member> members = memberRepository.findAll();

        Assert.assertEquals("Only one member", 1, members.size());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/members/generate")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());
        members = memberRepository.findAll();

        Assert.assertEquals("Many members", 21, members.size());

        List<Clazz> clazzes = clazzService.listFutureActiveClasses();

        Assert.assertEquals("No future clazzes", 0, clazzes.size());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/classes/generate")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());

        clazzes = clazzService.listFutureActiveClasses();

        Assert.assertTrue("Future clazzes", clazzes.size() > 0);

        Set<Integer> users_registered = new HashSet<>();

        for( Clazz clazz : clazzes) {
            users_registered.addAll(clazz.getMembers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList())
            );
        }

        Assert.assertEquals("No registered members.", 0, users_registered.size());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/classes/generateRegistrations")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());

        users_registered.clear();

        for( Clazz clazz : clazzes) {
            users_registered.addAll(clazz.getMembers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList())
            );
        }

        Assert.assertTrue("Many registered members.", 1 < users_registered.size());

    }



}
