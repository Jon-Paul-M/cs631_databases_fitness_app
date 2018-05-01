package edu.njit.cs631.fitness.usecases.mvp.unauthenticated_user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.njit.cs631.fitness.testutils.BaseTest;

public class T_004_VisitClassesPage_Test extends BaseTest {

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void anonGetClazzesPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/classes")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

}

