package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Member;
import org.junit.Assert;
import org.junit.Test;

import edu.njit.cs631.fitness.testutils.BaseTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class T_001_CanRegisterNewMember extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior


    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesUser() throws Exception {
        // login
        loginAs(getAdminUser());
        Integer membershipRepoId = membershipRepository.findAll().get(0).getId();
        mockMvc.perform(
                MockMvcRequestBuilders
                .post("/admin/members/create")
                .accept(MediaType.TEXT_HTML)
                        .param("name", "buddy")
                        .param("email", "buddy@test.com")
                        .param("address1", "add1")
                        .param("address2", "")
                        .param("county", "county")
                        .param("city", "city")
                        .param("state", "state")
                        .param("membership", "" + membershipRepoId)
                        .param("postalCode", "00000"))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection());
        Member member = userService.findMemberByEmail("buddy@test.com");
        Assert.assertNotNull(member);
        logout();
        loginAs("buddy@test.com");


    }
}
