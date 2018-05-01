package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.njit.cs631.fitness.testutils.BaseTest;

public class T_009_CanVisitAdminPane_Test extends BaseTest {
    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminUserCanVisitAdminPanel() throws Exception {
        // we expect no exceptions thrown during the render
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin")
                        .accept(MediaType.TEXT_HTML)
                .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }
}
