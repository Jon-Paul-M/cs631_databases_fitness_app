package edu.njit.cs631.fitness.issues;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import edu.njit.cs631.fitness.testutils.BaseTest;

public class T_Issue_0032_Members_Not_Registering_Test extends BaseTest {

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void defaultMemberInMemberRepo() throws Exception {
        Assert.assertNotNull(memberRepository.findByEmail("member@test.com"));

        /*
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin")
                        .accept(MediaType.TEXT_HTML).with(user(getMemberUser())))
                .andExpect(status().isForbidden());
                */
    }

}

