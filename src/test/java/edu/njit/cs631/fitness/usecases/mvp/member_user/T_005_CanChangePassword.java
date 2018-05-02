package edu.njit.cs631.fitness.usecases.mvp.member_user;

import edu.njit.cs631.fitness.data.entity.Member;
import edu.njit.cs631.fitness.testutils.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class T_005_CanChangePassword extends BaseTest {

    @Autowired
    private PasswordEncoder encoder;


    @Test
    @Sql(scripts = {"classpath:/truncate_all.sql", "classpath:/data-h2.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void memberChangesPassword() throws Exception {
        Member member = userService.findMemberByEmail("member@test.com");
        Assert.assertNotNull(member);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/password")
                        .accept(MediaType.TEXT_HTML)
                        .param("oldPassword", "password")
                        .param("newPassword", "password2")
                        .param("confirmNewPassword", "password2")
                        .with(user(getMemberUser())))
                .andExpect(status().is3xxRedirection());
        member = userService.findMemberByEmail("member@test.com");
        Assert.assertNotNull(member);
        Assert.assertTrue("Password has been changed", encoder.matches("password2", member.getPasswordHash()));
    }
}
