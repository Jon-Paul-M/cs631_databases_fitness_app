package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.njit.cs631.fitness.data.entity.Instructor;
import edu.njit.cs631.fitness.data.entity.InstructorTypes;
import edu.njit.cs631.fitness.testutils.BaseTest;

public class T_002_CanRegisterNewInstructor_Test extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior
    @Test
    public void testClassesMustHaveAtLeastOneRunnableMethod() {
        // DELETE ME LATER
    }

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminGetInstructorCreationForm() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/instructors/create")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesUser() throws Exception {
        Instructor instructor = userService.findInstructor("buddy@test.com");
        Assert.assertNull(instructor);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/instructors/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("name", "buddy")
                        .param("email", "buddy@test.com")
                        .param("instructorType", InstructorTypes.HOURLY.name())
                        // TODO: Implement error handling / validation on wage
                        .param("wage", "0")
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());
        instructor = userService.findInstructor("buddy@test.com");
        Assert.assertNotNull(instructor);
    }



    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesUser_fails() throws Exception {
        // login
        Instructor instructor = userService.findInstructor("buddy@test.com");
        Assert.assertNull(instructor);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/instructors/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("name", "") // name required
                        .param("email", "buddy") // invalid e-mail
                        .param("instructorType", InstructorTypes.HOURLY.name())
                        // TODO: Implement error handling / validation on wage
                        .param("wage", "0")
                        .with(user(getAdminUser())))
                .andExpect(model().errorCount(2))
                .andExpect(status().isOk());
        instructor = userService.findInstructor("buddy@test.com");
        Assert.assertNull(instructor);
    }
}
