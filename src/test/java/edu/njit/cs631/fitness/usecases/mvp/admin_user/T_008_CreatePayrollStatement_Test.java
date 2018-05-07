package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import org.junit.Test;

import edu.njit.cs631.fitness.testutils.BaseTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_008_CreatePayrollStatement_Test extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminRunsPayroll() throws Exception {
        // login
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/payroll/list")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(model().attribute("payrolls", hasSize(greaterThan(0))))
                .andExpect(status().isOk());
    }

}
