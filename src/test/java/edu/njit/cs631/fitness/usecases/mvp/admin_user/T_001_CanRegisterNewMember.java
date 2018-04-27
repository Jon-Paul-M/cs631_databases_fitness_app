package edu.njit.cs631.fitness.usecases.mvp.admin_user;

import edu.njit.cs631.fitness.data.entity.Member;
import org.junit.Assert;
import org.junit.Test;

import edu.njit.cs631.fitness.testutils.BaseTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class T_001_CanRegisterNewMember extends BaseTest {
    // UnitOfWork_StateUnderTest_ExpectedBehavior

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminGetMemberCreationForm() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/admin/members/create")
                        .accept(MediaType.TEXT_HTML)
                        .with(user(getAdminUser())))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesUser() throws Exception {
        Member member = userService.findMemberByEmail("buddy@test.com");
        Assert.assertNull(member);
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
                        .param("postalCode", "00000")
                        .with(user(getAdminUser())))
                .andExpect(status().is3xxRedirection());
        member = userService.findMemberByEmail("buddy@test.com");
        Assert.assertNotNull(member);
    }



    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-default.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void adminCreatesUser_fails() throws Exception {
        // login
        Member member = userService.findMemberByEmail("buddy@test.com");
        Assert.assertNull(member);
        Integer membershipRepoId = membershipRepository.findAll().get(0).getId();
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/members/create")
                        .accept(MediaType.TEXT_HTML)
                        .param("name", "buddy")
                        .param("email", "buddy") // invalid e-mail
                        .param("address1", "") // required address
                        .param("address2", "")
                        .param("county", "county")
                        .param("city", "city")
                        .param("state", "state")
                        .param("membership", "" + membershipRepoId)
                        .param("postalCode", "00000")
                        .with(user(getAdminUser())))
                .andExpect(model().errorCount(2))
                .andExpect(status().isOk());
    }
}
