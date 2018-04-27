package edu.njit.cs631.fitness.usecases.mvp.member_user;


import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_004_ForbiddenFromAdmin extends BaseTest {

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void unauthenticatedUserForbiddenFromAdmin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin")
                        .accept(MediaType.TEXT_HTML).with(user(getMemberUser())))
                .andExpect(status().isForbidden());
    }

}

