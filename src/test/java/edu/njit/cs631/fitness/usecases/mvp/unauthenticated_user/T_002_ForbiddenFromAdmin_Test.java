package edu.njit.cs631.fitness.usecases.mvp.unauthenticated_user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.njit.cs631.fitness.testutils.BaseTest;


public class T_002_ForbiddenFromAdmin_Test extends BaseTest{

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void unauthenticatedUserForbiddenFromAdmin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection());
    }
}
