package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class T_013_CanReviewDetails_Test extends BaseTest {

    @Test
    public void reviewDetail_Instructor_CanViewDetail() throws Exception {
        Instructor instructor = userService.findInstructor("hourlyinstructor@test.com");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/instructors/details?id=" + instructor.getId())
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());

    }

    @Test
    public void reviewDetail_Member_CanViewDetail() throws Exception {
        Member member = userService.findMemberByEmail("member@test.com");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/members/details?id=" + member.getId())
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }
}
